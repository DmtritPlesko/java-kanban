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

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerHandlerTest extends HttpTaskServerTest {

    @Test
    public void getTaskTest() throws InterruptedException, IOException {
        Task task1 = new Task("Test1", "Test1");
        Duration dur = Duration.parse("PT1H30M");
        task1.setDuration(dur);
        task1.setStartTime(LocalDateTime.of(2024, 1, 1, 0, 0));

        String taskJson1 = gson.toJson(task1);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson1))
                .build();


        HttpResponse<String> response1 = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response1.statusCode());

        URI url1 = URI.create("http://localhost:8080/tasks/1");

        request = HttpRequest.newBuilder()
                .uri(url1)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Task taskOfGet = gson.fromJson(response.body(), Task.class);

        System.out.println(taskOfGet);

        assertEquals(task1, taskOfGet);

    }


    @Test
    public void testDeleteTask() throws IOException, InterruptedException {
        Task task1 = new Task("Test1", "Test1");
        Duration dur = Duration.parse("PT1H30M");
        task1.setDuration(dur);
        task1.setStartTime(LocalDateTime.of(2024, 1, 1, 0, 0));

        String taskJson1 = gson.toJson(task1);

        //POST
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");

        HttpRequest requestGet = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson1))
                .build();

        HttpResponse<String> response1 = client.send(requestGet, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response1.statusCode());


        //GET
        URI url1 = URI.create("http://localhost:8080/tasks/1");

        HttpRequest requestGET = HttpRequest.newBuilder()
                .uri(url1)
                .GET()
                .build();
        HttpResponse<String> response = client.send(requestGET, HttpResponse.BodyHandlers.ofString());

        Task taskOfGet = gson.fromJson(response.body(), Task.class);

        System.out.println(taskOfGet);

        assertEquals(task1, taskOfGet);


        // DEL
        HttpRequest requestDelete = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/1"))
                .DELETE()
                .build();
        HttpResponse<String> responseDelete = client.send(requestDelete, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, responseDelete.statusCode());

        HttpRequest requestGetAfterDelete = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/1"))
                .GET()
                .build();

        HttpResponse<String> responseAfterDelete = client.send(requestGetAfterDelete, HttpResponse.BodyHandlers.ofString());

        Task taskOfDel = gson.fromJson(responseAfterDelete.body(), Task.class);

        assertNull(taskOfDel);

    }

}