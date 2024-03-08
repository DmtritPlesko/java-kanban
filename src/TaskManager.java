import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class TaskManager {

    protected static Integer countSubtask = 0;
    protected static Integer id = 0;
    protected static Integer idSubtask = 0;
    private HashMap<Integer, Task> listTask;
    private HashMap<Integer, ArrayList<Subtask>> listSubtask;
    private HashMap<Integer, ArrayList<Subtask>> listEpic;


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
        listTask.put(id, task);
        System.out.println("Задача доавлена");
    }

    public void updateTask(Task task) {
        for (Integer i : listTask.keySet()) {
            if (task.getId() == i) {
                listTask.put(i, task);
                System.out.println("Задача обновлена");
            } else {
                System.out.println("Невозможно обновить задачу");
            }
        }
    }
    public void createNewSubtask(Subtask subtask) {
        ArrayList<Subtask> sub = new ArrayList<>();//у задачи могут быть несколько подзадач поэтому решил сделать ArrayList
        int i = 0;
        sub.add(new Subtask(subtask.name, subtask.description));
        listSubtask.put(id, sub);
        System.out.println("Подзадача добавлена");
    }
    public void updateSubtask(Subtask subtask) {
        for (Integer i : listSubtask.keySet()) {
            for(Subtask sub : listSubtask.get(i)) {
                if(sub.idSubtask.equals (subtask.idSubtask)) {
                   sub = subtask;
                }
            }
            if(listSubtask.get(i).size()>4) {
                createNewEpic();
                System.out.println("Подзадач слишком много поэтому они были перемещены в эпик");
            }
        }
    }

    private void createNewEpic() {//если я правильно понял то епик формируется тогда когда подадач много соответсвенно там будут все наши сабтаски
        for(Integer i: listSubtask.keySet()) {
            ArrayList<Subtask> sub = listSubtask.get(i);
            if(sub.size()>4) {
                listEpic.put(i,sub);
            }
        }
    }
    public void deleteAllTask() { //идея такая что если список основных задач будет очищен все другие тоже будут очищены
        listTask.clear();
        listSubtask.clear();
        listEpic.clear();
        id = 0;
    }

    public void deleteTaskForID(Integer _id) {
        if (listTask.containsKey(_id)) {
            listTask.remove(_id);
            listSubtask.remove(_id);
            listEpic.remove(_id);
            System.out.println("Задача и все её подзадачи удалены");
        } else {
            System.out.println("нет такой задачи");
        }
    }


    public Task printTaskForID(Integer _id) {
        Object obj = null;
        if (listTask.containsKey(_id)) {
            obj = listTask.get(_id);
        } else {
            System.out.println("нет такой задачи");
        }
        return (Task) obj;
    }

    public ArrayList<Subtask> printSubtaskForID(Integer _id) {
        if (listSubtask.containsKey(_id)) {
            return listSubtask.get(_id);
        } else {
            System.out.println("нет такой подзадачи");
        }
        return null;
    }

    public ArrayList<Subtask> printEpicForID(Integer _id) {
        Object obj = null;
        if (listEpic.containsKey(_id)) {
            obj = listEpic.get(_id);
            return listEpic.get(_id);
        } else {
            System.out.println("нет такого эпика");
        }
        return null;
    }

    public void printTaskForEpic() {
        for (Integer i : listEpic.keySet()) {
            ArrayList<Subtask> epics = listEpic.get(i);
            System.out.println(i);
            for (int j = 0; j < epics.size(); j++) {
                System.out.println(epics.get(j));
            }
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskManager that = (TaskManager) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(listTask, that.listTask) &&
                Objects.equals(listSubtask, that.listSubtask) &&
                Objects.equals(listEpic, that.listEpic);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, listTask, listSubtask, listEpic);

    }


    @Override
    public String toString() {
        return "TaskManager{" +
                "id=" + id +
                ", listTask=" + listTask +
                ", listSubtask=" + listSubtask +
                ", listEpic=" + listEpic +
                '}';
    }
}
