package service;

import model.Epic;
import model.SubTask;
import model.Task;
import model.Tasks;

import java.util.List;

public interface TaskManager {
    List<Task> getTasks();

    List<Epic> getEpics();

    List<SubTask> getSubTasks();

    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubTask(SubTask subTask);

    List<SubTask> getSubtasksByEpic(int epicId);

    void deleteTask();

    void deleteEpic();

    void deleteSubTask();

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubTask(SubTask subTask);

    void deleteTaskForId(int id);

    void deleteEpicForId(int id);

    void deleteSubTaskForId(int id);

    void checkStatus(int epicId);

    List<Tasks> getHistory();

    Task getTask(Integer id);

    Epic getEpic(Integer id);

    SubTask getSubTask(Integer id);

    List<Tasks> getPrioritizedTasks();

}


