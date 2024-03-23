package com.yandex.practicum.models;

import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

public class Epic extends Task {

    private List<Integer> idSubtasks;

    public Epic (String newName,String newDescription) {
       super(newName,newDescription);
       idSubtasks = new ArrayList<>();
    }

    public void setIdSubtasks(Integer idSubtask) {
        this.idSubtasks.add(idSubtask);
    }
    public List<Integer> getSubtaskByEpic () {
        return idSubtasks;
    }

   public void deleteSubtaskByEpic (int id) {
        if(idSubtasks.contains(id)) {
            idSubtasks.remove(id);
        } else {
            System.out.println("Невозмодно удалить задачу");
        }
   }

    @Override
    public String toString () {
        return "Epic{" +
                "id=" + idTask +
                ", idSubtasks=" + idSubtasks +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", idTask=" + idTask +
                ", status=" + status +
                '}';
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