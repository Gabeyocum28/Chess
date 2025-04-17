
import chess.ChessGame;
import com.sun.nio.sctp.HandlerResult;
import com.sun.nio.sctp.Notification;
import dataaccess.DataAccessException;
import exceptions.ResponseException;
import ui.Repl;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class ClientMain {
    public static void main(String[] args) throws MalformedURLException, URISyntaxException, SQLException, DataAccessException, ResponseException {
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }

        new Repl(serverUrl) {

            @Override
            public HandlerResult handleNotification(Notification notification, Object attachment) {
                return null;
            }

        }.run();

    }
}