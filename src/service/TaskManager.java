package service;

import model.Epic;
import model.SubTask;
import model.Task;

public interface TaskManager {
    HistoryManager getHistoryManager();


    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubTask(SubTask subTask);

    String printAllTask();

    String printAllEpic();

    String printAllSubTaskForEpic(int epicId);

    String printAllSubTask();

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

}

