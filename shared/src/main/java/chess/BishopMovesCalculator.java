package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMovesCalculator extends PieceMovesCalculator {
    public BishopMovesCalculator() {
    }

    public Collection<ChessMove> bishopMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> possibleMoves = new ArrayList<>();
        int row = myPosition.getRow();
        int column = myPosition.getColumn();
        ChessPiece myPiece = board.getPiece(myPosition);
        int start = 0;
        int i = 0;

        while (true) {
            if (start == 0) {
                row = myPosition.getRow();
                column = myPosition.getColumn();
                start = 1;
            }

            if (i == 0) {
                row++;
                column++;
            } else if (i == 1) {
                row++;
                column--;
            } else if (i == 2) {
                row--;
                column++;
            } else if (i == 3) {
                row--;
                column--;
            } else {
                break;
            }
            if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                start = 0;
                i++;
                continue;
            }
            ChessPosition checkPosition = new ChessPosition(row, column);
            if (board.getPiece(checkPosition) != null) {
                if (myPiece.getTeamColor() != board.getPiece(checkPosition).getTeamColor()) {
                    ChessMove move = new ChessMove(myPosition, checkPosition, null);
                    possibleMoves.add(move);
                    start = 0;
                    i++;
                    continue;

                } else {
                    start = 0;
                    i++;
                    continue;
                }
            }
            ChessPosition newPosition = new ChessPosition(row, column);
            ChessMove move = new ChessMove(myPosition, newPosition, null);
            possibleMoves.add(move);

        }
        return possibleMoves;
    }
}
/*
ChatGPT CODE I GOT AFTER I MADE THE ORIGINAL TRYING TO UNDERSTAND HOW TO OPTIMIZE MY CODE

package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMovesCalculator extends pieceMovesCalculator {
    public BishopMovesCalculator() {
    }

    public Collection<ChessMove> bishopMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> possibleMoves = new ArrayList<>();
        ChessPiece myPiece = board.getPiece(myPosition);
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        for (int[] direction : directions) {
            int row = myPosition.getRow();
            int column = myPosition.getColumn();

            while (true) {
                row += direction[0];
                column += direction[1];

                if (row < 1 || row > 8 || column < 1 || column > 8) break;

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

 */