package websocket;

import com.google.gson.Gson;
import exceptions.ResponseException;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import model.AuthData;
import model.JoinRequest;
import model.Move;
import model.UserData;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerNotification;
import websocket.commands.UserNotification;


public class WebSocketFacade extends Endpoint{
    Session session;
    NotificationHandler notificationHandler;

    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");
            this.notificationHandler = notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(new Endpoint() {

                @Override
                public void onOpen(javax.websocket.Session session, EndpointConfig config) {
                    try {
                        session.addMessageHandler(new MessageHandler.Whole<String>() {
                            @Override
                            public void onMessage(String message) {
                                 // if userGame make userGame message and send
                                //else if specific server message send to specific handler
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, socketURI);

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

    public void move(AuthData user, Move move){

    }

    public void leave(AuthData user){

    }

    public void resign(AuthData user){

    }


}