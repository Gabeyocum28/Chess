package dataaccess;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import dataaccess.SQLUserDAO;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;

import java.sql.SQLException;
import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DAOUnitTests {
    @BeforeEach
    public void clear() throws SQLException, DataAccessException {
        new ClearService().clear();

    }
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

    @Test
    public void UserDAOFailures() throws SQLException, DataAccessException {
        SQLUserDAO.configureDatabase();
        SQLUserDAO dao = new SQLUserDAO();

        // Attempt to retrieve a non-existent user
        UserData userData = dao.getUser("nonexistent_user");
        assertNull(userData, "Expected null user, but got not-null."); // Should fail if DAO correctly returns null

        // Attempt to create a duplicate user
        UserData validUser = new UserData("username", "password", "email");
        dao.createUser(validUser);
        assertThrows(Exception.class, () -> dao.createUser(validUser), "Expected exception for duplicate user.");

        // Attempt to retrieve a user after clearing the database
        dao.clear();
        UserData clearedUser = dao.getUser(validUser.username());
        assertNull(clearedUser, "Expected user to be null after clear, but got an object.");
    }

    @Test
    public void AuthDAOFailures() throws SQLException, DataAccessException {
        SQLAuthDAO.configureDatabase();
        SQLAuthDAO dao = new SQLAuthDAO();

        // Attempt to retrieve an auth token that doesn't exist
        AuthData authData = dao.getAuth("invalid_token");
        assertNull(authData, "Expected null auth data, but got data."); // Should fail if DAO correctly returns null

        // Create a valid auth token
        String authToken = UUID.randomUUID().toString();
        AuthData validAuth = new AuthData(authToken, "username");
        dao.createAuth(validAuth);

        // Attempt to delete a non-existent auth token
        dao.deleteAuth("nonexistent_token");

        // Ensure deleting a valid auth token actually removes it
        dao.deleteAuth(authToken);
        AuthData deletedAuth = dao.getAuth(authToken);
        assertNull(deletedAuth, "Expected auth token to be deleted, but it was still found."); // Should fail if deletion worked

        // Clear the database and check if auth token is still retrievable
        dao.clear();
        AuthData clearedAuth = dao.getAuth(authToken);
        assertNull(clearedAuth, "Expected null after clear, but got an object.");
    }

    @Test
    public void testGameDAOFailures() throws SQLException, DataAccessException {
        SQLGameDAO.configureDatabase();
        SQLGameDAO dao = new SQLGameDAO();

        // Attempt to retrieve a non-existent game
        Collection<GameList> gamesBefore = dao.listGames();
        assertTrue(gamesBefore.isEmpty(), "Expected an empty game list, but found games."); // Should fail if DAO correctly returns an empty list

        // Create a valid game
        GameData game = new GameData(0, null, null, "game", new ChessGame());
        int gameId = dao.createGame(game);

        // Attempt to update a non-existent game (should not throw an exception but also not affect any valid game)
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, "username");
        JoinRequest joinRequest = new JoinRequest("WHITE", 9999); // Non-existent game ID

        assertThrows(Exception.class, () -> dao.updateGame(joinRequest, authData), "Expected updateGame to handle non-existent game gracefully.");

        // Clear the database and check if games are still listed
        dao.clear();
        Collection<GameList> gamesAfterClear = dao.listGames();
        assertTrue(gamesAfterClear.isEmpty(), "Expected no games to be listed after clear, but found games.");
    }

}
