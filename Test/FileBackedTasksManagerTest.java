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
    @BeforeEach
    public void before() {
        File file = new File("fileTest.csv");
        setTaskManager(new FileBackedTasksManager(file));
    }

    @Test
    public void fileManagerTest() {
        File file = new File("fileTest.csv");
        FileBackedTasksManager fileManager = new FileBackedTasksManager(file);
        boolean answer = fileManager.getSaveList().isEmpty();
        Assertions.assertEquals(true, answer, "Список не пуст");

        fileManager.addTask(new Task("Задача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30));
        boolean answer2 = fileManager.getHistory().isEmpty();
        Assertions.assertEquals(true, answer2, "Список не пуст");

        fileManager.addEpic(new Epic("Эпик", "Для проверки"));
        fileManager.printEpic(2);

        fileManager.addSubTask(new SubTask("Подзадача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,6,20,00), 30, 2));
        boolean answer3 = fileManager.getHistory().isEmpty();
        Assertions.assertEquals(false, answer3, "Список пуст");

        FileBackedTasksManager fileBackedTasksManager = loadFromFile(file);
        Assertions.assertEquals(fileManager.getSaveList(), fileBackedTasksManager.getSaveList(), "Не создается из файла");

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
        System.out.println();
    }

}