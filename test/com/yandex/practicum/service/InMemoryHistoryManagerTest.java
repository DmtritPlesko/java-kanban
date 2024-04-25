package com.yandex.practicum.service;

import com.yandex.practicum.models.Task;
import com.yandex.practicum.intr.HistoryManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    public static HistoryManager manager;

    public static InMemoryTaskManager taskManager;

    @BeforeAll
    public static void setUp() {
        manager = new InMemoryHistoryManager();
        taskManager = new InMemoryTaskManager();
    }

    @Test
    public void testRemoveNodeTask() {
        Task task = new Task("Тут что то есть", "И тут тоже что то есть");

        task.setID(1);

        manager.addHistory(task);

        manager.remove(task.getId());

        assertEquals(false, manager.getHistory().contains(task));
    }

    @Test
    void addHistory() {
        Task task = new Task("Тут что то есть ", "Тут тоже есть что то");

        taskManager.createNewTask(task);

        manager.addHistory(task);

        assertNotNull(manager.getHistory());
    }

    @Test
    public void testGetHistory() {

        Task task = new Task("Тут что то есть ", "Тут тоже есть что то");
        Task task1 = new Task("Тут что то есть ", "Тут тоже есть что то");
        Task task2 = new Task("Тут что то есть ", "Тут тоже есть что то");
        Task task3 = new Task("Тут что то есть ", "Тут тоже есть что то");
        manager.addHistory(task);
        manager.addHistory(task1);
        manager.addHistory(task2);
        manager.addHistory(task3);


        assertEquals(2, manager.getHistory().size());
    }


}