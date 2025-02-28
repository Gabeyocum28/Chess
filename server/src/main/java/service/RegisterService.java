package service;


import dataaccess.DataAccessException;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;

import java.time.DateTimeException;

public class RegisterService {
    public static AuthData registerUser(UserData user){
        UserData userData = new MemoryUserDAO().getUser(user.username());
        if(userData != null){
            throw DataAccessException;
        }

    }
}