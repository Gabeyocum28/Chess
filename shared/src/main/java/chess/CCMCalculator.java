package chess;

import java.util.*;

public class CCMCalculator {
    public CCMCalculator() {
    }

    private Collection<ChessMove> oppAttacks = new ArrayList<>();
    private Collection<ChessPosition> checkStop = new ArrayList<>();
    private Collection<ChessMove> checkPieces = new ArrayList<>();
    private Collection<ChessMove> pieceMoves = new ArrayList<>();
    private Collection<ChessMove> allMyMoves = new ArrayList<>();
    private Collection<ChessMove> savingMoves = new ArrayList<>();
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
                                    if(board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.KING && board.getPiece(checkPosition).getTeamColor() == teamColor){
                                        ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                        checkPieces.add(check);
                                    }
                                    ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                    oppAttacks.add(check);
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                oppAttacks.add(check);
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
                                    if(board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.KING && board.getPiece(checkPosition).getTeamColor() == teamColor){
                                        ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                        checkPieces.add(check);
                                    }
                                    ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                    oppAttacks.add(check);
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                oppAttacks.add(check);

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
                                    if(board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.KING && board.getPiece(checkPosition).getTeamColor() == teamColor){
                                        ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                        checkPieces.add(check);
                                    }
                                    ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                    oppAttacks.add(check);
                                    start = 0;
                                    m++;
                                    continue;
                                }
                                ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                oppAttacks.add(check);
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
                                        if(board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.KING && board.getPiece(checkPosition).getTeamColor() == teamColor){
                                            ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                            checkPieces.add(check);
                                        }
                                        ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                        oppAttacks.add(check);
                                    }
                                    ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                    oppAttacks.add(check);
                                }
                                column--;
                                column--;
                                if (row > 0 && row < 9 && column > 0 && column < 9) {
                                    ChessPosition checkPosition = new ChessPosition(row, column);
                                    if (board.getPiece(checkPosition) != null) {
                                        if(board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.KING && board.getPiece(checkPosition).getTeamColor() == teamColor){
                                            ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                            checkPieces.add(check);
                                        }
                                        ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                        oppAttacks.add(check);
                                    }
                                    ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                    oppAttacks.add(check);
                                }
                            }
                            if (board.getPiece(oppPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                                row++;
                                column++;
                                if (row > 0 && row < 9 && column > 0 && column < 9) {
                                    ChessPosition checkPosition = new ChessPosition(row, column);
                                    if (board.getPiece(checkPosition) != null) {
                                        if(board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.KING && board.getPiece(checkPosition).getTeamColor() == teamColor){
                                            ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                            checkPieces.add(check);
                                        }
                                        ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                        oppAttacks.add(check);
                                    }else {
                                        ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                        oppAttacks.add(check);
                                    }
                                }
                                column--;
                                column--;
                                if (row > 0 && row < 9 && column > 0 && column < 9) {
                                    ChessPosition checkPosition = new ChessPosition(row, column);
                                    if (board.getPiece(checkPosition) != null) {
                                        if(board.getPiece(checkPosition).getPieceType() == ChessPiece.PieceType.KING && board.getPiece(checkPosition).getTeamColor() == teamColor){
                                            ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                            checkPieces.add(check);
                                        }
                                        ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                        oppAttacks.add(check);
                                    } else {
                                        ChessMove check = new ChessMove(oppPosition, checkPosition,board.getPiece(oppPosition).getPieceType());
                                        oppAttacks.add(check);
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
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition position = new ChessPosition(i, j);
                if (board.getPiece(position) != null) {
                    ChessPiece checkpiece = board.getPiece(position);
                    if ((checkpiece.getTeamColor() == teamColor)) {
                        PieceMovesCalculator myMoves = new PieceMovesCalculator();
                        pieceMoves = myMoves.PieceMovesCalculator(board,position);
                        allMyMoves.addAll(pieceMoves);
                    }
                }
            }
        }
        Iterator<ChessMove> allMyMovesIterator = allMyMoves.iterator();
        Iterator<ChessMove> checkPiecesIterator = checkPieces.iterator();
        Iterator<ChessMove> oppAttacksIterator = oppAttacks.iterator();
        int i = 0;
        while(checkPiecesIterator.hasNext()) {
            ChessMove Attack = checkPiecesIterator.next();
            while (allMyMovesIterator.hasNext()) {
                ChessMove Save = allMyMovesIterator.next();
                if (Save.getEndPosition().getRow() == Attack.getStartPosition().getRow() && Save.getEndPosition().getColumn() == Attack.getStartPosition().getColumn()) {
                    int j = 0;
                    while (oppAttacksIterator.hasNext()){
                        ChessMove Protected = oppAttacksIterator.next();
                        if(Protected.getEndPosition().getRow() == Attack.getStartPosition().getRow() && Protected.getEndPosition().getColumn() == Attack.getStartPosition().getColumn()){
                            j++;
                        }
                    }
                    if(j == 0) {
                        savingMoves.add(Save);
                        i++;
                    }
                }


            }
            if(i > 0){
                checkPiecesIterator.remove();
                i = 0;
            }
        }
        System.out.println("My moves " + allMyMoves);
        System.out.println("My saving moves " + savingMoves);
        System.out.println("Attacker " + checkPieces);
        if(!checkPieces.isEmpty()) {
            return true;
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
        return Objects.equals(oppAttacks, that.oppAttacks) && Objects.equals(myKing, that.myKing) && Objects.equals(myKingPosition, that.myKingPosition) && Objects.equals(checkPieces, that.checkPieces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oppAttacks, myKing, myKingPosition, checkPieces);
    }

    @Override
    public String toString() {
        return "CCMCalculator{" +
                "oppAttacks=" + oppAttacks +
                ", myKing=" + myKing +
                ", myKingPosition=" + myKingPosition +
                ", checkPieces=" + checkPieces +
                '}';
    }
}
