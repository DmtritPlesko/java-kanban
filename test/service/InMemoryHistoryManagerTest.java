package service;

import com.yandex.practicum.models.Task;
import com.yandex.practicum.models.Node;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    public static InMemoryHistoryManager manager;

    @BeforeAll
    public static void setUp() {
        manager = new InMemoryHistoryManager();
    }

    @Test
    void ChekRemoveNodeFromTask() {
        Task task = new Task("Тут что то есть", "И тут тоже что то есть");

        manager.addHistory(task);

        assertNotNull(manager.getTasks());

        Node<Task> noda = new Node<>(null, task, null);
        manager.removeNode(noda);

        assertEquals(false, manager.getTasks().equals(task));
    }

    @Test
    void addHistory() {
        Task task = new Task("Тут что то есть ", "Тут тоже есть что то");
        manager.addHistory(task);

        assertNotNull(manager.getTasks());
    }

    @Test
    void checkRemoveHistoryById() {

    }
}