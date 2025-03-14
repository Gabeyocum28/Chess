package dataaccess;

import model.AuthData;
import model.GameData;
import model.GameList;
import model.JoinRequest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import static dataaccess.DatabaseManager.getConnection;

public class SQLGameDAO implements GameDAO{

    private final Connection conn = DatabaseManager.getConnection();

    public SQLGameDAO() throws SQLException, DataAccessException {
        configureDatabase();
    }

    public void createGame(GameData gameData){

    }


    public Collection<GameList> listGames(){
        return List.of();
    }


    public void updateGame(JoinRequest joinRequest, AuthData authData){

    }

    public void clear() throws DataAccessException {
        try (var preparedStatement = conn.prepareStatement("DELETE FROM GameData")) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        void configureDatabase() throws SQLException, DataAccessException {
        try (var conn = getConnection()) {
            var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS pet_store");
            createDbStatement.executeUpdate();

            var propStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
            Properties props = new Properties();
            props.load(propStream);

            conn.setCatalog(props.getProperty("db.name"));

            var createPetTable = """
            CREATE TABLE IF NOT EXISTS `GameData` (
            `gameID` int NOT NULL,
            `whiteUsername` varchar(100) DEFAULT NULL,
            `blackUsername` varchar(100) DEFAULT NULL,
            `gameName` varchar(100) NOT NULL,
            `game` json NOT NULL,
            PRIMARY KEY (`gameID`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """;


            try (var createTableStatement = conn.prepareStatement(createPetTable)) {
                createTableStatement.executeUpdate();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
