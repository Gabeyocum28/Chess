package server.WebSocket;

import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;
import websocket.messages.Notification;

import java.sql.SQLException;

public class ResignCommandHandler {
    public void handle(UserGameCommand command, Session session, WebSocketHandler webSocketHandler) throws SQLException, DataAccessException {
        String username = new SQLAuthDAO().getAuth(command.getAuthToken()).username();
        UserGameCommand.CommandType type = command.getCommandType();
        // Update game state to reflect resignation
        // GameManager.getInstance().resign(gameId, username);

        Notification resignNotification = new Notification(type, username + " has resigned.");

        try {
            webSocketHandler.broadcast(username, resignNotification);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("User resigned: " + username);
    }
}
