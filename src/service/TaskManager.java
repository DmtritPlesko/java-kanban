package service;

import com.yandex.practicum.models.Epic;
import com.yandex.practicum.models.Subtask;
import com.yandex.practicum.models.Task;

import java.util.List;

public interface TaskManager {
    void print();

    void createNewTask(Task task);

    void updateTask(Task task);

    void createNewSubtask(Subtask subtask);

    void updateSubtask(Subtask subtask);

    void createNewEpic(Epic epic);

    void updateEpic(Epic epic);

    void deleteAllTask();

    void deleteTaskForID(Integer identef);

    void deleteSubtaskForID(Integer identef);

    void deleteEpicForID(Integer identef);

    Task getTaskById(Integer identef);

    Subtask getSubtaskById(Integer identef);

    Epic getEpicById(Integer identef);

    List<Subtask> printTaskForEpic(Epic epic);

    List<Task> getListTask();

    List<Subtask> getListSubtask();

    List<Epic> getListEpic();

    List<Task> getHistory();

}
