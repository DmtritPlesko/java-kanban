package com.yandex.practicum.handler;

import com.yandex.practicum.models.Task;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerHandlerTest extends HttpTaskServerTest {

    @Test
    public void checkHistory() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        Task task1 = new Task("TaskName1", "TaskDesk1");
        Duration dur = Duration.parse("PT1H30M");
        task1.setDuration(dur);
        task1.setStartTime(LocalDateTime.of(2024, 1, 1, 0, 0));
        task1.setID(1);

        Task task2 = new Task("TaskName2", "TaskDesk2");
        Duration dur2 = Duration.parse("PT1H20M");
        task2.setDuration(dur);
        task2.setStartTime(LocalDateTime.of(2024, 2, 1, 1, 42));
        task2.setID(1);

        String task1ToJson = gson.toJson(task1);
        String task2ToJson = gson.toJson(task2);

        //POST
        URI uri = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(task1ToJson))
                .uri(uri)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(task2ToJson))
                .uri(uri)
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        //GET and check
        List<Task> list = new ArrayList<>();
        uri = URI.create("http://localhost:8080/tasks/1");
        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        list.add(gson.fromJson(response.body(), Task.class));

        uri = URI.create("http://localhost:8080/tasks/2");
        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        list.add(gson.fromJson(response.body(), Task.class));
    }

}