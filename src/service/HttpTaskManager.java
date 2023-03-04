package service;

import client.KVTaskClient;
import com.google.gson.Gson;
import model.Epic;
import model.SubTask;
import model.Task;
import model.Tasks;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class HttpTaskManager extends FileBackedTasksManager {
    private URI uri;
    private KVTaskClient taskClient;
    private Gson gson = new Gson();

    public HttpTaskManager(URI uri) {
        this(uri, false);
        this.taskClient = new KVTaskClient(uri);
    }

    public HttpTaskManager(URI uri, boolean answer) {
        this.uri = uri;
        this.taskClient = new KVTaskClient(uri);
        if (answer == true) {
            load();
        }

    }

    @Override
    protected void save() { //переопределенный метод для сохранения данных на сервере
        List<String> stringBuilder = new ArrayList<>();
        for (Task task : getTasks()) {
            stringBuilder.add(gson.toJson(task));
        }
        for (Epic epic : getEpics()) {
            stringBuilder.add(gson.toJson(epic));
        }
        for (SubTask subTask : getSubTasks()) {
            stringBuilder.add(gson.toJson(subTask));
        }

        List<Tasks> history = historyManager.getHistory();
        if (!history.isEmpty()) {
            stringBuilder.add(String.valueOf(history.get(0).getId()));
        }
        for (int i = 1; i < history.size(); i++) {
            stringBuilder.add(String.valueOf(history.get(i).getId()));
        }

        String allTasks = stringBuilder.toString();

        taskClient.put(String.valueOf(100), allTasks);
    }

    private HttpTaskManager load() {//метод для выгрузки с сервера
        HttpTaskManager httpTaskManager = Managers.getDefault(uri);
        String tasksAndHistory = taskClient.load(String.valueOf(100));
        String[] split = tasksAndHistory.split(Pattern.quote(", "));
        split[0] = split[0].replaceFirst(Pattern.quote("["), "");
        int answer = split.length - 1;
        split[answer] = split[answer].replaceFirst("]", "");
        for (String line : split) {

                if(line.contains("Task")) {
                    Task task = gson.fromJson(line, Task.class);
                    addTask(task);
                } else if (line.contains("Epic")) {
                    Epic epic = gson.fromJson(line, Epic.class);
                    addEpic(epic);
                } else if (line.contains("Subtask")) {
                    SubTask subTask = gson.fromJson(line, SubTask.class);
                    addSubTask(subTask);
                }     else {
            String[] history = line.split("");
            fileToHistory(history);
            }
        }
        return httpTaskManager;
    }
}
