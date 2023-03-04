package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Tasks {
    private List<Integer> subtaskIds = new ArrayList<>();//лист для id сабзадач

    private LocalDateTime endTime;



    private String name = "Epic";

    public Epic(Integer id, String title, String description, Status status) {
        super(id, title, description, status);
    }

    public Epic(String title, String description) {
        super(title, description);
    }

    public Epic(String title, String description, Status status, LocalDateTime startTime, long duration) {
        super(title, description, status, startTime, duration);
    }

    public Epic(Integer id, String title, String description, Status status, LocalDateTime start, LocalDateTime endTime) {
        super(id, title, description, status, start);
        this.endTime = endTime;
    }

    public Epic(Integer id, String title, String description) {
        super(id, title, description);
    }

    public Epic(Integer id, String title, String description, Status status, LocalDateTime startTime, LocalDateTime endTime, long duration) {
        super(id, title, description, status, startTime, duration);
        this.endTime = endTime;
    }

    public List<Integer> getSubtaskId() {
        return subtaskIds;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "\n" +  getId() +
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
                endTime +
                 "," +
                getDuration();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Epic epic = (Epic) o;
        return getId() == epic.getId() && getDuration() == epic.getDuration() && Objects.equals(getTitle(), epic.getTitle())
                && subtaskIds.equals(epic.subtaskIds) && endTime.equals(epic.endTime)
                && Objects.equals(getDuration(), epic.getDuration()) && Objects.equals(getStatus(), epic.getStatus())
                && Objects.equals(getStartTime(), epic.getStartTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getStatus(), getStartTime(), getDuration(), subtaskIds, endTime);
    }

    public void setSubTaskIds(List<Integer> subTaskIds) {
        this.subtaskIds = subTaskIds;
    }
}
