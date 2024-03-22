package service;

import com.yandex.practicum.models.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static LinkedList<Task> history = new LinkedList<>();

    @Override
    public void addHistory (Task task) {
       if(history.size()>=10) {
           history.pop();
       }
       history.add(task);
    }

    @Override
    public List <Task> getHistory() {
        return history;
    }



}
