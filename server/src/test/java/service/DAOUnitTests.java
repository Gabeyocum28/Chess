package service;

import dataaccess.DataAccessException;
import dataaccess.SQLUserDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class DAOUnitTests {
    @Test
    public void UserDAOTest() throws SQLException, DataAccessException {
        UserData userData = new UserData("username", "password", "email");
        SQLUserDAO.configureDatabase();

        new SQLUserDAO().createUser(userData);
        UserData authData = new SQLUserDAO().getUser(userData.username());
        new SQLUserDAO().clear();
    }
}
