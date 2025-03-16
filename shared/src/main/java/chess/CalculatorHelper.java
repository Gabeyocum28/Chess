package chess;

import java.util.ArrayList;
import java.util.Collection;

public class CalculatorHelper {
    public Collection<ChessMove> continualCalculatorHelper(ChessPiece myPiece, int [][] directions, ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> moves = new ArrayList<>();

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
                        moves.add(new ChessMove(myPosition, newPosition, null));
                    }
                    break;
                }

                moves.add(new ChessMove(myPosition, newPosition, null));
            }
        }

        return moves;
    }
    public Collection<ChessMove> calculatorHelper(ChessPiece myPiece, int [][] directions, ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> moves = new ArrayList<>();

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
                moves.add(new ChessMove(myPosition, newPosition, null));
            }
        }
        return moves;
    }
}
