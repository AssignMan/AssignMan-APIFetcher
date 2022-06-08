package AssignMan;

import com.microsoft.graph.models.Message;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.MessageCollectionPage;
import okhttp3.Request;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static AssignMan.GraphAuth.authProvider;

public class GraphMail {
    public static void getMails(){
        System.out.println("test");
        GraphServiceClient<Request> graphClient = GraphServiceClient.builder().authenticationProvider( authProvider ).buildClient();
        MessageCollectionPage messages = Objects.requireNonNull(graphClient.me().messages()
                        .buildRequest())
                .select("sender,subject,bodyPreview")
                .get();
        assert messages != null;
        List<Message> allMessages = new LinkedList<>(messages.getCurrentPage());
        for (Message message : allMessages) {
            assert message.sender != null;
            System.out.println(message.bodyPreview);
        }
    }
}
