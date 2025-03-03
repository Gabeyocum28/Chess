package service;

import chess.ChessGame;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import exceptions.UnauthorizedException;
import model.AuthData;
import model.GameData;
import model.Login;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class LogoutTest {

    @BeforeEach
    public void setup() {
        new ClearService().clear();
    }

    @Test
    public void SuccessfulLogout() throws Exception {
        HashMap<String, AuthData> emptyAuthData = new HashMap<>();

        UserData userData = new UserData("username", "password", "email");

        String authToken = RegisterService.registerUser(userData).authToken();
        AuthData authData = new AuthData(authToken, userData.username());
        new MemoryAuthDAO().createAuth(authData);

        new LogoutService().logout(authToken);

        Assertions.assertEquals(emptyAuthData,MemoryAuthDAO.authtokens);


    }

    @Test
    public void FailedLogout() throws Exception {
        UserData userData = new UserData("username", "password", "email");

        String authToken = RegisterService.registerUser(userData).authToken();
        AuthData authData = new AuthData(authToken, userData.username());
        new MemoryAuthDAO().createAuth(authData);

        try {
            new LogoutService().logout("Bad-AuthToken");
        } catch (UnauthorizedException e) {

        }

    }
}
