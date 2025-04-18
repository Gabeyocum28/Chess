package websocket;

import chess.ChessMove;
import com.google.gson.Gson;
import exceptions.ResponseException;
import model.AuthData;
import model.JoinRequest;
import websocket.commands.MakeMoveHelper;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class WebSocketFacade extends Endpoint{
    Session session;
    NotificationHandler notificationHandler;

    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");
            this.notificationHandler = notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);


            session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage command = new Gson().fromJson(message, ServerMessage.class);
                    ServerMessage.ServerMessageType type = command.getServerMessageType();

                    switch (type) {
                        case LOAD_GAME -> {
                            LoadGameMessage loadGameMessage = new Gson().fromJson(message, LoadGameMessage.class);
                            notificationHandler.notifyLoadGame(loadGameMessage);
                        }
                        case ERROR -> {
                            ErrorMessage errorMessage = new Gson().fromJson(message, ErrorMessage.class);
                            notificationHandler.notifyError(errorMessage);
                        }
                        case NOTIFICATION -> {
                            NotificationMessage notificationMessage = new Gson().fromJson(message, NotificationMessage.class);
                            notificationHandler.notifyNotification(notificationMessage);
                        }
                        default -> System.out.println("Unhandled command: " + type);
                    }

                }
            });

        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }


    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {}

    public void enterGame(AuthData user, JoinRequest request) throws ResponseException {
        try{
            var command = new UserGameCommand(UserGameCommand.CommandType.CONNECT, user.authToken(), request.gameID());
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (IOException e) {
            throw new ResponseException(500, e.getMessage());
        }
    }

    public void move(AuthData user, JoinRequest request, ChessMove move){
        try{
            var command = new MakeMoveHelper(UserGameCommand.CommandType.MAKE_MOVE, user.authToken(), request.gameID(), move);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (IOException e) {
            try {
                throw new ResponseException(500, e.getMessage());
            } catch (ResponseException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void leave(AuthData user, JoinRequest request){
        try{
            var command = new UserGameCommand(UserGameCommand.CommandType.LEAVE, user.authToken(), request.gameID());
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (IOException e) {
            try {
                throw new ResponseException(500, e.getMessage());
            } catch (ResponseException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void resign(AuthData user, JoinRequest request){
        try{
            var command = new UserGameCommand(UserGameCommand.CommandType.RESIGN, user.authToken(), request.gameID());
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (IOException e) {
            try {
                throw new ResponseException(500, e.getMessage());
            } catch (ResponseException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


}