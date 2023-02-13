package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Task extends Tasks {

    public Task(int id, String title, String description, String status) {
        super(id, title, description, status);
    }

    public Task(String title, String description, String status) {
        super(title, description, status);
    }

    public Task(int id, String title, String description, String status, LocalDateTime startTime, Duration duration) {
        super(id, title, description, status, startTime, duration);
    }

    public Task(String title, String description, String status, LocalDateTime startTime, Duration duration) {
        super(title, description, status, startTime, duration);
    }

    public Task(int id, String title, String description, String status, LocalDateTime startTime, Duration duration, LocalDateTime endTime) {
        super(id, title, description, status, startTime, duration, endTime);
    }


    @Override
    public LocalDateTime getEndTime() {
        return super.getEndTime();
    }

    @Override
    public String toString() {
        return "\n" + id +
                "," +
                "Task" +
                "," +
                 title +
                "," +
                status +
                "," +
                 description +
                "," +
                startTime +
                "," +
                duration;
    }
}
