package service;

import com.yandex.practicum.models.Task;

import java.util.LinkedList;

public interface HistoryManager {

    void addHistory(Task task);

    LinkedList<Task> getHistory();



}
