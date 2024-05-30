package com.yandex.practicum.handler;

import com.yandex.practicum.intrerfaces.TaskManager;
import com.yandex.practicum.models.Epic;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EpicManagerHandlerTest extends HttpTaskServerTest {

    @Test
    public void getAndPostAndDeleteByEpic() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        Epic epic = new Epic("Name Epic", "Description Epic");
        Duration dur = Duration.parse("PT1H30M");
        epic.setDuration(dur);
        epic.setStartTime(LocalDateTime.of(2024, 2, 1, 0, 0));
        epic.setID(1);

        String epicToJson = gson.toJson(epic);

        //POST
        URI uri = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(epicToJson))
                .uri(uri)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        //GET
        uri = URI.create("http://localhost:8080/epics/1");
        request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Epic epicAfterGet = gson.fromJson(response.body(), Epic.class);
        assertEquals(epic, epicAfterGet);

        //DELETE
        request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create("http://localhost:8080/epics/1"))
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());


        //GET_AFTER_DELETE
        request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/epics/1"))
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Epic epicAfterDelete = gson.fromJson(response.body(), Epic.class);
        assertNull(epicAfterDelete);
    }
}