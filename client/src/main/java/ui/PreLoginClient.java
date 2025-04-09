package ui;

import com.sun.net.httpserver.Request;
import com.sun.nio.sctp.NotificationHandler;
import exceptions.ResponseException;
import exceptions.UnauthorizedException;
import model.AuthData;
import model.Login;
import model.UserData;
import server.ServerFacade;
import websocket.commands.UserGameCommand;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Arrays;
import static ui.EscapeSequences.*;

public class PreLoginClient {
    private final ServerFacade server;
    private final String serverUrl;
    private final NotificationHandler notificationHandler;
    public AuthData user;

    public PreLoginClient(String serverUrl, NotificationHandler notificationHandler) throws MalformedURLException, URISyntaxException {
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
                case "register" -> register(params);
                case "login" -> login(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String register(String... params) throws ResponseException {
        if (params.length >= 3) {
            UserData request = new UserData(params[0], params[1], params[2]);
            try {
                user = server.register(request);
            }catch(Exception e){
                return String.format("User already registered\n");
            }


            return String.format("logged in as %s" + "\n", params[0]);
        }
        throw new ResponseException(400, "Expected: <USERNAME> <PASSWORD> <EMAIL>");
    }

    public String login(String... params) throws ResponseException {
        if (params.length >= 2) {
            Login request = new Login(params[0], params[1]);
            try{
                user = server.login(request);
            } catch (Exception e) {
                return String.format("Not Authorized\nCheck Username and Password or register new user\n");
            }

            return String.format("logged in as %s" + "\n", params[0]);

        }
        throw new ResponseException(400, "Expected: <USERNAME> <PASSWORD>");
    }

    public String help() {

        return  SET_TEXT_COLOR_BLUE +
                """
                - "register" <USERNAME> <PASSWORD> <EMAIL> - to create an account
                - "login" <USERNAME> <PASSWORD> - to play chess
                - "quit" - exit program
                _ "help" - display possible actions
                """;


    }
}
