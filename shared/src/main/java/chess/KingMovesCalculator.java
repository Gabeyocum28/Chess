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

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (newRow < 1 || newRow > 8 || newCol < 1 || newCol > 8) {
                continue;
            }

            ChessPosition newPosition = new ChessPosition(newRow, newCol);
            ChessPiece targetPiece = board.getPiece(newPosition);

            if (targetPiece == null || myPiece.getTeamColor() != targetPiece.getTeamColor()) {
                possibleMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        return possibleMoves;
    }
}
