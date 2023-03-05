package service;

import client.KVTaskClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Epic;
import model.SubTask;
import model.Task;
import model.Tasks;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager {
    private final URI uri;
    private final KVTaskClient taskClient;
    private final static Gson gson = new Gson();

    public HttpTaskManager(URI uri) {
        this(uri, false);
    }

    public HttpTaskManager(URI uri, boolean answer) {
        this.uri = uri;
        this.taskClient = new KVTaskClient(uri);
        if (answer) {
            load();
        }

    }

    @Override
    protected void save() { //переопределенный метод для сохранения данных на сервере

        String jsonTasks = gson.toJson(new ArrayList<>(tasks.values()));
        taskClient.put("tasks", jsonTasks);

        String jsonEpics = gson.toJson(new ArrayList<>(epics.values()));
        taskClient.put("epics", jsonEpics);

        String jsonSubtask = gson.toJson(new ArrayList<>(subTasks.values()));
        taskClient.put("subtasks", jsonSubtask);

        List<Tasks> history = historyManager.getHistory();
        StringBuilder sb = new StringBuilder();
        if (!history.isEmpty()) {
            sb.append(history.get(0).getId());
        }
        for (int i = 1; i < history.size(); i++) {
            sb.append(",").append(history.get(i).getId());
        }
        String jsonHistory = gson.toJson(sb);
        taskClient.put("history", jsonHistory);
    }

    private HttpTaskManager load() {//метод для выгрузки с сервера
        HttpTaskManager httpTaskManager = Managers.getDefault(uri);

        ArrayList<Task> task = gson.fromJson(taskClient.load("tasks"), new TypeToken<ArrayList<Task>>() {
        }.getType());
        for (Task newTask : task) {
            tasks.put(newTask.getId(), newTask);
            prioritizedTasks.add(newTask);
            findNextId();
        }
        ArrayList<Epic> epic = gson.fromJson(taskClient.load("epics"), new TypeToken<ArrayList<Epic>>() {
        }.getType());

        for (Epic newEpic : epic) {
            epics.put(newEpic.getId(), newEpic);
            findNextId();
        }

        ArrayList<SubTask> subtask = gson.fromJson(taskClient.load("subtasks"), new TypeToken<ArrayList<SubTask>>() {
        }.getType());

        for (SubTask newSubtask : subtask) {
            subTasks.put(newSubtask.getId(), newSubtask);
            prioritizedTasks.add(newSubtask);
            findNextId();
        }
        String history = gson.fromJson(taskClient.load("history"), new TypeToken<String>() {
        }.getType());
        String[] split = history.split(",");
        fileToHistory(split);

        return httpTaskManager;
    }

    private void findNextId() {
        for (Task value : tasks.values()) {
            if(value.getId() > nextId) {
                nextId = value.getId();
            }
        }

        for (Epic value : epics.values()) {
            if(value.getId() > nextId) {
                nextId = value.getId();
            }
        }

        for (SubTask value : subTasks.values()) {
            if(value.getId() > nextId) {
                nextId = value.getId();
            }
        }
        nextId = nextId + 1;
    }

}
