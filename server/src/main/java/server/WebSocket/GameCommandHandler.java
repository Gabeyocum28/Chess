package server.WebSocket;


import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;

public interface GameCommandHandler {
    void handle(UserGameCommand command, Session session);
}
