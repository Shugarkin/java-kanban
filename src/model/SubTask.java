package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class SubTask extends Tasks {

    protected int epicId;

    public SubTask(int id, String title, String description, Status status, int epicId) {
        super(id, title, description, status);
        this.epicId = epicId;
    }

    public SubTask(String title, String description, Status status, int epicId) {
        super(title, description, status);
        this.epicId = epicId;
    }

    public SubTask(String title, String description, Status status, LocalDateTime startTime, long duration, int epicId) {
        super(title, description, status, startTime, duration);
        this.epicId = epicId;
    }

    public SubTask(int id, String title, String description, Status status, LocalDateTime start, long duration, int epicId) {
        super(id, title, description,status, start, duration);
        this.epicId = epicId;
    }


    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return  "\n" + getId() +
                "," +
                "SubTask" +
                "," +
                getTitle() +
                "," +
                getStatus() +
                "," +
                getDescription() +
                "," +
                getStartTime() +
                "," +
                getDuration() +
                "," +
                getEpicId();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubTask subTask = (SubTask) o;
        return getId() == subTask.getId() && getDuration() == subTask.getDuration() && Objects.equals(getTitle(), subTask.getTitle())
                && epicId == subTask.epicId && Objects.equals(getDuration(), subTask.getDuration()) && Objects.equals(getStatus(), subTask.getStatus())
                && Objects.equals(getStartTime(), subTask.getStartTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getStatus(), getStartTime(), getDuration(), epicId);
    }
}