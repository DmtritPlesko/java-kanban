package com.yandex.practicum.handler;

import com.sun.net.httpserver.HttpExchange;
import com.yandex.practicum.intrerfaces.TaskManager;
import com.yandex.practicum.models.Subtask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class SubtaskManagerHandler extends TaskManagerHandler {
    public SubtaskManagerHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        try {
            switch (method) {
                case "GET": {
                    if (Pattern.matches("^/subtasks$", path)) {

                        String response = gson.toJson(taskManager.getListSubtask());
                        sendText(exchange, response);
                        exchange.sendResponseHeaders(200, 0);

                    } else if (Pattern.matches("^/subtasks/\\d+$", path)) {

                        String pathId = path.replaceFirst("/subtasks/", "");
                        int id = parseInt(pathId);

                        if (id != -1) {
                            sendText(exchange, taskManager.getSubtaskById(id).toString());
                            exchange.sendResponseHeaders(200, 0);
                            sendText(exchange, "Подзадача с идентификатором - " + id);
                        } else {
                            sendNotFound(exchange, "Неверный идентификатор подзадачи - " + pathId);
                            exchange.sendResponseHeaders(404, 0);
                        }

                    } else {
                        sendNotFound(exchange, "Неверный путь");
                        exchange.sendResponseHeaders(400, 0);
                    }
                }
                case "POST": {
                    if (Pattern.matches("^/sudtasks", path)) {
                        String responseBody = String.valueOf(new BufferedReader(
                                new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8)));
                        Subtask sub = gson.fromJson(responseBody, Subtask.class);
                        taskManager.createNewSubtask(sub);
                        sendText(exchange, "Задача добавлена");
                    } else {
                        sendNotFound(exchange, "Неверный путь");
                    }
                }
                case "DELETE": {
                    if (Pattern.matches("^/subtasks/\\d+$", path)) {

                        String pathId = path.replaceFirst("/subtasks/", "");
                        int id = parseInt(pathId);

                        if (id != -1) {
                            taskManager.getSubtaskById(id);
                            sendText(exchange, "Задача с идентификатором: - " + id + " удалена");
                        } else {
                            sendNotFound(exchange, "Неверный идентификатор задачи: " + pathId);
                        }

                    } else {

                        sendNotFound(exchange, "Неверный путь " +
                                "\npath: " + path);
                        exchange.sendResponseHeaders(400, 0);

                    }
                }
                default: {

                    sendNotFound(exchange, "Нвеерный метод");
                    exchange.sendResponseHeaders(400, 0);

                }
            }
        } catch (Exception e) {
            sendNotFound(exchange, e.getMessage());
        }
    }
}
