package dataaccess;

import model.GameData;

public interface GameDAO {
    void clear() throws DataAccessException;
    void createGame(GameData gameData) throws DataAccessException;
}
