package service;

import com.yandex.practicum.models.Epic;
import com.yandex.practicum.models.Task;

import java.util.List;
import java.util.Objects;

public interface HistoryManager {

    void addHistory(Task task);

    List<Task> getTasks();

    void remove(int id);



}
