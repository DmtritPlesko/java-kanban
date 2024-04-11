package service;

import com.yandex.practicum.models.*;

public class Main {
    public static void main(String[] args) {
        TaskManager man = new InMemoryTaskManager();

        Task task = new Task("qwqwwqq", "wfqwfwqfqw");
        man.createNewTask(task);

        Subtask subtask = new Subtask("fqfqwfwq", "qwfqwfqwfqw");
        man.createNewSubtask(subtask);
        subtask.setStatus(Status.IN_PROGRESS);

        Epic epic1 = new Epic("qwfqwfqwfqwfqwfqw", "qwfqw");
        man.createNewEpic(epic1);
        man.updateEpic(epic1);
        man.print();
    }
}