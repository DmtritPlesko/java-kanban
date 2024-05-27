package com.yandex.practicum.server;
import com.sun.net.httpserver.HttpServer;
import com.yandex.practicum.handler.EpicManagerHandler;
import com.yandex.practicum.handler.HistoryManagerHandler;
import com.yandex.practicum.handler.SubtaskManagerHandler;
import com.yandex.practicum.handler.TaskManagerHandler;
import com.yandex.practicum.intrerfaces.TaskManager;
import com.yandex.practicum.service.Managers;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer{
     static final int PORT = 8080;
     private HttpServer server  = HttpServer.create(new InetSocketAddress(PORT),0);
     private TaskManager manager;

     public HttpTaskServer(TaskManager manager) throws IOException {
         this.manager = manager;
     }

     public void startServer() throws IOException {
         server.createContext("/tasks", new TaskManagerHandler(manager));
         server.createContext("/subtasks", new SubtaskManagerHandler(manager));
         server.createContext("/epics", new EpicManagerHandler(manager));
         server.createContext("/history", new HistoryManagerHandler());
         server.createContext("/prioritized", new TaskManagerHandler(manager));

         server.start();
         System.out.println("Server start. \nPORT: "+PORT);
     }

     public void shutDownServer() {
         server.stop(1);
         System.out.println("Server shutDown ");
     }

    public static void main(String[] args) throws IOException {
        HttpTaskServer server1 = new HttpTaskServer(Managers.getDefault());

    }

}

