package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public class CCMCalculator {
    public CCMCalculator() {
    }

    private Collection<ChessPosition> oppMoves = new ArrayList<>();
    private Collection<ChessMove> checkPieces = new ArrayList<>();
    private Collection<ChessMove> allMyMoves = new ArrayList<>();
    private Collection<ChessPosition> checkStop = new ArrayList<>();
    private ChessPiece myKing;
    private ChessPosition myKingPosition;

    public boolean isInCheck(ChessGame.TeamColor teamColor, ChessBoard board) {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition oppPosition = new ChessPosition(i, j);
                if (board.getPiece(oppPosition) != null) {
                    ChessPiece oppPiece = board.getPiece(oppPosition);
                    if (oppPiece.getTeamColor() != teamColor) {
                        if(board.getPiece(oppPosition).getPieceType() == ChessPiece.PieceType.BISHOP || board.getPiece(oppPosition).getPieceType() == ChessPiece.PieceType.QUEEN) {
                            int row = oppPosition.getRow();
                            int column = oppPosition.getColumn();
                            ChessPiece piece = board.getPiece(oppPosition);
                            int start = 0;
                            int m = 0;
                            while (true) {
                                if (start == 0) {
                                    row = oppPosition.getRow();
                                    column = oppPosition.getColumn();
                                    start = 1;
                                }
                                if (m == 0) {
                                    row++;
                                    column++;
                                } else if (m == 1) {
                                    row++;
                                    column--;
                                } else if (m == 2) {
                                    row--;
                                    column++;
                                } else if (m == 3) {
                                    row--;
                                    column--;
                                } else {
                                    break;
                                }
                                if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessPosition checkPosition = new ChessPosition(row, column);
                                if (board.getPiece(checkPosition) != null) {
                                    if (piece.getTeamColor() != board.getPiece(checkPosition).getTeamColor()) {
                                        if(board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.KING){
                                            ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                            checkPieces.add(check);
                                        }
                                        oppMoves.add(checkPosition);
                                        start = 0;
                                        m++;
                                        continue;
                                    } else {
                                        start = 0;
                                        m++;
                                        continue;
                                    }
                                }
                                oppMoves.add(checkPosition);
                            }
                        }
                        if(board.getPiece(oppPosition).getPieceType() == ChessPiece.PieceType.ROOK || board.getPiece(oppPosition).getPieceType() == ChessPiece.PieceType.QUEEN) {
                            int row = oppPosition.getRow();
                            int column = oppPosition.getColumn();
                            ChessPiece piece = board.getPiece(oppPosition);
                            int start = 0;
                            int m = 0;
                            while (true) {
                                if (start == 0) {
                                    row = oppPosition.getRow();
                                    column = oppPosition.getColumn();
                                    start = 1;
                                }
                                if (m == 0) {
                                    row++;
                                } else if (m == 1) {
                                    row--;
                                } else if (m == 2) {
                                    column++;
                                } else if (m == 3) {
                                    column--;
                                } else {
                                    break;
                                }
                                if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessPosition checkPosition = new ChessPosition(row, column);
                                if (board.getPiece(checkPosition) != null) {
                                    if (piece.getTeamColor() != board.getPiece(checkPosition).getTeamColor()) {
                                        if(board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.KING){
                                            ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                            checkPieces.add(check);
                                        }
                                        oppMoves.add(checkPosition);
                                        start = 0;
                                        m++;
                                        continue;

                                    } else {
                                        start = 0;
                                        m++;
                                        continue;
                                    }
                                }
                                oppMoves.add(checkPosition);

                            }
                        }
                        if(board.getPiece(oppPosition).getPieceType() == ChessPiece.PieceType.KNIGHT) {
                            int row = oppPosition.getRow();
                            int column = oppPosition.getColumn();
                            ChessPiece piece = board.getPiece(oppPosition);
                            int start = 0;
                            int m = 0;

                            while (true) {
                                if (start == 0) {
                                    row = oppPosition.getRow();
                                    column = oppPosition.getColumn();
                                    start = 1;
                                }
                                if (m == 0) {
                                    row++;
                                    row++;
                                    column++;
                                } else if (m == 1) {
                                    row++;
                                    row++;
                                    column--;
                                } else if (m == 2) {
                                    row--;
                                    row--;
                                    column++;
                                } else if (m == 3) {
                                    row--;
                                    row--;
                                    column--;
                                } else if (m == 4) {
                                    row++;
                                    column++;
                                    column++;
                                } else if (m == 5) {
                                    row--;
                                    column++;
                                    column++;
                                } else if (m == 6) {
                                    row++;
                                    column--;
                                    column--;
                                } else if (m == 7) {
                                    row--;
                                    column--;
                                    column--;
                                } else {
                                    break;
                                }
                                if (row <= 0 || row > 8 || column <= 0 || column > 8) {
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessPosition checkPosition = new ChessPosition(row, column);
                                if (board.getPiece(checkPosition) != null) {
                                    if (piece.getTeamColor() != board.getPiece(checkPosition).getTeamColor()) {
                                        if(board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.KING){
                                            ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                            checkPieces.add(check);
                                        }
                                        oppMoves.add(checkPosition);
                                        start = 0;
                                        m++;
                                        continue;
                                    } else {
                                        start = 0;
                                        m++;
                                        continue;
                                    }
                                }
                                oppMoves.add(checkPosition);
                                m++;
                                start = 0;
                            }
                        }
                        if(board.getPiece(oppPosition).getPieceType() == ChessPiece.PieceType.PAWN) {
                            int row = oppPosition.getRow();
                            int column = oppPosition.getColumn();
                            ChessPiece piece = board.getPiece(oppPosition);
                            if (board.getPiece(oppPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
                                row--;
                                column++;
                                if (row > 0 && row < 9 && column > 0 && column < 9) {
                                    ChessPosition checkPosition = new ChessPosition(row, column);
                                    if (board.getPiece(checkPosition) != null) {
                                        if (piece.getTeamColor() != board.getPiece(checkPosition).getTeamColor()) {
                                            if(board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.KING){
                                                ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                                checkPieces.add(check);
                                            }
                                            oppMoves.add(checkPosition);
                                        }
                                    }
                                    oppMoves.add(checkPosition);
                                }
                                column--;
                                column--;
                                if (row > 0 && row < 9 && column > 0 && column < 9) {
                                    ChessPosition checkPosition = new ChessPosition(row, column);
                                    if (board.getPiece(checkPosition) != null) {
                                        if (piece.getTeamColor() != board.getPiece(checkPosition).getTeamColor()) {
                                            if(board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.KING){
                                                ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                                checkPieces.add(check);
                                            }
                                            oppMoves.add(checkPosition);
                                        }
                                    }
                                    oppMoves.add(checkPosition);
                                }
                            }
                            if (board.getPiece(oppPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                                row++;
                                column++;
                                if (row > 0 && row < 9 && column > 0 && column < 9) {
                                    ChessPosition checkPosition = new ChessPosition(row, column);
                                    if (board.getPiece(checkPosition) != null) {
                                        if (piece.getTeamColor() != board.getPiece(checkPosition).getTeamColor()) {
                                            if(board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.KING){
                                                ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                                checkPieces.add(check);
                                            }
                                            oppMoves.add(checkPosition);
                                        }
                                    }else {
                                        oppMoves.add(checkPosition);
                                    }
                                }
                                column--;
                                column--;
                                if (row > 0 && row < 9 && column > 0 && column < 9) {
                                    ChessPosition checkPosition = new ChessPosition(row, column);
                                    if (board.getPiece(checkPosition) != null) {
                                        if (piece.getTeamColor() != board.getPiece(checkPosition).getTeamColor()) {
                                            if(board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.KING){
                                                ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                                checkPieces.add(check);
                                            }
                                            oppMoves.add(checkPosition);
                                        }
                                    } else {
                                        oppMoves.add(checkPosition);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if(!checkPieces.isEmpty()){
            return true;
        }
        return false;
    }



    public boolean isInCheckmate(ChessGame.TeamColor teamColor, ChessBoard board) {

        Iterator<ChessMove> checkPieceIterator = checkPieces.iterator();
        while (checkPieceIterator.hasNext()) {
            ChessMove Check = checkPieceIterator.next();
            ChessPosition stopCheckPiece = new ChessPosition(Check.getStartPosition().getRow(),Check.getStartPosition().getColumn());
            checkStop.add(stopCheckPiece);
        }
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition position = new ChessPosition(i, j);
                if (board.getPiece(position) != null) {
                    ChessPiece checkpiece = board.getPiece(position);
                    if ((checkpiece.getTeamColor() == teamColor)) {
                        PieceMovesCalculator myMoves = new PieceMovesCalculator();
                        System.out.println(myMoves.PieceMovesCalculator(board,position));

                    }
                }
            }
        }

        return false;
    }

    public boolean isInStalemate(ChessGame.TeamColor teamColor, ChessBoard board) {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CCMCalculator that = (CCMCalculator) o;
        return Objects.equals(oppMoves, that.oppMoves) && Objects.equals(myKing, that.myKing) && Objects.equals(myKingPosition, that.myKingPosition) && Objects.equals(checkPieces, that.checkPieces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oppMoves, myKing, myKingPosition, checkPieces);
    }

    @Override
    public String toString() {
        return "CCMCalculator{" +
                "oppMoves=" + oppMoves +
                ", myKing=" + myKing +
                ", myKingPosition=" + myKingPosition +
                ", checkPieces=" + checkPieces +
                '}';
    }
}
