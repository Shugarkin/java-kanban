package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.time.Duration;
import java.util.HashMap;
import java.util.TreeSet;

public interface TaskManager {
    HistoryManager getHistoryManager();
    HashMap<Integer, Task> getTasks();

    HashMap<Integer, Epic> getEpics();

    HashMap<Integer, SubTask> getSubTasks();

    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubTask(SubTask subTask);

    void printAllTask();

    void printAllEpic();

    void printAllSubTaskForEpic(int epicId);

    void printAllSubTask();

    String printTask(Integer taskId);

    String printEpic(Integer epicId);

    String printSubTask(Integer subTaskId);

    void deleteTask();

    void deleteEpic();

    void deleteSubTask();

    void newTask(int id, Task task);

    void newEpic(int id, Epic epic);

    void newSubTask(int id, SubTask subTask);

    void deleteTaskForId(int id);

    void deleteEpicForId(int id);

    void deleteSubTaskForId(int id);

    String checkStatus(int epicId);

    Task getTask(Integer id);

    Epic getEpic(Integer id);

    SubTask getSubTask(Integer id);

    Duration durationEpicCheck(int epicId);

    Epic dateEpicCheck(int epicId);

    TreeSet getPrioritizedTasks();
}

