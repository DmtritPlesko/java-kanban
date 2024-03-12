package com.yandex.practicum.models;

public class Subtask extends Task {

    private int idEpic = 0;
    public Subtask(String newName, String newDescription) {
        super(newName, newDescription);
    }




    public void setIdEpic (int id) {
        this.idEpic = id;
    }

    public int getIdEpic () {
        return idEpic;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "idSubtask=" + idTask +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

}
