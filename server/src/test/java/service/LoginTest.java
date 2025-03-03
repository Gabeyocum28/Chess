package service;

import exceptions.UnauthorizedException;
import model.Login;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginTest {

    @BeforeEach
    public void setup() {
        new ClearService().clear();
    }

    @Test
    public void successfulLogin(){
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

        }
        try{
            Assertions.assertNull(LoginService.login(wrongUsernameLogin));
        } catch (UnauthorizedException e) {

        }


    }
}
