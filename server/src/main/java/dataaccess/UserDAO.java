package dataaccess;

import exceptions.SQLException;
import model.UserData;

public interface UserDAO {
    void createUser(UserData userData) throws SQLException, DataAccessException;
    UserData getUser(String username) throws SQLException;
    void clear() throws SQLException, DataAccessException;
}
