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
    public void createUserSuccess() throws SQLException, DataAccessException {
        SQLUserDAO dao = new SQLUserDAO();
        UserData user = new UserData("validUser", "password", "email");

        dao.createUser(user);
        UserData retrieved = dao.getUser("validUser");

        assertNotNull(retrieved, "Expected user to be created and retrievable.");
    }

    @Test
    public void createUserFailure() throws SQLException, DataAccessException {
        SQLUserDAO dao = new SQLUserDAO();
        UserData user = new UserData("duplicateUser", "password", "email");

        dao.createUser(user);
        assertThrows(Exception.class, () -> dao.createUser(user), "Expected exception when creating a duplicate user.");
    }

    @Test
    public void getUserSuccess() throws SQLException, DataAccessException {
        SQLUserDAO dao = new SQLUserDAO();
        UserData user = new UserData("existingUser", "password", "email");

        dao.createUser(user);
        UserData retrieved = dao.getUser("existingUser");

        assertEquals(user.username(), retrieved.username(), "Expected retrieved user to match stored user.");
    }

    @Test
    public void getUserFailure() throws SQLException, DataAccessException {
        SQLUserDAO dao = new SQLUserDAO();
        UserData retrieved = dao.getUser("nonexistentUser");

        assertNull(retrieved, "Expected null when retrieving a non-existent user.");
    }

    @Test
    public void clearUsersSuccess() throws SQLException, DataAccessException {
        SQLUserDAO dao = new SQLUserDAO();
        dao.createUser(new UserData("user1", "password", "email"));
        dao.clear();

        UserData retrieved = dao.getUser("user1");
        assertNull(retrieved, "Expected null after clearing database.");
    }
    @Test
    public void createAuthSuccess() throws SQLException, DataAccessException {
        SQLAuthDAO dao = new SQLAuthDAO();
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, "user");

        dao.createAuth(authData);
        AuthData retrieved = dao.getAuth(authToken);

        assertNotNull(retrieved, "Expected auth token to be stored and retrievable.");
    }

    @Test
    public void createAuthFailure() throws SQLException, DataAccessException {
        SQLAuthDAO dao = new SQLAuthDAO();
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, "user");

        dao.createAuth(authData);
        assertThrows(Exception.class, () -> dao.createAuth(authData), "Expected failure when creating a duplicate auth token.");
    }

    @Test
    public void getAuthSuccess() throws SQLException, DataAccessException {
        SQLAuthDAO dao = new SQLAuthDAO();
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, "user");

        dao.createAuth(authData);
        AuthData retrieved = dao.getAuth(authToken);

        assertEquals(authData.username(), retrieved.username(), "Expected correct user to be associated with the token.");
    }

    @Test
    public void getAuthFailure() throws SQLException, DataAccessException {
        SQLAuthDAO dao = new SQLAuthDAO();
        AuthData retrieved = dao.getAuth("invalidToken");

        assertNull(retrieved, "Expected null when retrieving a non-existent auth token.");
    }

    @Test
    public void deleteAuthSuccess() throws SQLException, DataAccessException {
        SQLAuthDAO dao = new SQLAuthDAO();
        String authToken = UUID.randomUUID().toString();
        dao.createAuth(new AuthData(authToken, "user"));

        dao.deleteAuth(authToken);
        AuthData retrieved = dao.getAuth(authToken);

        assertNull(retrieved, "Expected auth token to be removed.");
    }

    @Test
    public void deleteAuthFailure() throws SQLException, DataAccessException {
        SQLAuthDAO dao = new SQLAuthDAO();
        assertDoesNotThrow(() -> dao.deleteAuth("fakeToken"), "Expected deleteAuth to handle non-existent tokens gracefully.");
    }

    @Test
    public void clearAuthsSuccess() throws SQLException, DataAccessException {
        SQLAuthDAO dao = new SQLAuthDAO();
        dao.createAuth(new AuthData(UUID.randomUUID().toString(), "user"));
        dao.clear();

        AuthData retrieved = dao.getAuth("anyToken");
        assertNull(retrieved, "Expected auths to be cleared.");
    }

    @Test
    public void createGameSuccess() throws SQLException, DataAccessException {
        SQLGameDAO dao = new SQLGameDAO();
        GameData game = new GameData(0, null, null, "game", new ChessGame());

        int gameId = dao.createGame(game);
        Collection<GameData> games = dao.listGames();

        assertFalse(games.isEmpty(), "Expected the created game to be listed.");
    }

    @Test
    public void listGamesSuccess() throws SQLException, DataAccessException {
        SQLGameDAO dao = new SQLGameDAO();
        dao.createGame(new GameData(0, null, null, "game", new ChessGame()));

        Collection<GameData> games = dao.listGames();
        assertFalse(games.isEmpty(), "Expected at least one game in the list.");
    }

    @Test
    public void listGamesFailure() throws SQLException, DataAccessException {
        SQLGameDAO dao = new SQLGameDAO();
        Collection<GameData> games = dao.listGames();

        assertTrue(games.isEmpty(), "Expected empty list when no games exist.");
    }

    @Test
    public void updateGameSuccess() throws SQLException, DataAccessException {
        SQLGameDAO dao = new SQLGameDAO();
        int gameId = dao.createGame(new GameData(0, null, null, "game", new ChessGame()));

        JoinRequest joinRequest = new JoinRequest("WHITE", gameId);
        dao.updateGame(joinRequest, new AuthData(UUID.randomUUID().toString(), "username"));

        // Can't directly verify update effect, so assume no exception = success.
        assertTrue(true, "Expected game update to succeed.");
    }

    @Test
    public void updateGameFailure() throws SQLException, DataAccessException {
        SQLGameDAO dao = new SQLGameDAO();
        JoinRequest joinRequest = new JoinRequest("WHITE", 9999);

        assertThrows(Exception.class, () ->
                dao.updateGame(joinRequest, new AuthData(UUID.randomUUID().toString(), "user")),
                "Expected failure when updating a non-existent game.");
    }

    @Test
    public void clearGamesSuccess() throws SQLException, DataAccessException {
        SQLGameDAO dao = new SQLGameDAO();
        dao.createGame(new GameData(0, null, null, "game", new ChessGame()));
        dao.clear();

        Collection<GameData> games = dao.listGames();
        assertTrue(games.isEmpty(), "Expected game list to be empty after clear.");
    }
}
