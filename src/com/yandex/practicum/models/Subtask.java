package com.yandex.practicum.models;

public class Subtask extends Task {



    public Subtask (String newName,String newDescription) {
        super(newName,newDescription);
    }



    @Override
    public String toString() {
        return "Subtask{" +
                "idSubtask=" + idTask +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status="+status+
                '}';
    }
}
