package Service;

import service.HistoryManager;
import service.Managers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ManagersTest {

    @Test
    public void createNewHistoryManagerNotNull () {
        assertNotNull(Managers.getDefaultHistory());
    }

    @Test
    public void createNewTaskManagerNotNull () {
        assertNotNull(Managers.getDefault());
    }

}