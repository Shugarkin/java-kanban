package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task extends Tasks {



    private String name = "Task";

    public Task(Integer id, String title, String description, Status status) {
        super(id, title, description, status);
    }

    public Task(String title, String description, Status status) {
        super(title, description, status);
    }

    public Task(Integer id, String title, String description, Status status, LocalDateTime startTime, long duration) {
        super(id, title, description, status, startTime, duration);
    }

    public Task(String title, String description, Status status, LocalDateTime startTime, long duration) {
        super(title, description, status, startTime, duration);
    }

    @Override
    public LocalDateTime getEndTime() {
        return super.getEndTime();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "\n" + getId() +
                "," +
                getName() +
                "," +
                getTitle() +
                "," +
                getStatus() +
                "," +
                getDescription() +
                "," +
                getStartTime() +
                "," +
                getDuration();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return getId() == task.getId() && getDuration() == task.getDuration() && Objects.equals(getTitle(), task.getTitle())
                && Objects.equals(getDuration(), task.getDuration()) && Objects.equals(getStatus(), task.getStatus())
                && Objects.equals(getStartTime(), task.getStartTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getStatus(), getStartTime(), getDuration());
    }
}

