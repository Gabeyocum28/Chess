package server.WebSocket;

import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import model.AuthData;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;


import java.sql.SQLException;

public class LeaveCommandHandler {
    public void handle(UserGameCommand command, Session session, WebSocketHandler webSocketHandler) throws SQLException, DataAccessException {

    }
}
