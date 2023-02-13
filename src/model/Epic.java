package model;

import model.Tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Tasks {
    protected ArrayList<Integer> subTaskIds = new ArrayList<>();//лист для id сабзадач

    public LocalDateTime endTime;

    public Epic(int id, String title, String description, String status) {
        super(id, title, description, status);
    }

    public Epic(String title, String description) {
        super(title, description);
    }

    public Epic(String title, String description, String status, LocalDateTime startTime, Duration duration) {
        super(title, description, status, startTime, duration);
    }

    public Epic(int id, String title, String description, String status, LocalDateTime start, LocalDateTime endTime) {
        super(id, title, description, status, start);
        this.endTime = endTime;
    }

    public void addSubTaskId(int epicId) {
        subTaskIds.add(epicId);
    }

    public ArrayList<Integer> getSubTaskId() {
        return subTaskIds;
    }

    public void setSubTaskIds(ArrayList<Integer> subTaskIds) {
        this.subTaskIds = subTaskIds;
    }

    @Override
    public String toString() {
        return "\n" +  id +
                "," +
                "Epic" +
                "," +
                title +
                "," +
                status +
                "," +
                description +
                "," +
                startTime +
                "," +
                endTime;
    }
}
