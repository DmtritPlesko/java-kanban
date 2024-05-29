package com.yandex.practicum.handler;

import com.sun.net.httpserver.HttpExchange;
import com.yandex.practicum.intrerfaces.TaskManager;
import com.yandex.practicum.models.Epic;

import java.io.IOException;
import java.util.regex.Pattern;

public class EpicManagerHandler extends BaseHttpHandler {
    public EpicManagerHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        try {
            switch (method) {
                case "GET": {
                    if (Pattern.matches("^/epics$", path)) {

                        String response = gson.toJson(taskManager.getListEpic());
                        sendText(exchange, response);
                        exchange.sendResponseHeaders(200, 0);

                    } else if (Pattern.matches("^/epics/\\d+$", path)) {

                        String pathId = path.replaceFirst("/epics/", "");
                        int id = parseInt(pathId);

                        if (id != -1) {

                            Epic epic = taskManager.getEpicById(id);
                            String response = gson.toJson(epic);
                            sendText(exchange, response);

                        } else {

                            sendNotFound(exchange, "Неверный идентификатор - " + pathId);
                            exchange.sendResponseHeaders(404, 0);

                        }
                    } else if (Pattern.matches("^/epics/\\d+/subtasks$", path)) {

                        String pathId = path.replaceFirst("/epics/", "");
                        int id = parseInt(pathId.replaceFirst("/subtasks", ""));

                        if (id != -1) {
                            String str = gson.toJson(taskManager.getSubtasksByEpicId(id));
                            sendText(exchange, str);

                        } else {

                            sendNotFound(exchange, "Неверный идентификатор - "
                                    + pathId.replaceFirst("/subtasks", ""));
                            exchange.sendResponseHeaders(404, 0);

                        }
                    } else {
                        sendNotFound(exchange, "Неверный путь: " + path);
                        exchange.sendResponseHeaders(400, 0);
                    }
                }
                case "POST": {
                    Epic epic = gson.fromJson(readText(exchange), Epic.class);
                    taskManager.createNewEpic(epic);
                    sendText(exchange, "Задача добавлена");
                }
                case "DELETE": {
                    if (Pattern.matches("^/epics/\\d+$", path)) {

                        String pathID = path.replaceFirst("/epics/", "");
                        int id = parseInt(pathID);

                        if (id != -1) {
                            taskManager.deleteEpicForID(id);
                            sendText(exchange, "Задача удалена");
                        } else {

                            sendNotFound(exchange, "Неверный идентификатор: " + pathID);

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
