package service;

import com.yandex.practicum.models.Epic;
import com.yandex.practicum.models.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    static Map<Integer,Node<Task>> history = new HashMap<>();

    private Node<Task> linkLast (Task task) {
        Node<Task> node = new Node<>(task);


        return node;
    }

    public void removeNode(Node<Task> noda) {
        if (history.containsValue(noda)) {
            for(Integer temp : history.keySet()) {
                if(history.get(temp).equals(noda)) {
                    history.remove(temp);
                    break;
                }
            }
        } else {
            System.out.println("нет такого узла");
        }
    }


    @Override
    public void addHistory(Task task) {
        Node<Task> node = new Node<>(task);

        if(history.values().contains(task)) {
            history.remove(task.getId());
        }

        history.put(task.getId(),node);

    }
    @Override
    public List<Task> getTasks() {
        List<Task> tempHistory = new ArrayList<>();
        for(Integer temp : history.keySet()) {
            tempHistory.add(history.get(temp).data);
        }
        return tempHistory;
    }
    @Override
    public void remove(int id) {
        history.remove(id);
    }

}
