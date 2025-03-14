package dataaccess;

import exceptions.SQLException;
import model.AuthData;
import model.GameData;
import model.GameList;
import model.JoinRequest;

import java.util.Collection;
import java.util.List;

public class SQLGameDAO implements GameDAO{

    public void MySqlDataAccess() throws DataAccessException {
        configureDatabase();
    }

    public void createGame(GameData gameData) throws DataAccessException {

    }


    public Collection<GameList> listGames() throws DataAccessException {
        return List.of();
    }


    public void updateGame(JoinRequest joinRequest, AuthData authData) throws DataAccessException {

    }

    public void clear() throws DataAccessException {

    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS CREATE TABLE `GameData` (
            `gameID` int NOT NULL,
            `whiteUsername` varchar(100) DEFAULT NULL,
            `blackUsername` varchar(100) DEFAULT NULL,
            `gameName` varchar(100) NOT NULL,
            `game` json NOT NULL,
            PRIMARY KEY (`gameID`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (Exception ex) {
            throw new SQLException("Error: SQL exception");
        }
    }
}
