package server.WebSocket;

import com.mysql.cj.Session;
import websocket.commands.UserGameCommand;

public class LeaveCommandHandler implements GameCommandHandler {

    @Override
    public void handle(UserGameCommand command, org.eclipse.jetty.websocket.api.Session session) {

    }
}
