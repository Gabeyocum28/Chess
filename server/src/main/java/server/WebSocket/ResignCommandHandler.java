package server.WebSocket;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;


import java.io.IOException;
import java.sql.SQLException;

public class ResignCommandHandler {
    public void handle(UserGameCommand command, Session session, WebSocketHandler webSocketHandler) throws SQLException, DataAccessException, IOException {
        String username = new SQLAuthDAO().getAuth(command.getAuthToken()).username();
        Connection connection = webSocketHandler.getConnection(username);

        String message = String.format("%s has resigned", username);
        NotificationMessage notificationMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        String jsonMessage = new Gson().toJson(notificationMessage);
        webSocketHandler.broadcast(username, jsonMessage);

    }
}
