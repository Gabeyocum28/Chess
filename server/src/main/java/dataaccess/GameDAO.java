package dataaccess;

import exceptions.SQLException;
import model.*;

import java.util.Collection;

public interface GameDAO {
    void clear() throws SQLException, DataAccessException;
    void createGame(GameData gameData) throws SQLException;
    Collection<GameList> listGames() throws SQLException;
    void updateGame(JoinRequest joinRequest, AuthData authData) throws SQLException;
}
