package dataaccess;

import model.*;

import java.util.Collection;

public interface GameDAO {
    void clear() throws DataAccessException;
    void createGame(GameData gameData) throws DataAccessException;
    Collection<GameList> listGames() throws DataAccessException;
    void updateGame(JoinRequest joinRequest, AuthData authData) throws DataAccessException;
}
