package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import exceptions.GameOverException;
import exceptions.ObserverException;
import exceptions.WrongTurnException;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.MakeMoveHelper;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class MakeMoveCommandHandler {
    public void handle(MakeMoveHelper command, Session session, WebSocketHandler webSocketHandler) throws IOException {
        String authToken = command.getAuthToken();
        int gameId = command.getGameID();
        Connection connection = webSocketHandler.getConnection(gameId, authToken);


        try {
            if(connection== null){
                ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Invalid auth");
                String jsonMessage = new Gson().toJson(errorMessage);
                session.getRemote().sendString(jsonMessage);
            }
            String username = new SQLAuthDAO().getAuth(command.getAuthToken()).username();
            var gameData = new SQLGameDAO().getGame(command.getGameID());
            var game = gameData.game();

            if(game.getStatus()){
                throw new GameOverException("The game is over");
            }

            if(game.getTeamTurn() == ChessGame.TeamColor.WHITE && Objects.equals(username, gameData.blackUsername())){
                throw new WrongTurnException("");
            } else if(game.getTeamTurn() == ChessGame.TeamColor.BLACK && Objects.equals(username, gameData.whiteUsername())) {
                throw new WrongTurnException("");
            }
            if(!Objects.equals(username, gameData.blackUsername()) && !Objects.equals(username, gameData.whiteUsername())) {
                throw new ObserverException("");
            }
            String myTeam = game.getTeamTurn().toString();

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
            NotificationMessage notificationMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION,
                    move);
            String jsonMessage2 = new Gson().toJson(notificationMessage);
            webSocketHandler.broadcast(gameId, authToken, jsonMessage2);

            afterMoveStatus(webSocketHandler, game, myTeam, gameId, gameData);

            LoadGameMessage loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game);
            String jsonMessage1 = new Gson().toJson(loadGameMessage);
            connection.send(jsonMessage1);
            webSocketHandler.broadcast(gameId, authToken, jsonMessage1);

        } catch(GameOverException e){
            ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR,
                    "The game is over");
            String jsonMessage = new Gson().toJson(errorMessage);
            connection.send(jsonMessage);
        } catch(WrongTurnException e){
            ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR,
                    "It is not your turn");
            String jsonMessage = new Gson().toJson(errorMessage);
            connection.send(jsonMessage);
        } catch(ObserverException e){
            ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR,
                    "You are an observer");
            String jsonMessage = new Gson().toJson(errorMessage);
            connection.send(jsonMessage);
        } catch(Exception e){
            ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR,
                    "Invalid move");
            String jsonMessage = new Gson().toJson(errorMessage);
            connection.send(jsonMessage);
        }

    }

    private static void afterMoveStatus(WebSocketHandler webSocketHandler, ChessGame game, String myTeam, int gameId, GameData gameData) throws IOException, SQLException, DataAccessException {
        if(game.isInCheckmate(game.getTeamTurn())){
            String checkmate = String.format("%s is in chackmate\n%s has Won!", game.getTeamTurn(), myTeam);
            NotificationMessage checkmateMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION,
                    checkmate);
            String checkmatejsonMessage = new Gson().toJson(checkmateMessage);
            webSocketHandler.broadcast(gameId, "", checkmatejsonMessage);
            game.done();
            new SQLGameDAO().updateBoard(gameData.gameID(), game);
        }else if(game.isInCheck(game.getTeamTurn())){
            String check = String.format("%s is in chack", game.getTeamTurn(), myTeam);
            NotificationMessage checkMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION,
                    check);
            String checkjsonMessage = new Gson().toJson(checkMessage);
            webSocketHandler.broadcast(gameId, "", checkjsonMessage);
        }else if(game.isInStalemate(game.getTeamTurn())){
            String stalemate = "Stalemate!\nGame Over!";
            NotificationMessage stalemateMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION,
                    stalemate);
            String stalematejsonMessage = new Gson().toJson(stalemateMessage);
            webSocketHandler.broadcast(gameId, "", stalematejsonMessage);
            game.done();
            new SQLGameDAO().updateBoard(gameData.gameID(), game);
        }
    }
}
