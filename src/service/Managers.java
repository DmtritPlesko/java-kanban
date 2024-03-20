package service;

import service.TaskManager;

public class Managers  {
//    public static TaskManager createNewManager() { заготовка примерно такая будет
//
//
//
////        if(role.equals("admin")) {
////            return new MainManager();
////        }
////
////        return new SimpleManager();
//    }

    public  static HistoryManager getDefaultHistory () {
        return new InMemoryHistoryManager();
    }
    
    public TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

}
