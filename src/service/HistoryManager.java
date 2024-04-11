package service;

import com.yandex.practicum.models.Task;
import java.util.List;

public interface HistoryManager {

    void addHistory(Task task);

    List<Task> getTasks();

    void remove(int id);

}
