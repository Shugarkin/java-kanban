package service;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import model.Epic;
import model.SubTask;
import model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    static Gson gson;
    private HttpServer httpServer;
    static  TaskManager taskManager;

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskManager = taskManager;
        gson = Managers.getGson();
        httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TasksHandler());
    }

    public void start() {
        System.out.println("HttpTaskServer запущен на " + PORT);
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(1);
    }

    static class TasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) {
            try {
                String path = httpExchange.getRequestURI().getPath();
                String method = httpExchange.getRequestMethod();
                switch (method) {
                    case "DELETE":
                        if (Pattern.matches("^/tasks/task/$", path)) {
                            if(httpExchange.getRequestURI().getQuery() == null) {
                                taskManager.deleteTask();
                                System.out.println("Удаленны все задачи");
                                httpExchange.sendResponseHeaders(200, 0);
                            } else {
                                String query = httpExchange.getRequestURI().getQuery();
                                int id = parsePathId(query.replaceFirst("id=", ""));
                                if (id != -1) {
                                    taskManager.deleteTaskForId(id);
                                    httpExchange.sendResponseHeaders(200, 0);
                                    System.out.println("Задача " + id + " удаленна");
                                } else {
                                    System.out.println("Неверный id -" + id);
                                    httpExchange.sendResponseHeaders(405, 0);
                                }
                            }
                        } else if (Pattern.matches("^/tasks/epic/$", path)) {
                             if(httpExchange.getRequestURI().getQuery() == null) {
                                 taskManager.deleteEpic();
                                 System.out.println("Удаленны все эпики");
                                 httpExchange.sendResponseHeaders(200, 0);
                             } else {
                                 String query = httpExchange.getRequestURI().getQuery();
                                 int id = parsePathId(query.replaceFirst("id=", ""));
                                 if (id != -1) {
                                     taskManager.deleteEpicForId(id);
                                     httpExchange.sendResponseHeaders(200, 0);
                                     System.out.println("Эпик " + id + " удаленна");
                                 } else {
                                     System.out.println("Неверный id -" + id);
                                     httpExchange.sendResponseHeaders(405, 0);
                                 }
                             }
                        } else if (Pattern.matches("^/tasks/subtask/$", path)) {
                            if(httpExchange.getRequestURI().getQuery() == null) {
                                taskManager.deleteSubTask();
                                System.out.println("Удаленны все подзадачи");
                                httpExchange.sendResponseHeaders(200, 0);
                            } else {
                                String query = httpExchange.getRequestURI().getQuery();
                                int id = parsePathId(query.replaceFirst("id=", ""));
                                if (id != -1) {
                                    taskManager.deleteSubTaskForId(id);
                                    httpExchange.sendResponseHeaders(200, 0);
                                    System.out.println("Подзадача " + id + " удаленна");
                                } else {
                                    System.out.println("Неверный id -" + id);
                                    httpExchange.sendResponseHeaders(405, 0);
                                }
                            }
                        } else {
                            System.out.println("Ошибка пути удаления задачи");
                            httpExchange.sendResponseHeaders(405, 0);
                                }
                        break;
                    case "GET":
                        if (Pattern.matches("^/tasks/task/$", path)) {
                            if(httpExchange.getRequestURI().getQuery() == null) {
                                String answer = gson.toJson(taskManager.getTasks());
                                sendText(httpExchange, answer);
                                System.out.println("Получены все задачи");
                                httpExchange.sendResponseHeaders(200, 0);
                            } else {
                                String query = httpExchange.getRequestURI().getQuery();
                                int id = parsePathId(query.replaceFirst("id=", ""));
                                if (id != -1) {
                                    String answer = gson.toJson(taskManager.getTask(id));
                                    sendText(httpExchange, answer);
                                    httpExchange.sendResponseHeaders(200, 0);
                                    System.out.println("Задача " + id + " получена");
                                } else {
                                    System.out.println("Неверный id -" + id);
                                    httpExchange.sendResponseHeaders(405, 0);
                                }
                            }
                        } else if (Pattern.matches("^/tasks/epic/$", path)) {
                            if(httpExchange.getRequestURI().getQuery() == null) {
                                String answer = gson.toJson(taskManager.getEpics());
                                sendText(httpExchange, answer);
                                System.out.println("Получены все эпики");
                                httpExchange.sendResponseHeaders(200, 0);
                            } else {
                                String query = httpExchange.getRequestURI().getQuery();
                                int id = parsePathId(query.replaceFirst("id=", ""));
                                if (id != -1) {
                                    String answer = gson.toJson(taskManager.getEpic(id));
                                    sendText(httpExchange, answer);
                                    httpExchange.sendResponseHeaders(200, 0);
                                    System.out.println("Эпик " + id + " получен");
                                } else {
                                    System.out.println("Неверный id -" + id);
                                    httpExchange.sendResponseHeaders(405, 0);
                                }
                            }
                        } else if (Pattern.matches("^/tasks/subtask/$", path)) {
                            if(httpExchange.getRequestURI().getQuery() == null) {
                                String answer = gson.toJson(taskManager.getSubTasks());
                                sendText(httpExchange, answer);
                                System.out.println("Получены все подзадачи");
                                httpExchange.sendResponseHeaders(200, 0);
                            } else {
                                String query = httpExchange.getRequestURI().getQuery();
                                int id = parsePathId(query.replaceFirst("id=", ""));
                                if (id != -1) {
                                    String answer = gson.toJson(taskManager.getSubTask(id));
                                    sendText(httpExchange, answer);
                                    httpExchange.sendResponseHeaders(200, 0);
                                    System.out.println("Подзадача " + id + " получена");
                                } else {
                                    System.out.println("Неверный id -" + id);
                                    httpExchange.sendResponseHeaders(405, 0);
                                }
                            }
                        } else if (Pattern.matches("^/tasks/subtask/epic/$", path)) {
                            String query = httpExchange.getRequestURI().getQuery();
                            int id = parsePathId(query.replaceFirst("id=", ""));
                            if (id != -1) {
                                String answer = gson.toJson(taskManager.getSubtasksByEpic(id));
                                sendText(httpExchange, answer);
                                httpExchange.sendResponseHeaders(200, 0);
                                System.out.println("Подзадачи эпика " + id + " получены");
                            } else {
                                System.out.println("Неверный id -" + id);
                                httpExchange.sendResponseHeaders(405, 0);
                            }
                        } else if (Pattern.matches("^/tasks/history$", path)) {
                            String answer = gson.toJson(taskManager.getHistory());
                            sendText(httpExchange, answer);
                            System.out.println("История получена");
                            httpExchange.sendResponseHeaders(200, 0);
                        } else if (Pattern.matches("^/tasks/$", path)) {
                            String answer = gson.toJson(taskManager.getPrioritizedTasks());
                            sendText(httpExchange, answer);
                            System.out.println("Сортированный список получен");
                            httpExchange.sendResponseHeaders(200, 0);
                        } else {
                            System.out.println("Ошибка пути получения задачи");
                            httpExchange.sendResponseHeaders(405, 0);
                        }
                        break;
                    case "POST":
                        if (Pattern.matches("^/tasks/task/", path)) {
                            InputStream input = httpExchange.getRequestBody();
                            String taskString = new String(input.readAllBytes(), DEFAULT_CHARSET);
                            Task task = gson.fromJson(taskString, Task.class);
                            if (taskManager.getTasks().contains(task)) {
                                taskManager.updateTask(task);
                                httpExchange.sendResponseHeaders(200, 0);
                                System.out.println("Задача успешно заменена");
                            } else {
                                taskManager.addTask(task);
                                httpExchange.sendResponseHeaders(200, 0);
                                System.out.println("Задача успешно добавлена");
                            }
                        } else if (Pattern.matches("^/tasks/epic/", path)) {
                            InputStream input = httpExchange.getRequestBody();
                            String epicString = new String(input.readAllBytes(), DEFAULT_CHARSET);
                            Epic epic = gson.fromJson(epicString, Epic.class);
                            epic.setSubTaskIds(new ArrayList<>());//иначе поле subtaskIds выдает null если его не указывать при создании
                            if (taskManager.getEpics().contains(epic)) {
                                taskManager.updateEpic(epic);
                                httpExchange.sendResponseHeaders(200, 0);
                                System.out.println("Эпик успешно заменен");
                            } else {
                                taskManager.addEpic(epic);
                                httpExchange.sendResponseHeaders(200, 0);
                                System.out.println("Эпик успешно добавлен");
                            }
                        } else if (Pattern.matches("^/tasks/subtask/", path)) {
                            InputStream input = httpExchange.getRequestBody();
                            String subtaskString =
                                    new String(input.readAllBytes(), DEFAULT_CHARSET);
                            SubTask subtask = gson.fromJson(subtaskString, SubTask.class);
                            if (taskManager.getSubTasks().contains(subtask)) {
                                taskManager.updateSubTask(subtask);
                                httpExchange.sendResponseHeaders(200, 0);
                                System.out.println("Подзадача успешно заменена");
                            } else {
                                taskManager.addSubTask(subtask);
                                httpExchange.sendResponseHeaders(200, 0);
                                System.out.println("Подзадача успешно добавлена");
                            }
                        } else {
                            httpExchange.sendResponseHeaders(405, 0);
                            System.out.println("Произошли технические шоколадки");
                        }
                        break;
                    default: {
                        System.out.println("Неизвестный запрос - " + method);
                        httpExchange.sendResponseHeaders(405, 0);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                httpExchange.close();
            }
        }

        private int parsePathId(String path) {
            try {
                return Integer.parseInt(path);
            } catch (NumberFormatException e) {
                return -1;
            }
        }

        protected void sendText(HttpExchange h, String text) throws IOException {
            byte[] resp = text.getBytes(UTF_8);
            h.getResponseHeaders().add("Content-Type", "application/json");
            h.sendResponseHeaders(200, resp.length);
            h.getResponseBody().write(resp);
        }
    }
}
