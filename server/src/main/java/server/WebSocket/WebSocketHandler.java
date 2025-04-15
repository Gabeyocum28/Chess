package server.WebSocket;



import com.google.gson.Gson;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.UserGameCommand;


import javax.swing.*;
import java.io.IOException;

import static websocket.commands.UserGameCommand.*;
import static websocket.commands.UserGameCommand.CommandType.CONNECT;
import static websocket.commands.UserGameCommand.CommandType.MAKE_MOVE;


@WebSocket
public class WebSocketHandler {

    private final ConnectCommandHandler connections;

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class);
        switch (userGameCommand.getCommandType()) {
            case CONNECT -> connect(userGameCommand.getAuthToken(), session);
            //case MAKE_MOVE -> makeMove();
            //case LEAVE -> leave();
            //case RESIGN -> resign();
        }
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("WebSocket connected: " + session);
    }

    private void connect(String authToken, Session session){
        new ConnectCommandHandler(authToken, session);
    }
}