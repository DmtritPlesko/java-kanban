package com.yandex.practicum.service;

import java.time.LocalDateTime;
import java.util.*;

import com.yandex.practicum.enums.Status;
import com.yandex.practicum.models.*;
import com.yandex.practicum.intrerfaces.HistoryManager;
import com.yandex.practicum.intrerfaces.TaskManager;


public class InMemoryTaskManager implements TaskManager {
    protected int id = 0;
    protected Map<Integer, Task> listTask;
    protected Map<Integer, Subtask> listSubtask;
    protected Map<Integer, Epic> listEpic;
    private HistoryManager historyManager;
    private TreeSet<Task> priority;
    static Comparator<Task> comparator = Comparator.comparing(Task::getStartTime);

    public InMemoryTaskManager() {
        this.listTask = new HashMap<>();
        this.listSubtask = new HashMap<>();
        this.listEpic = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
        priority = new TreeSet<>(comparator);
    }

    private void changeStatusByEpic(Epic epic) {
        if (epic.getSubtaskByEpic().isEmpty()) {
            epic.setStatus(Status.NEW);
        } else {
            int countIsNew = 0;
            int countIsProcess = 0;
            List<Integer> temp = epic.getSubtaskByEpic();
            for (Integer i : temp) {
                if (listSubtask.get(i).getStatus().equals(Status.NEW)) {
                    countIsNew++;
                } else if (listSubtask.get(i).getStatus().equals(Status.DONE)) {
                    countIsProcess++;
                }
                if (countIsProcess == temp.size()) {
                    epic.setStatus(Status.DONE);
                } else if (countIsNew == temp.size()) {
                    epic.setStatus(Status.NEW);
                }
            }
        }
        epic.setStatus(Status.IN_PROGRESS);
    }

    public LocalDateTime getEndTime(Task task) {
        if (task instanceof Epic) {
            LocalDateTime time = listSubtask.get(((Epic) task).getIdSubtasks().get(0)).getStartTime();
            ((Epic) task).getIdSubtasks().stream()
                    .map(i -> listSubtask.get(i).getDuration())
                    .filter(Objects::nonNull)
                    .peek(i -> time.plus(i));

            return time;
        }
        if (checkStartTime(task)) {
            return task.getStartTime().plus(task.getDuration());

        }
        throw new RuntimeException("Ошибка вов ременном интервале");

    }

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return priority;
    }

    protected boolean checkStartTime(Task task) {
        return task.getStartTime() != null;
    }

    private boolean timeOverlap(Task task, Task task2) {
        return !getEndTime(task).isBefore(task2.getStartTime())
                && !getEndTime(task2).isBefore(task.getStartTime());
    }

    public boolean checkTime(Task task) {
        Optional<Task> first = priority.stream()
                .peek(task1 -> timeOverlap(task1, task)).findFirst();
        if (first.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public void print() {
        System.out.println(listTask);
        System.out.println(listSubtask);
        System.out.println(listEpic);
    }

    @Override
    public void createNewTask(Task task) {
        id++;
        task.setID(id);
        task.setStatus(Status.NEW);
        listTask.put(id, task);
        if (checkStartTime(task) && checkTime(task)) {
            priority.add(task);
        }
        System.out.println("Задача добавлена");
    }

    @Override
    public void updateTask(Task task) {
        if (listTask.containsKey(task.getId())) {
            listTask.replace(task.getId(), task);
            System.out.println("задача обновлена");
        } else {
            System.out.println("Невозможно обновить задачу");
        }
    }

    @Override
    public void createNewSubtask(Subtask subtask) {
        id++;
        subtask.setStatus(Status.NEW);
        subtask.setID(id);
        listSubtask.put(id, subtask);
        if (checkStartTime(subtask) && checkTime(subtask)) {
            priority.add(subtask);
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (listSubtask.containsKey(subtask.getId())) {
            listSubtask.replace(subtask.getId(), subtask);
            System.out.println("задача обновлена");
        } else {
            System.out.println("невозможно обновить подзадачу");
        }

    }

    @Override
    public void createNewEpic(Epic epic) {
        id++;
        epic.setID(id);
        listEpic.put(id, epic);
        System.out.println("задача добавлена в эпик");
    }

    @Override
    public void updateEpic(Epic epic) {
        if (listEpic.containsKey(epic.getId())) {
            listEpic.get(epic.getId()).setStatus(epic.getStatus());
            listEpic.replace(epic.getId(), epic);
        } else {
            System.out.println("невозможно обновить задачу в эпике");
        }
    }

    @Override
    public void deleteAllTask() {
        listTask.clear();
        listSubtask.clear();
        listEpic.clear();
        id = 0;
    }

    @Override
    public void deleteTaskForID(Integer id) {
        if (listTask.containsKey(id)) {
            listTask.remove(id);
            historyManager.remove(id);
            System.out.println("Задача удалена");
        } else {
            System.out.println("нет такой задачи");
        }
    }

    @Override
    public void deleteSubtaskForID(Integer id) {
        if (listSubtask.containsKey(id)) {
            Subtask sub = listSubtask.get(id);
//            Epic epic = listEpic.get(sub.getIdEpic());
//            epic.deleteSubtaskByEpic(id);
//            changeStatusByEpic(epic);
            listSubtask.remove(id);
            historyManager.remove(id);
            System.out.println("подзадача удалена");
        } else {

            System.out.println("невозможно удалить задачу");
        }
    }

    @Override
    public void deleteEpicForID(Integer id) {
        if (listEpic.containsKey(id)) {
            listEpic.remove(id);
//            historyManager.remove(id);
            System.out.println("эпик удалён");

        } else {
            System.out.println("невозможно удалить эпик");
        }
    }

    @Override
    public Task getTaskById(Integer id) {
        if (listTask.containsKey(id)) {
            historyManager.addHistory(listTask.get(id));
            return listTask.get(id);
        } else {
            System.out.println("задача с таким айди не существует");
        }
        return null;
    }

    @Override
    public Subtask getSubtaskById(Integer id) {
        if (listSubtask.containsKey(id)) {
            historyManager.addHistory(listSubtask.get(id));
            return listSubtask.get(id);
        } else {
            System.out.println("нет такой подзадачи");
        }
        return null;
    }

    @Override
    public Epic getEpicById(Integer id) {
        if (listEpic.containsKey(id)) {
            historyManager.addHistory(listEpic.get(id));
            return listEpic.get(id);
        } else {
            System.out.println("нет такой задачи в эпике");
        }
        return null;
    }

    @Override
    public ArrayList<Subtask> printTaskForEpic(Epic epic) {
        ArrayList<Subtask> temp = new ArrayList<>();
        for (Integer i : epic.getSubtaskByEpic()) {
            temp.add(listSubtask.get(i));
        }

        return temp;
    }

    @Override
    public List<Task> getListTask() {
        return new ArrayList<>(listTask.values());
    }

    @Override
    public List<Subtask> getListSubtask() {
        return new ArrayList<>(listSubtask.values());
    }

    @Override
    public List<Epic> getListEpic() {
        return new ArrayList<>(listEpic.values());
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public List<Subtask> getSubtasksByEpicId(Integer id) {
        Epic epic = getEpicById(id);
        List<Subtask> subList = new ArrayList<>();

        for (Integer temp : epic.getSubtaskByEpic()) {
            subList.add(listSubtask.get(temp));
        }
        return subList;
    }
}