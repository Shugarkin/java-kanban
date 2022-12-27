package model;

public class Task extends Tasks {

    public Task(int id, String title, String description, String status) {
        super(id, title, description, status);
    }

    public Task(String title, String description, String status) {
        super(title, description, status);
    }


    @Override
    public String toString() {
        return "\n" +  "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}