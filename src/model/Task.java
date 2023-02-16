package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task extends Tasks {

    public Task(int id, String title, String description, Enum status) {
        super(id, title, description, status);
    }

    public Task(String title, String description, Enum status) {
        super(title, description, status);
    }

    public Task(int id, String title, String description, Enum status, LocalDateTime startTime, long duration) {
        super(id, title, description, status, startTime, duration);
    }

    public Task(String title, String description, Enum status, LocalDateTime startTime, long duration) {
        super(title, description, status, startTime, duration);
    }

    public Task(int id, String title, String description, Enum status, LocalDateTime startTime, long duration, LocalDateTime endTime) {
        super(id, title, description, status, startTime, duration, endTime);
    }


    @Override
    public LocalDateTime getEndTime() {
        return super.getEndTime();
    }

    @Override
    public String toString() {
        return "\n" + getId() +
                "," +
                "Task" +
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

