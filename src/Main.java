import server.HttpTaskServer;
import server.KVServer;
import service.*;

import java.io.IOException;
import java.net.URI;

public class Main {

    public static void main(String[] args) throws IOException {
        new KVServer().start();

        HttpTaskManager taskManager = Managers.getDefault(URI.create("http://localhost:8078"));
        new HttpTaskServer(taskManager).start();

    }

}