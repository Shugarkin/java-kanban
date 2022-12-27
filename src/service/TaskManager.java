package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.HashMap;

public interface TaskManager {
    HistoryManager getHistoryManager();


    int addTask(Task task);

    int addEpic(Epic epic);

    int addSubTask(SubTask subTask);

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

    int newTask(int id, Task task);

    int newEpic(int id, Epic epic);

    int newSubTask(int id, SubTask subTask);

    void deleteTaskForId(int id);

    void deleteEpicForId(int id);

    void deleteSubTaskForId(int id);

    String checkStatus(int epicId);

}

