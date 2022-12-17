import java.util.ArrayList;

public class Epic extends Tasks {
    protected ArrayList<Integer> subTaskIds = new ArrayList<>();//лист для id сабзадач

    public Epic(int id, String title, String description, String status) {
        super(id, title, description, status);
    }

    public Epic(String title, String description) {
        super(title, description);
    }

    public void addSubTaskId(int epicId) {
        subTaskIds.add(epicId);
    }

    public ArrayList<Integer> getSubTaskId() {
        return subTaskIds;
    }

    public void setSubTaskIds(ArrayList<Integer> subTaskIds) {
        this.subTaskIds = subTaskIds;
    }

}
