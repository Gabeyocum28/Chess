package service;


import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import exceptions.AlreadyTakenException;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class RegisterService {


    public static AuthData registerUser(UserData user) throws Exception {
        UserData userData = new MemoryUserDAO().getUser(user.username());
        if(userData != null){
            throw new AlreadyTakenException("Error: Already Taken");
        }else{
            new MemoryUserDAO().createUser(user);
        }

        String authToken = UUID.randomUUID().toString();
        AuthData newAuth = new AuthData(authToken, user.username());
        new MemoryAuthDAO().createAuth(newAuth);

        return new MemoryAuthDAO().getAuth(authToken);

    }
}