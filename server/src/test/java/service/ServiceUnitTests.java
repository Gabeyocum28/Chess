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
    public void Clear() throws SQLException, DataAccessException {
        new ClearService().clear();
    }

    @Test
    public void NewUser(){
        UserData user = new UserData("username", "password", "email");

        try {
            AuthData auth = RegisterService.registerUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void FailNewUser() throws Exception{
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
    public void LoginUser() throws SQLException, DataAccessException {
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
    public void FailLoginUser() throws Exception{

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
    public void LogoutUser() throws SQLException, DataAccessException {
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
    public void FailLogoutUser() throws Exception{

        try {

            Assertions.assertThrows(UnauthorizedException.class, () -> {
                new LogoutService().logout("Bad Auth");
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void CreateGame() throws SQLException, DataAccessException {
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
    public void FailCreateGame() throws Exception{

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
    public void ListGame() throws SQLException, DataAccessException {
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
    public void FailListGame() throws Exception{

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
    public void JoinGame() throws SQLException, DataAccessException {
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
    public void FailJoinGame() throws Exception{

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
