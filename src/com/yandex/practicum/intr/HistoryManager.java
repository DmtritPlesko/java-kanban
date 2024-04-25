package com.yandex.practicum.intr;

import com.yandex.practicum.models.Task;

import java.util.List;

public interface HistoryManager {

    void addHistory(Task task);

    List<Task> getHistory();

    void remove(int id);

}
