public class Move {

    private boolean validMove (int startRow, int startCol, int endRow, int endCol) {
        // Size of board would be 8
        // Captures are handled in a different method, this just checks if a move is valid
        // The != "" represents an empty board piece, one with no checker on it, would be different in actual code
        if (endRow < 0 || endRow >= sizeOfBoard || endCol < 0 || endCol >= sizeOfBoard || board[endRow][endCol] != "") {
            return false;
        }
        char pieceToMove = board[startRow][startCol];

        // Check if the piece to move is a blank spot or not owned by the current player
        if (pieceToMove == "" || pieceToMove.getColor != currentPlayer) {
            return false;
        }

        // To check for diagonals and junk
        int rowDiff = endRow - startRow;
        int colDiff = endCol - startCol;
        boolean validMove = (pieceToMove.getColor == Red && rowDiff > 0) || (pieceToMove.getColor == Black && rowDiff < 0)
                || pieceToMove.isKing;

        if (validMove && colDiff == 1 && Math.abs(rowDiff) == 1) {
            return true;
        }

        if (validMove && colDiff == 2 && Math.abs(rowDiff) == 2) {
            int midRow = (startRow + endRow) / 2;
            int midCol = (startCol + endCol) / 2;
            char middlePiece =  board[midRow][midCol];
            return middlePiece != "" && middlePiece != currentPlayer;
        }
        return false;
    }
}
