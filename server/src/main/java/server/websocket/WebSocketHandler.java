package server.websocket;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.MakeMoveHelper;
import websocket.commands.UserGameCommand;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebSocket
public class WebSocketHandler {
    private final ConcurrentHashMap<Integer, ConcurrentHashMap<String, Connection>> connections = new ConcurrentHashMap<>();
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
    public void add(int gameId, String authToken, Session session) {
        Connection connection = new Connection(authToken, session);
        ConcurrentHashMap<String, Connection> users = new ConcurrentHashMap<>();
        if(connections.containsKey(gameId)){
            users = connections.get(gameId);
            if(!users.containsKey(authToken)){
                users.put(authToken, connection);
                connections.replace(gameId, users);
            }
        }else{
            users.put(authToken, connection);
            connections.put(gameId, users);
        }
    }

    public void remove(int gameId, String authToken) {
        ConcurrentHashMap<String, Connection> users = connections.get(gameId);
        users.remove(authToken);
        connections.replace(gameId, users);
    }



    public void broadcast(int gameId, String excludeAuthToken, String notification) throws IOException {
        ConcurrentHashMap<String, Connection> users = connections.get(gameId);
        if (users == null) return;

        List<Connection> removeList = new ArrayList<>();

        for (Map.Entry<String, Connection> entry : users.entrySet()) {
            String username = entry.getKey();
            Connection connection = entry.getValue();

            if (!username.equals(excludeAuthToken)) {
                if (connection.session.isOpen()) {
                    connection.send(notification);
                } else {
                    removeList.add(connection);
                }
            }
        }

        // Remove any closed connections
        for (Connection c : removeList) {
            users.remove(c.username); // assumes Connection has a 'username' field
        }

    }

    public Connection getConnection(int gameId, String authToken) {
        return connections.get(gameId).get(authToken);
    }

    public boolean hasConnection(int gameId, String username) {
        return connections.get(gameId).containsKey(username);
    }


}
