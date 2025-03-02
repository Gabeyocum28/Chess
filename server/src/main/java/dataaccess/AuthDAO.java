package dataaccess;

import model.AuthData;

public interface AuthDAO {
    void createAuth(AuthData authData) throws DataAccessException;
    AuthData getAuth(String authtoken) throws DataAccessException;
    void deleteAuth(String authtoken) throws DataAccessException;
    void clear() throws DataAccessException;
}
