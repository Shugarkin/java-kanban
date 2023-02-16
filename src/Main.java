import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import service.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        taskManager.addTask(new Task("Наладить личную жизнь", "Tinder в помощь", Status.IN_PROGRESS,
        LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30));

        Epic epic = new Epic("Прогулка", "Прогулка по парку");
        taskManager.addEpic(epic);

        taskManager.addSubTask(new SubTask("Одеться", "Как без одежды то", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY, 2, 12, 10), 30, 2));
        taskManager.addSubTask(new SubTask("Выйти на улицу", "Дома же не погуляешь", Status.DONE,
                LocalDateTime.of(2022, Month.JANUARY, 10, 12, 15), 60, 2));
        taskManager.addSubTask(new SubTask("Я все таки смог", "Решить этот спринт", Status.DONE,
                LocalDateTime.of(2023, Month.FEBRUARY, 10, 00, 00), 60,2));

        taskManager.addEpic(new Epic("Приборка дома", "Как бы грязно уже"));

        /*
        // удаление все задач
        taskManager.deleteSubTask();
        taskManager.deleteTask();
        taskManager.deleteEpic();
        //вывод всех задач
        taskManager.printAllTask();
        taskManager.printAllEpic();
        taskManager.printAllSubTask();
        //вывод определенной задачи
        System.out.println(taskManager.printEpic(3));
        System.out.println(taskManager.printTask(1));
        System.out.println(taskManager.printSubTask(4));
        //изменение по id
        System.out.println(taskManager.printTask(1));
        model.Task task3 = new model.Task("Заменить поиск девушки", "Учеба важнее", "IN_PROGRESS");
        taskManager.newTask(1, task3);
        System.out.println(taskManager.printTask(1));
        System.out.println(taskManager.printEpic(3));
        model.Epic epic4 = new model.Epic("Учеба важнее", "Потом погуляешь");
        taskManager.newEpic(3, epic4);
        System.out.println(taskManager.printEpic(3));
        System.out.println(taskManager.printSubTask(4));
        model.SubTask subTask7 = new model.SubTask("Потом полы помоешь", "Садись и учись", "NEW", 2);
        taskManager.newSubTask(4, subTask7);
        System.out.println(taskManager.printSubTask(4));
        //удаление по id
        System.out.println(taskManager.printTask(1));
        taskManager.deleteTaskForId(1);
        System.out.println(taskManager.printTask(1));
        System.out.println(taskManager.printEpic(3));
        taskManager.deleteEpicForId(3);
        System.out.println(taskManager.printEpic(3));
        System.out.println(taskManager.printSubTask(4));
        taskManager.deleteSubTaskForId(4);
        System.out.println(taskManager.printSubTask(4));
        //вывод всех задач эпика
        taskManager.printAllSubTaskForEpic(3);
        //распечатка истории задач
        System.out.println(taskManager.getHistoryManager().getHistory().toString());
        //распечатать задачи по приоритету
        System.out.println(taskManager.getPrioritizedTasks());
        */

    }

}