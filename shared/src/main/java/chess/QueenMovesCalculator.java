package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalculator {
    public QueenMovesCalculator() {
    }

    public Collection<ChessMove> queenMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> possibleMoves = new ArrayList<>();
        ChessPiece myPiece = board.getPiece(myPosition);
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}, {1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int[] direction : directions) {
            int row = myPosition.getRow();
            int column = myPosition.getColumn();

            while (true) {
                row += direction[0];
                column += direction[1];

                if (row < 1 || row > 8 || column < 1 || column > 8) {
                    break;
                }

                ChessPosition newPosition = new ChessPosition(row, column);
                ChessPiece targetPiece = board.getPiece(newPosition);

                if (targetPiece != null) {
                    if (myPiece.getTeamColor() != targetPiece.getTeamColor()) {
                        possibleMoves.add(new ChessMove(myPosition, newPosition, null));
                    }
                    break;
                }

                possibleMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        return possibleMoves;
    }
}
