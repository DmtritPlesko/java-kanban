package com.yandex.practicum.intrerfaces;

import com.yandex.practicum.models.Epic;
import com.yandex.practicum.models.Subtask;
import com.yandex.practicum.models.Task;

import java.io.IOException;
import java.util.List;

public interface TaskManager {
    void print();

    void createNewTask(Task task) throws IOException;

    void updateTask(Task task);

    void createNewSubtask(Subtask subtask) throws IOException;

    void updateSubtask(Subtask subtask);

    void createNewEpic(Epic epic) throws IOException;

    void updateEpic(Epic epic);

    void deleteAllTask();

    void deleteTaskForID(Integer id);

    void deleteSubtaskForID(Integer id);

    void deleteEpicForID(Integer id);

    Task getTaskById(Integer id);

    Subtask getSubtaskById(Integer id);

    Epic getEpicById(Integer id);

    List<Subtask> printTaskForEpic(Epic epic);

    List<Task> getListTask();

    List<Subtask> getListSubtask();

    List<Epic> getListEpic();

    List<Task> getHistory();

}

