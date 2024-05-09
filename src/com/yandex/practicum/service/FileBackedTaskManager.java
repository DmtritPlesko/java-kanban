package com.yandex.practicum.service;

import com.yandex.practicum.enums.Status;
import com.yandex.practicum.mistakes.ManagerSaveException;
import com.yandex.practicum.models.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager implements Serializable {
    private static File file;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public FileBackedTaskManager(File fileName) throws FileNotFoundException {
        file = fileName;
        loadFromFile();
    }

    private void save() {
        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {

            for (Task task : super.getListTask()) {
                writer.write(toString(task));
            }
            for (Subtask sub : super.getListSubtask()) {
                writer.write(toString(sub));
            }
            for (Epic epic : super.getListEpic()) {
                writer.write(toString(epic));
            }
        } catch (IOException error) {
            throw new ManagerSaveException("Ошибка при записи в файл");
        }
    }

    public String toString(Task task) {

        String temp = task.getId().toString() + "," + initTypeTask(task) + "," + task.getName()
                + "," + task.getStatus() + "," + task.getDescription();
        if (initTypeTask(task).equals("Subtask")) {
            temp += "," + super.getSubtaskById(task.getId()).getIdEpic();
        }
        temp += ","+task.getStartTime().format(formatter)+","+super.getEndTime(task).format(formatter);
        temp += '\n';
        return temp;
    }

    private String initTypeTask(Task task) {
        Object obj = task;
        if (task instanceof Subtask) {
            return "Subtask";
        } else if (task instanceof Epic) {
            return "Epic";
        } else {
            return "Task";
        }
    }

    private <T extends Task> T createObj(String value) {
        Object obj = null;
        String[] tempLine = value.split(",");
        switch (tempLine[1]) {
            case "Task": {
                Task task = new Task(tempLine[2], tempLine[4]);
                task.setStatus(takeStatus(tempLine[3]));
                task.setID(Integer.parseInt(tempLine[0]));
                String[] dataAndTime = tempLine[5].split(" ");//время начала

                obj = task;
                break;
            }
            case "Subtask": {
                Subtask sub = new Subtask(tempLine[2], tempLine[4]);
                sub.setStatus(takeStatus(tempLine[3]));
                sub.setID(Integer.parseInt(tempLine[0]));
                sub.setIdEpic(Integer.parseInt(tempLine[5]));
                obj = sub;
                break;
            }
            case "Epic": {
                Epic epic = new Epic(tempLine[2], tempLine[4]);
                epic.setStatus(takeStatus(tempLine[3]));
                epic.setID(Integer.parseInt(tempLine[0]));
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
            while (br.ready()) {
                String line = br.readLine();
                Object obj = createObj(line);
                if (obj instanceof Subtask) {
                    super.listSubtask.put(createObj(line).getId(), createObj(line));
                } else if (obj instanceof Epic) {
                    super.listEpic.put(createObj(line).getId(), createObj(line));
                } else {
                    super.listTask.put(createObj(line).getId(), createObj(line));
                }
            }
            br.close();
        } catch (IOException error) {
            throw new ManagerSaveException("Ошибка при чтении файла");
        }
    }

    @Override
    public void createNewTask(Task task) {
        super.createNewTask(task);
        save();
    }

    @Override
    public void createNewSubtask(Subtask subtask) {
        super.createNewSubtask(subtask);
        save();
    }

    @Override
    public void createNewEpic(Epic epic) {
        super.createNewEpic(epic);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteTaskForID(Integer id) {
        super.deleteTaskForID(id);
        save();
    }

    @Override
    public void deleteSubtaskForID(Integer id) {
        super.deleteSubtaskForID(id);
        save();
    }

    @Override
    public void deleteEpicForID(Integer id) {
        super.deleteEpicForID(id);
        save();
    }

    @Override
    public void deleteAllTask() {
        super.deleteAllTask();
        save();
    }

}
