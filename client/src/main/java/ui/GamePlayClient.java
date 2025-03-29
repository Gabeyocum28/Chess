package ui;

import com.sun.nio.sctp.NotificationHandler;
import exceptions.ResponseException;
import server.ServerFacade;

import java.util.Arrays;

import static ui.EscapeSequences.*;

public class GamePlayClient {
    private final ServerFacade server;
    private final String serverUrl;
    private final NotificationHandler notificationHandler;

    public GamePlayClient(String serverUrl, NotificationHandler notificationHandler) {
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
                case "move" -> move(params);
                case "check" -> check(params);
                case"redraw" -> redraw();
                case "quit" -> "quit";
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
        String printWhite = printWhite();
        String printBlack = printBlack();
        String boards = String.format(printWhite + printBlack);
        return boards;
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

        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
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

        board.append(SET_BG_COLOR_LIGHT_GREY + " 8 ");
        board.append(SET_BG_COLOR_BLUE + BLACK_ROOK);
        board.append(SET_BG_COLOR_RED + BLACK_KNIGHT);
        board.append(SET_BG_COLOR_BLUE + BLACK_BISHOP);
        board.append(SET_BG_COLOR_RED + BLACK_QUEEN);
        board.append(SET_BG_COLOR_BLUE + BLACK_KING);
        board.append(SET_BG_COLOR_RED + BLACK_BISHOP);
        board.append(SET_BG_COLOR_BLUE + BLACK_KNIGHT);
        board.append(SET_BG_COLOR_RED + BLACK_ROOK);
        board.append(SET_BG_COLOR_LIGHT_GREY + " 8 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_LIGHT_GREY + " 7 ");
        board.append(SET_BG_COLOR_RED + BLACK_PAWN);
        board.append(SET_BG_COLOR_BLUE + BLACK_PAWN);
        board.append(SET_BG_COLOR_RED + BLACK_PAWN);
        board.append(SET_BG_COLOR_BLUE + BLACK_PAWN);
        board.append(SET_BG_COLOR_RED + BLACK_PAWN);
        board.append(SET_BG_COLOR_BLUE + BLACK_PAWN);
        board.append(SET_BG_COLOR_RED + BLACK_PAWN);
        board.append(SET_BG_COLOR_BLUE + BLACK_PAWN);
        board.append(SET_BG_COLOR_LIGHT_GREY + " 7 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_LIGHT_GREY + " 6 ");
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + " 6 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_LIGHT_GREY + " 5 ");
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + " 5 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_LIGHT_GREY + " 4 ");
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + " 4 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_LIGHT_GREY + " 3 ");
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + " 3 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_LIGHT_GREY + " 2 ");
        board.append(SET_BG_COLOR_BLUE + WHITE_PAWN);
        board.append(SET_BG_COLOR_RED + WHITE_PAWN);
        board.append(SET_BG_COLOR_BLUE + WHITE_PAWN);
        board.append(SET_BG_COLOR_RED + WHITE_PAWN);
        board.append(SET_BG_COLOR_BLUE + WHITE_PAWN);
        board.append(SET_BG_COLOR_RED + WHITE_PAWN);
        board.append(SET_BG_COLOR_BLUE + WHITE_PAWN);
        board.append(SET_BG_COLOR_RED + WHITE_PAWN);
        board.append(SET_BG_COLOR_LIGHT_GREY + " 2 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_LIGHT_GREY + " 1 ");
        board.append(SET_BG_COLOR_RED + WHITE_ROOK);
        board.append(SET_BG_COLOR_BLUE + WHITE_BISHOP);
        board.append(SET_BG_COLOR_RED + WHITE_KNIGHT);
        board.append(SET_BG_COLOR_BLUE + WHITE_QUEEN);
        board.append(SET_BG_COLOR_RED + WHITE_KING);
        board.append(SET_BG_COLOR_BLUE + WHITE_KNIGHT);
        board.append(SET_BG_COLOR_RED + WHITE_BISHOP);
        board.append(SET_BG_COLOR_BLUE + WHITE_ROOK);
        board.append(SET_BG_COLOR_LIGHT_GREY + " 1 ");
        board.append(RESET_BG_COLOR + "\n");



        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
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
        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
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

        board.append(SET_BG_COLOR_LIGHT_GREY + " 1 ");
        board.append(SET_BG_COLOR_BLUE + WHITE_ROOK);
        board.append(SET_BG_COLOR_RED + WHITE_KNIGHT);
        board.append(SET_BG_COLOR_BLUE + WHITE_BISHOP);
        board.append(SET_BG_COLOR_RED + WHITE_KING);
        board.append(SET_BG_COLOR_BLUE + WHITE_QUEEN);
        board.append(SET_BG_COLOR_RED + WHITE_BISHOP);
        board.append(SET_BG_COLOR_BLUE + WHITE_KNIGHT);
        board.append(SET_BG_COLOR_RED + WHITE_ROOK);
        board.append(SET_BG_COLOR_LIGHT_GREY + " 1 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_LIGHT_GREY + " 2 ");
        board.append(SET_BG_COLOR_RED + WHITE_PAWN);
        board.append(SET_BG_COLOR_BLUE + WHITE_PAWN);
        board.append(SET_BG_COLOR_RED + WHITE_PAWN);
        board.append(SET_BG_COLOR_BLUE + WHITE_PAWN);
        board.append(SET_BG_COLOR_RED + WHITE_PAWN);
        board.append(SET_BG_COLOR_BLUE + WHITE_PAWN);
        board.append(SET_BG_COLOR_RED + WHITE_PAWN);
        board.append(SET_BG_COLOR_BLUE + WHITE_PAWN);
        board.append(SET_BG_COLOR_LIGHT_GREY + " 2 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_LIGHT_GREY + " 3 ");
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + " 3 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_LIGHT_GREY + " 4 ");
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + " 4 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_LIGHT_GREY + " 5 ");
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + " 5 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_LIGHT_GREY + " 6 ");
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_RED + EMPTY);
        board.append(SET_BG_COLOR_BLUE + EMPTY);
        board.append(SET_BG_COLOR_LIGHT_GREY + " 6 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_LIGHT_GREY + " 7 ");
        board.append(SET_BG_COLOR_BLUE + BLACK_PAWN);
        board.append(SET_BG_COLOR_RED + BLACK_PAWN);
        board.append(SET_BG_COLOR_BLUE + BLACK_PAWN);
        board.append(SET_BG_COLOR_RED + BLACK_PAWN);
        board.append(SET_BG_COLOR_BLUE + BLACK_PAWN);
        board.append(SET_BG_COLOR_RED + BLACK_PAWN);
        board.append(SET_BG_COLOR_BLUE + BLACK_PAWN);
        board.append(SET_BG_COLOR_RED + BLACK_PAWN);
        board.append(SET_BG_COLOR_LIGHT_GREY + " 7 ");
        board.append(RESET_BG_COLOR + "\n");

        board.append(SET_BG_COLOR_LIGHT_GREY + " 8 ");
        board.append(SET_BG_COLOR_RED + BLACK_ROOK);
        board.append(SET_BG_COLOR_BLUE + BLACK_BISHOP);
        board.append(SET_BG_COLOR_RED + BLACK_KNIGHT);
        board.append(SET_BG_COLOR_BLUE + BLACK_KING);
        board.append(SET_BG_COLOR_RED + BLACK_QUEEN);
        board.append(SET_BG_COLOR_BLUE + BLACK_KNIGHT);
        board.append(SET_BG_COLOR_RED + BLACK_BISHOP);
        board.append(SET_BG_COLOR_BLUE + BLACK_ROOK);
        board.append(SET_BG_COLOR_LIGHT_GREY + " 8 ");
        board.append(RESET_BG_COLOR + "\n");



        board.append(SET_BG_COLOR_LIGHT_GREY + EMPTY);
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
