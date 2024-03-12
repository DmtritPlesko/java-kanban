package com.yandex.practicum.models;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> idSubtasks;

    public Epic (String newName,String newDescription) {
       super(newName,newDescription);
       idSubtasks = new ArrayList<>();
    }

    public void setIdSubtasks(Integer idSubtask) {
        this.idSubtasks.add(idSubtask);
    }
    public ArrayList<Integer> getSubtaskByEpic () {
        return idSubtasks;
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
}