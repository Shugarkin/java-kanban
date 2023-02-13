package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Tasks {
    protected int id;
    protected String title;
    protected String description;
    public String status;

    public LocalDateTime startTime ;

    public Duration duration;

    public LocalDateTime endTime;
    public Tasks(int id, String title, String description, String status, LocalDateTime startTime, Duration duration) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Tasks(String title, String description, String status, LocalDateTime startTime, Duration duration) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Tasks(int id, String title, String description, String status, LocalDateTime startTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
    }

    public Tasks(int id, String title, String description, String status, LocalDateTime startTime, Duration duration, LocalDateTime endTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = startTime.plus(duration);
    }


    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public Tasks(int id, String title, String description, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Tasks(String title, String description, String status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Tasks(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }
}
