package server.WebSocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.MakeMoveHelper;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Objects;

public class MakeMoveCommandHandler {
    public void handle(MakeMoveHelper command, Session session, WebSocketHandler webSocketHandler) throws SQLException, DataAccessException, IOException {
        String authToken = command.getAuthToken();
        Connection connection = webSocketHandler.getConnection(authToken);


        try {
            if(connection== null){
                ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Invalid auth");
                String jsonMessage = new Gson().toJson(errorMessage);
                session.getRemote().sendString(jsonMessage);
            }
            String username = new SQLAuthDAO().getAuth(command.getAuthToken()).username();
            var gameData = new SQLGameDAO().getGame(command.getGameID());
            var game = gameData.game();

            if(game.getTeamTurn() == ChessGame.TeamColor.WHITE && Objects.equals(username, gameData.blackUsername())){
                DataAccessException Exception = null;
                throw Exception;
            } else if(game.getTeamTurn() == ChessGame.TeamColor.BLACK && Objects.equals(username, gameData.whiteUsername())) {
                DataAccessException Exception = null;
                throw Exception;
            }
            if(!Objects.equals(username, gameData.blackUsername()) && !Objects.equals(username, gameData.whiteUsername())) {
                DataAccessException Exception = null;
                throw Exception;
            }

            game.makeMove(command.getMove());

            new SQLGameDAO().updateBoard(gameData.gameID(), game);


            int fromColumn = command.getMove().getStartPosition().getColumn();
            int fromRow = command.getMove().getStartPosition().getRow();
            char fromFile = (char) ('a' + (fromColumn - 1));
            char fromRank = (char) ('0' + fromRow);
            String fromPosition = "" + fromFile + fromRank;

            int toColumn = command.getMove().getEndPosition().getColumn();
            int toRow = command.getMove().getEndPosition().getRow();
            char toFile = (char) ('a' + (toColumn - 1));
            char toRank = (char) ('0' + toRow);
            String toPosition = "" + toFile + toRank;

            String move = String.format("%s moved from %s to %s", username, fromPosition, toPosition);
            NotificationMessage notificationMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, move);
            String jsonMessage2 = new Gson().toJson(notificationMessage);
            webSocketHandler.broadcast(authToken, jsonMessage2);


            LoadGameMessage loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game);
            String jsonMessage1 = new Gson().toJson(loadGameMessage);
            connection.send(jsonMessage1);
            webSocketHandler.broadcast(authToken, jsonMessage1);
        }catch(Exception e){
            ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Invalid move");
            String jsonMessage = new Gson().toJson(errorMessage);
            connection.send(jsonMessage);
        }

    }
}
