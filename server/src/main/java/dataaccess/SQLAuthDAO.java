package dataaccess;

import exceptions.UnauthorizedException;
import model.AuthData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static dataaccess.DatabaseManager.getConnection;

public class SQLAuthDAO implements AuthDAO {

    private Connection conn = null;

    public SQLAuthDAO() throws SQLException, DataAccessException {
        DatabaseManager.createDatabase();
        configureDatabase();
        conn = DatabaseManager.getConnection();
    }

    public void createAuth(AuthData authData){

        try (var preparedStatement = conn.prepareStatement("INSERT INTO AuthData (authToken, username) VALUES(?, ?)")) {
            preparedStatement.setString(1, authData.authToken());
            preparedStatement.setString(2, authData.username());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public AuthData getAuth(String auth){

        try (var preparedStatement = conn.prepareStatement("SELECT authToken, username FROM AuthData WHERE authToken=?")) {
            preparedStatement.setString(1, auth);
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    var username = rs.getString("username");
                    var authToken = rs.getString("authToken");

                    return new AuthData(authToken, username);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void deleteAuth(String authToken){
        try (var preparedStatement = conn.prepareStatement("DELETE FROM AuthData WHERE authToken=?")) {
            preparedStatement.setString(1, authToken);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
            `authToken` varchar(100) NOT NULL,
            `username` varchar(100) NOT NULL,
            PRIMARY KEY (`authToken`)
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
