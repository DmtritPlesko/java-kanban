package com.yandex.practicum.intrerfaces;

import com.yandex.practicum.models.Task;

import java.util.List;

public interface HistoryManager {

    void addHistory(Task task);

    List<Task> getHistory();

    void remove(int id);

}
