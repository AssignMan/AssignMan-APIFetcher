package AssignMan;

import org.bytedream.untis4j.Session;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Scanner;

public class Timetable {
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";
    public static final String RESET = "\033[0m";
    public static void getTimetable() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your untis username:");
        String username = input.nextLine();
        System.out.println("Enter your untis password:");
        String password = input.nextLine();
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        for (int i = 0; i < 7; i++) {
            LocalDate date = LocalDate.now().plusDays(i);
            //0154 projectazure
            try {
                Session session = Session.login(username, password, "https://urania.webuntis.com", "htl3r");  // create a new webuntis session
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
