package Test;

import model.Epic;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.FileBackedTasksManager;
import service.InMemoryTaskManager;

import java.io.File;
import java.time.Duration;
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

        fileManager.addTask(new Task("Задача", "Для проверки", "NEW",
                LocalDateTime.of(2023, Month.JANUARY,01,12,00), Duration.ofMinutes(30)));
        boolean answer2 = fileManager.getHistoryManager().getHistory().isEmpty();
        Assertions.assertEquals(true, answer2, "Список не пуст");


        fileManager.addEpic(new Epic("Эпик", "Для проверки"));
        fileManager.printEpic(2);
        boolean answer3 = fileManager.getHistoryManager().getHistory().isEmpty();
        Assertions.assertEquals(false, answer3, "Список пуст");

        FileBackedTasksManager fileBackedTasksManager = loadFromFile(file);
        Assertions.assertEquals(fileManager.getSaveList(), fileBackedTasksManager.getSaveList(), "Не создается из файла");
    }

}