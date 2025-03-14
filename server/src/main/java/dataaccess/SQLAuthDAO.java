package dataaccess;

import model.AuthData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static dataaccess.DatabaseManager.getConnection;

public class SQLAuthDAO implements AuthDAO {

    private final Connection conn = DatabaseManager.getConnection();

    public SQLAuthDAO() throws SQLException, DataAccessException {
        configureDatabase();
    }

    public void createAuth(AuthData authData){

        try (var preparedStatement = conn.prepareStatement("INSERT INTO AuthData (username, authToken) VALUES(?, ?)")) {
            preparedStatement.setString(1, authData.username());
            preparedStatement.setString(2, authData.authToken());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public AuthData getAuth(String name){

        try (var preparedStatement = conn.prepareStatement("SELECT username, authToken FROM AuthData WHERE username=?")) {
            preparedStatement.setString(1, name);
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    var username = rs.getString("username");
                    var authToken = rs.getString("authToken");

                    return new AuthData(username,authToken);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new AuthData("","");
    }

    public void deleteAuth(String authToken){

    }

    public void clear() throws DataAccessException {
        try (var preparedStatement = conn.prepareStatement("DELETE FROM AuthData")) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void configureDatabase() throws DataAccessException, SQLException {
        try (var conn = getConnection()) {
            var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS pet_store");
            createDbStatement.executeUpdate();

            var propStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
            Properties props = new Properties();
            props.load(propStream);

            conn.setCatalog(props.getProperty("db.name"));

            var createPetTable = """
            CREATE TABLE IF NOT EXISTS `AuthData` (
            `username` varchar(100) NOT NULL,
            `authToken` varchar(100) NOT NULL,
            PRIMARY KEY (`username`)
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
