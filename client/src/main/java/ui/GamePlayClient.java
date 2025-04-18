package ui;

import chess.*;
import exceptions.ResponseException;
import model.AuthData;
import model.JoinRequest;
import server.ServerFacade;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static ui.EscapeSequences.*;

public class GamePlayClient {
    private final ServerFacade server;
    private final String serverUrl;
    private final NotificationHandler notificationHandler;
    private WebSocketFacade ws;
    public JoinRequest joinRequest;
    public AuthData user;
    private ChessBoard board;
    private Collection<ChessMove> validMoves;
    private ChessPosition checkPosition;
    private ChessGame game;

    public GamePlayClient(String serverUrl, websocket.NotificationHandler notificationHandler)
            throws MalformedURLException, URISyntaxException, ResponseException {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.notificationHandler = notificationHandler;
        board = new ChessGame().getBoard();
        ws = new WebSocketFacade(serverUrl, notificationHandler);
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "move" -> move(params);
                case "highlight" -> highlight(params);
                case"redraw" -> redraw();
                case "leave" -> leave();
                case "resign" -> resign();
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        } catch (InvalidMoveException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String move(String... params) throws ResponseException, InvalidMoveException, SQLException {
        if (params.length == 2) {

            char fromFile = params[0].charAt(0);
            char fromRank = params[0].charAt(1);
            if(Character.isDigit(fromFile) || !Character.isDigit(fromRank)){
                return "your move input must follow file/rank order, ex:move e2 e4\n";
            }

            int fromColumn = (fromFile - 'a') + 1;
            int fromRow = ((fromRank - '0'));
            if(fromColumn >= 9 || fromColumn < 1 || fromRow >= 9 || fromRow < 1){
                return "That spot does not exist\n";
            }

            char toFile = params[1].charAt(0);
            char toRank = params[1].charAt(1);
            if(Character.isDigit(toFile) || !Character.isDigit(toRank)){
                return "your move input must follow file/rank order, ex:move e2 e4\n";
            }

            int toColumn = (toFile - 'a') + 1;
            int toRow = ((toRank - '0'));
            if(toColumn >= 9 || toColumn < 1 || toRow >= 9 || toRow < 1){
                return "That spot does not exist\n";
            }

            ChessPosition fromPosition = new ChessPosition(fromRow, fromColumn);
            ChessPosition toPosition = new ChessPosition(toRow, toColumn);
            ChessMove move = new ChessMove(fromPosition, toPosition, null);

            ws = new WebSocketFacade(serverUrl, notificationHandler);
            ws.move(user, joinRequest, move);

            return "";
        }
        throw new ResponseException(400, "Expected 2 inputs\nEx:move <FROM> <TO>");
    }

    public String highlight(String... params) throws ResponseException, SQLException {
        if (params.length == 1) {

            char file = params[0].charAt(0);
            char rank = params[0].charAt(1);
            if(Character.isDigit(file) || !Character.isDigit(rank)){
                return "your highlight input must follow file/rank order, ex:highlight e2\n";
            }

            int column = (file - 'a') + 1;
            int row = ((rank - '0'));

            if(column >= 9 || column < 1 || row >= 9 || row < 1){
                return "That spot does not exist\n";
            }

            checkPosition = new ChessPosition(row, column);
            validMoves = game.validMoves(checkPosition);
            System.out.println(String.format("checking %s", params[0]));

            return redraw();
        }
        throw new ResponseException(400, "Expected 1 input\nEx:highlight <FROM>");
    }

    public String redraw() throws ResponseException, SQLException{
        if(joinRequest.playerColor().equals("black")) {
            return printBoard(false);
        }
        return printBoard(true);
    }

    public String leave() throws ResponseException {

        ws = new WebSocketFacade(serverUrl, notificationHandler);
        ws.leave(user, joinRequest);

        return "You have left the Game";
    }

    public String resign()throws ResponseException{
        ws = new WebSocketFacade(serverUrl, notificationHandler);
        ws.resign(user, joinRequest);
        return "";
    }

    public String help() {

        return SET_TEXT_COLOR_BLUE +
                """
                - "move" <FROM> <TO> - makes a move
                - "highlight" <FROM> - checks the moves
                - "redraw" - redraws the board
                - "leave" - exits gameplay
                - "resign" - give up
                _ "help" - display possible actions
                """;


    }

    public String printBoard(boolean isWhite) {
        StringBuilder board = new StringBuilder();
        String[] cols = isWhite ? new String[]{"a", "b", "c", "d", "e", "f", "g", "h"}
                : new String[]{"h", "g", "f", "e", "d", "c", "b", "a"};

        // Print column labels
        board.append(SET_BG_COLOR_DARK_GREY).append(EMPTY).append(SET_TEXT_COLOR_WHITE);
        for (String col : cols) {
            board.append(" ").append(col).append(" ");
        }
        board.append(EMPTY).append(RESET_BG_COLOR).append("\n");

        if(isWhite) {
            for (int row = 8; row >= 1; row--) {
                board.append(SET_BG_COLOR_DARK_GREY).append(SET_TEXT_COLOR_WHITE).append(" ").append(row).append(" ");

                for (int col = 1; col < 9; col++) {
                    String bgColor = ((row + col) % 2 == 0) ? SET_BG_COLOR_LIGHT_GREY : SET_BG_COLOR_GREY;
                    if(validMoves != null) {
                        for (ChessMove move : validMoves) {
                            if (move.getEndPosition().getRow() == row && move.getEndPosition().getColumn() == col) {
                                bgColor = ((row + col) % 2 == 0) ? SET_BG_COLOR_GREEN : SET_BG_COLOR_DARK_GREEN;
                                break;
                            }else {
                                bgColor = ((row + col) % 2 == 0) ? SET_BG_COLOR_LIGHT_GREY : SET_BG_COLOR_GREY;
                            }
                        }
                    }else{
                        bgColor = ((row + col) % 2 == 0) ? SET_BG_COLOR_LIGHT_GREY : SET_BG_COLOR_GREY;
                    }

                    if (checkPosition != null && checkPosition.getRow() == row && checkPosition.getColumn() == col) {
                        bgColor = SET_BG_COLOR_YELLOW;
                    }


                    ChessPiece piece = getPiece(row, col);
                    getPieceType(board, piece, bgColor);

                }

                board.append(SET_BG_COLOR_DARK_GREY).append(SET_TEXT_COLOR_WHITE).append(" ").append(row).append(" ");
                board.append(RESET_BG_COLOR).append("\n");
            }
        }else{
            for (int row = 1; row <= 8; row++) {
                board.append(SET_BG_COLOR_DARK_GREY).append(SET_TEXT_COLOR_WHITE).append(" ").append(row).append(" ");

                for (int col = 8; col > 0; col--) {
                    String bgColor = ((row + col) % 2 == 1) ? SET_BG_COLOR_LIGHT_GREY : SET_BG_COLOR_GREY;
                    if(validMoves != null) {
                        for (ChessMove move : validMoves) {
                            if (move.getEndPosition().getRow() == row && move.getEndPosition().getColumn() == col) {
                                bgColor = ((row + col) % 2 == 1) ? SET_BG_COLOR_GREEN : SET_BG_COLOR_DARK_GREEN;
                                break;
                            }else {
                                bgColor = ((row + col) % 2 == 1) ? SET_BG_COLOR_LIGHT_GREY : SET_BG_COLOR_GREY;
                            }
                        }
                    }else{
                        bgColor = ((row + col) % 2 == 1) ? SET_BG_COLOR_LIGHT_GREY : SET_BG_COLOR_GREY;
                    }

                    if (checkPosition != null && checkPosition.getRow() == row && checkPosition.getColumn() == col) {
                        bgColor = SET_BG_COLOR_YELLOW;
                    }
                    ChessPiece piece = getPiece(row, col);
                    getPieceType(board, piece, bgColor);

                }

                board.append(SET_BG_COLOR_DARK_GREY).append(SET_TEXT_COLOR_WHITE).append(" ").append(row).append(" ");
                board.append(RESET_BG_COLOR).append("\n");
            }
        }


        // Print column labels again
        board.append(SET_BG_COLOR_DARK_GREY).append(EMPTY);
        for (String col : cols) {
            board.append(" ").append(col).append(" ");
        }
        board.append(EMPTY).append(RESET_BG_COLOR).append("\n\n");

        validMoves = new ArrayList<>();
        checkPosition = null;

        return board.toString();
    }

    private ChessPiece getPiece(int row, int col) {
        ChessPosition position = new ChessPosition(row, col);
        return board.getPiece(position);
    }

    public void setUserData(AuthData authUser){
        user = authUser;
    }

    public void setJoinRequest(JoinRequest request){
        joinRequest = request;
    }


    public void setBoard(ChessGame game){
        this.game = game;
        ChessBoard board = game.getBoard();
        this.board = board;
    }

    public void getPieceType(StringBuilder board, ChessPiece piece, String bgColor){
        if(piece != null) {
            String pieceColor = String.valueOf(piece.getTeamColor());
            String textColor = pieceColor.equals("WHITE") ? SET_TEXT_COLOR_WHITE : SET_TEXT_COLOR_BLACK;
            String pieceType = String.valueOf(piece.getPieceType());
            if(pieceType == "ROOK") {
                board.append(bgColor).append(textColor).append(ROOK);
            }else if(pieceType == "KNIGHT") {
                board.append(bgColor).append(textColor).append(KNIGHT);
            }else if(pieceType == "BISHOP") {
                board.append(bgColor).append(textColor).append(BISHOP);
            }else if(pieceType == "KING") {
                board.append(bgColor).append(textColor).append(KING);
            }else if(pieceType == "QUEEN") {
                board.append(bgColor).append(textColor).append(QUEEN);
            }else if(pieceType == "PAWN") {
                board.append(bgColor).append(textColor).append(PAWN);
            }

        }else{board.append(bgColor).append(EMPTY);}
    }

}

