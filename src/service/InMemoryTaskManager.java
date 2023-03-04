package service;
import model.*;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    protected HistoryManager historyManager = Managers.getDefaultHistory();

    protected Map<Integer, Task> tasks = new HashMap<>();
    protected Map<Integer, Epic> epics = new HashMap<>();
    protected Map<Integer, SubTask> subTasks = new HashMap<>();
    private static final Comparator<Tasks> comparator = (o1, o2) -> {
        LocalDateTime dateTime1 = o1.getStartTime();
        LocalDateTime dateTime2 = o2.getStartTime();
        if(dateTime1 == null && dateTime2 == null) {
            return o1.getId() - o2.getId();
        } else if (dateTime1 == null) {
            return 1;
        } else if (dateTime2 == null) {
            return -1;
        } else {
            return dateTime1.compareTo(dateTime2);
        }
    };

    protected final Set<Tasks> prioritizedTasks = new TreeSet<>(comparator);


    protected int nextId = 1;

    @Override
    public void addTask(Task task){ //добавление задач
        intersections(task);
        task.setId(nextId++);
        tasks.put(task.getId(), task);
        prioritizedTasks.add(task);
    }
    @Override
    public void addEpic(Epic epic) { //добаление эпиков
        epic.setId(nextId++);
        epics.put(epic.getId(), epic);
        epic.setStatus(Status.NEW);
    }
    @Override
    public void addSubTask(SubTask subTask){ //добавление сабзадач
        intersections(subTask);
        subTask.setId(nextId++);
        epics.get(subTask.getEpicId()).getSubtaskId().add(subTask.getId());
        subTasks.put(subTask.getId(), subTask);
        checkStatus(subTask.getEpicId());
        dateEpicCheck(subTask.getEpicId());
        prioritizedTasks.add(subTask);
    }

    @Override
    public List<SubTask> getSubtasksByEpic(int epicId) {
        Epic epic = epics.get(epicId);
        List<SubTask> subTasksEpic = new ArrayList<>();
        for (Integer subtaskId : epic.getSubtaskId()) {
            subTasksEpic.add(subTasks.get(subtaskId));
        }
        return subTasksEpic;
    }

    @Override
    public void deleteTask() {//очищает задачи
        for (Task task : tasks.values()) {
            historyManager.remove(task.getId());
            prioritizedTasks.remove(task);
        }
        tasks.clear();
    }

    @Override
    public void deleteEpic() {//очищает эпики
        for (SubTask subTask : subTasks.values()) {
            prioritizedTasks.remove(subTask);
            historyManager.remove(subTask.getId());
        }
        for (Integer epicId : epics.keySet()) {
            historyManager.remove(epicId);
        }
        subTasks.clear();
        epics.clear();
    }
    @Override
    public void deleteSubTask() {//очищает подзадачи
        for (SubTask subTask : subTasks.values()) {
            historyManager.remove(subTask.getId());
            prioritizedTasks.remove(subTask);
        }
        for (Epic epic : epics.values()) {
            epic.getSubtaskId().clear();
            checkStatus(epic.getId());
            dateEpicCheck(epic.getId());
        }
        subTasks.clear();
    }

    @Override
    public void updateTask(Task task) {//заменяет старую задачу новой
        if (tasks.containsKey(task.getId())) {
            prioritizedTasks.remove(tasks.get(task.getId()));
            tasks.put(task.getId(), task);
            prioritizedTasks.add(task);
        } else {
            System.out.println("Что-то пошло не так ((");
        }
    }
    @Override
    public void updateEpic(Epic epic) {//заменяет старый эпик новым
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
        } else {
            System.out.println("Что-то пошло не так ((");
        }
    }
    @Override
    public void updateSubTask(SubTask subTask) {//заменяет старую подзадачу новой
        if (subTasks.containsKey(subTask.getId())) {
            prioritizedTasks.remove(subTasks.get(subTask.getId()));
            subTasks.put(subTask.getId(), subTask);
            prioritizedTasks.add(subTask);
            checkStatus(subTask.getEpicId());
            dateEpicCheck(subTask.getEpicId());
        } else {
            System.out.println("Что-то пошло не так ((");
        }
    }
    @Override
    public void deleteTaskForId(int id) {//удаляет задачу по id
        if(!tasks.containsKey(id)){
            throw new NullPointerException("Задачи с таким id нет");
        }
        prioritizedTasks.remove(tasks.get(id));
        tasks.remove(id);
        historyManager.remove(id);
    }
    @Override
    public void deleteEpicForId(int id) {//удаляет эпик по id
        if(!epics.containsKey(id)){
            throw new NullPointerException("Задачи с таким id нет");
        }
        Epic epic = epics.get(id);
        for (Integer subTaskId : epic.getSubtaskId()) {
            prioritizedTasks.remove(subTasks.remove(subTaskId));
            historyManager.remove(subTaskId);
        }
        epics.remove(id);
        historyManager.remove(id);
    }
    @Override
    public void deleteSubTaskForId(int id) {//удаляет подзадачу по id
        if(!subTasks.containsKey(id)){
            throw new NullPointerException("Задачи с таким id нет");
        }
        SubTask subTask = subTasks.get(id);
        epics.get(subTask.getEpicId()).getSubtaskId().remove((Integer) subTask.getId());
        prioritizedTasks.remove(subTasks.get(id));
        subTasks.remove(id);
        historyManager.remove(id);
        checkStatus(subTask.getEpicId());
        dateEpicCheck(subTask.getEpicId());
    }

    @Override
    public void checkStatus(int epicId) {//метод для определения статуса эпика
        Set<Status> statusSubTask = new HashSet<>();
        Epic epic = epics.get(epicId); //определенный эпик
        for (Integer subTaskId : epic.getSubtaskId()) {
            statusSubTask.add(subTasks.get(subTaskId).getStatus());
        }
        if (statusSubTask.contains(Status.NEW) && !statusSubTask.contains(Status.DONE) && !statusSubTask.contains(Status.IN_PROGRESS)) { //почему-то если сравнивать через Status.*** сравнение происходит не корректно
            epic.setStatus(Status.NEW);
        } else if (!statusSubTask.contains(Status.NEW) && statusSubTask.contains(Status.DONE) && !statusSubTask.contains(Status.IN_PROGRESS)) {//тоже самое
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public List<Tasks> getHistory() {//метод дает хистори менеджер
        return historyManager.getHistory();
    }
    @Override
    public Task getTask(Integer id) {//метод дает задачу
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }
    @Override
    public Epic getEpic(Integer id) {//метод дает эпик
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }
    @Override
    public SubTask getSubTask(Integer id) {//метод дает подзадачу
        SubTask subTask = subTasks.get(id);
        historyManager.add(subTask);
        return subTask;
    }

    @Override
    public List<Task> getTasks() {//метод дает список тасков
        return new ArrayList<>(tasks.values());

    }

    @Override
    public List<Epic> getEpics() {//метод дает список эпиков
        return new ArrayList<>(epics.values());
    }
    @Override
    public List<SubTask> getSubTasks() {//метод дает список подзадач
        return new ArrayList<>(subTasks.values());
    }


    public Epic dateEpicCheck(int epicId) { //метод для определения временных рамок эпика
        LocalDateTime start = null;
        LocalDateTime finish = null;
        long duration = 0L;

        Epic epic = epics.get(epicId);
        for (Integer subtaskId : epic.getSubtaskId()) {
            SubTask subtask = subTasks.get(subtaskId);
            LocalDateTime existEndTime = subtask.getEndTime();
            LocalDateTime existStartTime = subtask.getStartTime();
            if (existStartTime != null) {
                if (finish == null || existEndTime.isAfter(finish)) {
                    finish = existEndTime;
                }
                if (start == null || existStartTime.isBefore(start)) {
                    start = existStartTime;
                }
                duration+=subtask.getDuration();
            }
        }
        epic.setEndTime(finish);
        epic.setStartTime(start);
        epic.setDuration(duration);
        return epic;
    }

    public void intersections(Tasks task) { //метод для определения пересечений
            for (Tasks o : prioritizedTasks) {
                if(task.getStartTime() == null || o.getStartTime() == null) {
                    return;
                }
                if (!o.getEndTime().isAfter(task.getStartTime())) {
                    continue;
                }
                if (!o.getStartTime().isBefore(task.getEndTime())) {
                    continue;
                }
                if (task.getId() == o.getId()) {
                    continue;
                }
                throw new IllegalArgumentException ("Нельзя создать пересекающиеся задачи");
            }
        }

    @Override
    public List<Tasks> getPrioritizedTasks() {//геттер для сортированного списка
        return List.copyOf(prioritizedTasks);
    }

    public int getNextId() {//геттер для значения последней созданной задачи
        return nextId;
    }
}

