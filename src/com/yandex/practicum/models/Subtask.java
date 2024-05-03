package com.yandex.practicum.models;

public class Subtask extends Task {

    private int idEpic = 0;

    public Subtask(String newName, String newDescription) {
        super(newName, newDescription);
    }

    public void setIdEpic(int id) {
        this.idEpic = id;
    }

    public int getIdEpic() {
        return this.idEpic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return idTask == subtask.idTask;
    }
}
