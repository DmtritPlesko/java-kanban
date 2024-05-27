package com.yandex.practicum.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.yandex.practicum.intrerfaces.HistoryManager;
import com.yandex.practicum.intrerfaces.TaskManager;
import com.yandex.practicum.service.InMemoryHistoryManager;

import java.io.IOException;
import java.util.regex.Pattern;

public class EpicManagerHandler extends TaskManagerHandler {
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

                            taskManager.getEpicById(id);
                            sendText(exchange, "Эпик с идентификатором - " + id);
                            exchange.sendResponseHeaders(200, 0);

                        } else {

                            sendText(exchange, "Неверный идентификатор - " + pathId);
                            exchange.sendResponseHeaders(404, 0);

                        }
                    } else if (Pattern.matches("^/epics/\\d+/subtasks$", path)) {

                        String pathId = path.replaceFirst("/epics/", "");
                        int id = parseInt(pathId.replaceFirst("/subtasks", ""));

                        if (id != -1) {
                            String str = gson.toJson(taskManager.getSubtasksByEpicId(id));
                            sendText(exchange, str);
                            exchange.sendResponseHeaders(200, 0);

                        } else {

                            sendText(exchange, "Неверный идентификатор - "
                                    + pathId.replaceFirst("/subtasks", ""));
                            exchange.sendResponseHeaders(404, 0);

                        }
                    } else {
                        sendText(exchange, "Неверный путь: " + path);
                        exchange.sendResponseHeaders(400, 0);
                    }
                }
                case "POST": {
                    exchange.sendResponseHeaders(201, 0);
                }
                case "DELETE": {
                    if (Pattern.matches("^/epics/\\d+$", path)) {

                        String pathID = path.replaceFirst("/epics/", "");
                        int id = parseInt(pathID);

                        if (id != -1) {
                            taskManager.deleteEpicForID(id);
                            sendText(exchange, "Епик с идентификатором: " + id + " удалён");
                            exchange.sendResponseHeaders(200, 0);
                        } else {

                            sendText(exchange, "Неверный идентификатор: " + pathID);
                            exchange.sendResponseHeaders(404, 0);

                        }
                    } else {

                        sendText(exchange, "Неверный путь: " + path);
                        exchange.sendResponseHeaders(400, 0);

                    }
                }
                default: {

                }
            }
        } catch (Exception e) {
            sendText(exchange, e.getMessage());
        }
    }
}
