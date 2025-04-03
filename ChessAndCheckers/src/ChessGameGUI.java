import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChessGameGUI extends JFrame{
    private final JPanel[][] squares = new JPanel[8][8];
    private boolean clickedSquare = false;
    private int savedRow = 0, savedCol = 0;
    private final PieceLoader loader = new PieceLoader();
    private final ButtonActions buttonActions = new ButtonActions();

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

    private boolean redCanGo;
    private boolean blackCanGo;

    private boolean redTurn = true;
    private JPanel border;

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

        border = getjPanel();
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
                buttonActions.helpClick();
            }
        });

        checkerGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonActions.checkerClick(ChessGameGUI.this);
            }});

        chessGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonActions.chessClick(ChessGameGUI.this);
            }});
        newRandomGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonActions.randomClick(ChessGameGUI.this);
            }
        });

        border.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                border = gameLoop(e, border);
            }
        });

        add(border, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel gameLoop(MouseEvent e, JPanel border) {
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
        return border;
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
            System.out.println("Piece not null");

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

    public void takePiece(int startRow, int startCol, int endRow, int endCol) {
        if (getPiece(squares[endRow][endCol]) != null && getPiece(squares[startRow][startCol]) != null) {
            if (getPiece(squares[endRow][endCol]).getColour() != getPiece(squares[startRow][startCol]).getColour()) {
                squares[endRow][endCol].removeAll();
                squares[endRow][endCol].repaint();
            }
        }
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
                takePiece(startRow, startCol, endRow, endCol);
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
        if (startRow == endRow || startCol == endCol) {
            if (!blockedPath(startRow, startCol, endRow, endCol)) {
                takePiece(startRow, startCol, endRow, endCol);
                return true;
            }
        }
        return false;
    }

    private boolean isValidMoveBishop(int startRow, int startCol, int endRow, int endCol) {
        System.out.println("In validMoveBishop");
        if (Math.abs(endRow - startRow) != Math.abs(endCol - startCol)) {
            return false;
        }
        if(blockedPath(startRow, startCol, endRow, endCol)) {
            return false;
        }
        //takePiece(startRow, startCol, endRow, endCol);
        return true;
    }

    private boolean isValidMoveKnight(int startRow, int startCol, int endRow, int endCol) {
        System.out.println("In validMoveKnight");
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
        System.out.println("In validMoveKing");
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
        System.out.println("In validMoveQueen");
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

            if (col > 7) {
                col = 7;
            }

            System.out.println("Blocked path row: " + row + " Blocked path col: " + col);

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

    public void movePiece(int row, int col, Colour colour) {
        if (clickedSquare && validMove(savedRow, savedCol, row, col)) {
            System.out.println("Moving Piece");
            ChessPiece chessPiece = getPiece(squares[savedRow][savedCol]);
            System.out.println("ChessPiece: " + chessPiece);

            squares[savedRow][savedCol].removeAll();
            squares[savedRow][savedCol].repaint();
            squares[row][col].add(chessPiece);
            squares[row][col].repaint();

            if (getPiece(squares[row][col]).getChessRank() != ChessRank.KING) {
                if (isCheckmate(row, col, Colour.WHITE)) {
                    getWinner("Black Wins!");
                }

                if (isCheckmate(row, col, Colour.BLACK)) {
                    getWinner("White Wins!");
                }
            }

            if (isInCheck(row, col, colour)) {
                squares[row][col].removeAll();
                squares[row][col].repaint();
                squares[savedRow][savedCol].add(chessPiece);
                squares[savedRow][savedCol].repaint();
                System.out.println("ChessPiece: King is in check");
            } else {
                clickedSquare = false;
                redTurn = !redTurn;
            }

            ChessPiece piece = getPiece(squares[row][col]);
            if (piece != null) {
                if (piece.getChessRank() == ChessRank.PAWN) {
                    if (row == 7 && piece.getColour() == Colour.WHITE) {
                        piece.Upgrade(whiteQueen);
                    } else if (row == 0 && piece.getColour() == Colour.BLACK) {
                        piece.Upgrade(blackQueen);
                    }
                }
                squares[row][col].removeAll();
                squares[row][col].add(piece);
                squares[row][col].repaint();
            }

            //System.out.println("1Clicked square: " + clickedSquare + " redTurn: " + redTurn);
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

    private boolean isInCheck(int row, int col, Colour colour) {
        int kingRow = -1, kingCol = -1;
        ChessPiece chessPiece = getPiece(squares[row][col]);
        System.out.println("IN IS CHECK");
        if (chessPiece != null && chessPiece.getChessRank() == ChessRank.KING && chessPiece.getColour() == colour) {
            System.out.println("PIECE IS KING");
            kingRow = row;
            kingCol = col;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    ChessPiece attackingPiece = getPiece(squares[i][j]);
                    if (attackingPiece != null && attackingPiece.getColour() != colour) {
                        System.out.println("ATTACKING PIECE NOT NULL");
                        if (validMove(i, j, kingRow, kingCol)) {
                            System.out.println("ATTACKING IN CHECK");
                            return true;
                        }
                    }
                }
            }
        }


        return false;
    }

    private boolean isCheckmate(int row, int col, Colour colour) {
        if (!isInCheck(row, col, colour)) {
            return false;
        }
        ChessPiece chessPiece = getPiece(squares[row][col]);
        if (chessPiece != null && chessPiece.getColour() == colour) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (validMove(i, j, row, col)) {
                        ChessPiece attackingPiece = getPiece(squares[row][col]);
                        squares[row][col].removeAll();
                        squares[i][j].removeAll();
                        squares[row][col].add(attackingPiece);
                        squares[row][col].repaint();

//                        if (!isInCheck(row, col, colour)) {
//                            return false;
//                        }
                        squares[row][col].removeAll();
                        squares[i][j].add(attackingPiece);
                        squares[i][j].repaint();
                    }
                }
            }
        }
        return true;
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

    private void getWinner(String winner) {
        JOptionPane.showMessageDialog(this, winner, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        new EmptyBoard();
        this.dispose();
    }

    private void getWinnerTakenPieces(Colour colour) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                ChessPiece chessPiece = getPiece(squares[i][j]);



                if (chessPiece != null && chessPiece.getChessRank() == ChessRank.KING && chessPiece.getColour() == colour) {
                    JOptionPane.showMessageDialog(this, colour.toString() + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

}