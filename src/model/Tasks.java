package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

abstract public class Tasks {
    private Integer id;
    private String title;
    private String description;
    private Status status;
    private LocalDateTime startTime ;
    private long duration;

    public Tasks(Integer id, String title, String description, Status status, LocalDateTime startTime, long duration) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Tasks(String title, String description, Status status, LocalDateTime startTime, long duration) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Tasks(Integer id, String title, String description, Status status, LocalDateTime startTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
    }

    public Tasks(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }


    public LocalDateTime getEndTime() {
        if(startTime == null){
            return null;
        }
        return startTime.plus(Duration.ofMinutes(duration));
    }

    public Tasks(Integer id, String title, String description, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Tasks(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Tasks(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Integer getId() {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tasks tasks = (Tasks) o;
        return id == tasks.id && duration == tasks.duration && Objects.equals(title, tasks.title) && Objects.equals(description, tasks.description) && Objects.equals(status, tasks.status) && Objects.equals(startTime, tasks.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status, startTime, duration);
    }
}
