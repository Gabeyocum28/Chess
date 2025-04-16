package server.WebSocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;
import websocket.commands.UserNotification;
import websocket.messages.LoadGameMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.sql.SQLException;

public class ConnectCommandHandler {
    public void handle(UserGameCommand command, Session session, WebSocketHandler webSocketHandler) throws SQLException, DataAccessException, IOException {
        String username = new SQLAuthDAO().getAuth(command.getAuthToken()).username();
        webSocketHandler.add(username, session);
        Connection connection = webSocketHandler.getConnection(username);

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
                ? String.format("%s connected as %s player", username, playerColor.toString().toLowerCase())
                : String.format("%s connected as an observer", username);

        UserNotification notification = new UserNotification(UserGameCommand.CommandType.CONNECT, roleMessage);
        String notiGson = new Gson().toJson(notification);
        webSocketHandler.broadcast(username, notiGson);
    }
}