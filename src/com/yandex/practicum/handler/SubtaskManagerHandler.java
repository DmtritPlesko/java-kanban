package com.yandex.practicum.handler;

import com.sun.net.httpserver.HttpExchange;
import com.yandex.practicum.intrerfaces.TaskManager;
import com.yandex.practicum.models.Subtask;

import java.io.IOException;
import java.util.regex.Pattern;

public class SubtaskManagerHandler extends BaseHttpHandler {
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

                    } else if (Pattern.matches("^/subtasks/\\d+$", path)) {

                        String pathId = path.replaceFirst("/subtasks/", "");
                        int id = parseInt(pathId);

                        if (id != -1) {
                            String response = gson.toJson(taskManager.getSubtaskById(id));
                            sendText(exchange, response);
                        } else {
                            sendNotFound(exchange, "Неверный идентификатор подзадачи - " + pathId);
                        }

                    } else {
                        sendNotFound(exchange, "Неверный путь");
                    }
                }
                case "POST": {
                    if (Pattern.matches("^/subtasks$", path)) {
                        Subtask sub = gson.fromJson(readText(exchange), Subtask.class);
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
                            taskManager.deleteSubtaskForID(id);
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
                    sendNotFound(exchange, "BadRequest");
                }
            }
        } catch (Exception e) {
            sendNotFound(exchange, e.getMessage());
        }
    }
}
