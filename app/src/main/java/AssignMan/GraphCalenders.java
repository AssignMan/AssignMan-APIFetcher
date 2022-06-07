package AssignMan;

import com.microsoft.graph.models.Event;
import com.microsoft.graph.models.User;
import com.microsoft.graph.options.HeaderOption;
import com.microsoft.graph.options.Option;
import com.microsoft.graph.options.QueryOption;
import com.microsoft.graph.requests.EventCollectionPage;
import com.microsoft.graph.requests.EventCollectionRequestBuilder;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import static AssignMan.GraphAuth.graphClient;

public class GraphCalenders {
    public static User getUser() {
        if (graphClient == null) throw new NullPointerException(
                "Graph client has not been initialized. Call initializeGraphAuth before calling this method");

        // GET /me to get authenticated user
        User me = graphClient
                .me()
                .buildRequest()
                .select("displayName,mailboxSettings")
                .get();

        return me;
    }
    public static List<Event> getCalendarView(
            ZonedDateTime viewStart, ZonedDateTime viewEnd, String timeZone) {
        if (graphClient == null) throw new NullPointerException(
                "Graph client has not been initialized. Call initializeGraphAuth before calling this method");

        List<Option> options = new LinkedList<Option>();
        options.add(new QueryOption("startDateTime", viewStart.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)));
        options.add(new QueryOption("endDateTime", viewEnd.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)));
        // Sort results by start time
        options.add(new QueryOption("$orderby", "start/dateTime"));

        // Start and end times adjusted to user's time zone
        options.add(new HeaderOption("Prefer", "outlook.timezone=\"" + timeZone + "\""));

        // GET /me/events
        EventCollectionPage eventPage = graphClient
                .me()
                .calendarView()
                .buildRequest(options)
                .select("subject,organizer,start,end")
                .top(10)
                .get();

        List<Event> allEvents = new LinkedList<Event>();

        // Create a separate list of options for the paging requests
        // paging request should not include the query parameters from the initial
        // request, but should include the headers.
        List<Option> pagingOptions = new LinkedList<Option>();
        pagingOptions.add(new HeaderOption("Prefer", "outlook.timezone=\"" + timeZone + "\""));

        while (eventPage != null) {
            allEvents.addAll(eventPage.getCurrentPage());

            EventCollectionRequestBuilder nextPage =
                    eventPage.getNextPage();

            if (nextPage == null) {
                break;
            } else {
                eventPage = nextPage
                        .buildRequest(pagingOptions)
                        .get();
            }
        }

        return allEvents;
    }
}
