package service;

import model.Epic;
import model.SubTask;
import model.Task;
import model.Tasks;

import java.io.IOException;
import java.net.URI;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager {
    URI uri;
    KVTaskClient taskClient;
    public HttpTaskManager(URI uri) {
        this.uri = uri;
        this.taskClient = new KVTaskClient(uri);
    }

    @Override
    protected void save() { //переопределенный метод для сохранения данных на сервере
        StringBuilder stringBuilder =  new StringBuilder();
        for (Task task : getTasks()) {
            stringBuilder.append(task);
        }
        for (Epic epic : getEpics()) {
            stringBuilder.append(epic);
        }
        for (SubTask subTask : getSubTasks()) {
            stringBuilder.append(subTask);
        }

        stringBuilder.append("\n");
        stringBuilder.append("\n");

        List<Tasks> history = historyManager.getHistory();
        if (!history.isEmpty()) {
            stringBuilder.append(history.get(0).getId());
        }
        for (int i = 1; i < history.size(); i++) {
            stringBuilder.append(",").append(history.get(i).getId());
        }

        String allTasks = String.valueOf(stringBuilder);

        try {
            taskClient.put(String.valueOf(100), allTasks);
        } catch (Exception e) {
            e.getMessage();
            System.out.println("Возникли проблемы с отправкой на сервер");
        }
    }

    public HttpTaskManager load() throws IOException, InterruptedException {//метод для выгрузки с сервера
        HttpTaskManager httpTaskManager = Managers.getDefault(uri);
        String tasksAndHistory = taskClient.load(String.valueOf(100));
        String[] split = tasksAndHistory.split("\n");
        for (String line : split) {
            if(line.isBlank()) {
                continue;
            }
            try {
                httpTaskManager.fromString(line);
            } catch (Exception e) {
                String[] history = line.split(",");
                httpTaskManager.fileToHistory(history);
            }
        }
        return httpTaskManager;
    }
}
