package service;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;
import java.util.HashMap;

import com.yandex.practicum.models.*;

public class InMemoryTaskManager implements TaskManager {
    protected int id = 0;
    private Map<Integer, Task> listTask;
    private Map<Integer, Subtask> listSubtask;
    private Map<Integer, Epic> listEpic;
    private HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.listTask = new HashMap<>();
        this.listSubtask = new HashMap<>();
        this.listEpic = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
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
        System.out.println("Задача доавлена");
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
        id++;//добавил
        subtask.setStatus(Status.NEW);
        subtask.setID(id);
        listSubtask.put(id, subtask);


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

    private void changeStatusByEpic (Epic epic) {
        if (epic.getSubtaskByEpic().isEmpty()) {
            epic.setStatus( Status.NEW);
        } else {
            int countIsNew = 0;
            int countIsProcess = 0;
            List <Integer>  temp = epic.getSubtaskByEpic();
            for(Integer i : temp) {
                if(listSubtask.get(i).getStatus().equals(Status.NEW)) {
                    countIsNew++;
                } else if (listSubtask.get(i).getStatus().equals(Status.DONE)) {
                    countIsProcess++;
                }
                if (countIsProcess == temp.size()) {
                    epic.setStatus(Status.DONE);
                } else if (countIsNew ==temp.size()){
                     epic.setStatus(Status.NEW);
                }
            }
        }
        epic.setStatus(Status.IN_PROGRESS);
    }

    @Override
    public void deleteAllTask() {
        listTask.clear();
        listSubtask.clear();
        listEpic.clear();
        id = 0;
    }

    @Override
    public void deleteTaskForID(Integer Id) {
        if (listTask.containsKey(Id)) {
            listTask.remove(Id);
            System.out.println("Задача удалена");
        } else {
            System.out.println("нет такой задачи");
        }
    }

    @Override
    public void deleteSubtaskForID(Integer Id) {
        if (listSubtask.containsKey(Id)) {
            Subtask sub = listSubtask.get(Id);
            Epic epic = listEpic.get(sub.getIdEpic());
            epic.deleteSubtaskByEpic(Id);
            changeStatusByEpic(epic);
            listSubtask.remove(Id);
            System.out.println("подзадача удалена");
        } else {
            System.out.println("невозможно удалить задачу");
        }
    }

    @Override
    public void deleteEpicForID(Integer Id) {
        if (listEpic.containsKey(Id)) {
            listEpic.remove(Id);
            System.out.println("эпик удалён");

        } else {
            System.out.println("невозможно удалить эпик");
        }
    }

    @Override
    public Task getTaskById(Integer ID) {
        if (listTask.containsKey(ID)) {
            historyManager.addHistory(listTask.get(ID));
            return listTask.get(ID);
        } else {
            System.out.println("задача с таким айди не существует");
        }
        return null;
    }

    @Override
    public Subtask getSubtaskById(Integer ID) {
        if (listSubtask.containsKey(ID)) {
            historyManager.addHistory(listSubtask.get(ID));
            return listSubtask.get(ID);
        } else {
            System.out.println("нет такой подзадачи");
        }
        return null;
    }

    @Override
    public Epic getEpicById(Integer ID) {
        if (listEpic.containsKey(ID)) {
            historyManager.addHistory(listEpic.get(ID));
            return listEpic.get(ID);
        } else {
            System.out.println("нет такой задачи в эпике");
        }
        return null;
    }

    @Override
    public ArrayList<Subtask> printTaskForEpic(Epic epic) {
        ArrayList<Subtask> temp = new ArrayList<>();
        for(Integer i : epic.getSubtaskByEpic()) {
            temp.add(listSubtask.get(i));
        }

        return temp;
    }


    //ВОТ ТУТ НЕМНОГО СОМНЕВАЮСЬ КАКОЙ ЛУЧШЕ ОСТАВИТЬ
    @Override//ТУТ ОН ПОДХОДИТ И БУДЕТ УНИВЕРСАЛЬНЫЙ
    public List <? extends Task> getList () {
        List <Task> tempTask = new ArrayList<>();
        for(Integer i : listTask.keySet()) {
            tempTask.add(listTask.get(i));
        }
        return tempTask;
    }

    //А ТУТ КАЖДЫЙ РАЗБИТ ПО СВОИМ СТРУКТУРАМ ТАК СКАЗАТЬ
    //НУЖНА ПОДСКАЗКА :)
    public List<Task> getListTask () {
        List <Task> tempTask = new ArrayList<>();
        for(Integer i : listTask.keySet()) {
            tempTask.add(listTask.get(i));
        }
        return tempTask;
    }

    public List<Subtask> getListSubtask () {
        List <Subtask> tempSub = new ArrayList<>();
        for(Integer i : listSubtask.keySet()) {
            tempSub.add(listSubtask.get(i));
        }
        return tempSub;
    }

    public List<Epic> getListEpic () {
        List <Epic> tempEpic = new ArrayList<>();
        for(Integer i : listSubtask.keySet()) {
            tempEpic.add(listEpic.get(i));
        }
        return tempEpic;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
