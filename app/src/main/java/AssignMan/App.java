package AssignMan;

import com.microsoft.graph.models.User;

import java.util.*;
import java.io.IOException;

import java.util.List;


import static AssignMan.GraphTeams.getTeamsMessages;
import static AssignMan.GraphTolana.listCalendarEvents;
import static AssignMan.GraphMail.getMails;
import static AssignMan.Timetable.getTimetable;

public class App {
    public static void main(String[] args) {
        System.out.println();

        // Load OAuth settings
        final Properties oAuthProperties = new Properties();
        try {
            oAuthProperties.load(App.class.getResourceAsStream("oAuth.properties"));
        } catch (IOException e) {
            System.out.println("Unable to read OAuth configuration. Make sure you have a properly formatted oAuth.properties file. See README for details.");
            return;
        }

        final String appId = oAuthProperties.getProperty("app.id");
        final List<String> appScopes = Arrays
                .asList(oAuthProperties.getProperty("app.scopes").split(","));

        // Initialize Graph with auth settings
        GraphAuth.initializeGraphAuth(appId, appScopes);
        final String accessToken = GraphAuth.getUserAccessToken();

        // Greet the user
        User user = GraphCalenders.getUser();
        System.out.println("Welcome " + user.displayName);
        System.out.println("Time zone: " + user.mailboxSettings.timeZone);
        System.out.println();

        Scanner input = new Scanner(System.in);

        int choice = 1;

        while (choice != 0) {
            System.out.println("Please choose one of the following options:");
            System.out.println("0. Exit");
            System.out.println("1. Display access token");
            System.out.println("2. View this week's calendar");
            System.out.println("3. Get Mails");
            System.out.println("4. Get Teams");
            System.out.println("5. Get Timetable");

            try {
                choice = input.nextInt();
            } catch (InputMismatchException ex) {
                // Skip over non-integer input
            }

            input.nextLine();

            // Process user choice
            switch (choice) {
                case 0 -> System.out.println("Goodbye...");
                case 1 -> System.out.println("Access token: " + accessToken);
                case 2 -> listCalendarEvents(user.mailboxSettings.timeZone);
                case 3 -> getMails();
                case 4 -> getTeamsMessages();
                case 5 -> getTimetable();
                default -> System.out.println("Invalid choice");
            }
        }
        input.close();
    }
}