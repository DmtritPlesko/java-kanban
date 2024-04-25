package com.yandex.practicum.service;

import com.yandex.practicum.intr.HistoryManager;
import com.yandex.practicum.intr.TaskManager;

public class Managers {
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

}
