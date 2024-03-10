package com.yandex.practicum.models;

import java.util.Objects;

public class Task {

    protected String name;
    protected String description;
    protected Integer idTask;
    protected Status status;

    public Task(String _name, String _description) {
        this.name = _name;
        this.description = _description;
    }

    public Integer getId() {
        return idTask;
    }

    public String getName() {
        return this.name;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
    public void setID(Integer id) {
        this.idTask = id;
    }
    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description.length ='" + description.length() + '\'' +
                ", idTask=" + idTask +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(idTask, task.idTask) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, idTask, status);
    }
}