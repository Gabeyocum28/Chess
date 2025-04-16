import com.sun.nio.sctp.HandlerResult;
import com.sun.nio.sctp.Notification;
import dataaccess.DataAccessException;
import ui.Repl;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class ClientMain {
    public static void main(String[] args) throws MalformedURLException, URISyntaxException, SQLException, DataAccessException {
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }

        new Repl(serverUrl) {
            @Override
            public void notify(websocket.messages.Notification notification) {

            }

            @Override
            public HandlerResult handleNotification(Notification notification, Object attachment) {
                return null;
            }
        }.run();

    }
}