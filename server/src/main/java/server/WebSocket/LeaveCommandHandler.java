package server.WebSocket;

import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import model.AuthData;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;
import websocket.messages.Notification;

import java.sql.SQLException;

public class LeaveCommandHandler {
    public void handle(UserGameCommand command, Session session, WebSocketHandler webSocketHandler) throws SQLException, DataAccessException {
        String username = new SQLAuthDAO().getAuth(command.getAuthToken()).username();
        Integer gameId = command.getGameID();
        UserGameCommand.CommandType type = command.getCommandType();

        // Optional: remove the player from the game object if needed
        // GameManager.getInstance().leaveGame(gameId, username);

        // Remove from connections
        webSocketHandler.remove(username);

        // Broadcast leave notification
        Notification notification = new Notification(type, username + " has left the game.");
        try {
            webSocketHandler.broadcast(username, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("User left: " + username);
    }
}
