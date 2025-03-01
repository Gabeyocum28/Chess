package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import exceptions.UnauthorizedException;
import model.AuthData;
import model.Login;
import model.UserData;

import java.util.UUID;

public class LoginService {
    public static AuthData login(Login login) {
        UserData user = new MemoryUserDAO().getUser(login.username());
        if(user == null){
            throw new UnauthorizedException("Error: unauthorized");
        }
        if(login.password().equals(user.password())){
            String authToken = UUID.randomUUID().toString();
            AuthData newAuth = new AuthData(authToken, login.username());
            new MemoryAuthDAO().createAuth(newAuth);

            return new MemoryAuthDAO().getAuth(authToken);
        }
        throw new UnauthorizedException("Error: unauthorized");
    }
}
