package ui;

import com.sun.nio.sctp.NotificationHandler;
import exceptions.ResponseException;
import model.AuthData;
import model.GameData;
import server.ServerFacade;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class PostLoginClient {
    private final ServerFacade server;
    private final String serverUrl;
    private final NotificationHandler notificationHandler;
    private AuthData user;

    public PostLoginClient(String serverUrl, NotificationHandler notificationHandler) throws MalformedURLException, URISyntaxException {
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
                case "create" -> create(params);
                case "list" -> list();
                case "join" -> join(params);
                case "observe" -> observe(params);
                case "logout" -> logout();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String create(String... params) throws ResponseException {
        if (params.length >= 1) {
            GameData game = new GameData(0, null, null, params[0], null);
            server.createGame(user.authToken(), game);
            return String.format("game created %s", params[0]);
        }
        throw new ResponseException(400, "Expected: <NAME>");
    }

    public String list() throws ResponseException {
        try {
            server.listGames(user.authToken());
        }catch(Exception e){
            return "Not Authorized";
        }
        return server.listGames(user.authToken()).toString();
    }

    public String join(String... params) throws ResponseException {
        if (params.length >= 2) {
            return String.format("joined game as %s", params[1]);
        }
        throw new ResponseException(400, "Expected: <ID> [WHITE/BLACK]");
    }

    public String observe(String... params) throws ResponseException {
        if (params.length >= 1) {
            return String.format("observing game %s", params[0]);
        }
        throw new ResponseException(400, "Expected: <ID>");
    }

    public String logout() throws ResponseException {
        try {
            server.logout(user.authToken());
        }catch(Exception e){
            return "Not Authorized";
        }
        return "logged out (quit)";
    }

    public String help() {

        return """
                - "create" <NAME> - a game
                - "list" - games
                - "join" <ID> [WHITE/BLACK] - a game
                - "observe" <ID> - a game
                - "logout" - when you are done
                - "quit" - playing chess
                _ "help" - display possible actions
                """;


    }

    public void setUserData(AuthData authUser){
        user = authUser;
    }
}
