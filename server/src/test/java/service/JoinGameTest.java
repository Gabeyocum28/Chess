package service;

import chess.ChessGame;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import exceptions.UnauthorizedException;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class JoinGameTest {

    @BeforeEach
    public void setup() {
        new ClearService().clear();
    }

    @Test
    public void successfulJoinGame() throws Exception {
        HashMap<Integer, GameData> games = new HashMap<>();

        GameData game = new GameData(2,null,null,"gameName", new ChessGame());
        GameList gameList = new GameList(2,null,null,"gameName");
        UserData userData = new UserData("username", "password", "email");


        String authToken = RegisterService.registerUser(userData).authToken();
        AuthData authData = new AuthData(authToken, userData.username());
        new MemoryAuthDAO().createAuth(authData);


        int gameID = CreateGameService.createGame(authToken,game).gameID();
        JoinRequest joinRequest = new JoinRequest("WHITE", gameID);
        games.put(gameID, new GameData(4,"username",null,"gameName", new ChessGame()));

        JoinGameService.joinGame(authToken, joinRequest);
        Assertions.assertEquals(games,MemoryGameDAO.GAMES);


    }

    @Test
    public void failedJoinGame() throws Exception {
        HashMap<Integer, GameData> games = new HashMap<>();

        GameData game = new GameData(2,null,null,"gameName", new ChessGame());
        GameList gameList = new GameList(2,null,null,"gameName");
        UserData userData = new UserData("username", "password", "email");


        String authToken = RegisterService.registerUser(userData).authToken();
        AuthData authData = new AuthData(authToken, userData.username());
        new MemoryAuthDAO().createAuth(authData);


        int gameID = CreateGameService.createGame(authToken,game).gameID();
        JoinRequest joinRequest = new JoinRequest("WHITE", gameID);
        games.put(gameID, new GameData(4,"username",null,"gameName", new ChessGame()));

        try {
            JoinGameService.joinGame("Bad-AuthToken",joinRequest);
        } catch (UnauthorizedException e) {

        }
    }
}
