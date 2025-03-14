package dataaccess;

import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import model.GameList;
import model.JoinRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import static dataaccess.DatabaseManager.getConnection;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class SQLGameDAO implements GameDAO{

    private final Connection conn = DatabaseManager.getConnection();

    public SQLGameDAO() throws SQLException, DataAccessException {
        configureDatabase();
    }

    public Integer createGame(GameData gameData){

        try (var preparedStatement = conn.prepareStatement("INSERT INTO GameData (whiteUsername, blackUsername, gameName, game) VALUES(?, ?, ?, ?)", RETURN_GENERATED_KEYS)) {
            Gson gson = new Gson();
            String gameJson = gson.toJson(gameData.game());

            preparedStatement.setString(1, gameData.whiteUsername());
            preparedStatement.setString(2, gameData.blackUsername());
            preparedStatement.setString(3, gameData.gameName());
            preparedStatement.setString(4, gameJson);

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    int generatedId = resultSet.getInt(1);
                    return generatedId;
                } else {
                    throw new SQLException("Creating game failed, no ID obtained.");
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Collection<GameList> listGames(){
        Collection<GameList> gameCollection = new ArrayList<>();
        Gson gson = new Gson();

        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM GameData");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int gameId = resultSet.getInt("gameId");
                String whiteUsername = resultSet.getString("whiteUsername");
                String blackUsername = resultSet.getString("blackUsername");
                String gameName = resultSet.getString("gameName");

                // Create GameData object and add it to the collection
                GameList gameList = new GameList(gameId, whiteUsername, blackUsername, gameName);
                gameCollection.add(gameList);
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return gameCollection;
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
            `gameId` int NOT NULL AUTO_INCREMENT,
            `whiteUsername` varchar(100) DEFAULT NULL,
            `blackUsername` varchar(100) DEFAULT NULL,
            `gameName` varchar(100) NOT NULL,
            `game` json NOT NULL,
            PRIMARY KEY (`gameId`)
            ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """;


            try (var createTableStatement = conn.prepareStatement(createPetTable)) {
                createTableStatement.executeUpdate();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
