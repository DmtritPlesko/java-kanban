package com.yandex.practicum.models;

import com.yandex.practicum.enums.Status;

import java.util.Objects;

public class Task {

    protected String name;
    protected String description;
    protected int idTask;
    protected Status status;

    public Task(String newName, String newDescription) {
        this.name = newName;
        this.description = newDescription;
    }

    public Integer getId() {
        return this.idTask;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, idTask, status);
    }
}