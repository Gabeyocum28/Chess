package ui;

import com.sun.nio.sctp.NotificationHandler;
import exceptions.ResponseException;
import server.ServerFacade;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Arrays;

import static ui.EscapeSequences.*;

public class GamePlayClient {
    private final ServerFacade server;
    private final String serverUrl;
    private final NotificationHandler notificationHandler;
    private String teamColor = "White";

    public GamePlayClient(String serverUrl, NotificationHandler notificationHandler, String color) throws MalformedURLException, URISyntaxException {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.notificationHandler = notificationHandler;
        teamColor = color;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "move" -> move(params);
                case "check" -> check(params);
                case"redraw" -> redraw();
                case "quit" -> "quit game";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String move(String... params) throws ResponseException {
        if (params.length >= 2) {
            return String.format("moved from %s to %s", params[0], params[1]);
        }
        throw new ResponseException(400, "Expected: <FROM> <TO>");
    }

    public String check(String... params) throws ResponseException {
        if (params.length >= 1) {
            return String.format("checking %s", params[0]);
        }
        throw new ResponseException(400, "Expected: <FROM>");
    }

    public String redraw() throws ResponseException {
        if(teamColor.equals("Black")) {
            return printBlack();
        }
        return printWhite();
    }

    public String help() {

        return """
                - "move" <FROM> <TO> - makes a move
                - "check" <FROM> - checks the moves
                - "redraw" - redraws the board
                - "quit" - exits gameplay
                _ "help" - display possible actions
                """;


    }

    public String printWhite(){
        StringBuilder board = new StringBuilder();

        board.append(SET_BG_COLOR_DARK_GREY + EMPTY);
        board.append(" a ");
        board.append(" b ");
        board.append(" c ");
        board.append(" d ");
        board.append(" e ");
        board.append(" f ");
        board.append(" g ");
        board.append(" h ");
        board.append(EMPTY);
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE +  " 8 ");
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + ROOK);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_BLACK + KNIGHT);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + BISHOP);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_BLACK + QUEEN);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + KING);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_BLACK + BISHOP);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + KNIGHT);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_BLACK + ROOK);
        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 8 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 7 ");
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_BLACK + PAWN);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + PAWN);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_BLACK + PAWN);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + PAWN);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_BLACK + PAWN);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + PAWN);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_BLACK + PAWN);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + PAWN);
        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 7 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 6 ");
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 6 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 5 ");
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 5 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 4 ");
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 4 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 3 ");
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 3 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 2 ");
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_WHITE + PAWN);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_WHITE + PAWN);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_WHITE + PAWN);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_WHITE + PAWN);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_WHITE + PAWN);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_WHITE + PAWN);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_WHITE + PAWN);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_WHITE + PAWN);
        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 2 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 1 ");
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_WHITE + ROOK);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_WHITE + BISHOP);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_WHITE + KNIGHT);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_WHITE + QUEEN);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_WHITE + KING);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_WHITE + KNIGHT);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_WHITE + BISHOP);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_WHITE + ROOK);
        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 1 ");
        board.append(RESET_BG_COLOR + "\n");



        board.append(SET_BG_COLOR_DARK_GREY + EMPTY);
        board.append(" a ");
        board.append(" b ");
        board.append(" c ");
        board.append(" d ");
        board.append(" e ");
        board.append(" f ");
        board.append(" g ");
        board.append(" h ");
        board.append(EMPTY);
        board.append(RESET_BG_COLOR + "\n");

        board.append("\n");

        return board.toString();

    }

    public String printBlack(){
        StringBuilder board = new StringBuilder();
        board.append(SET_BG_COLOR_DARK_GREY + EMPTY);
        board.append(" h ");
        board.append(" g ");
        board.append(" f ");
        board.append(" e ");
        board.append(" d ");
        board.append(" c ");
        board.append(" b ");
        board.append(" a ");
        board.append(EMPTY);
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE +  " 1 ");
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_WHITE + ROOK);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_WHITE + KNIGHT);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_WHITE + BISHOP);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_WHITE + KING);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_WHITE + QUEEN);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_WHITE + BISHOP);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_WHITE + KNIGHT);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_WHITE + ROOK);
        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 1 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 2 ");
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_WHITE + PAWN);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_WHITE + PAWN);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_WHITE + PAWN);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_WHITE + PAWN);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_WHITE + PAWN);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_WHITE + PAWN);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_WHITE + PAWN);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_WHITE + PAWN);
        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 2 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 3 ");
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 3 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 4 ");
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 4 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 5 ");
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 5 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 6 ");
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_GREY + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 6 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 7 ");
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + PAWN);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_BLACK + PAWN);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + PAWN);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_BLACK + PAWN);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + PAWN);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_BLACK + PAWN);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + PAWN);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_BLACK + PAWN);
        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 7 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 8 ");
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_BLACK + ROOK);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + BISHOP);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_BLACK + KNIGHT);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + KING);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_BLACK + QUEEN);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + KNIGHT);
        board.append(SET_BG_COLOR_GREY + SET_TEXT_COLOR_BLACK + BISHOP);
        board.append(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + ROOK);
        board.append(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 8 ");
        board.append(RESET_BG_COLOR + "\n");



        board.append(SET_BG_COLOR_DARK_GREY + EMPTY);
        board.append(" h ");
        board.append(" g ");
        board.append(" f ");
        board.append(" e ");
        board.append(" d ");
        board.append(" c ");
        board.append(" b ");
        board.append(" a ");
        board.append(EMPTY);
        board.append(RESET_BG_COLOR + "\n");

        board.append("\n");

        return board.toString();
    }
}
