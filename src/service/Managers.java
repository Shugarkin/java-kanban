package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.net.URI;
import java.time.LocalDateTime;

public class Managers {
    private Managers() {
    }
    public static HttpTaskManager getDefault(URI uri){
        return new HttpTaskManager(uri);
    }
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getFileBackedTasksManager(File file) {
        return new FileBackedTasksManager(file);
    }
    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        Gson gson = gsonBuilder.create();
        return gson;
    }
}