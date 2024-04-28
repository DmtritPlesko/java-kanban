package com.yandex.practicum.service;

import com.yandex.practicum.intrerfaces.TaskManager;
import com.yandex.practicum.models.Subtask;
import com.yandex.practicum.models.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {
    public static TaskManager manager;

    @BeforeAll
    public static void setup() throws FileNotFoundException {
        manager = new FileBackedTaskManager("output.csv");
    }

    @Test
    public void createAndSaveTaskFromFile() throws IOException {
        Task task = new Task("qwfq", "qwfqwf");

        manager.createNewTask(task);

        assertNotNull(manager.getListTask());
    }

    @Test
    public void createAndSaveSubForFile() throws IOException {
        Subtask subtask = new Subtask("а туту ужэе будет саб", "qwfqwfqwf");

        manager.createNewSubtask(subtask);

        assertNotNull(manager.getHistory());
    }
}