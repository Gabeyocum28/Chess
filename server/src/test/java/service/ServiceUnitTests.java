package service;

import dataaccess.DataAccessException;
import exceptions.AlreadyTakenException;
import exceptions.UnauthorizedException;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.SQLException;

public class ServiceUnitTests {

    @BeforeEach
    public void clear() throws SQLException, DataAccessException {
        new ClearService().clear();
    }

    @Test
    public void newUser(){
        UserData user = new UserData("username", "password", "email");

        try {
            AuthData auth = RegisterService.registerUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void failNewUser() throws Exception{
        UserData user = new UserData("username", "password", "email");

        try {
            AuthData auth = RegisterService.registerUser(user);
            Assertions.assertThrows(AlreadyTakenException.class, () -> {
                RegisterService.registerUser(user);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void loginUser() throws SQLException, DataAccessException {
        UserData user = new UserData("username", "password", "email");

        AuthData auth;
        try {
            auth = RegisterService.registerUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Login login = new Login(user.username(), user.password());
        auth = LoginService.login(login);
    }

    @Test
    public void failLoginUser() throws Exception{

        try {
            Login login = new Login("username", "password");
            Assertions.assertThrows(UnauthorizedException.class, () -> {
                LoginService.login(login);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void logoutUser() throws SQLException, DataAccessException {
        UserData user = new UserData("username", "password", "email");

        AuthData auth;
        try {
            auth = RegisterService.registerUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        new LogoutService().logout(auth.authToken());
    }

    @Test
    public void failLogoutUser() throws Exception{

        try {

            Assertions.assertThrows(UnauthorizedException.class, () -> {
                new LogoutService().logout("Bad Auth");
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createGame() throws SQLException, DataAccessException {
        UserData user = new UserData("username", "password", "email");

        AuthData auth;
        try {
            auth = RegisterService.registerUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        GameData g = new GameData(0,null,null,"game", null);

        GameData game = CreateGameService.createGame(auth.authToken(), g);
    }

    @Test
    public void failCreateGame() throws Exception{

        try {
            GameData g = new GameData(0,null,null,"game", null);
            Assertions.assertThrows(UnauthorizedException.class, () -> {
                GameData game = CreateGameService.createGame("Bad Auth",g);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void listGame() throws SQLException, DataAccessException {
        UserData user = new UserData("username", "password", "email");

        AuthData auth;
        try {
            auth = RegisterService.registerUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        GameData g = new GameData(0,null,null,"game", null);

        GameData game = CreateGameService.createGame(auth.authToken(), g);

        System.out.println(ListGamesService.listGames(auth.authToken()));
    }

    @Test
    public void failListGame() throws Exception{

        try {
            GameData g = new GameData(0,null,null,"game", null);
            Assertions.assertThrows(UnauthorizedException.class, () -> {
                ListGamesService.listGames("Bad Auth");
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void joinGame() throws SQLException, DataAccessException {
        UserData user = new UserData("username", "password", "email");

        AuthData auth;
        try {
            auth = RegisterService.registerUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        GameData g = new GameData(0,null,null,"game", null);

        GameData game = CreateGameService.createGame(auth.authToken(), g);
        JoinRequest join = new JoinRequest("WHITE", game.gameID());
        JoinGameService.joinGame(auth.authToken(), join);
        System.out.println(ListGamesService.listGames(auth.authToken()));
    }

    @Test
    public void failJoinGame() throws Exception{

        try {
            GameData g = new GameData(0,null,null,"game", null);
            Assertions.assertThrows(UnauthorizedException.class, () -> {
                JoinRequest join = new JoinRequest("WHITE", 0);
                JoinGameService.joinGame("Bad Auth", join);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
