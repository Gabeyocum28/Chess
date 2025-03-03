package service;


import exceptions.AlreadyTakenException;
import model.UserData;
import org.junit.jupiter.api.*;


public class RegisterTest {

    @BeforeEach
    public void setup() {
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
