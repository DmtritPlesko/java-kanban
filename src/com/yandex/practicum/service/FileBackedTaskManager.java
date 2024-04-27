package com.yandex.practicum.service;

import com.yandex.practicum.enums.Status;
import com.yandex.practicum.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class FileBackedTaskManager extends InMemoryTaskManager {
    InMemoryTaskManager manager;
    private static File file;
    private Map<Integer, Task> listTask;
    private Map<Integer, Subtask> listSubtask;
    private Map<Integer, Epic> listEpic;


    private List<Task> tempTask;

    public FileBackedTaskManager(String fileName) throws FileNotFoundException {
        file = new File(fileName + ".txt");
        manager = new InMemoryTaskManager();
        listTask = new HashMap<>();
        listSubtask = new HashMap<>();
        listEpic = new HashMap<>();
    }

    private void save() {
        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {

            for (Task task : tempTask) {
                writer.write(toString(task));
            }

        } catch (IOException error) {
            System.out.println("Ошибка при записи в файл");
        }
    }

    private String toString(Task task) {

        String temp = task.getId().toString() + "," + initTypeTask(task)  + "," + task.getName()
                + "," + task.getStatus() + "," + task.getDescription();

        return temp;
    }

    private String initTypeTask(Task task) {
        Object obj = task;
        if (obj instanceof Task) {
            return "Task";
        } else if (obj instanceof Subtask) {
            return "Subtask";
        } else {
            return "Epic";
        }
    }

    private Task fromString(String value) {
        String[] tempTask;
        tempTask = value.split(",");
        Task task = new Task(tempTask[2], tempTask[4]);
        task.setID(Integer.parseInt(tempTask[0]));
        if (tempTask[3].equals(Status.NEW.toString())) {
            task.setStatus(Status.NEW);
        } else if (tempTask[3].equals(Status.IN_PROGRESS.toString())) {
            task.setStatus(Status.IN_PROGRESS);
        } else {
            task.setStatus(Status.DONE);
        }

        return task;
    }

    private void loadFromFile() {
        try (FileReader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            BufferedReader br = new BufferedReader(reader);

            while (br.ready()) {
                String line = br.readLine();
                switch (initTypeTask(fromString(line))) {
                    case "Task": {
                        listTask.put(fromString(line).getId(),fromString(line));
                        break;
                    }
                    case "Subtask": {
                        listSubtask.put(fromString(line).getId(),(Subtask) fromString(line));
                        break;
                    }
                    case "Epic": {
                        listEpic.put(fromString(line).getId(),(Epic) fromString(line));
                        break;
                    }
                }
            }
            br.close();
        } catch (IOException error) {
            System.out.println("Ошибка при чтении файл ");
            return;
        }

    }

    @Override
    public void createNewTask(Task task) {
        super.createNewTask(task);
        tempTask = new ArrayList<>(super.getListTask());
        save();
    }

    @Override
    public void createNewSubtask(Subtask subtask) {
        super.createNewSubtask(subtask);
        tempTask = new ArrayList<>(super.getListSubtask());
        save();
    }

    @Override
    public void createNewEpic(Epic epic) {
        super.createNewEpic(epic);
        tempTask = new ArrayList<>(super.getListEpic());
        save();
    }
}
