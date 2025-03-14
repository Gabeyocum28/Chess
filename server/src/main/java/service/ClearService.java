package service;

import dataaccess.*;

import java.sql.SQLException;

public class ClearService {
    public void clear() throws DataAccessException, SQLException {
        new SQLUserDAO().clear();
        new SQLAuthDAO().clear();
        new SQLGameDAO().clear();
    }
}
