package com.yandex.practicum.service;

import com.yandex.practicum.intrerfaces.HistoryManager;
import com.yandex.practicum.intrerfaces.TaskManager;
import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;

public class Managers {
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static FileBackedTaskManager getDefaultFileManager(File file) throws FileNotFoundException {
        return new FileBackedTaskManager(file);
    }
}
