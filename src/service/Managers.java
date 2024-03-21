package service;

import service.TaskManager;

public class Managers  {
    public  static HistoryManager getDefaultHistory () {
        return new InMemoryHistoryManager();
    }
    
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }



}
