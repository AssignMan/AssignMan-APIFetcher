package AssignMan;

import com.microsoft.graph.models.Event;
import com.microsoft.graph.models.Message;
import com.microsoft.graph.requests.EventCollectionRequestBuilder;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.MessageCollectionPage;

import java.util.LinkedList;
import java.util.List;

import static AssignMan.GraphAuth.authProvider;

public class GraphMail {
    public static void getMails(){
        System.out.println("test");
        GraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider( authProvider ).buildClient();
        MessageCollectionPage messages = graphClient.me().messages()
                .buildRequest()
                .select("sender,subject")
                .get();
        List<Message> allMessages = new LinkedList<>();
        allMessages.addAll(messages.getCurrentPage());
        for (Message message : allMessages) {
            assert message.sender != null;
            System.out.println(message.toString());
        }
    }
}
