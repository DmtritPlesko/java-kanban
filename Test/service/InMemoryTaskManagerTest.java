package service;

import static org.junit.jupiter.api.Assertions.*;

import com.yandex.practicum.models.Epic;
import com.yandex.practicum.models.Subtask;
import com.yandex.practicum.models.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class InMemoryTaskManagerTest {

    public static InMemoryTaskManager manager;
    public int ID;

    @BeforeAll
    public static void setup () {
        manager = new InMemoryTaskManager();
    }

   @Test
    public void IDEqualityCheckFromTask () {
       Task task = new Task("Task1","Что то тут есть");

       task.setID(1);

       Task task1 = new Task("Task2", "Hello hello");
       task1.setID(1);


       assertEquals(task,task1,"Задачи не совпадают");

   }

   @Test
    public void IDEqualityCheckFromSubtask () {
       Subtask sub = new Subtask("Sub","hello world");

        sub.setID(1);

        Subtask sub2 = new Subtask("Sub2","Hello");
        sub2.setID(1);

        assertEquals(sub,sub2,"Ошибка сравнения подзадач");

   }

   @Test
    public void IDEqualityCheckFromEpic () {
       Epic epic = new Epic("Epic1","Hello Hello");
       epic.setID(1);

       Epic epic1 = new Epic("Epic1","Hello Hello");
       epic1.setID(1);

       assertEquals(epic,epic1,"Ошибка в сравнении эпиков");
   }

   @Test
    public void checkAddNewTask () {
        Task task = new Task("Task1","Что то есть");

        manager.createNewTask(task);

        final int taskId = task.getId();

        assertNotNull(manager.getTaskById(taskId));

   }

   @Test
   public void checkAddNewSubtask () {
        Subtask sub = new Subtask("Sub1","Hello hello");

        manager.createNewSubtask(sub);

        final int subID = sub.getId();

        assertNotNull(manager.getSubtaskById(subID));
   }


   @Test
    public void checkAddNewEpic () {
        Epic epic = new Epic("Epic1","Hello hello");

        manager.createNewEpic(epic);

        final int epicID = epic.getId();

        assertNotNull(manager.getEpicById(epicID));
   }
   
}