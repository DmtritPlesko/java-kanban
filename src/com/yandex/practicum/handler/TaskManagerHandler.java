package com.yandex.practicum.handler;


import com.sun.net.httpserver.HttpExchange;
import com.yandex.practicum.intrerfaces.TaskManager;
import com.yandex.practicum.models.Task;

import java.io.IOException;
import java.util.regex.Pattern;

public class TaskManagerHandler extends BaseHttpHandler {
    public TaskManagerHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            switch (method) {
                case "GET": {
                    if (Pattern.matches("^/tasks$", path)) {

                        String response = gson.toJson(taskManager.getListTask());
                        sendText(exchange, response);

                    } else if (Pattern.matches("^/tasks/\\d+$", path)) {

                        String patId = path.replaceFirst("/tasks/", "");
                        int id = parseInt(patId);
                        System.out.println(id + "- вызов из обработчика ");
                        if (id != -1) {
                            String response = gson.toJson(taskManager.getTaskById(id));
                            System.err.println(taskManager.getTaskById(id));
                            sendText(exchange, response);

                        } else {

                            sendNotFound(exchange, "Неверный идентефикатор - " + patId);

                        }
                    } else {
                        sendNotFound(exchange, "Неверный путь: " + path);

                    }
                    break;
                }
                case "POST": {
                    try {
                        if (Pattern.matches("^/tasks$", path)) {
                            Task task = gson.fromJson(readText(exchange), Task.class);
                            taskManager.createNewTask(task);
                            sendText(exchange, "Задача добавлена");
                        } else {
                            System.err.println("Ошибка в елс");
                            sendNotFound(exchange, "Неверный путь");
                        }
                    } catch (Exception e) {
                        sendNotFound(exchange,e.getMessage());
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

                        } else {

                            sendNotFound(exchange, "Неверный идентификатор - " + pathId);

                        }
                    } else {
                        sendNotFound(exchange, "Неверный путь: " + path);

                    }
                    break;
                }
                default: {
                    sendNotFound(exchange, "BadRequest");
                }
            }
        } catch (Exception e) {
            sendNotFound(exchange, e.getMessage());
        }
    }
}

