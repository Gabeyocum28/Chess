package server.WebSocket;

import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;

public class ConnectCommandHandler implements GameCommandHandler {
    @Override
    public void handle(UserGameCommand command, Session session) {

    }
}