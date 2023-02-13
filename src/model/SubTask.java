package model;

import model.Tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Tasks {

    protected int epicId;

    public SubTask(int id, String title, String description, String status, int epicId) {
        super(id, title, description, status);
        this.epicId = epicId;
    }

    public SubTask(String title, String description, String status, int epicId) {
        super(title, description, status);
        this.epicId = epicId;
    }

    public SubTask(String title, String description, String status, LocalDateTime startTime, Duration duration, int epicId) {
        super(title, description, status, startTime, duration);
        this.epicId = epicId;
    }

    public SubTask(int id, String title, String description, String status, LocalDateTime start, Duration duration, int epicId) {
        super(id, title, description,status, start, duration);
        this.epicId = epicId;
    }

    public SubTask(int id, String title, String description, String status, LocalDateTime startTime, Duration duration, LocalDateTime endTime, int epicId) {
        super(id, title, description, status, startTime, duration, endTime);
        this.epicId = epicId;
    }


    @Override
    public LocalDateTime getEndTime() {
        return super.getEndTime();
    }
    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return  "\n" + id +
                "," +
                "SubTask" +
                "," +
                title +
                "," +
                status +
                "," +
                description +
                "," +
                startTime +
                "," +
                duration +
                "," +
                getEpicId();
    }
}