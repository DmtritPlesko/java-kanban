package service;

import com.yandex.practicum.models.Epic;
import com.yandex.practicum.models.Subtask;
import com.yandex.practicum.models.Task;

import javax.swing.text.MaskFormatter;
import java.util.Map;


import java.util.ArrayList;

public interface TaskManager {
    void print();

    void createNewTask(Task task);

    void updateTask(Task task);

    void createNewSubtask(Subtask subtask);

    void updateSubtask(Subtask subtask);

    void createNewEpic(Epic epic);

    void updateEpic(Epic epic);

    void deleteAllTask();

    void deleteTaskForID(Integer Id);

    void deleteSubtaskForID(Integer Id);

    void deleteEpicForID(Integer Id);

    Task getTaskById(Integer ID);

    Subtask getSubtaskById(Integer ID);

    Epic getEpicById(Integer ID);

    ArrayList<Subtask> printTaskForEpic(Epic epic);

    Map<Integer,Task> getTaskList ();
    Map<Integer,Subtask> getSubtaskList ();
    Map<Integer,Epic> getEpicList ();
}
