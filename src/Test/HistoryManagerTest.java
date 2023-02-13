package Test;

import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;


class HistoryManagerTest {
    TaskManager taskManager = new InMemoryTaskManager();
    HistoryManager historyManager = new InMemoryHistoryManager();
    @Test
    public void add() {
        boolean answer = historyManager.getHistory().isEmpty();
        Assertions.assertEquals(true, answer, "Список истории не пустой");

        Task task = new Task("Задача", "Для теста", "NEW");
        historyManager.add(task);
        Assertions.assertEquals(task, historyManager.getHistory().get(0), "Задачи нет в списке истории");
    }

    @Test
    public void remoteTest() {
        Task task = new Task("Задача", "Для теста", "NEW",
                LocalDateTime.of(2023, Month.JANUARY,01,12,00), Duration.ofMinutes(30));
        Task task2 = new Task("Задача 2", "Для теста", "NEW",
                LocalDateTime.of(2023, Month.JANUARY,02,12,00), Duration.ofMinutes(30));
        Task task3 = new Task("Задача 3", "Для теста", "NEW",
                LocalDateTime.of(2023, Month.JANUARY,03,12,00), Duration.ofMinutes(30));
        Task task4 = new Task("Задача 4", "Для теста", "NEW",
                LocalDateTime.of(2023, Month.JANUARY,04,12,00), Duration.ofMinutes(30));
        taskManager.addTask(task);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.addTask(task4);
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.add(task4);
        //порядок задач в истории сейчас 1,2,3,4

       historyManager.remove(1);
        //порядок задач в истории сейчас 2,3,4

        Assertions.assertEquals(historyManager.getHistory().get(1), task3, "Втрое место в мапе занимает не 3я задача");

        historyManager.remove(3);
        //порядок задач в истории сейчас 2,4
        Assertions.assertEquals(historyManager.getHistory().get(1), task4, "Не удалилось из середины");

        historyManager.remove(4);
        //порядок задач в истории сейчас 2
        Assertions.assertEquals(historyManager.getHistory().get(0), task2, "Не удалилось с конца");
    }


    @Test
    public void getHistory() {
        boolean answer = historyManager.getHistory().isEmpty();

        Assertions.assertEquals(true, answer, "Список не пуст");

        Task task = new Task("Задача", "Для теста", "NEW");
        taskManager.addTask(task);
        historyManager.add(task);
        historyManager.add(task);
        historyManager.add(task);

        int size = historyManager.getHistory().size();

        Assertions.assertEquals(1, size, "В истории задачи не перезаписываются");

    }
}