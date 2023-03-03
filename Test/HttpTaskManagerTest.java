import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.*;
import service.*;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.Month;


class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager>  {
    private URI uri = URI.create("http://localhost:8078");
    private HttpTaskManager taskManager = Managers.getDefault(uri);
    private KVServer server;
    private HttpTaskServer httpTaskServer;

    @BeforeEach
    public  void before() throws IOException {
        server = new KVServer();
        server.start();
        httpTaskServer = new HttpTaskServer(taskManager);
        httpTaskServer.start();
        setTaskManager(taskManager);
    }

    @AfterEach
    public void after(){
        server.stop();
        httpTaskServer.stop();
    }



    @Test
    public void httpTaskManagerTest() throws IOException, InterruptedException {

        boolean answer1 = taskManager.getTasks().isEmpty();
        Assertions.assertEquals(true, answer1, "Список не пуст");
        boolean answer2 = taskManager.getEpics().isEmpty();
        Assertions.assertEquals(true, answer2, "Список не пуст");
        boolean answer3 = taskManager.getSubTasks().isEmpty();
        Assertions.assertEquals(true, answer3, "Список не пуст");

        taskManager.addTask(new Task("Задача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30));
        taskManager.getTask(1);
        boolean answer4 = taskManager.getTasks().isEmpty();
        Assertions.assertEquals(false, answer4, "Список пуст");

        taskManager.addEpic(new Epic("Эпик", "Для проверки"));
        taskManager.addSubTask(new SubTask("Подзадача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,6,20,00), 30, 2));
        taskManager.getSubTask(3);
        boolean answer6 = taskManager.getSubTasks().isEmpty();
        Assertions.assertEquals(false, answer6, "Список пуст");

        HttpTaskManager httpTasksManager = taskManager.load();

        Assertions.assertEquals(taskManager.getTasks(),
                httpTasksManager.getTasks(), "Список задач после выгрузки не совпададает");

        Assertions.assertEquals(taskManager.getEpics(),
                httpTasksManager.getEpics(), "Список эпиков после выгрузки не совпададает");

        Assertions.assertEquals(taskManager.getSubTasks(),
                httpTasksManager.getSubTasks(), "Список подзадач после выгрузки не совпададает");

        Assertions.assertEquals(taskManager.getPrioritizedTasks().toString(),
                httpTasksManager.getPrioritizedTasks().toString(), "Отсортированный список не совпадает");

        Assertions.assertEquals(taskManager.getHistory(), httpTasksManager.getHistory(), "Список истории не совпадает");

        Assertions.assertEquals(taskManager.getNextId(),
                httpTasksManager.getNextId(), "Значения последней созданной задачи не совпадает");
    }
}
