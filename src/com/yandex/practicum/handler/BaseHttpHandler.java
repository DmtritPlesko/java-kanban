package com.yandex.practicum.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.yandex.practicum.intrerfaces.TaskManager;
import com.google.gson.*;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class BaseHttpHandler implements HttpHandler {

    protected TaskManager taskManager;
    protected Gson gson;

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }

    public void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected String readText(HttpExchange e) throws IOException {
        return new String(e.getRequestBody().readAllBytes(), UTF_8);
    }

    public void sendNotFound(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(404, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    public void sendHasInteractions(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(406, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }
}
