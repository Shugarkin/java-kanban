import model.Epic;
import model.Status;
import model.SubTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.InMemoryTaskManager;
import service.TaskManager;

import java.time.LocalDateTime;
import java.time.Month;

class EpicStatusTest {
    TaskManager taskManager = new InMemoryTaskManager();
        @Test
        public void emptyListOfSubtasks() {
            Epic epic = new Epic("Эпик", "Для проверки");
            taskManager.addEpic(epic);
            Assertions.assertEquals(Status.NEW, epic.getStatus(), "Неверно определяет статус Epic при создании без подзадач");
        }

        @Test
        public void allSubtasksWithStatusNew() {
            Epic epic = new Epic("Эпик", "Для проверки");
            taskManager.addEpic(epic);
            taskManager.addSubTask(new SubTask("Подзадача 1", "Для теста", Status.NEW,
                    LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30, 1));
            taskManager.addSubTask(new SubTask("Подзадача 2", "Для теста", Status.NEW,
                    LocalDateTime.of(2023, Month.JANUARY,03,12,00), 30, 1));
            Assertions.assertEquals(Status.NEW, epic.getStatus(), "Неверно определяет статус Epic при создании когда подзадачи с статусом NEW");
        }

        @Test
        public void allSubtasksWithStatusDone() {
            Epic epic = new Epic("Эпик", "Для проверки");
            taskManager.addEpic(epic);
            taskManager.addSubTask(new SubTask("Подзадача 1", "Для теста", Status.DONE,
                    LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30, 1));
            taskManager.addSubTask(new SubTask("Подзадача 2", "Для теста", Status.DONE,
                    LocalDateTime.of(2023, Month.JANUARY,03,12,00), 30, 1));
            Assertions.assertEquals(Status.DONE, epic.getStatus(), "Неверно определяет статус Epic при создании когда подзадачи с статусом DONE");
        }

        @Test
        public void allSubtasksWithStatusDifferent() {
        Epic epic = new Epic("Эпик", "Для проверки");
            taskManager.addEpic(epic);
            taskManager.addSubTask(new SubTask("Подзадача 1", "Для теста", Status.NEW,
                    LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30,1));
            taskManager.addSubTask(new SubTask("Подзадача 2", "Для теста", Status.DONE,
                    LocalDateTime.of(2023, Month.JANUARY,03,12,00), 30,1));
            Assertions.assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Неверно определяет статус Epic при создании когда подзадачи с разным статусом ");
        }

        @Test
        public void allSubtasksWithStatusInProgress(){
            Epic epic = new Epic("Эпик", "Для проверки");
            taskManager.addEpic(epic);
            taskManager.addSubTask(new SubTask("Подзадача 1", "Для теста", Status.IN_PROGRESS,
                    LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30,1));
            taskManager.addSubTask(new SubTask("Подзадача 2", "Для теста", Status.IN_PROGRESS,
                    LocalDateTime.of(2023, Month.JANUARY,02,12,00), 30,1));
            Assertions.assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Неверно определяет статус Epic при создании когда подзадачи с статусом IN PROGRESS");
        }
}