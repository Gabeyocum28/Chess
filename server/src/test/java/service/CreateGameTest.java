package service;

import chess.ChessGame;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import exceptions.UnauthorizedException;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class CreateGameTest {

    @BeforeEach
    public void setup() {
        new ClearService().clear();
    }

    @Test
    public void successfulCreateGame() throws Exception {
        HashMap<Integer, GameData> games = new HashMap<>();

        GameData game = new GameData(2,"whiteUsername","blackUsername","gameName", new ChessGame());
        UserData userData = new UserData("username", "password", "email");

        String authToken = RegisterService.registerUser(userData).authToken();
        AuthData authData = new AuthData(authToken, userData.username());
        new MemoryAuthDAO().createAuth(authData);

        int gameID = CreateGameService.createGame(authToken,game).gameID();
        GameData newGame = new GameData(gameID,"whiteUsername","blackUsername","gameName", new ChessGame());
        games.put(newGame.gameID(), newGame);

        Assertions.assertEquals(games,MemoryGameDAO.GAMES);

    }

    @Test
    public void failedCreateGame() throws Exception {
        HashMap<Integer, GameData> games = new HashMap<>();

        GameData game = new GameData(20,"whiteUsername","blackUsername","gameName", new ChessGame());
        UserData userData = new UserData("username", "password", "email");

        games.put(game.gameID(), game);
        String authToken = RegisterService.registerUser(userData).authToken();
        AuthData authData = new AuthData(authToken, userData.username());
        new MemoryAuthDAO().createAuth(authData);

        try {
            CreateGameService.createGame("Bad-AuthToken",game);
        } catch (UnauthorizedException e) {

        }


    }
}
