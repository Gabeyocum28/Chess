package dataaccess;

import exceptions.SQLException;
import model.AuthData;

public class SQLAuthDAO implements AuthDAO{

    public void MySqlDataAccess() throws DataAccessException {
        configureDatabase();
    }

    public void createAuth(AuthData authData) throws DataAccessException {

    }

    public AuthData getAuth(String authtoken) throws DataAccessException {
        return null;
    }

    public void deleteAuth(String authtoken) throws DataAccessException {

    }

    public void clear() throws DataAccessException {

    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  `AuthData` (
            `authToken` varchar(100) NOT NULL,
            `username` varchar(100) NOT NULL,
            PRIMARY KEY (`authToken`)
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
