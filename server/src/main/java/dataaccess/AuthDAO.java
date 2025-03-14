package dataaccess;

import exceptions.SQLException;
import model.AuthData;

public interface AuthDAO {
    void createAuth(AuthData authData) throws SQLException;
    AuthData getAuth(String authtoken) throws SQLException;
    void deleteAuth(String authtoken) throws SQLException;
    void clear() throws SQLException, DataAccessException;
}
