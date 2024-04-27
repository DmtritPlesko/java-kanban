package com.yandex.practicum.service;

import com.yandex.practicum.intrerfaces.HistoryManager;
import com.yandex.practicum.intrerfaces.TaskManager;

public class Managers {
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

}
