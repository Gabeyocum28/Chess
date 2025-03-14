package service;

import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import exceptions.UnauthorizedException;
import model.AuthData;
import model.GameList;

import java.sql.SQLException;
import java.util.Collection;

public class ListGamesService {
    public static Collection<GameList> listGames(String authRequest) throws DataAccessException, SQLException {
        AuthData authData = new SQLAuthDAO().getAuth(authRequest);
        if(authData == null){
            throw new UnauthorizedException("Error: unauthorized");
        }
        return new SQLGameDAO().listGames();
    }
}
