package service;

import dataaccess.DataAccessException;
import exceptions.UnauthorizedException;
import model.Login;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class LoginTest {

    @BeforeEach
    public void setup() throws SQLException, DataAccessException {
        new ClearService().clear();
    }

    @Test
    public void successfulLogin() throws SQLException, DataAccessException {
        UserData userData = new UserData("username", "password", "email");
        Login login = new Login("username", "password");
        try {
            RegisterService.registerUser(userData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assertions.assertNotNull(LoginService.login(login));
    }

    @Test
    public void failedLogout(){
        UserData userData = new UserData("username", "password", "email");
        Login wrongPasswordLogin = new Login("username", "wrongPassword");
        Login wrongUsernameLogin = new Login("wrongUsername", "Password");

        try {
            RegisterService.registerUser(userData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try{
            Assertions.assertNull(LoginService.login(wrongPasswordLogin));
        } catch (UnauthorizedException e) {

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        try{
            Assertions.assertNull(LoginService.login(wrongUsernameLogin));
        } catch (UnauthorizedException e) {

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }


    }
}
