package com.yandex.practicum.service;

import static org.junit.jupiter.api.Assertions.*;

import com.yandex.practicum.models.Epic;
import com.yandex.practicum.models.Subtask;
import com.yandex.practicum.models.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

class InMemoryTaskManagerTest {

    public static InMemoryTaskManager manager;
    public int ID;

    @BeforeAll
    public static void setup() {
        manager = new InMemoryTaskManager();
    }

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

    @Test
    public void checkAddNewTask() {
        Task task = new Task("Task1", "Что то есть");

        manager.createNewTask(task);

        final int taskId = task.getId();

        assertNotNull(manager.getTaskById(taskId));

    }

    @Test
    public void checkAddNewSubtask() {
        Subtask sub = new Subtask("Sub1", "Hello hello");

        manager.createNewSubtask(sub);

        final int subID = sub.getId();

        assertNotNull(manager.getSubtaskById(subID));
    }


    @Test
    public void checkAddNewEpic() {
        Epic epic = new Epic("Epic1", "Hello hello");

        manager.createNewEpic(epic);

        final int epicID = epic.getId();

        assertNotNull(manager.getEpicById(epicID));
    }

    @Test
    public void checkDeleteTask() {
        Task task = new Task("Task1", "Что то есть");

        manager.createNewTask(task);


        final int taskID = task.getId();
        manager.getTaskById(taskID);

        manager.deleteTaskForID(taskID);

        assertNull(manager.getTaskById(taskID));
    }

    @Test
    public void checkDeleteEpic() {
        Epic epic = new Epic("Epic1", "Hello hello");

        manager.createNewEpic(epic);

        final int epicID = epic.getId();
        manager.getEpicById(epicID);

        manager.deleteEpicForID(epicID);

        assertNull(manager.getEpicById(epicID));

    }

    @Test
    public void checkUpdateTask() {
        Task task1 = new Task("Task1", "Текст для первой задачи");

        manager.createNewTask(task1);

        final int taskID = task1.getId();

        Task task2 = new Task("Task2", "Текс для второй задачи");
        task2.setID(taskID);

        manager.updateTask(task2);

        assertEquals(manager.getTaskById(taskID).getName(), task2.getName(), "Задача не обнавляется");
    }

    @Test
    public void checkUpdateSubtask() {
        Subtask subTask1 = new Subtask("Subtask1", "Текст для первой прдзадачи");

        manager.createNewSubtask(subTask1);

        final int subID = subTask1.getId();

        Subtask subTask2 = new Subtask("Subtask2", "Текст для второй прдзадачи");

        subTask2.setID(subID);

        manager.updateSubtask(subTask2);

        assertEquals(manager.getSubtaskById(subID).getName(), subTask2.getName(), "Подзадачи не обнавляются");
    }

    @Test
    public void checkUpdateEpic() {
        Epic epic1 = new Epic("Epic1", "Текст для эпика1");

        manager.createNewEpic(epic1);

        final int epicID = epic1.getId();

        Epic epic2 = new Epic("Epic2", "Tекст для эпика2");
        epic2.setID(epicID);

        manager.updateEpic(epic2);

        assertEquals(manager.getEpicById(epicID).getName(), epic2.getName(), "Епик не обнавляется");
    }

    @Test
    public void checkAllDelete() {
        Task task = new Task("Task1", "Что то тут есть");
        Task task2 = new Task("Task2", "Что то тут есть");

        manager.createNewTask(task);
        final int taskID = task.getId();
        manager.createNewTask(task2);

        Subtask subTask1 = new Subtask("Subtask1", "Текст для первой прдзадачи");
        Subtask subTask2 = new Subtask("Subtask2", "Текст для второй прдзадачи");

        manager.createNewSubtask(subTask1);
        final int subID = subTask1.getId();
        manager.createNewSubtask(subTask2);

        Epic epic1 = new Epic("Epic1", "Текст для эпика1");
        Epic epic2 = new Epic("Epic2", "Текст для эпика2");

        manager.createNewEpic(epic1);
        final int epicID = epic1.getId();
        manager.createNewEpic(epic2);

        manager.deleteAllTask();

        assertNull(manager.getTaskById(taskID), "Задачи не удалились");
        assertNull(manager.getSubtaskById(subID), "Подзадачи не удалились");
        assertNull(manager.getEpicById(epicID), "Эпики не удалились");
        System.out.println("Метод ниже");
    }

    @Test
    public void checkGetListSubtask() {

        Subtask sub = new Subtask("wqf", "qwf");
        Subtask sub2 = new Subtask("wqf", "qwf");
        Subtask sub3 = new Subtask("wqf", "qwf");
        Subtask sub4 = new Subtask("wqf", "qwf");

        manager.createNewSubtask(sub);
        manager.createNewSubtask(sub2);
        manager.createNewSubtask(sub3);
        manager.createNewSubtask(sub4);

        List<Subtask> tempTask = manager.getListSubtask();

        assertNotNull(tempTask);
    }

    @Test
    public void checkGetListTask() {
        Task task = new Task("Task1", "Что то тут есть");
        Task task2 = new Task("Task2", "Что то тут есть");
        Task task3 = new Task("Task3", "Что то тут есть");
        Task task4 = new Task("Task4", "Что то тут есть");

        manager.createNewTask(task);
        manager.createNewTask(task2);
        manager.createNewTask(task3);
        manager.createNewTask(task4);

        List<Task> tempTask = manager.getListTask();

        assertNotNull(tempTask);
    }

    @Test
    public void checkGeListEpic() {
        Epic epic1 = new Epic("Epic1", "Текст для эпика1");
        Epic epic2 = new Epic("Epic2", "Текст для эпика2");
        Epic epic3 = new Epic("Epic3", "Текст для эпика3");
        Epic epic4 = new Epic("Epic4", "Текст для эпика4");

        manager.createNewEpic(epic1);
        manager.createNewEpic(epic2);
        manager.createNewEpic(epic3);
        manager.createNewEpic(epic4);

        List<Epic> tempEpic = manager.getListEpic();

        assertNotNull(tempEpic);
    }

    @Test
    public void checkContainsInHistory() {
        Task task = new Task("Тут что то есть", "и тут тоже что то есть");
        manager.createNewTask(task);

        manager.getTaskById(task.getId());

        assertEquals(true, manager.getHistory().contains(task));
    }

    @Test
    public void removeInHistoryAndPullTasks() {
        Task taska = new Task("Тут что то есть", "и тут тоже что то есть");
        manager.createNewTask(taska);

        manager.getTaskById(taska.getId());

        manager.deleteTaskForID(taska.getId());

        assertEquals(false, manager.getHistory().equals(taska));
    }

}