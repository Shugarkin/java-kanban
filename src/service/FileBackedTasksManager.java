package service;

import model.*;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;


public class FileBackedTasksManager extends InMemoryTaskManager {
    static List<String> historyList = new ArrayList<>(); //для истории
    File file;
    public FileBackedTasksManager(File file) {
        super();
        this.file = file;
    }

    public static void main(String[] args) {
        File file = new File("QWE.csv");

        TaskManager taskManager = new FileBackedTasksManager(file);

        taskManager.addTask(new Task("Наладить личную жизнь", "Tinder в помощь", Status.IN_PROGRESS,
                LocalDateTime.of(2023, Month.JANUARY,01,12,00), 30));
        taskManager.addTask(new Task("Забыть бывшую", "Макс Корж ты где?", Status.IN_PROGRESS,
                LocalDateTime.of(2023, Month.OCTOBER,01,11,00), 60));

        taskManager.addEpic(new Epic("Прогулка", "Прогулка по парку"));

        taskManager.addSubTask(new SubTask("Одеться", "Как без одежды то", Status.NEW,
                LocalDateTime.of(2022, Month.JANUARY, 01, 12, 00), 60,3));
        taskManager.addSubTask(new SubTask("Выйти на улицу", "Дома же не погуляешь", Status.DONE,
                LocalDateTime.of(2022, Month.MARCH, 01, 13, 00), 60,3));
        taskManager.addSubTask(new SubTask("Я все таки смог", "Решить этот спринт", Status.DONE,
                LocalDateTime.of(2022, Month.NOVEMBER, 01, 14, 00), 60,3));

        taskManager.addEpic(new Epic("Приборка дома", "Как бы грязно уже"));


        FileBackedTasksManager fileBackedTasksManager = loadFromFile(file);


    }


    public List<String> getSaveList() {
        return saveList;
    }

    List<String> saveList = new ArrayList<>(); //для задач и истории
    List<String> saveListTask = new ArrayList<>(); //лист для тасков

    List<String> readList = new ArrayList<>(); //лист для вывода задач из файла
    List<String> readListHistory = new ArrayList<>(); //лист для вывода истории из файла

    public void save() { //метод для добавления списка задач и истории в файл
        saveAllList();
        try (Writer fileWriter = new FileWriter(file)) {
            for (String s : saveList) {
                fileWriter.write(s);
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Что-то пошло не так");
        }
    }


    public String toString(Tasks taskForString) { //метод для перевода таска в строку
        if (tasks.containsKey(taskForString.getId())) {
            String a = String.valueOf(tasks.get(taskForString.getId()));
            return a;
        } else if (epics.containsKey(taskForString.getId())) {
            String a = String.valueOf(epics.get(taskForString.getId()));
            return a;
        } else if (subTasks.containsKey(taskForString.getId())) {
            String a = String.valueOf(subTasks.get(taskForString.getId()));
            return a;
        } else {
            return "Что-то пошло не так((";
        }
    }

    static String historyToString(HistoryManager manager) { //метод для записи айди задачи из истории в лист

        String superA = "";

        if(historyList.isEmpty()) {
            historyList.add(manager.getHistory().toString());
            for (String s : historyList) {
                String[] mass = s.split(",");
                String a = String.valueOf(mass[0].charAt(2));
                superA += a + ",";
            }
        } else {
            historyList.clear();
            historyList.add(manager.getHistory().toString());
            for (String s : historyList) {
                String[] mass = s.split("\n");
                mass[0] = null;
                for (String s1 : mass) {
                    if( s1 != null) {
                        String a = String.valueOf(s1.charAt(0));
                        superA += a + ",";
                    }
                }
            }
        }
        historyList.clear();

        String superB = superA;
        return superB;
    }

    public void addHistoryToList(HistoryManager manager) { //метод для добавления истории в лист
        historyList.add(historyToString(manager));
    }

    public void saveAllList() { // метод для обьединения листов с задачами и истории в один
        if (saveList.isEmpty()) {
            saveList.add("id,type,name,status,description,start,finish,epic\n");
            for (String s : saveListTask) {
                saveList.add(s);
            }
            saveList.add("\n\n");
            for (String s : historyList) {
                saveList.add(s);
            }
        } else {
            saveList.clear();
            saveList.add("id,type,name,status,description,start,finish,epic\n");
            for (String s : saveListTask) {
                saveList.add(s);
            }
            saveList.add("\n\n");
            for (String s : historyList) {
                saveList.add(s);
            }
        }
    }

    public void addToTask() { //метод для добавления строк из тасков в лист
        saveListTask.clear();
        int newId = 1;
        while(newId < nextId) {
            for (Integer integer1 : tasks.keySet()) {
                if (integer1 == newId) {
                    saveListTask.add(toString(tasks.get(integer1)));
                }
            }
            for (Integer integer1 : epics.keySet()) {
                if (integer1 == newId) {
                    saveListTask.add(toString(epics.get(integer1)));
                }
            }
            for (Integer integer2 : subTasks.keySet()) {
                if (integer2 == newId) {
                    saveListTask.add(toString(subTasks.get(integer2)));
                }
            }
            newId++;
        }

    }

    public void fileForTask (File file) {//метод для чтения файла
        List<String> allInFile = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.ready()) {
                String line = br.readLine();
                allInFile.add(line);
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Что-то пошло не так");
        }
        allInFile.remove(0);
        for (String s : allInFile) {
            if(s.contains("Task") || s.contains("Epic") || s.contains("SubTask")) {
                readList.add(s);
            } else {
                readListHistory.add(s);
            }
        }
    }

    protected void listToTask(){ //метод для создания задач
        fileForTask(file);
        for (String s : readList) {
            fromString(s);
        }
    }



    public Tasks fromString(String value) { //метод для создания задачи из строки
        Status statusEnum;
        Tasks task;
        String[] split = value.split(",");
        int id = Integer.parseInt(split[0]);
        String title = split[2];
        String description = split[4];
        String status = split[3];
        if(status.equals("DONE")) {
            statusEnum = Status.DONE;
        } else if (status.equals("NEW")) {
            statusEnum = Status.NEW;
        } else {
            statusEnum = Status.IN_PROGRESS;
        }
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
            task = new Epic(id, title, description, statusEnum, start, finish);
            addEpic((Epic) task);
        } else if(name.equals("Task")) {
            long duration = Long.parseLong(split[6]);
            task = new Task(id, title, description, statusEnum, start, duration);
            addTask((Task) task);
        } else {
            long duration = Long.parseLong(split[6]);
            int epicId = Integer.parseInt(split[7]);
            task = new SubTask(id, title, description, statusEnum, start, duration, epicId);
            addSubTask((SubTask) task);
        }
        return task;
    }

    public void fileToHistory () { //метод берет из листа с истории файла и добавляяет в обычную
        readListHistory.remove(0);
        readListHistory.remove(0);
        for (String s : readListHistory) {
            String[] split = s.split(",");
            for (int i = 0; i < split.length; i++) {
                if (tasks.containsKey(Integer.valueOf(split[i]))) {
                    getTask(Integer.valueOf(split[i]));
                }
                if (epics.containsKey(Integer.valueOf(split[i]))) {
                    getEpic(Integer.valueOf(split[i]));
                }
                if (subTasks.containsKey(Integer.valueOf(split[i]))) {
                    getSubTask(Integer.valueOf(split[i]));
                }
            }
        }
    }

    public void recovery()  { //метод обьединяющий добавление задач и истории из файла
        listToTask();
        fileToHistory();
    }

    public static FileBackedTasksManager  loadFromFile(File file) { //метод создает новый FileBackedTasksManager из файла
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        fileBackedTasksManager.recovery();
        return fileBackedTasksManager;
    }


    // ниже переопределенные методы
    @Override
    public void addTask(Task task)  { //добавление таска
        super.addTask(task);
        addToTask();
        save();
    }

    @Override
    public void addEpic(Epic epic) { //добавление эпика
        super.addEpic(epic);
        addToTask();
        save();
    }

    @Override
    public void addSubTask(SubTask subTask) {//добавление сабтаска
        super.addSubTask(subTask);
        addToTask();
        save();
    }

    @Override
    public Task getTask(Integer id) {//геттер для таска
        Task task = tasks.get(id);
        historyManager.add(task);
        addHistoryToList(historyManager);
        save();
        return task;
    }

    @Override
    public Epic getEpic(Integer id) {//геттер для эпика
        Epic epic = epics.get(id);
        historyManager.add(epic);
        addHistoryToList(historyManager);
        save();
        return epic;
    }

    @Override
    public SubTask getSubTask(Integer id) {//геттер для сабтаска
        SubTask subTask = subTasks.get(id);
        historyManager.add(subTask);
        addHistoryToList(historyManager);
        save();
        return subTask;
    }

    @Override
    public void deleteTask() {//очищает задачи
        super.deleteTask();
        addToTask();
        save();
    }

    @Override
    public void deleteEpic() {//очищает эпики
        super.deleteEpic();
        addToTask();
        save();
    }
    @Override
    public void deleteSubTask() {//очищает подзадачи
        super.deleteSubTask();
        addToTask();
        save();
    }

    @Override
    public void updateTask(Task task) {//заменяет старую задачу новой
        super.updateTask(task);
        addToTask();
        save();
    }

    @Override
    public void updateEpic(Epic epic) {//заменяет старый эпик новым
        super.updateEpic(epic);
        addToTask();
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {//заменяет старую подзадачу новой
        super.updateSubTask(subTask);
        addToTask();
        save();
    }

    @Override
    public void deleteTaskForId(int id) {//удаляет задачу по id
        super.deleteTaskForId(id);
        addToTask();
        save();
    }

    @Override
    public void deleteEpicForId(int id) {//удаляет эпик по id
        super.deleteEpicForId(id);
        addToTask();
        save();
    }
    @Override
    public void deleteSubTaskForId(int id) {//удаляет подзадачу по id
        super.deleteSubTaskForId(id);
        addToTask();
        save();
    }

}


