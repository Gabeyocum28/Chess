package server.WebSocket;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import exceptions.AlreadyResignException;
import exceptions.ObserverException;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class ResignCommandHandler {
    public void handle(UserGameCommand command, Session session, WebSocketHandler webSocketHandler) throws SQLException, DataAccessException, IOException {
        String authToken = command.getAuthToken();
        int gameId = command.getGameID();
        Connection connection = webSocketHandler.getConnection(gameId, authToken);
        String resigner = "";

        try {
            String username = new SQLAuthDAO().getAuth(command.getAuthToken()).username();
            resigner = username;
            var gameData = new SQLGameDAO().getGame(command.getGameID());
            var game = gameData.game();

            if (!Objects.equals(username, gameData.blackUsername()) && !Objects.equals(username, gameData.whiteUsername())) {
                throw new ObserverException("You are an Observer");
            }
            if(game.getStatus()){
                throw new AlreadyResignException("");
            }

            game.done();
            new SQLGameDAO().updateBoard(gameData.gameID(), game);
            String opp;
            if(Objects.equals(gameData.whiteUsername(), username)){
                opp = gameData.blackUsername();
            }else{
                opp = gameData.whiteUsername();
            }

            String message = String.format("%s has resigned\n%s has Won!", resigner, opp);
            NotificationMessage notificationMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
            String jsonMessage = new Gson().toJson(notificationMessage);
            webSocketHandler.broadcast(gameId, "", jsonMessage);
        }catch(ObserverException e){
            ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "You are an Observer");
            String jsonMessage = new Gson().toJson(errorMessage);
            session.getRemote().sendString(jsonMessage);
        }catch(AlreadyResignException e){
            ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, resigner + " has already resigned");
            String jsonMessage = new Gson().toJson(errorMessage);
            session.getRemote().sendString(jsonMessage);
        }


    }
}
