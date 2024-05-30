package com.yandex.practicum.handler;

import com.sun.net.httpserver.HttpExchange;
import com.yandex.practicum.intrerfaces.TaskManager;

import java.io.IOException;
import java.util.regex.Pattern;

public class PrioritizedHandler extends BaseHttpHandler {
    public PrioritizedHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        try {
            switch (method) {
                case "GET": {
                    if (Pattern.matches("^/prioritized", path)) {
                        String response = gson.toJson(taskManager.getPrioritizedTasks());
                        sendText(exchange, response);
                        exchange.sendResponseHeaders(200, 0);
                    } else {
                        sendNotFound(exchange, "Неверный путь");
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
