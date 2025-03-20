import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ChessGameGUI extends JFrame{
    private final JPanel[][] squares = new JPanel[8][8];
    private boolean clickedSquare = false;
    private int savedRow = 0, savedCol = 0;
    private PieceLoader loader = new PieceLoader();

    private final ImageIcon blackPawn = loader.getPiece("black_pawn");
    private final ImageIcon whitePawn = loader.getPiece("white_pawn");

    private final ImageIcon blackRook = loader.getPiece("black_rook");
    private final ImageIcon whiteRook = loader.getPiece("white_rook");

    private final ImageIcon blackKnight = loader.getPiece("black_knight");
    private final ImageIcon whiteKnight = loader.getPiece("white_knight");

    private final ImageIcon blackBishop = loader.getPiece("black_bishop");
    private final ImageIcon whiteBishop = loader.getPiece("white_bishop");

    private final ImageIcon blackKing = loader.getPiece("black_king");
    private final ImageIcon whiteKing = loader.getPiece("white_king");

    private final ImageIcon blackQueen = loader.getPiece("black_queen");
    private final ImageIcon whiteQueen = loader.getPiece("white_queen");

    boolean redCanGo;
    boolean blackCanGo;

    private boolean redTurn = true;
    private int blackPoints = 0, redPoints = 0;

    public ChessGameGUI() {


        JPanel button = new JPanel(new GridLayout(2, 1));
        JButton newRandomGame = new JButton("New Random Game");
        JButton checkerGame = new JButton("Checker Game");
        JButton chessGame = new JButton("Chess Game");
        JButton helpGame = new JButton("Help Me!");

        setTitle("Simple Chess OR Checkers Game");
        setSize(900, 900);
        this.setResizable(false);
        setLayout(new BorderLayout());

        JPanel border = getjPanel();
        button.add(newRandomGame).setLocation(1,1);
        button.add(helpGame).setLocation(2,1);
        button.add(chessGame).setLocation(2,2);
        button.add(checkerGame).setLocation(1,2);

        newRandomGame.setFont(new Font("Arial", Font.PLAIN, 40));
        checkerGame.setFont(new Font("Arial", Font.PLAIN, 40));
        chessGame.setFont(new Font("Arial", Font.PLAIN, 40));
        helpGame.setFont(new Font("Arial", Font.PLAIN, 40));

        helpGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Help Me! was clicked!");
                try {
                    URI uri = new URI("https://www.chess.com/learn-how-to-play-chess");
                    URI uri2 = new URI("https://www.officialgamerules.org/board-games/checkers");
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().browse(uri);
                        Desktop.getDesktop().browse(uri2);
                    } else {
                        System.out.println("Desktop not supported.");
                    }
                } catch (URISyntaxException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        checkerGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CheckersGameGUI();
            }});

        chessGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ChessGameGUI();
            }});

        border.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Insets insets = border.getInsets();
                int x = e.getX() - insets.left;
                int y = e.getY() - insets.top;

                int cellWidth = border.getWidth() / 8;
                int cellHeight = border.getHeight() / 8;
                int col = x / cellWidth;
                int row = y / cellHeight;
                border.revalidate();

                if (col >= 0 && col < 8 && row >= 0 && row < 8) {
                    if (redTurn) {
                        movePiece(row, col, Colour.WHITE);
                    }

                    if (!redTurn) {
                        movePiece(row, col, Colour.BLACK);
                    }
                } else {
                    System.out.println("Clicked out of bounds");
                }
            }
        });

        add(border, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel getjPanel() {
        JPanel border = new JPanel(new GridLayout(8, 8));

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JPanel square = new JPanel();
                squares[row][col] = square;
                square.setSize(80, 80);
                if ((row % 2 == 0) == (col % 2 == 0)) {
                    square.setBackground(Color.WHITE);

                } else {
                    square.setBackground(Color.DARK_GRAY);
                }

                if (row == 0 && col == 0 || row == 0 && col == 7) {
                    ChessPiece piece = new ChessPiece(ChessRank.ROOK, Colour.WHITE, whiteRook);
                    square.add(piece);
                } else if (row == 7 && col == 0 || row == 7 && col == 7) {
                    ChessPiece piece = new ChessPiece(ChessRank.ROOK, Colour.BLACK, blackRook);
                    square.add(piece);
                }

                if (row == 0 && col == 1 || row == 0 && col == 6) {
                    ChessPiece piece = new ChessPiece(ChessRank.KNIGHT, Colour.WHITE, whiteKnight);
                    square.add(piece);
                } else if (row == 7 && col == 1 || row == 7 && col == 6) {
                    ChessPiece piece = new ChessPiece(ChessRank.KNIGHT, Colour.BLACK, blackKnight);
                    square.add(piece);
                }

                if (row == 0 && col == 2 || row == 0 && col == 5) {
                    ChessPiece piece = new ChessPiece(ChessRank.BISHOP, Colour.WHITE, whiteBishop);
                    square.add(piece);
                } else if (row == 7 && col == 2 || row == 7 && col == 5) {
                    ChessPiece piece = new ChessPiece(ChessRank.BISHOP, Colour.BLACK, blackBishop);
                    square.add(piece);
                }

                if (row == 0 && col == 3) {
                    ChessPiece piece = new ChessPiece(ChessRank.QUEEN, Colour.WHITE, whiteQueen);
                    square.add(piece);
                } else if (row == 7 && col == 3) {
                    ChessPiece piece = new ChessPiece(ChessRank.QUEEN, Colour.BLACK, blackQueen);
                    square.add(piece);
                }

                if (row == 0 && col == 4) {
                    ChessPiece piece = new ChessPiece(ChessRank.KING, Colour.WHITE, whiteKing);
                    square.add(piece);
                } else if (row == 7 && col == 4) {
                    ChessPiece piece = new ChessPiece(ChessRank.KING, Colour.BLACK, blackKing);
                    square.add(piece);
                }

                if (row == 1) {
                    ChessPiece piece = new ChessPiece(ChessRank.PAWN, Colour.WHITE, whitePawn);
                    square.add(piece);
                } else if (row == 6) {
                    ChessPiece piece = new ChessPiece(ChessRank.PAWN, Colour.BLACK, blackPawn);
                    square.add(piece);
                }
                border.add(square);
            }
        }
        return border;
    }

    public boolean validMove(int startRow, int startCol, int endRow, int endCol) {
        //System.out.println("Method Called: VALID MOVE");
        if (endRow < 0 || endRow >= 8 || endCol < 0 || endCol >= 8) {
            return false;
        }
        if (getPiece(squares[startRow][startCol]) != null) {
            ChessPiece pieceToMove = getPiece(squares[startRow][startCol]);
            ChessRank rank = pieceToMove.getChessRank();

            return switch (rank) {
                case PAWN -> isValidMovePawn(startRow, startCol, endRow, endCol);
                case ROOK -> isValidMoveRook(startRow, startCol, endRow, endCol);
                case BISHOP -> isValidMoveBishop(startRow, startCol, endRow, endCol);
                case KNIGHT -> isValidMoveKnight(startRow, startCol, endRow, endCol);
                case KING -> isValidMoveKing(startRow, startCol, endRow, endCol);
                case QUEEN -> isValidMoveQueen(startRow, startCol, endRow, endCol);
            };
        }
        return false;


    }

    private boolean isValidMovePawn(int startRow, int startCol, int endRow, int endCol) {
        ChessPiece pieceToMove = getPiece(squares[startRow][startCol]);
        int direction = pieceToMove.getColour() == Colour.WHITE ? 1 : -1;
        System.out.println("1Start: " + startRow + ", " + startCol + "End: " + endRow + ", " + endCol + "\n");
        if (Math.abs(startCol - endCol) == 1 && endRow == startRow + direction) {
            ChessPiece opponentPiece = getPiece(squares[endRow][endCol]);
            boolean canJump = opponentPiece != null && opponentPiece.getColour() != pieceToMove.getColour();
            //System.out.println("2Start: " + startRow + ", " + startCol + "End: " + endRow + ", " + endCol + "\n");
            if (canJump) {
                squares[startRow][startCol].repaint();
                squares[endRow][endCol].removeAll();
                squares[endRow][endCol].add(pieceToMove);
                squares[endRow][endCol].repaint();
                savedCol = endCol;
                savedRow = endRow;
                //System.out.println("3Start: " + startRow + ", " + startCol + "End: " + endRow + ", " + endCol + "\n");
                return true;
            }
        }
        //System.out.println("4Start: " + startRow + ", " + startCol + "End: " + endRow + ", " + endCol + "\n");
        if (startCol == endCol && endRow == startRow + direction && getPiece(squares[endRow][endCol]) == null) {
            return true;
        }

        return false;
    }

    private boolean isValidMoveRook(int startRow, int startCol, int endRow, int endCol) {
        System.out.println("In validMoveRook");
        if (startRow == endRow) {
            return !blockedPath(startRow, startCol, endRow, endCol);
        } else if (startCol == endCol) {
            return !blockedPath(startRow, startCol, endRow, endCol);
        }

        return false;
    }

    private boolean isValidMoveBishop(int startRow, int startCol, int endRow, int endCol) {
        if (Math.abs(endRow - startRow) != Math.abs(endCol - startCol)) {
            return false;
        }
        if(blockedPath(startRow, startCol, endRow, endCol)) {
            return false;
        }
        takePiece(startRow, startCol, endRow, endCol);
        return true;
    }

    private boolean isValidMoveKnight(int startRow, int startCol, int endRow, int endCol) {
        int rowDiff = Math.abs(endRow - startRow);
        int colDiff = Math.abs(endCol - startCol);
        if (rowDiff == 2 && colDiff == 1 || rowDiff == 1 && colDiff == 2) {
            if (getPiece(squares[endRow][endCol]) != null && getPiece(squares[endRow][endCol]).getColour() != getPiece(squares[startRow][startCol]).getColour()) {
                takePiece(startRow, startCol, endRow, endCol);
                return true;
            }
            if (getPiece(squares[endRow][endCol]) == null) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidMoveKing(int startRow, int startCol, int endRow, int endCol) {
        int rowDiff = Math.abs(endRow - startRow);
        int colDiff = Math.abs(endCol - startCol);
        if (rowDiff <= 1 && colDiff <= 1) {
            if (!blockedPath(startRow, startCol, endRow, endCol)) {
                takePiece(startRow, startCol, endRow, endCol);
                return true;
            }
        }
        return false;
    }

    public boolean isValidMoveQueen(int startRow, int startCol, int endRow, int endCol) {
        if(blockedPath(startRow, startCol, endRow, endCol)) {
            return false;
        }
        takePiece(startRow, startCol, endRow, endCol);
        return true;
    }

    private boolean blockedPath(int startRow, int startCol, int endRow, int endCol) {
        int rowDirection = Integer.signum(endRow - startRow);
        int colDirection = Integer.signum(endCol - startCol);

        int row = startRow + rowDirection;
        int col = startCol + colDirection;

        while (row != endRow || col != endCol) {
            if (getPiece(squares[row][col]) != null) {
                return true;
            }
            row += rowDirection;
            col += colDirection;
        }
        if (getPiece(squares[endRow][endCol]) != null) {
            System.out.println("In blockedPath not null");
            if (getPiece(squares[endRow][endCol]).getColour() != getPiece(squares[startRow][startCol]).getColour()) {
                takePiece(startRow, startCol, endRow, endCol);
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public void takePiece(int startRow, int startCol, int endRow, int endCol) {
        if (getPiece(squares[endRow][endCol]) != null) {
            if (getPiece(squares[endRow][endCol]).getColour() != getPiece(squares[startRow][startCol]).getColour()) {
                System.out.println("Colours don't match");
                squares[endRow][endCol].removeAll();
                squares[endRow][endCol].repaint();
            }
        }
    }


    public void movePiece(int row, int col, Colour colour) {
        if (clickedSquare && validMove(savedRow, savedCol, row, col)) {
            System.out.println("Moving Piece");
            ChessPiece chessPiece = getPiece(squares[savedRow][savedCol]);
            System.out.println("ChessPiece: " + chessPiece);

            squares[savedRow][savedCol].removeAll();
            squares[savedRow][savedCol].repaint();

            squares[row][col].add(chessPiece);
            squares[row][col].repaint();
            ///ChessPiece piece = getPiece(squares[row][col]);
//            if (!piece.isKing()) {
//                piece = makeKing(piece, row);
//                if (piece != null) {
//                    squares[row][col].removeAll();
//                    squares[row][col].add(piece);
//                    squares[row][col].repaint();
//                }
//            }
            //System.out.println("1Clicked square: " + clickedSquare + " redTurn: " + redTurn);
            clickedSquare = false;
            redTurn = !redTurn;
            //System.out.println("2Clicked square: " + clickedSquare + " redTurn: " + redTurn);
        }
        try {
            if (colour == Colour.WHITE) {
                redCanGo = redTurn && getPiece(squares[row][col]).getColour() == colour;
            } else {
                blackCanGo = !redTurn && getPiece(squares[row][col]).getColour() == colour;
            }
        } catch (Exception ex){
            redCanGo = false;
        }
        if (redCanGo) {
            highlightSquare(row, col);
            clickedSquare = true;
            savedRow = row;
            savedCol = col;
        } else if (blackCanGo) {
            highlightSquare(row, col);
            clickedSquare = true;
            savedRow = row;
            savedCol = col;
        }
    }

    private void highlightSquare(int row, int col) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i % 2 == 0) == (j % 2 == 0)) {
                    squares[i][j].setBackground(Color.WHITE);

                } else {
                    squares[i][j].setBackground(Color.DARK_GRAY);
                }
            }
        }
        squares[row][col].setBackground(Color.YELLOW);

    }



    private boolean hasLabel(JPanel square) {
        Component[] components = square.getComponents();
        for (Component component : components) {
            if (component instanceof JLabel) {
                return true;
            }
        }
        return false;
    }

    private ChessPiece getPiece(JPanel square) {
        Component[] components = square.getComponents();
        for (Component component : components) {
            if (component instanceof ChessPiece) {
                return (ChessPiece)component;
            }
        }
        return null;
    }
//
//    private boolean validMove (int startRow, int startCol, int endRow, int endCol) {
//        if (endRow < 0 || endRow >= 8 || endCol < 0 || endCol >= 8 || hasLabel(squares[endRow][endCol])) {
//            return false;
//        }
//        CheckerPiece pieceToMove = getPiece(squares[startRow][startCol]);
//        int rowDiff = endRow - startRow;
//        int colDiff = Math.abs(endCol - startCol);
//
//        boolean validMove = (pieceToMove.getCheckerColour() == CheckerColour.RED && rowDiff == 1) || (pieceToMove.getCheckerColour() == CheckerColour.BLACK && rowDiff == -1)
//                || pieceToMove.isKing();
//
//        if (validMove && colDiff == 1) {
//            return true;
//        }
//
//        if ((pieceToMove.getCheckerColour().equals(CheckerColour.RED) && rowDiff == 2) || (pieceToMove.getCheckerColour().equals(CheckerColour.BLACK) && rowDiff == -2)
//                || (pieceToMove.isKing() && rowDiff == 2 || rowDiff == -2)) {
//            if (colDiff == 2) {
//                int midRow = (startRow + endRow) / 2;
//                int midCol = (startCol + endCol) / 2;
//                CheckerPiece middlePiece = getPiece(squares[midRow][midCol]);
//                JPanel middlePiece2 = squares[midRow][midCol];
//
//                if (middlePiece != null) {
//                    if ((pieceToMove.getCheckerColour().equals(CheckerColour.RED) && middlePiece.getCheckerColour().equals(CheckerColour.BLACK))
//                            || (pieceToMove.getCheckerColour().equals(CheckerColour.BLACK) && middlePiece.getCheckerColour().equals(CheckerColour.RED))) {
//                        if (redTurn) {
//                            redPoints ++;
//                            System.out.println(redPoints);
//                        } else {
//                            blackPoints ++;
//                            System.out.println(blackPoints);
//                        }
//                        middlePiece2.removeAll();
//                        middlePiece2.repaint();
//                        checkWinCondition();
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    private void checkWinCondition() {
//        int redCount = 0;
//        int blackCount = 0;
//
//        for (int row = 0; row < 8; row++) {
//            for (int col = 0; col < 8; col++) {
//                CheckerPiece piece = getPiece(squares[row][col]);
//                if (piece != null) {
//                    if (piece.getCheckerColour().equals(CheckerColour.RED)) {
//                        redCount++;
//                    } else if (piece.getCheckerColour().equals(CheckerColour.BLACK)) {
//                        blackCount++;
//                    }
//                }
//            }
//        }
//
//        if (redCount == 0) {
//            JOptionPane.showMessageDialog(this, "Black Wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
//            new EmptyBoard();
//            this.dispose();
//        } else if (blackCount == 0) {
//            JOptionPane.showMessageDialog(this, "Red Wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
//            new EmptyBoard();
//            this.dispose();
//        }
//    }
}