package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator {
    public PawnMovesCalculator() {

    }

    public Collection<ChessMove> pieceMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> possibleMoves = new ArrayList<>();
        int row = myPosition.getRow();
        int column = myPosition.getColumn();
        ChessPiece myPiece = board.getPiece(myPosition);
        int foreward = 0;



        if(myPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            row++;
        }
        if(myPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            row--;
        }

        if (row > 0 || row <= 8 || column > 0 || column <= 8) { // can advance foreward once
            ChessPosition checkPosition = new ChessPosition(row, column);
            if (board.getPiece(checkPosition) == null) {
                ChessPosition newPosition = new ChessPosition(row, column);
                if(row > 1 && myPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                    ChessMove move = new ChessMove(myPosition, newPosition, null);
                    possibleMoves.add(move);
                }
                if(row == 1 && myPiece.getTeamColor() == ChessGame.TeamColor.BLACK){
                    ChessMove move = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN);
                    possibleMoves.add(move);
                    move = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK);
                    possibleMoves.add(move);
                    move = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP);
                    possibleMoves.add(move);
                    move = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT);
                    possibleMoves.add(move);
                }
                if(row < 8 && myPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    ChessMove move = new ChessMove(myPosition, newPosition, null);
                    possibleMoves.add(move);
                }
                if(row == 8 && myPiece.getTeamColor() == ChessGame.TeamColor.WHITE){
                    ChessMove move = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN);
                    possibleMoves.add(move);
                    move = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK);
                    possibleMoves.add(move);
                    move = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP);
                    possibleMoves.add(move);
                    move = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT);
                    possibleMoves.add(move);
                }
                foreward++;
            }
        }

        if(myPiece.getTeamColor() == ChessGame.TeamColor.WHITE && foreward == 1 && myPosition.getRow() == 2) {
            row++;
            if (row > 0 || row <= 8 || column > 0 || column <= 8) { // can advance foreward once
                ChessPosition checkPosition = new ChessPosition(row, column);
                if (board.getPiece(checkPosition) == null) {
                    ChessPosition newPosition = new ChessPosition(row, column);
                    ChessMove move = new ChessMove(myPosition, newPosition, null);
                    possibleMoves.add(move);
                }
            }
        }

        if(myPiece.getTeamColor() == ChessGame.TeamColor.BLACK && foreward == 1 && myPosition.getRow() == 7) {
            row--;
            if (row > 0 || row <= 8 || column > 0 || column <= 8) { // can advance foreward once
                ChessPosition checkPosition = new ChessPosition(row, column);
                if (board.getPiece(checkPosition) == null) {
                    ChessPosition newPosition = new ChessPosition(row, column);
                    ChessMove move = new ChessMove(myPosition, newPosition, null);
                    possibleMoves.add(move);
                }
            }
        }

        row = myPosition.getRow();
        column = myPosition.getColumn();
        if(myPiece.getTeamColor() == ChessGame.TeamColor.WHITE && column < 8) {
            row++;
            column++;
            if ((row > 0 || row <= 8) && (column > 0 || column <= 8)) {
                ChessPosition checkPosition = new ChessPosition(row, column);
                if (board.getPiece(checkPosition) != null) {
                    if (myPiece.getTeamColor() != board.getPiece(checkPosition).getTeamColor()) {
                        ChessMove move = new ChessMove(myPosition, checkPosition, null);
                        possibleMoves.add(move);

                    }
                }
            }
        }

        row = myPosition.getRow();
        column = myPosition.getColumn();

        if(myPiece.getTeamColor() == ChessGame.TeamColor.WHITE && column > 1) {
            row++;
            column--;

            if ((row > 0 || row <= 8) && (column > 0 || column <= 8)) {
                ChessPosition checkPosition = new ChessPosition(row, column);
                if (board.getPiece(checkPosition) != null) {
                    if (myPiece.getTeamColor() != board.getPiece(checkPosition).getTeamColor()) {
                        if(row < 8 && myPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                            ChessMove move = new ChessMove(myPosition, checkPosition, null);
                            possibleMoves.add(move);
                        }
                        if(row == 8 && myPiece.getTeamColor() == ChessGame.TeamColor.WHITE){
                            ChessMove move = new ChessMove(myPosition, checkPosition, ChessPiece.PieceType.QUEEN);
                            possibleMoves.add(move);
                            move = new ChessMove(myPosition, checkPosition, ChessPiece.PieceType.ROOK);
                            possibleMoves.add(move);
                            move = new ChessMove(myPosition, checkPosition, ChessPiece.PieceType.BISHOP);
                            possibleMoves.add(move);
                            move = new ChessMove(myPosition, checkPosition, ChessPiece.PieceType.KNIGHT);
                            possibleMoves.add(move);
                        }
                    }
                }
            }
        }

        row = myPosition.getRow();
        column = myPosition.getColumn();
        if(myPiece.getTeamColor() == ChessGame.TeamColor.BLACK && column < 8) {
            row--;
            column++;
            if ((row > 0 || row <= 8) && (column > 0 || column <= 8)) {
                ChessPosition checkPosition = new ChessPosition(row, column);
                if (board.getPiece(checkPosition) != null) {
                    if (myPiece.getTeamColor() != board.getPiece(checkPosition).getTeamColor()) {
                        if(row > 1 && myPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                            ChessMove move = new ChessMove(myPosition, checkPosition, null);
                            possibleMoves.add(move);
                        }
                        if(row == 1 && myPiece.getTeamColor() == ChessGame.TeamColor.BLACK){
                            ChessMove move = new ChessMove(myPosition, checkPosition, ChessPiece.PieceType.QUEEN);
                            possibleMoves.add(move);
                            move = new ChessMove(myPosition, checkPosition, ChessPiece.PieceType.ROOK);
                            possibleMoves.add(move);
                            move = new ChessMove(myPosition, checkPosition, ChessPiece.PieceType.BISHOP);
                            possibleMoves.add(move);
                            move = new ChessMove(myPosition, checkPosition, ChessPiece.PieceType.KNIGHT);
                            possibleMoves.add(move);
                        }

                    }
                }
            }
        }

        row = myPosition.getRow();
        column = myPosition.getColumn();

        if(myPiece.getTeamColor() == ChessGame.TeamColor.BLACK && column > 1) {
            row--;
            column--;

            if ((row > 0 || row <= 8) && (column > 0 || column <= 8)) {
                ChessPosition checkPosition = new ChessPosition(row, column);
                if (board.getPiece(checkPosition) != null) {
                    if (myPiece.getTeamColor() != board.getPiece(checkPosition).getTeamColor()) {
                        if(row > 1 && myPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                            ChessMove move = new ChessMove(myPosition, checkPosition, null);
                            possibleMoves.add(move);
                        }
                        if(row == 1 && myPiece.getTeamColor() == ChessGame.TeamColor.BLACK){
                            ChessMove move = new ChessMove(myPosition, checkPosition, ChessPiece.PieceType.QUEEN);
                            possibleMoves.add(move);
                            move = new ChessMove(myPosition, checkPosition, ChessPiece.PieceType.ROOK);
                            possibleMoves.add(move);
                            move = new ChessMove(myPosition, checkPosition, ChessPiece.PieceType.BISHOP);
                            possibleMoves.add(move);
                            move = new ChessMove(myPosition, checkPosition, ChessPiece.PieceType.KNIGHT);
                            possibleMoves.add(move);
                        }

                    }
                }
            }
        }

        return possibleMoves;
    }

}

