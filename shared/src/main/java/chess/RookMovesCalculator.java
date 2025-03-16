package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalculator extends PieceMovesCalculator {
    public RookMovesCalculator() {
    }

    public Collection<ChessMove> rookMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> possibleMoves = new ArrayList<>();
        ChessPiece myPiece = board.getPiece(myPosition);
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        possibleMoves = new CalculatorHelper().continualCalculatorHelper(myPiece, directions, board, myPosition);

        return possibleMoves;
    }
}
