package com.yandex.practicum.service;

import com.yandex.practicum.models.Epic;
import com.yandex.practicum.models.Subtask;
import com.yandex.practicum.models.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {
    @BeforeEach
    public void setup() throws FileNotFoundException {
        String filePath = "output.csv";
        try {
            File file = new File(filePath);
            manager = Managers.getDefaultFileManager(file);
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка при чтении файла");
        }

    }

    @Test
    public void createAndSaveTaskFromFile() throws IOException {
        Task task = new Task("qwfq", "qwfqwf");
        LocalDate data = LocalDate.of(2004, 5, 21);
        LocalTime time = LocalTime.now();
        LocalDateTime dataTime = LocalDateTime.of(data, time);
        Duration duration = Duration.ofMinutes(12_545_655);
        task.setStartTime(dataTime);
        task.setDuration(duration);

        manager.createNewTask(task);

        assertNotNull(manager.getListTask());
    }

    @Test
    public void createAndSaveSubForFile() throws IOException {
        Subtask subtask = new Subtask("а туту ужэе будет саб", "qwfqwfqwf");

        manager.createNewSubtask(subtask);

        assertNotNull(manager.getHistory());
    }

    @Test
    public void checkAddAndWriteFromFileTaskAndSubtaskAndEpic() {
        Task task = new Task("Тут будет тип Task", "Какое то описание");
        Subtask sub = new Subtask("Тут будет тип Subtask", "Какое то описание");
        Epic epic = new Epic("Tут будет тип Epic", "Какое то описание");

        manager.createNewTask(task);
        manager.createNewSubtask(sub);
        manager.createNewEpic(epic);

        final int idTask = task.getId();
        final int idSub = sub.getId();
        final int idEpic = epic.getId();

        assertEquals(task, manager.getTaskById(idTask));
        assertEquals(sub, manager.getSubtaskById(idSub));
        assertEquals(epic, manager.getEpicById(idEpic));

    }

    @Test
    public void loadFromFile() {
        assertNotNull(manager.getListEpic(), "Пусто");
        assertNotNull(manager.getListSubtask(), "Пусто");
        assertNotNull(manager.getListEpic(), "Пусто");
    }
}



