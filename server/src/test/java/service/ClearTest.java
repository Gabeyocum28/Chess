package service;

import chess.ChessGame;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.GameData;
import model.GameList;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.HashMap;

public class ClearTest {

    @Test
    public void insertTest(){
        HashMap<String, UserData> userData = new HashMap<>();
        HashMap<Integer, GameData> gameData = new HashMap<>();
        HashMap<String, AuthData> authData = new HashMap<>();
        HashMap<Integer, GameList> gamesOutput = new HashMap<>();

        UserData user = new UserData("username", "password", "email");
        GameData game = new GameData(1234,"whiteUsername","blackUsername","gameName", new ChessGame());
        GameList list = new GameList(1234,"whiteUsername","blackUsername","gameName");
        AuthData auth = new AuthData("authToken", "username");

        new MemoryUserDAO().createUser(user);
        userData.put(user.username(), user);
        new MemoryGameDAO().createGame(game);
        gameData.put(game.gameID(), game);
        gamesOutput.put(game.gameID(), list);
        new MemoryAuthDAO().createAuth(auth);
        authData.put(auth.authToken(), auth);

        Assertions.assertEquals(userData,MemoryUserDAO.USERS);
        Assertions.assertEquals(gameData,MemoryGameDAO.GAMES);
        Assertions.assertEquals(gamesOutput,MemoryGameDAO.GAMES_OUTPUT);
        Assertions.assertEquals(authData,MemoryAuthDAO.AUTH_TOKENS);

    }

    @Test
    public void clearAll(){
        HashMap<String, UserData> emptyUserData = new HashMap<>();
        HashMap<String, GameData> emptyGameData = new HashMap<>();
        HashMap<String, AuthData> emptyAuthData = new HashMap<>();
        HashMap<Integer, GameList> emptygamesOutput = new HashMap<>();

        new MemoryUserDAO().createUser(new UserData("username", "password", "email"));
        new MemoryGameDAO().createGame(new GameData(1234,"whiteUsername","blackUsername","gameName", new ChessGame()));
        new MemoryAuthDAO().createAuth(new AuthData("authToken", "username"));

        new ClearService().clear();

        Assertions.assertEquals(emptyUserData,MemoryUserDAO.USERS);
        Assertions.assertEquals(emptyGameData,MemoryGameDAO.GAMES);
        Assertions.assertEquals(emptygamesOutput,MemoryGameDAO.GAMES_OUTPUT);
        Assertions.assertEquals(emptyAuthData,MemoryAuthDAO.AUTH_TOKENS);

    }
}
