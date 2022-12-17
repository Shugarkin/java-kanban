
public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        Task task1 = new Task("Наладить личную жизнь", "Tinder в помощь", "IN_PROGRESS");
        manager.addTask(task1);

        Task task2 = new Task("Забыть бывшую", "Макс Корж ты где?", "IN_PROGRESS");
        manager.addTask(task2);

        Epic epic1 = new Epic("Прогулка", "Прогулка по парку");
        manager.addEpic(epic1);
        SubTask subTask1 = new SubTask("Одеться", "Как без одежды то", "NEW", 3);
        manager.addSubTask(subTask1);
        SubTask subTask2 = new SubTask("Выйти на улицу", "Дома же не погуляешь", "DONE", 3);
        manager.addSubTask(subTask2);


        Epic epic2 = new Epic("Приборка дома", "Как бы грязно уже");
        manager.addEpic(epic2);
        SubTask subTask4 = new SubTask("Проборка", "Залей все водой как обычно", "IN_PROGRESS", 6);
        manager.addSubTask(subTask4);

        manager.printAllTask();
        manager.printAllEpic();
        manager.printAllSubTask();

        /*
        // удаление все задач
        manager.deleteSubTask();
        manager.deleteTask();
        manager.deleteEpic();

        //вывод всех задач
        manager.printAllTask();
        manager.printAllEpic();
        manager.printAllSubTask();

        //вывод определенной задачи
        System.out.println(manager.printEpic(3));
        System.out.println(manager.printTask(1));
        System.out.println(manager.printSubTask(4));

        //изменение по id
        System.out.println(manager.printTask(1));
        Task task3 = new Task("Заменить поиск девушки", "Учеба важнее", "IN_PROGRESS");
        manager.newTask(1, task3);
        System.out.println(manager.printTask(1));

        System.out.println(manager.printEpic(3));
        Epic epic4 = new Epic("Учеба важнее", "Потом погуляешь");
        manager.newEpic(3, epic4);
        System.out.println(manager.printEpic(3));

        System.out.println(manager.printSubTask(4));
        SubTask subTask7 = new SubTask("Потом полы помоешь", "Садись и учись", "NEW", 2);
        manager.newSubTask(4, subTask7);
        System.out.println(manager.printSubTask(4));

        //удаление по id
        System.out.println(manager.printTask(1));
        manager.deleteTaskForId(1);
        System.out.println(manager.printTask(1));

        System.out.println(manager.printEpic(3));
        manager.deleteEpicForId(3);
        System.out.println(manager.printEpic(3));

        System.out.println(manager.printSubTask(4));
        manager.deleteSubTaskForId(4);
        System.out.println(manager.printSubTask(4));

        //вывод всех задач эпика
        manager.printAllSubTaskForEpic(3);
        */

    }
}

