package service;

import com.yandex.practicum.models.Task;

import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager {
    private static LinkedList<Task> history = new LinkedList<>();

    @Override
    public void addHistory (Task task) {
        if(history.size()<10) {
            history.add(task);
        } else {
            history.pop();
            history.add(task);

        }
    }

    @Override
    public LinkedList<Task> getHistory() {
        return this.history;
    }
}
