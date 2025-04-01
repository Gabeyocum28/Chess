package client;

import chess.ChessGame;
import exceptions.ResponseException;
import model.*;
import org.junit.jupiter.api.*;
import server.Server;
import server.ServerFacade;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() throws MalformedURLException, URISyntaxException, ResponseException {

        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:8080");
    }

    @AfterAll
    static void stopServer() throws ResponseException {
        server.stop();
    }

    @BeforeEach
    void clear() throws ResponseException {
        facade.clear();
    }

    @Test
    void registerSuccess() throws ResponseException {
        UserData user = new UserData("username", "password", "email");
        assertNotNull(facade.register(user));
    }

    @Test
    void registerFail() throws ResponseException {
        UserData user = new UserData("duplicateUsername", "password", "email");
        facade.register(user);
        assertThrows(Exception.class, () -> facade.register(user), "Expected exception when creating a duplicate user.");
    }

    @Test
    void loginSuccess() throws ResponseException{
        UserData user = new UserData("login", "password", "email");
        facade.register(user);

        Login login = new Login(user.username(), user.password());
        assertNotNull(facade.login(login));
    }

    @Test
    void loginFail(){
        Login login = new Login("Nonexistent", "password");
        assertThrows(Exception.class, () -> facade.login(login), "Expected exception when logging in");
    }

    @Test
    void createGameSuccess() throws ResponseException {
        AuthData auth = facade.register(new UserData("gameUser1", "password", "email"));
        GameData game = new GameData(0, null, null, "game1", null);
        assertNotNull(facade.createGame(auth.authToken(), game));
    }

    @Test
    void createGameFail() throws ResponseException {
        AuthData auth = facade.register(new UserData("gameUser2", "password", "email"));
        GameData game = new GameData(0, null, null, null, null);
        assertThrows(Exception.class, () -> facade.createGame(auth.authToken(), game), "Expected no game creation");
    }

    @Test
    void listGameSuccess() throws ResponseException {
        AuthData auth = facade.register(new UserData("gameUser3", "password", "email"));
        GameData game2 = new GameData(0, null, null, "game2", null);
        facade.createGame(auth.authToken(), game2);

        Collection<GameList> games;
        games = facade.listGames(auth.authToken());
        assertTrue(games.size() >= 1);
    }

    @Test
    void listGameFail() throws ResponseException {
        AuthData auth = facade.register(new UserData("gameUser4", "password", "email"));

        assertThrows(Exception.class, () -> facade.listGames("FalseAuth"), "Expected no games");
    }

    @Test
    void logoutSuccess() throws ResponseException {
        AuthData auth = facade.register(new UserData("logoutUser", "password", "email"));
        facade.logout(auth.authToken());

    }

    @Test
    void logoutFailed() throws ResponseException {
        AuthData auth = facade.register(new UserData("logoutUser1", "password", "email"));
        assertThrows(Exception.class, () -> facade.logout("FalseAuth"), "Expected no logout");
    }

    @Test
    void joinSuccess() throws ResponseException {
        AuthData auth = facade.register(new UserData("gameUser7", "password", "email"));
        GameData game2 = new GameData(0, null, null, "game7", null);
        facade.createGame(auth.authToken(), game2);

        Collection<GameList> games;
        Map<Integer, GameList> gameMap;
        gameMap = new HashMap<>();
        games = facade.listGames(auth.authToken());
        int index = 1;
        for (GameList game : games) {
            gameMap.put(index++, game);
        }

        facade.joinGame(auth.authToken(), new JoinRequest("white", gameMap.get(1).gameID()));
    }

    @Test
    void joinFailed() throws ResponseException {
        AuthData auth = facade.register(new UserData("gameUser8", "password", "email"));
        GameData game2 = new GameData(0, null, null, "game8", null);
        facade.createGame(auth.authToken(), game2);

        assertThrows(Exception.class, () -> facade.joinGame(auth.authToken(), new JoinRequest("white",
                0)), "Expected no game to exist with index 0");

    }


}
