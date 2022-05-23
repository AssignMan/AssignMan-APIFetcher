package graphtutorial;

import org.bytedream.untis4j.Session;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.time.LocalDate;

public class Timetable {
    public static void main(String[] args) {
        try {
            Session session = Session.login("0154", "projectazure", "https://urania.webuntis.com/WebUntis/?school=htl3r#/basic/main", "htl3r");  // create a new webuntis session

            // get the timetable and print every lesson
            Timetable timetable1 = session.getTimetableFromClassId(LocalDate.now(), LocalDate.now(), session.getInfos().getClassId());
            for (int i = 0; i < timetable1.size(); i++) {
                System.out.println("Lesson " + (i + 1) + ": " + timetable1.get(i).getSubjects().toString());
            }

            // logout
            session.logout();
        } catch (LoginException e) {
            // this exception get thrown if something went wrong with Session.login
            System.out.println("Failed to login: " + e.getMessage());
        } catch (IOException e) {
            // if an error appears this get thrown
            e.printStackTrace();
        }
    }
}
