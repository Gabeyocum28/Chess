package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import dataaccess.SQLUserDAO;
import model.*;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

public class DAOUnitTests {
    @Test
    public void userDAOTest() throws SQLException, DataAccessException {
        UserData userData = new UserData("username", "password", "email");
        SQLUserDAO.configureDatabase();

        new SQLUserDAO().createUser(userData);
        UserData authData = new SQLUserDAO().getUser(userData.username());
        new SQLUserDAO().clear();
    }

    @Test
    public void authDAOTest() throws SQLException, DataAccessException {
        UserData userData = new UserData("username", "password", "email");
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, userData.username());
        SQLAuthDAO.configureDatabase();

        new SQLAuthDAO().createAuth(authData);
        authData = new SQLAuthDAO().getAuth(userData.username());
        new SQLAuthDAO().deleteAuth(authToken);
        new SQLAuthDAO().clear();
    }

    @Test
    public void gameDAOTest() throws SQLException, DataAccessException {
        UserData userData = new UserData("username", "password", "email");
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, userData.username());
        GameData game = new GameData(0,null,null, "game", new ChessGame());
        JoinRequest joinRequest = new JoinRequest("WHITE", new SQLGameDAO().createGame(game) + 1);
        SQLGameDAO.configureDatabase();

        new SQLGameDAO().createGame(game);
        System.out.println(new SQLGameDAO().listGames());
        new SQLGameDAO().updateGame(joinRequest, authData);
        new SQLGameDAO().clear();
    }
}
