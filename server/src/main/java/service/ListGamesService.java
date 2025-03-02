package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import exceptions.UnauthorizedException;
import model.AuthData;
import model.GameData;

import java.util.Collection;

public class ListGamesService {
    public Collection<GameData> listGames(String authRequest) throws DataAccessException {
        AuthData authData = new MemoryAuthDAO().getAuth(authRequest);
        if(authData == null){
            throw new UnauthorizedException("Error: unauthorized");
        }
        return new MemoryGameDAO().listGames();
    }
}
