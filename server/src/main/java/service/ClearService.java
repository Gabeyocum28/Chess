package service;

import dataaccess.*;

public class ClearService {
    public void clear() throws DataAccessException {
        new SQLUserDAO().clear();
        new SQLAuthDAO().clear();
        new SQLGameDAO().clear();
    }
}
