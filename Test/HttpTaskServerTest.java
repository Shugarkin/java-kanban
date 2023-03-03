import com.google.gson.Gson;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.HttpTaskManager;
import service.HttpTaskServer;
import service.KVServer;
import service.Managers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.Month;

public class HttpTaskServerTest {
    private URI uri = URI.create("http://localhost:8078");
    private HttpTaskManager taskManager = Managers.getDefault(uri);
    private KVServer server;
    private HttpTaskServer httpTaskServer;
    private HttpClient client;
    private Gson gson = Managers.getGson();

    @BeforeEach
    public  void before() throws IOException {
        server = new KVServer();
        server.start();
        httpTaskServer = new HttpTaskServer(taskManager);
        httpTaskServer.start();
        client = HttpClient.newHttpClient();
    }

    @AfterEach
    public void after(){
        server.stop();
        httpTaskServer.stop();
    }


    @Test
    public void getTaskForIdTest() throws IOException, InterruptedException {
        taskManager.addTask(new Task("Задача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30));


        URI urlTask = URI.create("http://localhost:8080/tasks/task/?id=1");

        String answerTask = gson.toJson(taskManager.getTask(1));
        HttpRequest requestTask = HttpRequest.newBuilder().uri(urlTask).GET().build();
        HttpResponse<String> responseTask = client.send(requestTask, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(responseTask.statusCode(), 200);

        String responseTaskString = responseTask.body();
        Assertions.assertEquals(responseTaskString, answerTask);
    }

    @Test
    public void getEpicAndSubtaskForIdTest() throws IOException, InterruptedException {
        taskManager.addEpic(new Epic("Эпик", "Для проверки"));

        taskManager.addSubTask(new SubTask("Подзадача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,6,20,00), 30, 1));


        URI urlEpic = URI.create("http://localhost:8080/tasks/epic/?id=1");
        URI urlSubtask = URI.create("http://localhost:8080/tasks/subtask/?id=2");

        String answerEpic = gson.toJson(taskManager.getEpic(1));
        HttpRequest requestEpic = HttpRequest.newBuilder().uri(urlEpic).GET().build();
        HttpResponse<String> responseEpic = client.send(requestEpic, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(responseEpic.statusCode(), 200);

        String responseEpicString = responseEpic.body();
        Assertions.assertEquals(responseEpicString, answerEpic);

        String answerSubtask = gson.toJson(taskManager.getSubTask(2));
        HttpRequest requestSubtask = HttpRequest.newBuilder().uri(urlSubtask).GET().build();
        HttpResponse<String> responseSubtask = client.send(requestSubtask, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(responseSubtask.statusCode(), 200);

        String responseSubtaskString = responseSubtask.body();
        Assertions.assertEquals(responseSubtaskString, answerSubtask);
    }

    @Test
    public void getTask() throws IOException, InterruptedException {
        taskManager.addTask(new Task("Задача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30));

        URI urlTask = URI.create("http://localhost:8080/tasks/task/");

        String answerTask = gson.toJson(taskManager.getTasks());
        HttpRequest requestTask = HttpRequest.newBuilder().uri(urlTask).GET().build();
        HttpResponse<String> responseTask = client.send(requestTask, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(responseTask.statusCode(), 200);

        String responseTaskString = responseTask.body();
        Assertions.assertEquals(responseTaskString, answerTask);
    }

    @Test
    public void getEpicAndSubtask() throws IOException, InterruptedException {
        taskManager.addEpic(new Epic("Эпик", "Для проверки"));

        taskManager.addSubTask(new SubTask("Подзадача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,6,20,00), 30, 1));


        URI urlEpic = URI.create("http://localhost:8080/tasks/epic/");
        URI urlSubtask = URI.create("http://localhost:8080/tasks/subtask/");

        String answerEpic = gson.toJson(taskManager.getEpics());
        HttpRequest requestEpic = HttpRequest.newBuilder().uri(urlEpic).GET().build();
        HttpResponse<String> responseEpic = client.send(requestEpic, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(responseEpic.statusCode(), 200);

        String responseEpicString = responseEpic.body();
        Assertions.assertEquals(responseEpicString, answerEpic);

        String answerSubtask = gson.toJson(taskManager.getSubTasks());
        HttpRequest requestSubtask = HttpRequest.newBuilder().uri(urlSubtask).GET().build();
        HttpResponse<String> responseSubtask = client.send(requestSubtask, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(responseSubtask.statusCode(), 200);

        String responseSubtaskString = responseSubtask.body();
        Assertions.assertEquals(responseSubtaskString, answerSubtask);
    }

    @Test
    public void postTask() throws IOException, InterruptedException {
        Task task = new Task("Задача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30);
        URI urlTask = URI.create("http://localhost:8080/tasks/task/");
        String json = gson.toJson(task);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(urlTask).POST(body).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(response.statusCode(), 200);

        Assertions.assertNotNull(response.body());
    }

    @Test
    public void postEpicAndSubtask() throws IOException, InterruptedException {
        Epic epic = new Epic("Эпик", "Для проверки");
        SubTask subTask = new SubTask("Подзадача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,6,20,00), 30, 1);

        URI urlEpic = URI.create("http://localhost:8080/tasks/epic/");
        URI urlSubtask = URI.create("http://localhost:8080/tasks/subtask/");

        Gson gsonEpic = new Gson();

        String jsonEpic = gsonEpic.toJson(epic);
        final HttpRequest.BodyPublisher bodyEpic = HttpRequest.BodyPublishers.ofString(jsonEpic);
        HttpRequest requestEpic = HttpRequest.newBuilder().uri(urlEpic).POST(bodyEpic).build();
        HttpResponse<String> responseEpic = client.send(requestEpic, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(responseEpic.statusCode(), 200);

        Assertions.assertNotNull(responseEpic.body());

        String jsonSubtask = gson.toJson(subTask);
        final HttpRequest.BodyPublisher bodySubtask = HttpRequest.BodyPublishers.ofString(jsonSubtask);
        HttpRequest requestSubtask = HttpRequest.newBuilder().uri(urlSubtask).POST(bodySubtask).build();
        HttpResponse<String> responseSubtask = client.send(requestSubtask, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(responseSubtask.statusCode(), 200);

        Assertions.assertNotNull(responseSubtask.body());
    }

    @Test
    public void deleteTask() throws IOException, InterruptedException {
        taskManager.addTask(new Task("Задача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30));

        URI urlTask = URI.create("http://localhost:8080/tasks/task/");


        HttpRequest requestTask = HttpRequest.newBuilder().uri(urlTask).DELETE().build();
        HttpResponse<String> responseTask = client.send(requestTask, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(responseTask.statusCode(), 200);

        String responseTaskString = responseTask.body();
        Assertions.assertEquals(responseTaskString, "");

        taskManager.addTask(new Task("Задача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30));

        URI urlTaskId = URI.create("http://localhost:8080/tasks/task/?id=2");

        HttpRequest requestTaskId = HttpRequest.newBuilder().uri(urlTaskId).DELETE().build();
        HttpResponse<String> responseTaskId = client.send(requestTaskId, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(responseTaskId.statusCode(), 200);

        String responseTaskStringId = responseTaskId.body();
        Assertions.assertEquals(responseTaskStringId, "");
    }

    @Test
    public void deleteEpicAndSubtask() throws IOException, InterruptedException {
        taskManager.addEpic(new Epic("Эпик", "Для проверки"));

        taskManager.addSubTask(new SubTask("Подзадача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,6,20,00), 30, 1));


        URI urlEpic = URI.create("http://localhost:8080/tasks/epic/");
        URI urlSubtask = URI.create("http://localhost:8080/tasks/subtask/");

        HttpRequest requestEpic = HttpRequest.newBuilder().uri(urlEpic).DELETE().build();
        HttpResponse<String> responseEpic = client.send(requestEpic, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(responseEpic.statusCode(), 200);

        String responseEpicString = responseEpic.body();
        Assertions.assertEquals(responseEpicString, "");

        HttpRequest requestSubtask = HttpRequest.newBuilder().uri(urlSubtask).DELETE().build();
        HttpResponse<String> responseSubtask = client.send(requestSubtask, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(responseSubtask.statusCode(), 200);

        String responseSubtaskString = responseSubtask.body();
        Assertions.assertEquals(responseSubtaskString, "");

        taskManager.addEpic(new Epic("Эпик", "Для проверки"));

        taskManager.addSubTask(new SubTask("Подзадача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,6,20,00), 30, 3));

        URI urlEpicId = URI.create("http://localhost:8080/tasks/epic/");
        URI urlSubtaskId = URI.create("http://localhost:8080/tasks/subtask/");

        HttpRequest requestEpicId = HttpRequest.newBuilder().uri(urlEpicId).DELETE().build();
        HttpResponse<String> responseEpicId = client.send(requestEpicId, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(responseEpicId.statusCode(), 200);

        String responseEpicStringId = responseEpicId.body();
        Assertions.assertEquals(responseEpicStringId, "");

        HttpRequest requestSubtaskId = HttpRequest.newBuilder().uri(urlSubtaskId).DELETE().build();
        HttpResponse<String> responseSubtaskId = client.send(requestSubtaskId, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(responseSubtaskId.statusCode(), 200);

        String responseSubtaskStringId = responseSubtaskId.body();
        Assertions.assertEquals(responseSubtaskStringId, "");
    }
}
