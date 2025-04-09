package server.WebSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import server.WebSocket.*;
import websocket.commands.UserGameCommand;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebSocket
public class WebSocketHandler {

    private static final Map<UserGameCommand.CommandType, GameCommandHandler> handlers = new HashMap<>();
    private final Gson gson = new Gson();
    static {
        handlers.put(UserGameCommand.CommandType.CONNECT, new ConnectCommandHandler());
        handlers.put(UserGameCommand.CommandType.MAKE_MOVE, new MakeMoveCommandHandler());
        handlers.put(UserGameCommand.CommandType.RESIGN, new ResignCommandHandler());
        handlers.put(UserGameCommand.CommandType.LEAVE, new LeaveCommandHandler());
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("WebSocket connected: " + session);
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        System.out.println("WebSocket closed: " + session + " Reason: " + reason);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        // Parse the incoming JSON to a command
        UserGameCommand command = parseCommand(message);
        GameCommandHandler handler = handlers.get(command.getCommandType());

        if (handler != null) {
            handler.handle(command, session);
        } else {
            session.getRemote().sendString("Unknown command type.");
        }
    }

    private UserGameCommand parseCommand(String json) {

        return gson.fromJson(json, UserGameCommand.class);
    }
}