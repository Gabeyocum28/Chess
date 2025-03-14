package service;


import dataaccess.DataAccessException;
import exceptions.AlreadyTakenException;
import model.UserData;
import org.junit.jupiter.api.*;

import java.sql.SQLException;


public class RegisterTest {

    @BeforeEach
    public void setup() throws SQLException, DataAccessException {
        new ClearService().clear();
    }

    @Test
    public void successfulRegister() throws Exception {
        UserData user = new UserData("username", "password", "email");

        Assertions.assertNotNull(RegisterService.registerUser(user));
    }

    @Test
    public void failedRegister() throws Exception {
        UserData user = new UserData("username", "password", "email");

        Assertions.assertNotNull(RegisterService.registerUser(user));

        try{
            RegisterService.registerUser(user);
        } catch (AlreadyTakenException e) {

        }
    }
}
