package com.yandex.practicum.handler;

import com.yandex.practicum.models.Epic;
import com.yandex.practicum.models.Subtask;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskManagerHandlerTest extends HttpTaskServerTest {


    @Test
    public void addAndGetNewSubtask() throws IOException, InterruptedException {
        Subtask subtask = new Subtask("Subtask name", "sub Discription");
        Duration dur = Duration.parse("PT1H30M");
        subtask.setDuration(dur);
        subtask.setStartTime(LocalDateTime.of(2024, 1, 1, 0, 0));
        subtask.setID(1);

        String subFromJson = gson.toJson(subtask);

        HttpClient client = HttpClient.newHttpClient();
        //POST
        URI uriPOST = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(subFromJson))
                .uri(uriPOST)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        //GET
        URI uriGET = URI.create("http://localhost:8080/subtasks/1");
        request = HttpRequest.newBuilder()
                .GET()
                .uri(uriGET)
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        Subtask subGet = gson.fromJson(response.body(), Subtask.class);
        System.err.println(subGet);

        assertEquals(subtask, subGet);

    }

    @Test
    public void deleteSubtask() throws IOException, InterruptedException {
        Subtask subtask = new Subtask("subtask Name", "subtask Description");
        Duration dur = Duration.parse("PT1H30M");
        subtask.setDuration(dur);
        subtask.setStartTime(LocalDateTime.of(2024, 1, 1, 0, 0));

        Epic epic = new Epic("привет", "qwf");
        epic.setID(1);
        manager.createNewEpic(epic);

        String subtaskJson = gson.toJson(subtask);
        HttpClient client = HttpClient.newHttpClient();

        //POST
        URI uri = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(subtaskJson))
                .uri(uri)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        //GET
        request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/subtasks/2"))
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Subtask subGet = gson.fromJson(response.body(), Subtask.class);
        System.err.println(subGet + " qfqwfqwf");
        assertNotNull(subGet);

        //DELETE
        request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create("http://localhost:8080/subtasks/2"))
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        //GET
        request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/subtasks/2"))
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        subGet = gson.fromJson(response.body(), Subtask.class);
        System.err.println(subGet);
        assertNull(subGet);
    }
}