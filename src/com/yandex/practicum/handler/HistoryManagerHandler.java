package com.yandex.practicum.handler;

import com.sun.net.httpserver.HttpExchange;
import com.yandex.practicum.intrerfaces.TaskManager;

import java.io.IOException;
import java.util.regex.Pattern;

public class HistoryManagerHandler extends BaseHttpHandler {
    public HistoryManagerHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        try {
            switch (method) {
                case "GET": {
                    if (Pattern.matches("^/history$", path)) {
                        super.sendText(exchange, gson.toJson(taskManager.getHistory()));
                    } else {

                        sendNotFound(exchange, "Неверный запрос");
                        exchange.sendResponseHeaders(400, 0);

                    }
                }
                default: {
                    sendNotFound(exchange, "BadRequest");
                }
            }
        } catch (Exception e) {
            sendText(exchange, e.getMessage());
        }

    }
}
