package service;

import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import exceptions.UnauthorizedException;
import model.AuthData;

public class LogoutService {
    public void logout(String authRequest) throws DataAccessException {
        AuthData authData = new SQLAuthDAO().getAuth(authRequest);
        if(authData == null){
            throw new UnauthorizedException("Error: unauthorized");
        }
        new SQLAuthDAO().deleteAuth(String.valueOf(authRequest));
    }
}
