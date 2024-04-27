package com.yandex.practicum.service;

import com.yandex.practicum.intrerfaces.TaskManager;
import com.yandex.practicum.models.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {
    private static TaskManager manager;

    @BeforeAll
    public static void setup() {

    }
    @Test
    public void checkCreateNewTask () {
        Task task = new Task("Тут что то есть","и тут что то есть");


    }

}