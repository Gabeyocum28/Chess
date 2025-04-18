package ui;


import chess.ChessGame;
import com.sun.nio.sctp.HandlerResult;
import com.sun.nio.sctp.Notification;
import exceptions.ResponseException;
import model.AuthData;
import model.JoinRequest;
import websocket.NotificationHandler;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl implements NotificationHandler {

    private final PreLoginClient preLoginClient;
    private final PostLoginClient postLoginClient;
    private final GamePlayClient gamePlayClient;
    private String state;

    public Repl(String serverUrl) throws MalformedURLException, URISyntaxException, ResponseException {
        preLoginClient = new PreLoginClient(serverUrl);
        postLoginClient = new PostLoginClient(serverUrl,  this);
        gamePlayClient = new GamePlayClient(serverUrl,  this);
    }

    public void run() throws SQLException {
        System.out.println(SET_TEXT_COLOR_WHITE + "Welcome to 240 chess!");
        runPre();
        System.out.println();
    }

    public void runPre() throws SQLException{
        System.out.print(SET_TEXT_COLOR_BLUE + preLoginClient.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            state = "[LOGGED_OUT]";
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = preLoginClient.eval(line);
                System.out.print("\n" + SET_TEXT_COLOR_GREEN + result + "\n");

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

    public void runPost(AuthData user) throws SQLException {
        postLoginClient.setUserData(user);
        System.out.print(SET_TEXT_COLOR_BLUE + postLoginClient.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("You have logged out")) {
            state = "[LOGGED_IN]";
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = postLoginClient.eval(line);
                System.out.print("\n" + SET_TEXT_COLOR_GREEN + result + "\n");
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

    public void runGame(JoinRequest joinRequest, AuthData user) throws SQLException {
        gamePlayClient.setJoinRequest(joinRequest);
        gamePlayClient.setUserData(user);


        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("You have left the Game")) {
            state = "[PLAYING]";
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = gamePlayClient.eval(line);
                System.out.print("\n" + SET_TEXT_COLOR_GREEN + result + "\n");
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


    @Override
    public void notifyLoadGame(LoadGameMessage notification) {
        ChessGame game = notification.getGame();
        gamePlayClient.setBoard(game);
        try {
            System.out.println("\n\n" + gamePlayClient.redraw());
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        printPrompt();
    }

    @Override
    public void notifyError(ErrorMessage notification) {
        System.out.println("\n\n" + notification.getErrorMessage() + "\n");
        printPrompt();
    }

    @Override
    public void notifyNotification(NotificationMessage notification) {
        System.out.println("\n\n" + notification.getMessage() + "\n");
        printPrompt();
    }

}
