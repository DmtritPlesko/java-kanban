package com.yandex.practicum.models;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

public class Epic extends Task {

    private List<Integer> idSubtasks;

    public Epic(String newName, String newDescription) {
        super(newName, newDescription);
        idSubtasks = new ArrayList<>();
    }

    public void setIdSubtasks(Integer idSubtask) {
        this.idSubtasks.add(idSubtask);
    }

    public List<Integer> getSubtaskByEpic() {
        return idSubtasks;
    }

    public void deleteSubtaskByEpic(int id) {
        if (idSubtasks.contains(id)) {
            idSubtasks.remove(id);
        } else {
            System.out.println("Невозмодно удалить задачу");
        }
    }

    public List<Integer> getIdSubtasks() {
        return idSubtasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(idTask, epic.idTask);
    }

}