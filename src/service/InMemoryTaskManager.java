package service;
import model.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    protected HistoryManager historyManager = Managers.getDefaultHistory();

    protected Map<Integer, Task> tasks = new HashMap<>();
    protected Map<Integer, Epic> epics = new HashMap<>();
    protected Map<Integer, SubTask> subTasks = new HashMap<>();
    Comparator<Tasks> comparator = (o1, o2) -> {
        if(o1.getStartTime().isBefore(o2.getStartTime())) {
            return -1;
        } else {
            return 1;
        }
    };

    private TreeSet<Tasks> prioritizedTasks = new TreeSet(comparator);


    protected int nextId = 1;

    @Override
    public void addTask(Task task){ //добавление задач
        intersections(task);
        task.setId(nextId++);
        tasks.put(task.getId(), task);
        prioritizedTasksInLIst();
    }
    @Override
    public void addEpic(Epic epic) { //добаление эпиков
        epic.setId(nextId++);
        epics.put(epic.getId(), epic);
        epic.setStatus(Status.NEW);
        prioritizedTasksInLIst();
    }
    @Override
    public void addSubTask(SubTask subTask){ //добавление сабзадач
        intersections(subTask);
        if (epics.isEmpty()) {
            throw new NullPointerException("Список задач пуст");
        }
        subTask.setId(nextId++);
        subTasks.put(subTask.getId(), subTask);
        epics.get(subTask.getEpicId()).addSubTaskId(subTask.getId());
        checkStatus(subTask.getEpicId());
        epicCheck(subTask);
        prioritizedTasksInLIst();
    }
    @Override
    public Task printAllTask() { // печатает все задачи
        if(tasks.isEmpty()) {
            throw new NullPointerException("Список задач пуст");
        }
        return (Task) tasks.values();
    }
    @Override
    public Epic printAllEpic() { // печатает все эпики
        if(epics.isEmpty()) {
            throw  new NullPointerException("Список эпиков пуст");
        }
        return (Epic) epics.values();
    }

    @Override
    public void printAllSubTaskForEpic(int epicId) { //печатает подзадачи определенного эпика
        if(epics.isEmpty()) {
            throw  new NullPointerException("Список эпиков пуст");
        }
        if(subTasks.isEmpty()) {
            throw  new NullPointerException("Список подзадач пуст");
        }
        System.out.println(printEpic(epicId));
        Epic epic = epics.get(epicId); //определенный эпик
        if (!epic.getSubTaskId().isEmpty()) {
            for (Integer subTaskId : epic.getSubTaskId()) {
                if (epic.getSubTaskId().contains(subTaskId)) {
                    System.out.println(printSubTask(subTaskId));
                } else {
                    System.out.println("В данном EPIC нет подзадач.");
                }
            }
        }
    }

    @Override
    public SubTask printAllSubTask() { // печатает все подзадачи
        if(subTasks.isEmpty()) {
            throw  new NullPointerException("Список подзадач пуст");
        }
        return (SubTask) subTasks.values();
    }

    @Override
    public Task printTask(Integer taskId) { //метод для печати задач
        if(tasks.isEmpty()) {
            throw new NullPointerException("Список задач пуст");
        }
        try {
            tasks.containsKey(getTask(taskId).getId());
        } catch (NullPointerException e) {
            throw new NullPointerException("Возможно вы неверно ввели id или задача удалена");
        }
        return tasks.get(taskId);
    }
    @Override
    public Epic printEpic(Integer epicId) { //метод для печати эпиков
        if(epics.isEmpty()) {
            throw  new NullPointerException("Список эпиков пуст");
        }
        try {
            epics.containsKey(getEpic(epicId).getId());
        } catch (NullPointerException e) {
            throw new NullPointerException("Возможно вы неверно ввели id или задача удалена");
        }
        return epics.get(epicId);
    }
    @Override
    public SubTask printSubTask(Integer subTaskId) { //метод для печати сабзадач
        if(subTasks.isEmpty()) {
            throw  new NullPointerException("Список подзадач пуст");
        }
        try {
            subTasks.containsKey(getSubTask(subTaskId).getId());
        } catch (NullPointerException e) {
            throw new NullPointerException("Возможно вы неверно ввели id или задача удалена");
        }
        return subTasks.get(subTaskId);
    }
    @Override
    public void deleteTask() {//очищает задачи
        for (Integer integer : tasks.keySet()) {
            historyManager.getHistory().remove(integer);
        }
        for (Task integer : tasks.values()) {
            prioritizedTasks.remove(integer);
        }
        tasks.clear();
    }
    @Override
    public void deleteEpic() {//очищает эпики
        for (SubTask integer : subTasks.values()) {
            prioritizedTasks.remove(integer);
        }
        for (Epic integer : epics.values()) {
            prioritizedTasks.remove(integer);
        }
        for (Integer integer : subTasks.keySet()) {
            historyManager.getHistory().remove(integer);
        }
        for (Integer integer : epics.keySet()) {
            historyManager.getHistory().remove(integer);
        }
        subTasks.clear();
        epics.clear();
    }
    @Override
    public void deleteSubTask() {//очищает подзадачи
        for (Integer integer : subTasks.keySet()) {
            historyManager.getHistory().remove(integer);
        }
        for (SubTask integer : subTasks.values()) {
            prioritizedTasks.remove(integer);
        }
        for (SubTask subTask : subTasks.values()) {
            epics.get(subTask.getEpicId()).getSubTaskId().clear();
            epicCheck(subTask);
        }
        subTasks.clear();
    }

    @Override
    public void updateTask(Task task) {//заменяет старую задачу новой
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
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
            subTasks.put(subTask.getId(), subTask);
            epics.get(subTask.getEpicId()).addSubTaskId(subTask.getId());
            checkStatus(epics.get(subTask.getEpicId()).getId());
            epicCheck(subTask);
        } else {
            System.out.println("Что-то пошло не так ((");
        }
    }
    @Override
    public void deleteTaskForId(int id) {//удаляет задачу по id
        if(!tasks.containsKey(id)){
            throw new NullPointerException("Задачи с таким id нет");
        }
        tasks.remove(id);
        historyManager.remove(id);
    }
    @Override
    public void deleteEpicForId(int id) {//удаляет эпик по id
        if(!epics.containsKey(id)){
            throw new NullPointerException("Задачи с таким id нет");
        }
        Epic epic = epics.get(id);
        if (!epic.getSubTaskId().isEmpty()) {
            for (Integer subTaskId : epic.getSubTaskId()) {
                if (epic.getSubTaskId().contains(subTaskId)) {
                    subTasks.remove(subTaskId);
                    if(historyManager.getHistory().contains(subTasks.get(subTaskId))) {
                        historyManager.remove(subTaskId);
                    }
                }
            }
        }
        epics.remove(id);
        historyManager.remove(id);
    }
    @Override
    public void deleteSubTaskForId(int id) {//удаляет подзадачу по id
        if(!subTasks.containsKey(id)){
            throw new NullPointerException("Задачи с таким id нет");
        }
        subTasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void checkStatus(int epicId) {//метод для определения статуса эпика
        List<Enum> statusSubTask = new ArrayList<>();
        Epic epic = epics.get(epicId); //определенный эпик
        for (Integer subTaskId : epic.getSubTaskId()) {
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
        if(tasks.isEmpty()) {
            throw new NullPointerException("Список задач пуст");
        }
        return new ArrayList<>(tasks.values());

    }

    @Override
    public List<Epic> getEpics() {//метод дает список эпиков
        if(epics.isEmpty()) {
            throw new NullPointerException("Список задач пуст");
        }
        return new ArrayList<>(epics.values());
    }
    @Override
    public List<SubTask> getSubTasks() {//метод дает список подзадач
        if(subTasks.isEmpty()) {
            throw new NullPointerException("Список задач пуст");
        }
        return new ArrayList<>(subTasks.values());
    }
    @Override
    public void durationEpicCheck(int epicId) { //метод для вычисления продолжительности эпика (не знаю зачем, но написал)
        long durationEpic = 0;
        Epic epic = epics.get(epicId);
        for (Integer subtaskid : epic.getSubTaskId()) {
            durationEpic += subTasks.get(subtaskid).getDuration();
        }
        epic.setDuration(durationEpic);
    }
    @Override
    public Epic dateEpicCheck(int epicId) { //метод для определения временных рамок эпика
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime finish = LocalDateTime.of(1980, Month.JANUARY, 1, 00, 00);

        Epic epic = epics.get(epicId);
        for (Integer subtask : epic.getSubTaskId()) {
            if(start.isAfter(subTasks.get(subtask).getStartTime())) {
                start = subTasks.get(subtask).getStartTime();
            }
            if(finish.isBefore(subTasks.get(subtask).getEndTime())) {
                finish = subTasks.get(subtask).getEndTime();
            }
        }
        epic.setStartTime(start);
        epic.setEndTime(finish);
        return epic;
    }
    @Override
    public void prioritizedTasksInLIst() { //метод для определения приоритета задач
        prioritizedTasks.clear();

        for (Task task : tasks.values()) {
            prioritizedTasks.add(task);
        }
        for (Epic epic : epics.values()) {
            if(epic.getStartTime() != null) {
                prioritizedTasks.add(epic);
            }
        }
        for (SubTask subTask : subTasks.values()) {
            prioritizedTasks.add(subTask);
        }
    }

    public void intersections(Tasks task) { //метод для определения пересечений
        if (!prioritizedTasks.isEmpty()) {
            for (Tasks o : prioritizedTasks) {
                if (!o.getEndTime().isAfter(task.getStartTime())) {
                    continue;
                }
                if (!o.getStartTime().isBefore(task.getEndTime())) {
                    continue;
                }
                if (task.getId() == o.getId()) {
                    continue;
                }
                if(epics.containsKey(o.getId())) {
                    continue;
                }
                throw new IllegalArgumentException ("Нельзя создать пересекающиеся задачи");
            }
        }
    }


    private void epicCheck(SubTask subTask) {//метод для уменьшения дублирования кода
        if (subTask.getStartTime() != null ) {
            durationEpicCheck(subTask.getEpicId());
            dateEpicCheck(subTask.getEpicId());
        }
    }

    @Override
    public TreeSet<Tasks> getPrioritizedTasks() {//геттер для сортированного списка
        return prioritizedTasks;
    }

    public int getNextId() {//геттер для значения последней созданной задачи
        return nextId;
    }
}

