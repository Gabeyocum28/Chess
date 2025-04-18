package server.websocket;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;


import java.io.IOException;
import java.sql.SQLException;

public class LeaveCommandHandler {
    public void handle(UserGameCommand command, Session session, WebSocketHandler webSocketHandler) throws SQLException, DataAccessException, IOException {
        String authToken = command.getAuthToken();
        String username = new SQLAuthDAO().getAuth(command.getAuthToken()).username();
        int gameId = command.getGameID();
        webSocketHandler.remove(gameId, authToken);
        Connection connection = webSocketHandler.getConnection(gameId, authToken);


        var gameData = new SQLGameDAO().getGame(command.getGameID());
        var game = gameData.game();


        new SQLGameDAO().updatePlayers(gameData.gameID(), username);


        String message = String.format("%s has left the game", username);
        NotificationMessage notificationMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        String jsonMessage = new Gson().toJson(notificationMessage);
        webSocketHandler.broadcast(gameId, authToken, jsonMessage);

    }
}
