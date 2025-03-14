package service;

import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLUserDAO;
import exceptions.UnauthorizedException;
import model.AuthData;
import model.Login;
import model.UserData;

import java.util.UUID;

public class LoginService {
    public static AuthData login(Login login) throws DataAccessException {
        UserData user = new SQLUserDAO().getUser(login.username());
        if(user == null){
            throw new UnauthorizedException("Error: unauthorized");
        }
        if(login.password().equals(user.password())){
            String authToken = UUID.randomUUID().toString();
            AuthData newAuth = new AuthData(authToken, login.username());
            new SQLAuthDAO().createAuth(newAuth);

            return new SQLAuthDAO().getAuth(authToken);
        }
        throw new UnauthorizedException("Error: unauthorized");
    }
}
