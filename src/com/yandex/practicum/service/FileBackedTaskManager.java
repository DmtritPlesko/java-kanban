package com.yandex.practicum.service;

import com.yandex.practicum.enums.Status;
import com.yandex.practicum.mistakes.ManagerSaveException;
import com.yandex.practicum.models.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private static File file;

    public FileBackedTaskManager(String fileName) throws FileNotFoundException {
        file = new File(fileName);
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
                    super.addInTaskList(createObj(line).getId(), createObj(line));
                } else if (obj instanceof Subtask) {
                    super.addInSubtaskList(createObj(line).getId(), createObj(line));
                } else {
                    super.addInEpicList(createObj(line).getId(), createObj(line));
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
