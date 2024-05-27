package com.yandex.practicum.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.regex.Pattern;

public class HistoryManagerHandler extends BaseHttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        try {
            switch (method) {
                case "GET": {
                    if (Pattern.matches("^/history$", path)) {

                    } else {

                        sendNotFound(exchange, "Неверный запрос");
                        exchange.sendResponseHeaders(400, 0);

                    }
                }
                default: {

                }
            }
        } catch (Exception e) {

        }

    }
}
