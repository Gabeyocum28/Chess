package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMovesCalculator {
    public KingMovesCalculator() {
    }

    public Collection<ChessMove> kingMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> possibleMoves = new ArrayList<>();
        ChessPiece myPiece = board.getPiece(myPosition);
        int[][] directions = {
                {1, 0}, {1, 1}, {0, 1}, {1, -1},
                {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}
        };

        possibleMoves = new CalculatorHelper().calculatorHelper(myPiece, directions, board, myPosition);

        return possibleMoves;
    }
}
