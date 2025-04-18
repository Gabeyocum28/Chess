package server.WebSocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import dataaccess.SQLUserDAO;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;
import websocket.commands.UserNotification;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.sql.SQLException;

public class ConnectCommandHandler {
    public void handle(UserGameCommand command, Session session, WebSocketHandler webSocketHandler) throws SQLException, DataAccessException, IOException {
        String authToken = command.getAuthToken();
        int gameId = command.getGameID();
        webSocketHandler.add(gameId, authToken, session);


        try {
            Connection connection = webSocketHandler.getConnection(gameId, authToken);
            String username = new SQLAuthDAO().getAuth(command.getAuthToken()).username();
            var gameData = new SQLGameDAO().getGame(command.getGameID());
            var game = gameData.game();
            LoadGameMessage loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game);
            String jsonMessage = new Gson().toJson(loadGameMessage);
            connection.send(jsonMessage);

            ChessGame.TeamColor playerColor = null;
            if (username.equals(gameData.whiteUsername())) {
                playerColor = ChessGame.TeamColor.WHITE;
            } else if (username.equals(gameData.blackUsername())) {
                playerColor = ChessGame.TeamColor.BLACK;
            }

            String roleMessage = (playerColor != null)
                    ? String.format("%s connected as %s", username, playerColor.toString().toLowerCase())
                    : String.format("%s connected as an observer", username);

            NotificationMessage notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, roleMessage);
            String notiGson = new Gson().toJson(notification);
            webSocketHandler.broadcast(gameId, authToken, notiGson);

        }catch(NullPointerException e){

            ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "The GameID Does Not Exist");
            String jsonMessage = new Gson().toJson(errorMessage);
            session.getRemote().sendString(jsonMessage);
        }

    }
}