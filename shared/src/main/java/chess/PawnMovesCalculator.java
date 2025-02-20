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
/*
ChatGPT CODE I GOT AFTER I MADE THE ORIGINAL TRYING TO UNDERSTAND HOW TO OPTIMIZE MY CODE
package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator {

    public Collection<ChessMove> pieceMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> possibleMoves = new ArrayList<>();
        ChessPiece myPiece = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        int direction = (myPiece.getTeamColor() == ChessGame.TeamColor.WHITE) ? 1 : -1;
        int startRow = (myPiece.getTeamColor() == ChessGame.TeamColor.WHITE) ? 2 : 7;
        int promotionRow = (myPiece.getTeamColor() == ChessGame.TeamColor.WHITE) ? 8 : 1;

        // Forward move
        addMoveIfValid(board, possibleMoves, myPosition, row + direction, col, myPiece, false);

        // Double move from start position
        if (row == startRow && board.getPiece(new ChessPosition(row + direction, col)) == null) {
            addMoveIfValid(board, possibleMoves, myPosition, row + 2 * direction, col, myPiece, false);
        }

        // Captures (diagonal moves)
        addMoveIfValid(board, possibleMoves, myPosition, row + direction, col + 1, myPiece, true);
        addMoveIfValid(board, possibleMoves, myPosition, row + direction, col - 1, myPiece, true);

        return possibleMoves;
    }

    private void addMoveIfValid(ChessBoard board, Collection<ChessMove> moves, ChessPosition from, int toRow, int toCol, ChessPiece piece, boolean isCapture) {
        if (toRow < 1 || toRow > 8 || toCol < 1 || toCol > 8) return;
        ChessPosition to = new ChessPosition(toRow, toCol);
        ChessPiece targetPiece = board.getPiece(to);
        boolean isEmpty = (targetPiece == null);

        if ((isCapture && !isEmpty && targetPiece.getTeamColor() != piece.getTeamColor()) || (!isCapture && isEmpty)) {
            if (toRow == (piece.getTeamColor() == ChessGame.TeamColor.WHITE ? 8 : 1)) {
                addPromotionMoves(moves, from, to);
            } else {
                moves.add(new ChessMove(from, to, null));
            }
        }
    }

    private void addPromotionMoves(Collection<ChessMove> moves, ChessPosition from, ChessPosition to) {
        moves.add(new ChessMove(from, to, ChessPiece.PieceType.QUEEN));
        moves.add(new ChessMove(from, to, ChessPiece.PieceType.ROOK));
        moves.add(new ChessMove(from, to, ChessPiece.PieceType.BISHOP));
        moves.add(new ChessMove(from, to, ChessPiece.PieceType.KNIGHT));
    }
}
 */
