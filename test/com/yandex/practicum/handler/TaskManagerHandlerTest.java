package com.yandex.practicum.handler;

import com.yandex.practicum.models.Task;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerHandlerTest extends HttpTaskServerTest {

    public TaskManagerHandlerTest() throws IOException {
    }

//    @Test
//    public void getTask() throws IOException, InterruptedException {
//        HttpClient client = HttpClient.newHttpClient();
//        URI uri = URI.create("http://localhost:8080/tasks/1");
//        Task task = new Task("что тот тту етсь","йцщалзйцлазйцл");
//        manager.createNewTask(task);
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(uri)
//                .GET()
//                .build();
//        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
//
//        assertEquals(200,response.statusCode());
//    }
}