import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.yandex.practicum.models.*;

public class TaskManager {
    protected int id = 0;
    private HashMap<Integer, Task> listTask;
    private HashMap<Integer, Subtask> listSubtask;
    private HashMap<Integer, Epic> listEpic;

    public TaskManager() {
        this.listTask = new HashMap<>();
        this.listSubtask = new HashMap<>();
        this.listEpic = new HashMap<>();
    }

    public void print() {
        System.out.println(listTask);
        System.out.println(listSubtask);
        System.out.println(listEpic);
    }

    public void createNewTask(Task task) {
        id++;
        task.setID(id);
        task.setStatus(Status.NEW);
        listTask.put(id, task);
        System.out.println("Задача доавлена");
    }

    public void updateTask(Task task) {
        if (listTask.containsKey(task.getId())) {
            listTask.replace(task.getId(), task);
            System.out.println("задача обновлена");
        } else {
            System.out.println("Невозможно обновить задачу");
        }
    }

    
    public void createNewSubtask(Subtask subtask) {

        subtask.setStatus(Status.NEW);
        subtask.setID(id);
        listSubtask.put(id, subtask);

//        initStatusByEpic(subtask);
    }

    public void updateSubtask(Subtask subtask) {
        if (listSubtask.containsKey(subtask.getId())) {
            listSubtask.replace(subtask.getId(), subtask);
            System.out.println("задача обновлена");
        } else {
            System.out.println("невозможно обновить подзадачу");
        }

//        initStatusByEpic(subtask);
    }

    public void createNewEpic(Epic epic) {
        epic.setID(id);
        listEpic.put(id, epic);
        System.out.println("задача добавлена в эпик");
    }

    public void updateEpic(Epic epic) {
        if (listEpic.containsKey(epic.getId())) {
            listEpic.get(epic.getId()).setStatus(epic.getStatus());
            listEpic.replace(epic.getId(), epic);
        } else {
            System.out.println("невозможно обновить задачу в эпике");
        }
    }

    private Status changeStatusByEpic (Epic epic) {
        if (epic.getSubtaskByEpic().isEmpty()) {
            return Status.NEW;//если пустой
        } else {
            int countIsNew = 0;
            int countIsProcess = 0;
            ArrayList <Integer>  temp = epic.getSubtaskByEpic();
            for(Integer i : temp) {
                if(listSubtask.get(i).getStatus().equals(Status.NEW)) {
                    countIsNew++;
                } else if (listSubtask.get(i).getStatus().equals(Status.DONE)) {
                    countIsProcess++;
                }
                if (countIsProcess == temp.size()) {
                    return Status.DONE;//если все завершены
                } else if (countIsNew ==temp.size()){
                     return Status.NEW;
                }
            }
        }
        return Status.IN_PROGRESS;//все другие случаи
    }

    public void deleteAllTask() { //идея такая что если список основных задач будет очищен все другие тоже будут очищены
        listTask.clear();
        listSubtask.clear();
        listEpic.clear();
        id = 0;
    }

    public void deleteTaskForID(Integer Id) {
        if (listTask.containsKey(Id)) {
            listTask.remove(Id);
            System.out.println("Задача удалена");
        } else {
            System.out.println("нет такой задачи");
        }
    }

    public void deleteSubtaskForID(Integer Id) {
        if (listSubtask.containsKey(Id)) {
            listSubtask.remove(Id);
            System.out.println("подзадача удалена");
        } else {
            System.out.println("невозможно удалить задачу");
        }
    }

    public void deleteEpicForID(Integer Id) {
        if (listEpic.containsKey(Id)) {
            listEpic.remove(Id);
            System.out.println("задача удалена из эпика");
        } else {
            System.out.println("невозможно удалить задачу из эпика");
        }
    }


    public Task getTaskById(Integer ID) {
        if (listTask.containsKey(ID)) {
            return listTask.get(ID);
        } else {
            System.out.println("задача с таким айди не существует");
        }
        return null;
    }

    public Subtask getSubtaskById(Integer ID) {
        if (listSubtask.containsKey(ID)) {
            return listSubtask.get(ID);
        } else {
            System.out.println("нет такой подзадачи");
        }
        return null;
    }

    public Epic getEpicById(Integer ID) {
        if (listEpic.containsKey(ID)) {
            return listEpic.get(ID);
        } else {
            System.out.println("нет такой задачи в эпике");
        }
        return null;
    }

    public void printTaskForEpic() {
        Epic epic;
        int count = 1;
        for (Integer i : listEpic.keySet()) {
            epic = listEpic.get(i);

            System.out.println(count + " - " + epic.getName());
            count++;
        }
    }


}
