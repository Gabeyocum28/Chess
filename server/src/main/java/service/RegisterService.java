package service;


import dataaccess.SQLAuthDAO;
import dataaccess.SQLUserDAO;
import exceptions.AlreadyTakenException;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class RegisterService {


    public static AuthData registerUser(UserData user) throws Exception {
        UserData userData = new SQLUserDAO().getUser(user.username());
        if(userData != null){
            throw new AlreadyTakenException("Error: Already Taken");
        }else{
            new SQLUserDAO().createUser(user);
        }

        String authToken = UUID.randomUUID().toString();
        AuthData newAuth = new AuthData(authToken, user.username());
        new SQLAuthDAO().createAuth(newAuth);

        return new SQLAuthDAO().getAuth(authToken);

    }
}