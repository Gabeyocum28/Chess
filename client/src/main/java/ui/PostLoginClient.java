package ui;

import com.sun.nio.sctp.NotificationHandler;
import exceptions.ResponseException;
import server.ServerFacade;

import java.util.Arrays;

public class PostLoginClient {
    private final ServerFacade server;
    private final String serverUrl;
    private final NotificationHandler notificationHandler;

    public PostLoginClient(String serverUrl, NotificationHandler notificationHandler) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.notificationHandler = notificationHandler;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "join" -> join(params);
                case "login" -> login(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String join(String... params) throws ResponseException {
        if (params.length >= 2) {
            return String.format("joined %s %s", params[0], params[1]);
        }
        throw new ResponseException(400, "Expected: <USERNAME> <PASSWORD> <EMAIL>");
    }

    public String login(String... params) throws ResponseException {
        if (params.length >= 2) {
            return String.format("Logged in as %s", params[0]);
        }
        throw new ResponseException(400, "Expected: <USERNAME> <PASSWORD>");
    }

    public String help() {

        return """
                - PostLogin
                - register <USERNAME> <PASSWORD> <EMAIL>
                - login <USERNAME> <PASSWORD>
                - quit - Exit Program
                _ help - Display Possible Actions
                """;


    }
}
