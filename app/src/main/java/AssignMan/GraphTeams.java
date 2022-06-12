package AssignMan;

import com.microsoft.graph.requests.ChatMessageCollectionPage;

import static AssignMan.GraphAuth.graphClient;

public class GraphTeams {
    public static void getTeamsMessages(){
        //ChatMessageCollectionPage messages = graphClient.teams("{48cd7712-e433-4629-a0a2-b1124a5c0708}").channels("{19}").messages()
        //        .buildRequest()
        //        .get();
        System.out.println("teams test");
    }
}
