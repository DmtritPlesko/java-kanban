package com.yandex.practicum.handler;

import com.sun.net.httpserver.HttpExchange;
import com.yandex.practicum.intrerfaces.TaskManager;
import com.yandex.practicum.models.Task;
import com.yandex.practicum.service.InMemoryTaskManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class TaskManagerHandler extends BaseHttpHandler {
    public TaskManagerHandler(TaskManager manager) {
        taskManager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            taskManager = new InMemoryTaskManager();
            switch (method) {
                case "GET": {
                    if (Pattern.matches("^/tasks$", path)) {

                        String response = gson.toJson(taskManager.getListTask());
                        sendText(exchange, response);
                        exchange.sendResponseHeaders(200, 0);

                    } else if (Pattern.matches("^/tasks/\\d+$", path)) {

                        String patId = path.replaceFirst("/tasks/", "");
                        int id = parseInt(patId);
                        System.out.println(id + "- вызов из обработчика ");
                        if (id != -1) {
                            String response = gson.toJson(taskManager.getTaskById(id));
                            sendText(exchange, response);
                            exchange.sendResponseHeaders(200, 0);

                        } else {

                            sendNotFound(exchange, "Неверный идентефикатор - " + patId);
                            exchange.sendResponseHeaders(404, 0);

                        }
                    } else {
                        sendNotFound(exchange, "Неверный путь: " + path);
                        exchange.sendResponseHeaders(400, 0);

                    }
                    break;
                }
                case "POST": {
                    if (Pattern.matches("^/tasks$", path)) {
                        String requestBody = String.valueOf(new BufferedReader(
                                new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8)));
                        Task task = gson.fromJson(requestBody, Task.class);
                        taskManager.createNewTask(task);
                        sendText(exchange, "Задача добавлена");
                    } else {
                        sendNotFound(exchange, "Неверный путь");
                        exchange.sendResponseHeaders(400, 0);
                    }
                    break;
                }
                case "DELETE": {
                    if (Pattern.matches("^/tasks/\\d+$", path)) {

                        String pathId = path.replaceFirst("/tasks/", "");
                        int id = parseInt(pathId);

                        if (id != -1) {

                            taskManager.deleteTaskForID(id);
                            sendText(exchange, "Задача под идентификатором - " + id + "удалена");
                            exchange.sendResponseHeaders(200, 0);

                        } else {

                            sendNotFound(exchange, "Неверный идентификатор - " + pathId);
                            exchange.sendResponseHeaders(404, 0);

                        }
                    } else {
                        sendNotFound(exchange, "Неверный путь: " + path);
                        exchange.sendResponseHeaders(400, 0);

                    }
                    break;
                }
                default: {
                    sendNotFound(exchange, "Неверный метод");
                    exchange.sendResponseHeaders(400, 0);
                }
            }
        } catch (Exception e) {
            sendNotFound(exchange, e.getMessage());
        }
    }

    protected int parseInt(String path) {
        try {
            return Integer.parseInt(path);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}

