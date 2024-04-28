package com.yandex.practicum.service;

import com.yandex.practicum.enums.Status;
import com.yandex.practicum.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private static File file;
    private Map<Integer, Task> listTask;
    private Map<Integer, Subtask> listSubtask;
    private Map<Integer, Epic> listEpic;
    private List<Task> tempTask;
    private static int count = 1;

    public FileBackedTaskManager(String fileName) throws FileNotFoundException {
        file = new File(fileName);
        listTask = new HashMap<>();
        listSubtask = new HashMap<>();
        listEpic = new HashMap<>();
        loadFromFile();
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

    public String toString(Task task) {

        String temp = count + "," + task.getId().toString() + "," + initTypeTask(task) + "," + task.getName()
                + "," + task.getStatus() + "," + task.getDescription();
        count++;
        return temp;
    }

    private String initTypeTask(Task task) {
        Object obj = task;
        if (task.getClass().toString().equals("Task")) {
            return "Task";
        } else if (task.getClass().toString().equals("Subtask")) {
            return "Subtask";
        } else {
            return "Epic";
        }
    }

    private <T extends Task> T createObj(String value) {
        Object obj = null;
        String[] tempLine = value.split(",");
        switch (tempLine[3]) {
            case "Task": {
                Task task = new Task(tempLine[3], tempLine[5]);
                task.setStatus(takeStatus(tempLine[4]));
                task.setID(Integer.parseInt(tempLine[1]));
                obj = task;
                break;
            }
            case "Subtask": {
                Subtask sub = new Subtask(tempLine[3], tempLine[5]);
                sub.setStatus(takeStatus(tempLine[4]));
                sub.setID(Integer.parseInt(tempLine[1]));
                obj = sub;
                break;
            }
            case "Epic": {
                Epic epic = new Epic(tempLine[3], tempLine[5]);
                epic.setStatus(takeStatus(tempLine[4]));
                epic.setID(Integer.parseInt(tempLine[1]));
                obj = epic;

                break;
            }
        }
        return (T) obj;
    }

    private Status takeStatus(String str) {
        if (str.equals(Status.NEW.toString())) {
            return Status.NEW;
        } else if (str.equals(Status.IN_PROGRESS.toString())) {
            return Status.IN_PROGRESS;
        } else {
            return Status.DONE;
        }
    }

    private void loadFromFile() {
        try (FileReader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            BufferedReader br = new BufferedReader(reader);
            br.readLine(); //считываем первую строку
            while (br.ready()) {
                String line = br.readLine();
                Object obj = createObj(line);
                if (obj instanceof Task) {
                    listTask.put(createObj(line).getId(), createObj(line));
                } else if (obj instanceof Subtask) {
                    listSubtask.put(createObj(line).getId(), createObj(line));
                } else {
                    listEpic.put(createObj(line).getId(), createObj(line));
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
        tempTask = super.getListTask();
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
