package ui;




import com.sun.nio.sctp.HandlerResult;
import dataaccess.DataAccessException;
import exceptions.ResponseException;
import model.AuthData;
import model.JoinRequest;
import model.UserData;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Scanner;
import websocket.NotificationHandler;

import static ui.EscapeSequences.*;

public abstract class Repl implements NotificationHandler {

    private final PreLoginClient preLoginClient;
    private final PostLoginClient postLoginClient;
    private final GamePlayClient gamePlayClient;
    private String state;

    public Repl(String serverUrl) throws MalformedURLException, URISyntaxException {
        preLoginClient = new PreLoginClient(serverUrl);
        postLoginClient = new PostLoginClient(serverUrl,  this);
        gamePlayClient = new GamePlayClient(serverUrl,  this);
    }

    public void run() throws SQLException, DataAccessException {
        System.out.println(SET_TEXT_COLOR_WHITE + "Welcome to 240 chess!");
        runPre();
        System.out.println();
    }

    public void runPre() throws SQLException, DataAccessException {
        System.out.print(SET_TEXT_COLOR_BLUE + preLoginClient.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            state = "[LOGGED_OUT]";
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = preLoginClient.eval(line);
                System.out.println();
                System.out.print(result);

            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }

            if(result.contains("logged ")) {
                System.out.println();
                runPost(preLoginClient.user);
            }
        }

    }

    public void runPost(AuthData user) throws SQLException, DataAccessException {
        postLoginClient.setUserData(user);
        System.out.print(SET_TEXT_COLOR_BLUE + postLoginClient.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            state = "[LOGGED_IN]";
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = postLoginClient.eval(line);
                System.out.print(SET_TEXT_COLOR_WHITE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
            if(result.contains("observing ") || result.contains("joined ")) {
                System.out.println();
                runGame(postLoginClient.joinRequest, postLoginClient.user);
            }
        }
        System.out.println();
        System.out.print(preLoginClient.help());
    }

    public void runGame(JoinRequest joinRequest, AuthData user) throws SQLException, DataAccessException {
        gamePlayClient.setJoinRequest(joinRequest);
        gamePlayClient.setUserData(user);
        gamePlayClient.getBoard(joinRequest.gameID());
        try {
            System.out.print(gamePlayClient.redraw());
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
        System.out.print(SET_TEXT_COLOR_BLUE + gamePlayClient.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("You have left the Game")) {
            state = "[PLAYING]";
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = gamePlayClient.eval(line);
                System.out.print(SET_TEXT_COLOR_WHITE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
        System.out.print(postLoginClient.help());
    }


    private void printPrompt() {
        System.out.print("\n" + SET_TEXT_COLOR_WHITE + state + " >>> " + SET_TEXT_COLOR_GREEN);
    }

    public abstract HandlerResult handleNotification(com.sun.nio.sctp.Notification notification, Object attachment);
}
