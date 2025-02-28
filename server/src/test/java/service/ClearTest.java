package service;

import chess.ChessGame;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.HashMap;

public class ClearTest {

    @Test
    public void insertTest(){
        HashMap<String, UserData> userData = new HashMap<>();
        HashMap<String, GameData> gameData = new HashMap<>();
        HashMap<String, AuthData> authData = new HashMap<>();

        UserData user = new UserData("username", "password", "email");
        GameData game = new GameData(1234,"whiteUsername","blackUsername","gameName", new ChessGame());
        AuthData auth = new AuthData("authToken", "username");

        new MemoryUserDAO().createUser(user);
        userData.put(user.username(), user);
        new MemoryGameDAO().createGame(game);
        gameData.put(game.gameName(), game);
        new MemoryAuthDAO().createAuth(auth);
        authData.put(auth.authToken(), auth);

        Assertions.assertEquals(userData,MemoryUserDAO.users);
        Assertions.assertEquals(gameData,MemoryGameDAO.games);
        Assertions.assertEquals(authData,MemoryAuthDAO.authtokens);

    }

    @Test
    public void ClearAll(){
        HashMap<String, UserData> emptyUserData = new HashMap<>();
        HashMap<String, GameData> emptyGameData = new HashMap<>();
        HashMap<String, AuthData> emptyAuthData = new HashMap<>();

        new MemoryUserDAO().createUser(new UserData("username", "password", "email"));
        new MemoryGameDAO().createGame(new GameData(1234,"whiteUsername","blackUsername","gameName", new ChessGame()));
        new MemoryAuthDAO().createAuth(new AuthData("authToken", "username"));

        new ClearService().clear();

        Assertions.assertEquals(emptyUserData,MemoryUserDAO.users);
        Assertions.assertEquals(emptyGameData,MemoryGameDAO.games);
        Assertions.assertEquals(emptyAuthData,MemoryAuthDAO.authtokens);

    }
}
