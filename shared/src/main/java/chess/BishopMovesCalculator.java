package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMovesCalculator {
    public BishopMovesCalculator() {
    }

    public Collection<ChessMove> bishopMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> possibleMoves = new ArrayList<>();
        ChessPiece myPiece = board.getPiece(myPosition);
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        possibleMoves = new CalculatorHelper().continualCalculatorHelper(myPiece, directions, board, myPosition);

        return possibleMoves;
    }
}

