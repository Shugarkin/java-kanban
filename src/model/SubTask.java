package model;

import model.Tasks;

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


    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return  "\n" + "SubTask{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
