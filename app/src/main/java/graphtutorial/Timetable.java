
package graphtutorial;

import org.bytedream.untis4j.Session;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Timetable {
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";
    public static final String RESET = "\033[0m";
    public static void main(String[] args) {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        for (int i = 0; i < 7; i++) {
            LocalDate date = LocalDate.now().plusDays(i-1);
            try {
                Session session = Session.login("0154", "projectazure", "https://urania.webuntis.com", "htl3r");  // create a new webuntis session
                // get the timetable and print every lesson
                org.bytedream.untis4j.responseObjects.Timetable timetable1 = session.getTimetableFromClassId(date, date, session.getInfos().getClassId());
                for (int j = 0; j < timetable1.size(); j++) {
                    System.out.println("Lesson " + (j + 1) + ": " + timetable1.get(j).getSubjects().toString()+" startet um: " + timetable1.get(j).getStartTime().toString() + " endet um: " + timetable1.get(j).getEndTime().toString());
                }
                // logout
                session.logout();
            } catch (Exception e) {
                // if an error appears this get thrown
                System.out.println(BLUE_BOLD_BRIGHT + "Webuntis error with day: " + date + RESET);
            }
        }
        System.out.println(BLUE_BOLD_BRIGHT + "Day of the Week :: " + dayOfWeek + RESET);
    }

}
