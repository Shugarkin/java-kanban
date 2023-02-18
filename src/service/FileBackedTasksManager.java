package service;

import model.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;


public class FileBackedTasksManager extends InMemoryTaskManager {
    private static final String HEADER = "id,type,name,status,description,start,finish/duration,epicId/duration";
    private final File file;
    public FileBackedTasksManager(File file) {
        super();
        this.file = file;
    }

    private void save() { //метод для добавления списка задач и истории в файл
        try (Writer fileWriter = new FileWriter(file)) {
            StringBuilder sb = new StringBuilder();
            sb.append(HEADER); //Заголовок файла, вынесенный в поля класса в виде статической константы
            for (Task task : tasks.values()) {
                sb.append(task);
            }
            for (Epic epic : epics.values()) {
            sb.append(epic);
            }
            for (SubTask subTask : subTasks.values()) {
            sb.append(subTask);
            }
            sb.append("\n");
            sb.append("\n");
            historyToString(sb);
            fileWriter.write(sb.toString());
        } catch (IOException e) {
            throw new ManagerSaveException("Что-то пошло не так");
        }

    }

    private void historyToString(StringBuilder sb) {
        List<Tasks> history = historyManager.getHistory();
        if (!history.isEmpty()) {
            sb.append(history.get(0).getId());
        }
        for (int i = 1; i < history.size(); i++) {
            sb.append(",").append(history.get(i).getId());
        }
    }


    private Tasks fromString(String value) { //метод для создания задачи из строки
        Tasks task;
        String[] split = value.split(",");
        int id = Integer.parseInt(split[0]);
        String title = split[2];
        String description = split[4];
        Status status = Status.valueOf(split[3]);
        String name = split[1];
        String startString = split[5];
        LocalDateTime start;
        if(startString.equals("null")){
            start = null;
        } else {
            start = LocalDateTime.parse(startString);
        }
        if(name.equals("Epic")) {
            String finishString = split[6];
            LocalDateTime finish;
            if(finishString.equals("null")){
                finish = null;
            } else {
                finish = LocalDateTime.parse(finishString);
            }
            long duration = Long.parseLong(split[7]);
            task = new Epic(id, title, description, status, start, finish, duration);
            epics.put(id, (Epic) task);
            nextId++;
        } else if(name.equals("Task")) {
            long duration = Long.parseLong(split[6]);
            task = new Task(id, title, description, status, start, duration);
            tasks.put(id, (Task) task);
            nextId++;
            prioritizedTasks.add(task);
        } else {
            long duration = Long.parseLong(split[6]);
            int epicId = Integer.parseInt(split[7]);
            task = new SubTask(id, title, description, status, start, duration, epicId);
            subTasks.put(id, (SubTask) task);
            epics.get(((SubTask) task).getEpicId()).addSubTaskId(id);
            nextId++;
            prioritizedTasks.add(task);
        }
        return task;
    }

    private void fileToHistory (String[] history) { //метод берет из листа с истории файла и добавляяет в обычную
            for (int i = 0; i < history.length; i++) {
                int key = Integer.valueOf(history[i]);
                if (tasks.containsKey(key)) {
                    historyManager.add(tasks.get(key));
                }
                if (epics.containsKey(key)) {
                    historyManager.add(epics.get(key));
                }
                if (subTasks.containsKey(key)) {
                    historyManager.add(subTasks.get(key));
                }
            }
        }


    public static FileBackedTasksManager  loadFromFile(File file) { //метод создает новый FileBackedTasksManager из файла
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            bufferedReader.readLine();
            String str;
            while (bufferedReader.ready()) {
                str = bufferedReader.readLine();
                if (str.isBlank()) {
                    str = bufferedReader.readLine();
                    if (str != null && !str.isBlank()) {
                        String[] history = str.split(",");
                        fileBackedTasksManager.fileToHistory(history);
                        break;
                    }
                } else {
                    fileBackedTasksManager.fromString(str);
                }
            }
        } catch (IOException e) {
            System.out.println("Что-то пошло не так");
        }
            return fileBackedTasksManager;
        }



    // ниже переопределенные методы
    @Override
    public void addTask(Task task)  { //добавление таска
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) { //добавление эпика
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubTask(SubTask subTask) {//добавление сабтаска
        super.addSubTask(subTask);
        save();
    }

    @Override
    public void deleteTask() {//очищает задачи
        super.deleteTask();
        save();
    }

    @Override
    public void deleteEpic() {//очищает эпики
        super.deleteEpic();
        save();
    }
    @Override
    public void deleteSubTask() {//очищает подзадачи
        super.deleteSubTask();
        save();
    }

    @Override
    public void updateTask(Task task) {//заменяет старую задачу новой
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {//заменяет старый эпик новым
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {//заменяет старую подзадачу новой
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void deleteTaskForId(int id) {//удаляет задачу по id
        super.deleteTaskForId(id);
        save();
    }

    @Override
    public void deleteEpicForId(int id) {//удаляет эпик по id
        super.deleteEpicForId(id);
        save();
    }
    @Override
    public void deleteSubTaskForId(int id) {//удаляет подзадачу по id
        super.deleteSubTaskForId(id);
        save();
    }
    @Override
    public Task getTask(Integer id) {
        Task task = super.getTask(id);
        save();
        return task;
    }
    @Override
    public Epic getEpic(Integer id) {
        Epic epic = super.getEpic(id);
        save();
        return epic;
    }
    @Override
    public SubTask getSubTask(Integer id) {
        SubTask subTask = super.getSubTask(id);
        save();
        return subTask;
    }

}


