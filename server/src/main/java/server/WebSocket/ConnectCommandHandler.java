package server.WebSocket;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;
import websocket.messages.LoadGameMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.sql.SQLException;

public class ConnectCommandHandler {
    public void handle(UserGameCommand command, Session session, WebSocketHandler webSocketHandler) throws SQLException, DataAccessException, IOException {
        String username = new SQLAuthDAO().getAuth(command.getAuthToken()).username();
        webSocketHandler.add(username, session);
        System.out.println("User connected: " + username);
        Connection connection = webSocketHandler.getConnection(username);
        ChessGame game = new SQLGameDAO().getGame(command.getGameID()).game();
        connection.send(new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game));
    }
}