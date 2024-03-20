package service;

import service.TaskManager;

public class Managers  {
//    public static TaskManager createNewManager() {
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


}
