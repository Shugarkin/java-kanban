import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, SubTask> subTasks = new HashMap<>();
    protected int nextId = 1;

    public int addTask(Task task) { //добавление задач
        task.setId(nextId++);
        tasks.put(task.getId(), task);
        return task.getId();
    }

    public int addEpic(Epic epic) { //добаление эпиков
        epic.setId(nextId++);
        epics.put(epic.getId(), epic);
        epic.setStatus("NEW");
        return epic.getId();
    }

    public int addSubTask(SubTask subTask) { //добавление сабзадач

        subTask.setId(nextId++);
        subTasks.put(subTask.getId(), subTask);
        int epicId = subTask.getEpicId();
        Epic epic = epics.get(epicId);
        epics.get(epicId).addSubTaskId(subTask.getId());
        checkStatus(epicId);
        return subTask.getId();
    }

    public String printAllTask() { // печатает все задачи
        String a = "---------------------------------";
        for (Integer task : tasks.keySet()) {
            System.out.println(printTask(task));
            System.out.println(a);
        }
        return null;
    }

    public String printAllEpic() { // печатает все эпики
        String a = "---------------------------------";

        for (Integer epicx : epics.keySet()) {
            System.out.println(printEpic(epicx));
            System.out.println(a);
        }
        return null;
    }


    public String printAllSubTaskForEpic(int epicId) { //печатает подзадачи определенного эпика
        String a = "---------------------------------";
        System.out.println(printEpic(epicId));
        Epic epic = epics.get(epicId); //определенный эпик
        if (!epic.getSubTaskId().isEmpty()) {
            for (Integer subTaskId : epic.getSubTaskId()) {
                if (epic.getSubTaskId().contains(subTaskId)) {
                    System.out.println(printSubTask(subTaskId));
                    System.out.println(a);
                } else {
                    System.out.println("В данном EPIC нет подзадач.");
                }

            }
        }
        return null;
    }


    public String printAllSubTask() { // печатает все подзадачи
        String a = "---------------------------------";
        for (Integer sub : subTasks.keySet()) {
            System.out.println(printSubTask(sub));
            System.out.println(a);
        }
        return null;
    }


    public String printTask(int taskId) { //метод для печати задач
        Task task = tasks.get(taskId);
        if (tasks.containsKey(taskId)) {
            return "Task{" + " ID задачи = " + task.getId() +// имя класса
                    ", Название задачи ='" + task.getTitle() + '\'' + // поле1=значение1
                    ", Описание задачи ='" + task.getDescription() + '\'' +
                    ", Статус задачи ='" + task.getStatus() + '\'' +// поле2=значение2
                    '}';
        } else {
            return "Возможно вы неверно ввели id или задача удалена";
        }
    }

    public String printEpic(int epicId) { //метод для печати эпиков
        Epic epic = epics.get(epicId);
        if (epics.containsKey(epicId)) {
            return "Epic{" + " ID задачи = " + epic.getId() +// имя класса
                    ", Название задачи ='" + epic.getTitle() + '\'' + // поле1=значение1
                    ", Описание задачи ='" + epic.getDescription() + '\'' +
                    ", Статус задачи ='" + epic.getStatus() + '\'' +// поле2=значение2
                    '}';
        } else {
            return "Возможно вы неверно ввели id или задача удалена";
        }
    }

    public String printSubTask(int subTaskId) { //метод для печати сабзадач
        SubTask subTask = subTasks.get(subTaskId);
        if (subTasks.containsKey(subTaskId)) {
            return "SubTask{" + " ID задачи = " + subTask.getId() +// имя класса
                    ", Название подзадачи ='" + subTask.getTitle() + '\'' + // поле1=значение1
                    ", Описание подзадачи ='" + subTask.getDescription() + '\'' +
                    ", Статус задачи ='" + subTask.getStatus() + '\'' +// поле2=значение2
                    '}';
        } else {
            return "Возможно вы неверно ввели id  или задача удалена";
        }
    }

    public void deleteTask() {//очищает задачи
        tasks.clear();
    }

    public void deleteEpic() {//очищает эпики
        epics.clear();
        subTasks.clear();
    }

    public void deleteSubTask() {//очищает подзадачи
        subTasks.clear();
    }


    public int newTask(int id, Task task) {//заменяет старую задачу новой
        if (tasks.containsKey(id)) {
            task.setId(id);
            tasks.put(task.getId(), task);
            return task.getId();
        } else {
            return 0;
        }
    }

    public int newEpic(int id, Epic epic) {//заменяет старый эпик новым
        if (epics.containsKey(id)) {
            epic.setId(id);
            epics.put(epic.getId(), epic);
            return epic.getId();
        } else {
            return 0;
        }
    }

    public int newSubTask(int id, SubTask subTask) {//заменяет старую подзадачу новой
        if (subTasks.containsKey(id)) {
            subTask.setId(id);
            subTasks.put(subTask.getId(), subTask);
            int epicId = subTask.getEpicId();
            epics.get(epicId).addSubTaskId(subTask.getId());
            return subTask.getId();
        } else {
            return 0;
        }
    }

    public void deleteTaskForId(int id) {//удаляет задачу по id
        tasks.remove(id);
    }

    public void deleteEpicForId(int id) {//удаляет эпик по id
        Epic epic = epics.get(id);
        if (!epic.getSubTaskId().isEmpty()) {
            for (Integer subTaskId : epic.getSubTaskId()) {
                if (!epic.getSubTaskId().contains(subTaskId)) {
                    subTasks.remove(subTaskId);
                }
            }
        }
        epics.remove(id);
    }

    public void deleteSubTaskForId(int id) {//удаляет подзадачу по id
        subTasks.remove(id);
    }


    public String checkStatus(int epicId) {
        ArrayList<String> statusSubTask = new ArrayList<>();
        Epic epic = epics.get(epicId); //определенный эпик
        for (Integer subTaskId : epic.getSubTaskId()) {
            statusSubTask.add(subTasks.get(subTaskId).status);
        }
        if (statusSubTask.contains("NEW") && !statusSubTask.contains("DONE") && !statusSubTask.contains("IN_PROGRESS")) {
            return epic.status = "NEW";
        } else if (!statusSubTask.contains("NEW") && statusSubTask.contains("DONE") && !statusSubTask.contains("IN_PROGRESS")) {
            return epic.status = "DONE";
        } else {
            return epic.status = "IN_PROGRESS";
        }
    }
}
