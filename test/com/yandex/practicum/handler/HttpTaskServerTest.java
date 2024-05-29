package com.yandex.practicum.handler;

import com.yandex.practicum.adapter.*;
import com.yandex.practicum.intrerfaces.TaskManager;
import com.yandex.practicum.server.HttpTaskServer;
import com.yandex.practicum.service.InMemoryTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import com.google.gson.*;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServerTest {

    public static TaskManager manager;
    public HttpTaskServer server;
    public Gson gson;

    private void createElement () throws IOException {
        try {
            manager = new InMemoryTaskManager();
            server = new HttpTaskServer(manager);
            gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .registerTypeAdapter(Duration.class, new DurationAdapter())
                    .create();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    @BeforeEach
    void setUp() throws IOException {
        createElement();
        this.server.startServer();
    }

    @AfterEach
    void tearDown() {
        this.server.shutDownServer();
    }
}