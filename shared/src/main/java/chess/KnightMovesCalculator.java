package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalculator {
    public KnightMovesCalculator() {
    }

    public Collection<ChessMove> knightMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> possibleMoves = new ArrayList<>();
        ChessPiece myPiece = board.getPiece(myPosition);
        int[][] directions = {
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        possibleMoves = new CalculatorHelper().calculatorHelper(myPiece, directions, board, myPosition);

        return possibleMoves;
    }
}
