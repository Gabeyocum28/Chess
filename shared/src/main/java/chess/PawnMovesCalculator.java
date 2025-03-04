package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator {

    public Collection<ChessMove> pawnMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> possibleMoves = new ArrayList<>();
        ChessPiece myPiece = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        int direction;
        int startRow;

        if(myPiece.getTeamColor() == ChessGame.TeamColor.WHITE){
            direction = 1;
        }else{
            direction = -1;
        }

        if(myPiece.getTeamColor() == ChessGame.TeamColor.WHITE){
            startRow = 2;
        }else{
            startRow = 7;
        }

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

    private void addMoveIfValid(ChessBoard board, Collection<ChessMove> moves, ChessPosition startPosition, int toRow,
                                int toCol, ChessPiece piece, boolean isCapture) {
        if (toRow < 1 || toRow > 8 || toCol < 1 || toCol > 8) {
            return;
        }
        ChessPosition checkPosition = new ChessPosition(toRow, toCol);
        ChessPiece targetPiece = board.getPiece(checkPosition);
        boolean isEmpty = (targetPiece == null);

        if ((isCapture && !isEmpty && targetPiece.getTeamColor() != piece.getTeamColor()) || (!isCapture && isEmpty)) {
            int promotionRow;
            if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
                promotionRow = 8;
            }else{
                promotionRow = 1;
            }
            if (toRow == promotionRow) {
                addPromotionMoves(moves, startPosition, checkPosition);
            } else {
                moves.add(new ChessMove(startPosition, checkPosition, null));
            }
        }
    }

    private void addPromotionMoves(Collection<ChessMove> moves, ChessPosition startPosition, ChessPosition checkPosition) {
        moves.add(new ChessMove(startPosition, checkPosition, ChessPiece.PieceType.QUEEN));
        moves.add(new ChessMove(startPosition, checkPosition, ChessPiece.PieceType.ROOK));
        moves.add(new ChessMove(startPosition, checkPosition, ChessPiece.PieceType.BISHOP));
        moves.add(new ChessMove(startPosition, checkPosition, ChessPiece.PieceType.KNIGHT));
    }
}

