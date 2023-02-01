package service;

import model.Epic;
import model.SubTask;
import model.Task;
import model.Tasks;

import java.io.*;
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
//        TaskManager taskManager = new FileBackedTasksManager(file);

        TaskManager taskManager = new FileBackedTasksManager(file);

        taskManager.addTask(new Task("Наладить личную жизнь", "Tinder в помощь", "IN_PROGRESS"));
        taskManager.addTask(new Task("Забыть бывшую", "Макс Корж ты где?", "IN_PROGRESS"));

        taskManager.addEpic(new Epic("Прогулка", "Прогулка по парку"));

        taskManager.addSubTask(new SubTask("Одеться", "Как без одежды то", "NEW", 3));
        taskManager.addSubTask(new SubTask("Выйти на улицу", "Дома же не погуляешь", "DONE", 3));
        taskManager.addSubTask(new SubTask("Я все таки смог", "Решить этот спринт", "DONE", 3));

        taskManager.addEpic(new Epic("Приборка дома", "Как бы грязно уже"));


        System.out.println(taskManager.printTask(1));
        System.out.println(taskManager.printTask(2));
        System.out.println(taskManager.printEpic(3));
        System.out.println(taskManager.printSubTask(4));
        System.out.println(taskManager.printSubTask(5));
        System.out.println(taskManager.printSubTask(6));
        System.out.println(taskManager.printEpic(3));
        System.out.println(taskManager.printEpic(7));
        System.out.println(taskManager.printEpic(7));
        System.out.println(taskManager.printEpic(7));
        System.out.println(taskManager.printSubTask(5));
        System.out.println(taskManager.printSubTask(4));
        System.out.println(taskManager.printTask(1));


        FileBackedTasksManager fileBackedTasksManager = loadFromFile(file);


    }


    List<String> saveList = new ArrayList<>(); //для задач и истории
    List<String> saveListTask = new ArrayList<>(); //лист для тасков

    List<String> readList = new ArrayList<>(); //лист для вывода задач из файла
    List<String> readListHistory = new ArrayList<>(); //лист для вывода истории из файла

    public void save() { //метод для добавления списка задач и истории в файл
        saveAllList();
        if (!saveList.isEmpty()) {
            try (Writer fileWriter = new FileWriter(file)) {
                for (String s : saveList) {
                    fileWriter.write(s);
                }
            } catch (IOException e) {
                throw new ManagerSaveException("Что-то пошло не так");
            }
        } else {
            saveList.clear();
            try (Writer fileWriter = new FileWriter("TW 6")) {
                for (String s : saveList) {
                    fileWriter.write(s); // здесь над о подумать
                }
            } catch (IOException e) {
                throw new ManagerSaveException("Что-то пошло не так");
            }
        }
    }

    public String toString(Tasks taskForString) { //метод для перевода таска в строку
        if (tasks.containsKey(taskForString.getId())) {
            String a = String.valueOf(tasks.get(taskForString.getId()));
            String b = translationTasks(a);
            return b;
        } else if (epics.containsKey(taskForString.getId())) {
            String a = String.valueOf(epics.get(taskForString.getId()));
            String b = translationTasks(a);
            return b;
        } else if (subTasks.containsKey(taskForString.getId())) {
            String a = String.valueOf(subTasks.get(taskForString.getId()));
            String b = translationTasks(a);
            return b;
        } else {
            return "Что-то пошло не так((";
        }
    }

    public String translationTasks(String stringTask) { //метод для разбивки строки по условию задания
        String id = stringTask.substring(stringTask.indexOf("id=") + 3, stringTask.indexOf(", title='"));
        String title = stringTask.substring(stringTask.indexOf("title='") + 7, stringTask.indexOf("', description"));
        String description = stringTask.substring(stringTask.indexOf("description='") + 13, stringTask.indexOf(", status") - 1);
        String tasksName = stringTask.substring(stringTask.indexOf(0) + 2, stringTask.indexOf("{i"));
        if (stringTask.contains("epicId")) {
            String status1 = stringTask.substring(stringTask.indexOf("status='") + 8, stringTask.indexOf("', epicId"));
            String epicId = stringTask.substring(stringTask.indexOf("epicId='") + 8, stringTask.indexOf("'}"));
            String all1 = id + "," + tasksName + "," + title + "," + status1 + "," + description + "," + epicId + "\n";
            return all1;
        } else {
            String status = stringTask.substring(stringTask.indexOf("status='") + 8, stringTask.indexOf("'}"));
            String all = id + "," + tasksName + "," + title + "," + status + "," + description + "\n";
            return all;
        }
    }

    static String historyToString(HistoryManager manager) { //метод для записи айди задачи из истории в лист
        String superA = "";
        if (historyList.isEmpty()) {
            historyList.add(manager.getHistory().toString());
            for (String s : historyList) {
                String id = s.substring(s.indexOf("id=") + 3, s.indexOf(", title='"));
                superA += id + ",";
            }
        } else {
            historyList.clear();
            historyList.add(manager.getHistory().toString());
            for (String s : historyList) {
                String[] mass = s.split("}");
                int a = mass.length;
                mass[a - 1] = null;
                for (String mass1 : mass) {
                    if (mass1 != null) {
                        String id = mass1.substring(mass1.indexOf("id=") + 3, mass1.indexOf(", title='"));
                        superA += id + ",";
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
            saveList.add("id,type,name,status,description,epic\n");
            for (String s : saveListTask) {
                saveList.add(s);
            }
            saveList.add("\n");
            for (String s : historyList) {
                saveList.add(s);
            }
        } else {
            saveList.clear();
            saveList.add("id,type,name,status,description,epic\n");
            for (String s : saveListTask) {
                saveList.add(s);
            }
            saveList.add("\n");
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

    protected void listToTask() { //метод для создания задач
        fileForTask(file);
        for (String s : readList) {
            fromString(s);
        }
    }



    public Tasks fromString(String value) { //метод для создания задачи из строки
        Tasks task = null;
        String[] split = value.split(",");
        if (split.length == 5) {
            int id = Integer.parseInt(split[0]);
            String title = split[2];
            String description = split[4];
            String status = split[3];
            String name = split[1];
            if(name.equals("Epic")) {
                task = new Epic(id, title, description, status);
                addEpic((Epic) task);
            } else if(name.equals("Task")) {
                task = new Task(id, title, description, status);
                addTask((Task) task);
            }
        } else {
            int id = Integer.parseInt(split[0]);
            String title = split[2];
            String description = split[4];
            String status = split[3];
            int epicId = Integer.parseInt(split[5]);
            task = new SubTask(id, title, description, status, epicId);
            addSubTask((SubTask) task);
        }
        return task;
    }

    public void fileToHistory () { //метод берет из листа с истории файла и добавляяет в обычную
        readListHistory.remove(0);
        for (String s : readListHistory) {
            String[] split = s.split(",");
            for (int i = 0; i < split.length; i++) {
                if (tasks.containsKey(Integer.valueOf(split[i]))) {
                    printTask(Integer.valueOf(split[i]));
                }
                if (epics.containsKey(Integer.valueOf(split[i]))) {
                    printEpic(Integer.valueOf(split[i]));
                }
                if (subTasks.containsKey(Integer.valueOf(split[i]))) {
                    printSubTask(Integer.valueOf(split[i]));
                }
            }
        }
        }

        public void recovery () { //метод обьединяющий добавление задач и истории из файла
            listToTask();
            fileToHistory();
        }

    static FileBackedTasksManager  loadFromFile(File file) { //метод создает новый FileBackedTasksManager из файла
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        fileBackedTasksManager.recovery();
        return fileBackedTasksManager;
    }


    // ниже переопределенные методы
    @Override
    public void addTask(Task task) { //добавление таска
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
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(Integer id) {//геттер для эпика
        Epic epic = epics.get(id);
        historyManager.add(epic);
        addHistoryToList(historyManager);
        save();
        return epics.get(id);
    }

    @Override
    public SubTask getSubTask(Integer id) {//геттер для сабтаска
        SubTask subTask = subTasks.get(id);
        historyManager.add(subTask);
        addHistoryToList(historyManager);
        save();
        return subTasks.get(id);
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
    public void newTask(int id, Task task) {//заменяет старую задачу новой
        super.newTask(id, task);
        addToTask();
        save();
    }

    @Override
    public void newEpic(int id, Epic epic) {//заменяет старый эпик новым
        super.newEpic(id, epic);
        addToTask();
        save();
    }

    @Override
    public void newSubTask(int id, SubTask subTask) {//заменяет старую подзадачу новой
        super.newSubTask(id, subTask);
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
    class ManagerSaveException extends RuntimeException {
        public ManagerSaveException() {
        }

        public ManagerSaveException(final String message) {
            super(message);
        }

        public ManagerSaveException(final String message, final Throwable cause) {
            super(message, cause);
        }

        public ManagerSaveException(final Throwable cause) {
            super(cause);
        }
    }
