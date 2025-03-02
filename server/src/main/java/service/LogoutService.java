package service;

import dataaccess.MemoryAuthDAO;
import exceptions.UnauthorizedException;
import model.AuthData;

public class LogoutService {
    public void logout(String authRequest) {
        AuthData authData = new MemoryAuthDAO().getAuth(authRequest);
        if(authData == null){
            throw new UnauthorizedException("Error: unauthorized");
        }
        new MemoryAuthDAO().deleteAuth(String.valueOf(authRequest));
    }
}
