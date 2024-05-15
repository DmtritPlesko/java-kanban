package com.yandex.practicum.service;

import com.yandex.practicum.intrerfaces.TaskManager;
import com.yandex.practicum.models.Epic;
import com.yandex.practicum.models.Subtask;
import com.yandex.practicum.models.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T manager;

    @Test
    public void idEqualityCheckFromTask() {
        Task task = new Task("Task1", "Что то тут есть");

        task.setID(1);

        Task task1 = new Task("Task1", "а тут ничего нет");
        task1.setID(1);


        assertEquals(task.getId(), task1.getId(), "Задачи не совпадают");

    }

    @Test
    public void idEqualityCheckFromSubtask() {
        Subtask sub = new Subtask("Sub", "hello world");

        sub.setID(1);

        Subtask sub2 = new Subtask("Sub", "Hello");
        sub2.setID(1);

        assertEquals(sub.getId(), sub2.getId(), "Ошибка сравнения подзадач");

    }

    @Test
    public void idEqualityCheckFromEpic() {
        Epic epic = new Epic("Epic1", "Hello Hello");
        epic.setID(1);

        Epic epic1 = new Epic("Epic1", "Hello Hello");
        epic1.setID(1);

        assertEquals(epic, epic1, "Ошибка в сравнении эпиков");
    }
}
