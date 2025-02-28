package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;

public class ClearService {
    public void clear(){
        new MemoryUserDAO().clear();
        new MemoryAuthDAO().clear();
        new MemoryGameDAO().clear();
    }
}
