package server.WebSocket;

import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;
import websocket.messages.Notification;

import java.sql.SQLException;

public class MakeMoveCommandHandler {
    public void handle(UserGameCommand command, Session session, WebSocketHandler webSocketHandler) throws SQLException, DataAccessException {
        String username = new SQLAuthDAO().getAuth(command.getAuthToken()).username();
        UserGameCommand.CommandType type = command.getCommandType();
        // assuming your command contains move details

        // Validate and apply move
        // GameManager.getInstance().applyMove(gameId, move, username);

        // Notify the opponent (or all players)
        Notification moveNotification = new Notification(type, username + " made move: ");

        try {
            webSocketHandler.broadcast(username, moveNotification);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Move made by " + username + ": ");
    }
}
