import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.FileBackedTasksManager;
import java.io.File;
import java.time.LocalDateTime;
import java.time.Month;

import static service.FileBackedTasksManager.loadFromFile;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    private File file = new File("fileTest.csv");
    @BeforeEach
    public void before() {
        setTaskManager(new FileBackedTasksManager(file));
    }

    @Test
    public void fileManagerTest() {

        FileBackedTasksManager fileManager = new FileBackedTasksManager(file);

        boolean answer1 = fileManager.getTasks().isEmpty();
        Assertions.assertEquals(true, answer1, "Список не пуст");
        boolean answer2 = fileManager.getEpics().isEmpty();
        Assertions.assertEquals(true, answer2, "Список не пуст");
        boolean answer3 = fileManager.getSubTasks().isEmpty();
        Assertions.assertEquals(true, answer3, "Список не пуст");


        fileManager.addTask(new Task("Задача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30));
        fileManager.getTask(1);
        boolean answer4 = fileManager.getTasks().isEmpty();
        Assertions.assertEquals(false, answer4, "Список пуст");


        fileManager.addEpic(new Epic("Эпик", "Для проверки"));
        fileManager.getEpic(2);
        boolean answer5 = fileManager.getEpics().isEmpty();
        Assertions.assertEquals(false, answer5, "Список пуст");


        fileManager.addSubTask(new SubTask("Подзадача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,6,20,00), 30, 2));
        fileManager.getSubTask(3);
        boolean answer6 = fileManager.getSubTasks().isEmpty();
        Assertions.assertEquals(false, answer6, "Список пуст");

        FileBackedTasksManager fileBackedTasksManager = loadFromFile(file);

        Assertions.assertEquals(fileManager.getTasks(),
                fileBackedTasksManager.getTasks(), "Список задач после выгрузки не совпададает");

        Assertions.assertEquals(fileManager.getEpics(),
                fileBackedTasksManager.getEpics(), "Список эпиков после выгрузки не совпададает");

        Assertions.assertEquals(fileManager.getSubTasks(),
                fileBackedTasksManager.getSubTasks(), "Список подзадач после выгрузки не совпададает");

        Assertions.assertEquals(fileManager.getPrioritizedTasks().toString(),
                fileBackedTasksManager.getPrioritizedTasks().toString(), "Отсортированный список не совпадает");

        Assertions.assertEquals(fileManager.getHistory(), fileBackedTasksManager.getHistory(), "Список истории не совпадает");

        Assertions.assertEquals(fileManager.getNextId(),
                fileBackedTasksManager.getNextId(), "Значения последней созданной задачи не совпадает");
    }

}