package dataaccess;

import com.google.gson.Gson;
import exceptions.SQLException;
import model.UserData;
import org.eclipse.jetty.util.PathWatcher;
import server.ErrorHandler;

import java.sql.ResultSet;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;


public class SQLUserDAO implements UserDAO{
    public void MySqlDataAccess() throws DataAccessException {
        configureDatabase();
    }

    public void createUser(UserData userData){

    }

    public UserData getUser(String username){
        return null;
    }

    public void clear(){

    }

    private UserData readUserData(ResultSet rs) throws SQLException, java.sql.SQLException {
        var id = rs.getInt("id");
        var json = rs.getString("json");
        var user = new Gson().fromJson(json, UserData.class);
        return user.setId(id);
    }


    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  `UserData` (
            `username` varchar(100) NOT NULL,
            `password` varchar(100) NOT NULL,
            `email` varchar(100) NOT NULL,
            PRIMARY KEY (`username`)
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
