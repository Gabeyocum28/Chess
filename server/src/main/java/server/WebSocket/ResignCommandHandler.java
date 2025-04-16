package server.WebSocket;

import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;


import java.sql.SQLException;

public class ResignCommandHandler {
    public void handle(UserGameCommand command, Session session, WebSocketHandler webSocketHandler) throws SQLException, DataAccessException {

    }
}
