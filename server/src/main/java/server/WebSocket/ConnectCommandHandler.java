package server.WebSocket;

import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;

import java.io.IOException;
import java.sql.SQLException;

public class ConnectCommandHandler {
    public void handle(UserGameCommand command, Session session, WebSocketHandler webSocketHandler) throws SQLException, DataAccessException, IOException {
        String username = new SQLAuthDAO().getAuth(command.getAuthToken()).username();
        webSocketHandler.add(username, session);
        System.out.println("User connected: " + username);
        Connection connection = new WebSocketHandler().getConnection(username);
        connection.send("Welcome to the game!");
    }
}