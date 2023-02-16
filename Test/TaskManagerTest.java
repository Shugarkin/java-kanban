import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import service.TaskManager;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager>  {
    private T taskManager;

    public void setTaskManager(T taskManager) {
        this.taskManager = taskManager;
    }
    private Executable generateExecutableForEmptyMapTask() {//метод для создания ошибки с пустым списком таска
        return () -> taskManager.getTasks();
    }

    private Executable generateExecutableForEmptyMapEpic() {//метод для создания ошибки с пустым списком эпика
        return () -> taskManager.getEpics();
    }
    private Executable generateExecutableForEmptyMapSubtask() {//метод для создания ошибки с пустым списком сабтаска
        return () -> taskManager.getSubTasks();
    }

    private Executable generateExecutablePrintAllTaskTest() {
        return () -> taskManager.printAllTask();
    }

    private Executable generateExecutablePrintAllEpicTest() {
        return () -> taskManager.printAllEpic();
    }

    private Executable generateExecutablePrintAllSubtaskTest() {
        return () -> taskManager.printAllSubTask();
    }

    private Executable generateExecutablePrintTask(Integer id) {
        return () -> taskManager.printTask(id);
    }

    private Executable generateExecutablePrintEpic(Integer id) {
        return () -> taskManager.printEpic(id);
    }

    private Executable generateExecutablePrintSubTask(Integer id) {
        return () -> taskManager.printSubTask(id);
    }

    private Executable generateExecutableDeleteForIdTask(Integer id) {
        return () -> taskManager.deleteTaskForId(id);
    }

    private Executable generateExecutableDeleteForIdEpic(Integer id) {
        return () -> taskManager.deleteEpicForId(id);
    }

    private Executable generateExecutableDeleteForIdSubtask(Integer id) {
        return () -> taskManager.deleteSubTaskForId(id);
    }



    @Test
    void addTaskTest() { //тест для проверки метода создания таска

        Task task = new Task("Задача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30);
        taskManager.addTask(task);
        final int taskId = task.getId();

        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");


        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");

        //проверка на пустой список
        taskManager.deleteTask();
        NullPointerException e = Assertions.assertThrows(
                NullPointerException.class,
                generateExecutableForEmptyMapTask()
        );
        assertEquals(e.getMessage(), "Список задач пуст");
    }

    @Test
    void addEpicTest() { //тест для проверки метода создания эпика (тесты с проверкой статусов в отдельном классе)
        Epic epic = new Epic("Эпик", "Для проверки");
        taskManager.addEpic(epic);
        final int epicId = epic.getId();

        final Epic savedEpic = taskManager.getEpic(epicId);

        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");

        final List<Epic> epics = taskManager.getEpics();

        assertNotNull(epics, "Задачи не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.get(0), "Задачи не совпадают.");

        //проверка на пустой список
        taskManager.deleteEpic();
        NullPointerException e = Assertions.assertThrows(
                NullPointerException.class,
                generateExecutableForEmptyMapEpic()
        );
        assertEquals(e.getMessage(), "Список задач пуст");
    }

    @Test
    void addSubTaskTest() { //метод для проверки добавления сабтаска
        Epic epic = new Epic("Эпик", "Для проверки");
        SubTask subTask = new SubTask("Подзадача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30, 1);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask);

        final  int epicId = epic.getId();
        final int subTaskId = subTask.getId();


        final Epic savedEpic = taskManager.getEpic(epicId);
        final SubTask savedSubtask = taskManager.getSubTask(subTaskId);

        assertNotNull(savedEpic, "Задача не найдена.");
        assertNotNull(savedSubtask, "Задача не найдена.");
        assertEquals(subTask, savedSubtask, "Задачи не совпадают.");

        final List<SubTask> subTasks = taskManager.getSubTasks();

        assertNotNull(subTasks, "Задачи не возвращаются.");
        assertEquals(1, subTasks.size(), "Неверное количество задач.");
        assertEquals(subTask, subTasks.get(0), "Задачи не совпадают.");

        //проверка на пустой список эпиков
        taskManager.deleteEpic();
        NullPointerException ex = Assertions.assertThrows(
                NullPointerException.class,
                generateExecutableForEmptyMapEpic()
        );
        assertEquals(ex.getMessage(), "Список задач пуст");

        //проверка на пустой список
        taskManager.deleteSubTask();
        NullPointerException e = Assertions.assertThrows(
                NullPointerException.class,
                generateExecutableForEmptyMapSubtask()
        );
        assertEquals(e.getMessage(), "Список задач пуст");
    }

    @Test
    public void printAllTaskTest() {//тест для проверки метода печати всех тасков
        NullPointerException e = Assertions.assertThrows(
                NullPointerException.class,
                generateExecutablePrintAllTaskTest()
        );
        Assertions.assertEquals(e.getMessage(), "Список задач пуст");
    }

    @Test
    public void printAllEpicTest() {//тест для проверки метода печати всех эпиков
        NullPointerException e = Assertions.assertThrows(
                NullPointerException.class,
                generateExecutablePrintAllEpicTest()
        );
        Assertions.assertEquals(e.getMessage(), "Список эпиков пуст");
    }

    @Test
    public void printAllSubtaskTest() {//тест для проверки метода печати всех сабтасков
        NullPointerException e = Assertions.assertThrows(
                NullPointerException.class,
                generateExecutablePrintAllSubtaskTest()
        );
        Assertions.assertEquals(e.getMessage(), "Список подзадач пуст");
    }

    @Test
    public void printTaskTest()  {
        NullPointerException e = Assertions.assertThrows(
                NullPointerException.class,
                generateExecutablePrintTask(1)
        );
        Assertions.assertEquals(e.getMessage(), "Список задач пуст");

        Task task = new Task("Задача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30);
        taskManager.addTask(task);

        Assertions.assertEquals(taskManager.printTask(1), task);

        NullPointerException ex = Assertions.assertThrows(
                NullPointerException.class,
                generateExecutablePrintTask(2)
        );
        Assertions.assertEquals("Возможно вы неверно ввели id или задача удалена", ex.getMessage());
    }

    @Test
    public void printEpicTest() {
        NullPointerException e = Assertions.assertThrows(
                NullPointerException.class,
                generateExecutablePrintEpic(1)
        );
        Assertions.assertEquals(e.getMessage(), "Список эпиков пуст");

        Epic epic = new Epic("Эпик", "Для проверки");
        taskManager.addEpic(epic);

        Assertions.assertEquals(taskManager.printEpic(1), epic);

        NullPointerException ex = Assertions.assertThrows(
                NullPointerException.class,
                generateExecutablePrintEpic(2)
        );
        Assertions.assertEquals("Возможно вы неверно ввели id или задача удалена", ex.getMessage());
    }

    @Test
    public void printSubTaskTest() {
        NullPointerException e = Assertions.assertThrows(
                NullPointerException.class,
                generateExecutablePrintSubTask(1)
        );
        Assertions.assertEquals(e.getMessage(), "Список подзадач пуст");

        taskManager.addEpic(new Epic("Эпик", "Для проверки"));
        SubTask subTask = new SubTask("Задача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30, 1);
        taskManager.addSubTask(subTask);

        Assertions.assertEquals(taskManager.printSubTask(2), subTask);

        NullPointerException ex = Assertions.assertThrows(
                NullPointerException.class,
                generateExecutablePrintSubTask(1)
        );
        Assertions.assertEquals("Возможно вы неверно ввели id или задача удалена", ex.getMessage());
    }

    @Test
    public void deleteTaskTest() {
        taskManager.addTask(new Task("Задача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30));
        taskManager.deleteTask();

        NullPointerException e = Assertions.assertThrows(
                NullPointerException.class,
                generateExecutableForEmptyMapTask()
        );
        Assertions.assertEquals(e.getMessage(), "Список задач пуст", "Задачи не удаленны");
    }

    @Test
    public void deleteEpicTest() {
        taskManager.addEpic(new Epic("Эпик", "Для проверки"));
        taskManager.deleteEpic();

        NullPointerException e = Assertions.assertThrows(
                NullPointerException.class,
                generateExecutableForEmptyMapEpic()
        );
        Assertions.assertEquals(e.getMessage(), "Список задач пуст", "Задачи не удаленны");
    }

    @Test
    public void deleteSubtaskTest() {
        taskManager.addEpic(new Epic("Эпик", "Для проверки"));
        taskManager.addSubTask(new SubTask("Подзадача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30, 1));
        taskManager.deleteSubTask();

        NullPointerException e = Assertions.assertThrows(
                NullPointerException.class,
                generateExecutableForEmptyMapSubtask()
        );
        Assertions.assertEquals(e.getMessage(), "Список задач пуст", "Задачи не удаленны");
    }

    @Test
    public void deleteTaskForIdTest() {
        NullPointerException ex = Assertions.assertThrows(
                NullPointerException.class,
                generateExecutableDeleteForIdTask(2)
        );
        Assertions.assertEquals(ex.getMessage(),"Задачи с таким id нет", "Задача не удаленна по id");

        taskManager.addTask(new Task("Задача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30));

        NullPointerException exp = Assertions.assertThrows(
                NullPointerException.class,
                generateExecutableDeleteForIdTask(2)
        );
        Assertions.assertEquals(exp.getMessage(), "Задачи с таким id нет");
    }

    @Test
    public void deleteEpicForIdTest() {
        NullPointerException ex = Assertions.assertThrows(
                NullPointerException.class,
                generateExecutableDeleteForIdEpic(2)
        );
        Assertions.assertEquals(ex.getMessage(),"Задачи с таким id нет", "Задача не удаленна по id");

        taskManager.addEpic(new Epic("Эпик", "Для проверки"));

        NullPointerException exp = Assertions.assertThrows(
                NullPointerException.class,
                generateExecutableDeleteForIdEpic(2)
        );
        Assertions.assertEquals(exp.getMessage(), "Задачи с таким id нет");
    }

    @Test
    public void deleteSubtaskForIdTest() {
        NullPointerException ex = Assertions.assertThrows(
                NullPointerException.class,
                generateExecutableDeleteForIdSubtask(2)
        );
        Assertions.assertEquals(ex.getMessage(),"Задачи с таким id нет", "Задача не удаленна по id");

        taskManager.addEpic(new Epic("Эпик", "Для проверки"));
        taskManager.addSubTask(new SubTask("Подзадача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30, 1));

        NullPointerException exp = Assertions.assertThrows(
                NullPointerException.class,
                generateExecutableDeleteForIdSubtask(3)
        );
        Assertions.assertEquals(exp.getMessage(), "Задачи с таким id нет");
    }


    @Test
    public void updateTaskTest(){
        taskManager.addTask(new Task("Задача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30));
        Task task = new Task(1,"Задача 2", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30);
        taskManager.updateTask(task);

        final int taskId = task.getId();
        final Task savedTask = taskManager.getTask(taskId);
        Assertions.assertEquals(savedTask, task);
    }

    @Test
    public void updateEpicTest() {
        taskManager.addEpic(new Epic("Эпик", "Для проверки"));
        Epic epic = new Epic(1, "Эпик 2", "Для проверки");
        taskManager.updateEpic(epic);

        final int epicId = epic.getId();
        final Epic savedEpic = taskManager.getEpic(epicId);
        Assertions.assertEquals(savedEpic, epic);
    }

    @Test
    public void updateSubtaskTest(){
        taskManager.addEpic(new Epic("Эпик", "Для проверки"));
        taskManager.addSubTask(new SubTask("Подзадача", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30, 1));
        SubTask subTask1 = new SubTask(2,"Подзадача 2", "Для проверки", Status.NEW,
                LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30, 1);
        taskManager.updateSubTask(subTask1);

        final int subtaskId = subTask1.getId();
        final SubTask savedSubtask = taskManager.getSubTask(subtaskId);
        Assertions.assertEquals(savedSubtask, subTask1);
    }
}