package ui;


import com.sun.nio.sctp.Notification;
import com.sun.nio.sctp.NotificationHandler;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public abstract class Repl implements NotificationHandler {

    private final String url;
    private PreLoginClient client;

    public Repl(String serverUrl) {
        url = serverUrl;
        client = new PreLoginClient(serverUrl, this);
    }

    public void run() {
        System.out.println("Chess!");
        System.out.print(client.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
            if(result.equals("register")){
                client = new PostLoginClient(url,this);
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        System.out.print("\n" + SET_TEXT_COLOR_BLACK + ">>> " + SET_TEXT_COLOR_BLUE);
    }
}
