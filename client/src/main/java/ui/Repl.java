package ui;


import com.sun.nio.sctp.Notification;
import com.sun.nio.sctp.NotificationHandler;
import exceptions.ResponseException;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public abstract class Repl implements NotificationHandler {

    private PreLoginClient preLoginClient;
    private PostLoginClient postLoginClient;
    private GamePlayClient gamePlayClient;
    private Object client;

    public Repl(String serverUrl) {
        preLoginClient = new PreLoginClient(serverUrl, this);
        postLoginClient = new PostLoginClient(serverUrl, this);
        gamePlayClient = new GamePlayClient(serverUrl, this);
    }

    public void run(){
        System.out.println(SET_TEXT_COLOR_WHITE + "Chess!");
        runPre();
        System.out.println();
    }

    public void runPre() {
        System.out.print(SET_TEXT_COLOR_BLUE + preLoginClient.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = preLoginClient.eval(line);
                System.out.print(SET_TEXT_COLOR_WHITE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }

            if(result.contains("Registered ") || result.contains("Logged ")) {
                System.out.println();
                runPost();
            }
        }

    }

    public void runPost() {
        System.out.print(SET_TEXT_COLOR_BLUE + postLoginClient.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
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
                runGame();
            }
        }
        System.out.println();
        System.out.print(preLoginClient.help());
    }

    public void runGame() {
        try {
            System.out.print(gamePlayClient.redraw());
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
        System.out.print(SET_TEXT_COLOR_BLUE + gamePlayClient.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
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
        System.out.print("\n" + SET_TEXT_COLOR_WHITE + ">>> " + SET_TEXT_COLOR_GREEN);
    }
}
