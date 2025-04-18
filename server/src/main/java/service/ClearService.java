package service;

import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import dataaccess.SQLUserDAO;

import java.sql.SQLException;

public class ClearService {
    public void clear() throws DataAccessException, SQLException {
        new SQLUserDAO().clear();
        new SQLAuthDAO().clear();
        new SQLGameDAO().clear();
    }
}
