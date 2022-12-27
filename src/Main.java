import model.Epic;
import model.SubTask;
import model.Task;
import service.*;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        taskManager.addTask(new Task("Наладить личную жизнь", "Tinder в помощь", "IN_PROGRESS"));

        taskManager.addTask(new Task("Забыть бывшую", "Макс Корж ты где?", "IN_PROGRESS"));

        taskManager.addEpic(new Epic("Прогулка", "Прогулка по парку"));

        taskManager.addSubTask(new SubTask("Одеться", "Как без одежды то", "NEW", 3));

        taskManager.addSubTask(new SubTask("Выйти на улицу", "Дома же не погуляешь", "DONE", 3));

        taskManager.addEpic(new Epic("Приборка дома", "Как бы грязно уже"));

        taskManager.addSubTask(new SubTask("Приборка", "Залей все водой как обычно", "IN_PROGRESS", 6));

        //taskManager.printAllTask();
        //taskManager.printAllEpic();
        //taskManager.printAllSubTask();

        // методы для просмотра задач по айди
        System.out.println(taskManager.printEpic(3));
        System.out.println(taskManager.printTask(1));
        System.out.println(taskManager.printSubTask(4));
        System.out.println(taskManager.printEpic(3));
        System.out.println(taskManager.printTask(1));
        System.out.println(taskManager.printSubTask(4));
        System.out.println(taskManager.printEpic(3));
        System.out.println(taskManager.printTask(1));
        System.out.println(taskManager.printSubTask(4));
        System.out.println(taskManager.printEpic(3));
        System.out.println(taskManager.printTask(1));
        System.out.println(taskManager.printSubTask(4));
        System.out.println(taskManager.printEpic(3));
        System.out.println(taskManager.printTask(1));
        System.out.println(taskManager.printSubTask(4));

        //метод для просмотра последних 10 задач
        System.out.println(taskManager.getHistoryManager().getHistory().toString());




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
        */

    }

}

