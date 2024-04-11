package service;

import com.yandex.practicum.models.Task;
import com.yandex.practicum.models.Node;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {
    static Map<Integer, Node<Task>> history = new HashMap<>();
    private Node<Task> head;
    private Node<Task> tail;

    private Node<Task> addLink(Task task) {

        final Node<Task> oldHead = head;

        final Node<Task> tempTail = tail;
        Node<Task> node;
        if (history.keySet().size() == 0) { //если история пустая то новый элемент будет и головой и хвостом
            node = new Node<>(null, task, oldHead);
            tail = node;
            head = node;
        } else {
            node = new Node<>(oldHead, task, tempTail);
            tail = node;
            head = tempTail;
        }

        return node;
    }

    public void removeNode(Node<Task> noda) {
        if (history.containsValue(noda)) {
            head.setNext(history.get(noda.getData().getId()).getNext());
            tail.setPrev(history.get(noda.getData().getId()).getPrev());
            history.remove(noda.getData().getId());
            System.out.println("Узел удалён");
        } else {
            System.out.println("нет такого узла");
        }
    }


    @Override
    public void addHistory(Task task) {
        Node<Task> node = addLink(task);

        if (history.containsValue(node)) {
            history.remove(task.getId());
        }

        history.put(task.getId(), node);

    }

    @Override
    public List<Task> getTasks() {
        List<Task> tempHistory = new ArrayList<>();
        for (Integer temp : history.keySet()) {
            tempHistory.add(history.get(temp).getData());
        }
        return tempHistory;
    }

    @Override
    public void remove(int id) {
        history.remove(id);
    }

}

