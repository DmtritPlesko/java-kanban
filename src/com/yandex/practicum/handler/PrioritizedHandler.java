package com.yandex.practicum.handler;

import com.sun.net.httpserver.HttpExchange;
import com.yandex.practicum.intrerfaces.HistoryManager;
import com.yandex.practicum.intrerfaces.TaskManager;
import com.yandex.practicum.service.InMemoryHistoryManager;

import java.io.IOException;
import java.util.regex.Pattern;

public class PrioritizedHandler extends TaskManagerHandler {
    public PrioritizedHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        HistoryManager manager = new InMemoryHistoryManager();
        switch (method) {
            case "GET": {
                if (Pattern.matches("^/prioritized", path)) {
                    String response = gson.toJson(taskManager.getPrioritizedTasks());
                    sendText(exchange, response);
                    exchange.sendResponseHeaders(200, 0);
                } else {
                    sendNotFound(exchange, "Неверный путь");
                    exchange.sendResponseHeaders(400, 0);
                }
            }
            default: {

            }
        }
    }
}
