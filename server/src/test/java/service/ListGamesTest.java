package service;

import chess.ChessGame;
import dataaccess.MemoryAuthDAO;
import exceptions.UnauthorizedException;
import model.AuthData;
import model.GameData;
import model.GameList;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public class ListGamesTest {

    @BeforeEach
    public void setup() {
        new ClearService().clear();
    }

    @Test
    public void successfulListGames() throws Exception {
        GameData game = new GameData(2,"whiteUsername","blackUsername","gameName", new ChessGame());
        UserData userData = new UserData("username", "password", "email");

        String authToken = RegisterService.registerUser(userData).authToken();
        AuthData authData = new AuthData(authToken, userData.username());
        new MemoryAuthDAO().createAuth(authData);
        CreateGameService.createGame(authToken,game);

        Collection<GameList> gamesCollection = ListGamesService.listGames(authToken);


        Assertions.assertNotNull(gamesCollection);
    }

    @Test
    public void failedListGames() throws Exception {

        GameData game = new GameData(2,"whiteUsername","blackUsername","gameName", new ChessGame());
        UserData userData = new UserData("username", "password", "email");

        String authToken = RegisterService.registerUser(userData).authToken();
        AuthData authData = new AuthData(authToken, userData.username());
        new MemoryAuthDAO().createAuth(authData);
        CreateGameService.createGame(authToken,game);

        try {
            ListGamesService.listGames("Bad-AuthToken");
        } catch (UnauthorizedException e) {

        }

    }
}
