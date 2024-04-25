package com.yandex.practicum.service;

import com.yandex.practicum.models.Epic;
import com.yandex.practicum.models.Subtask;
import com.yandex.practicum.models.Task;

import java.io.File;
import java.nio.file.Path;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private File file;
    public FileBackedTaskManager (String fileName) {
        this.file = new File(fileName + ".txt");
    }
     private void save() {

     }

     @Override
    public void createNewTask(Task task) {
         super.createNewTask(task);
         save();
    }

    @Override
    public void createNewSubtask (Subtask subtask) {
        super.createNewSubtask(subtask);
        save();
    }

    @Override
    public void createNewEpic (Epic epic) {
        super.createNewEpic(epic);
        save();
    }

    @Override
    public String toString() {
        return null;
    }
}
