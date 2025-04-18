import com.sun.nio.sctp.HandlerResult;
import com.sun.nio.sctp.Notification;

import exceptions.ResponseException;
import ui.Repl;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class ClientMain {
    public static void main(String[] args) throws MalformedURLException, URISyntaxException, SQLException, ResponseException {
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }

        new Repl(serverUrl).run();

    }
}