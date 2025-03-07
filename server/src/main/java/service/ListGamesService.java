package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import exceptions.UnauthorizedException;
import model.AuthData;
import model.GameData;
import model.GameList;

import java.util.Collection;

public class ListGamesService {
    public static Collection<GameList> listGames(String authRequest) throws DataAccessException {
        AuthData authData = new MemoryAuthDAO().getAuth(authRequest);
        if(authData == null){
            throw new UnauthorizedException("Error: unauthorized");
        }
        return new MemoryGameDAO().listGames();
    }
}
