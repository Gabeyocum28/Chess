package dataaccess;

import model.UserData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static dataaccess.DatabaseManager.getConnection;

public class SQLUserDAO implements UserDAO {

    private final Connection conn = DatabaseManager.getConnection();

    public SQLUserDAO() throws DataAccessException, SQLException {
        configureDatabase();
    }

    public void createUser(UserData userData) throws DataAccessException {

        try (var preparedStatement = conn.prepareStatement("INSERT INTO UserData (username, password, email) VALUES(?, ?, ?)")) {
            preparedStatement.setString(1, userData.username());
            preparedStatement.setString(2, userData.password());
            preparedStatement.setString(3, userData.email());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public UserData getUser(String username){
        //USERS.get(username);

        return null;
    }

    public void clear() throws DataAccessException {
        try (var preparedStatement = conn.prepareStatement("DELETE FROM UserData")) {

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
            CREATE TABLE IF NOT EXISTS UserData (
            username VARCHAR(100) NOT NULL PRIMARY KEY,
            password VARCHAR(100) NOT NULL,
            email VARCHAR(100) NOT NULL
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