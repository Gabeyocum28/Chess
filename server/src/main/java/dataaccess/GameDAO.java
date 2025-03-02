package dataaccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {
    void clear() throws DataAccessException;
    void createGame(GameData gameData) throws DataAccessException;
    Collection<GameData> listGames() throws DataAccessException;
}
