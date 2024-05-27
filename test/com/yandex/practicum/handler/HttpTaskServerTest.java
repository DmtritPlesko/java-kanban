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

    public TaskManager manager;
    public HttpTaskServer server;
    public Gson gson;

    public HttpTaskServerTest() throws IOException {
        manager = new InMemoryTaskManager();
        server = new HttpTaskServer(manager);

    }

    @BeforeEach
    void setUp() throws IOException {
        this.server.startServer();
    }

    @AfterEach
    void tearDown() {
        this.server.shutDownServer();
    }
}