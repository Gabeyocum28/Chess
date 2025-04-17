package server.WebSocket;

import chess.ChessMove;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.MakeMoveHelper;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@WebSocket
public class WebSocketHandler {
    private final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();
    private final Gson gson = new Gson();

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("WebSocket connected: " + session);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, SQLException, DataAccessException {
        UserGameCommand command = gson.fromJson(message, UserGameCommand.class);
        UserGameCommand.CommandType type = command.getCommandType();

        switch (type) {
            case CONNECT -> new ConnectCommandHandler().handle(command, session, this);
            case MAKE_MOVE -> {
                MakeMoveHelper move = gson.fromJson(message, MakeMoveHelper.class);
                new MakeMoveCommandHandler().handle(move, session, this);
            }
            case LEAVE -> new LeaveCommandHandler().handle(command, session, this);
            case RESIGN -> new ResignCommandHandler().handle(command, session, this);
            default -> System.out.println("Unhandled command: " + type);
        }
    }

    // Connection management
    public void add(String username, Session session) {
        Connection connection = new Connection(username, session);
        connections.put(username, connection);
    }

    public void remove(String username) {
        connections.remove(username);
    }

    public void broadcast(String excludeUsername, String notification) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (Connection c : connections.values()) {
            if (c.session.isOpen()) {
                if (!c.username.equals(excludeUsername)) {
                    c.send(notification);
                }
            } else {
                removeList.add(c);
            }
        }

        for (Connection c : removeList) {
            connections.remove(c.username);
        }
    }

    public Connection getConnection(String username) {
        return connections.get(username);
    }

    public boolean hasConnection(String username) {
        return connections.containsKey(username);
    }


}
