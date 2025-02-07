import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ChessGameGUI extends JFrame{
    private JPanel[][] squares = new JPanel[8][8];
    private CheckerPiece checker = new CheckerPiece();
    private JPanel checkerPanel = new JPanel();
    private boolean clickedSquare = false;
    private int savedRow = 0, savedCol = 0;
    private String blackPiecePath = "src/blackcircle.png";
    private ImageIcon blackPiece = new ImageIcon(blackPiecePath);
    private String redPiecePath = "src/redcircle.png";
    private ImageIcon redPiece = new ImageIcon(redPiecePath);
    private boolean redTurn = true;

    public ChessGameGUI() {

        JPanel button = new JPanel(new GridLayout(2, 1));
        JButton newRandomGame = new JButton("New Random Game");
        JButton checkerGame = new JButton("Checker Game");
        JButton chessGame = new JButton("Chess Game");
        JButton helpGame = new JButton("Help Me!");


        setTitle("Simple Chess OR Checkers Game");
        setSize(900, 900);
        //this.setResizable(false);
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

                if (col >= 0 && col < 8 && row >= 0 && row < 8) {
                    if (clickedSquare) {
                        System.out.println("Clicked square" + savedRow + savedCol);
                        clickedSquare = false;
                    }
                    boolean redCanGo;
                    try {
                        redCanGo = redTurn && getLabel(squares[row][col]).getIcon().equals(redPiece);
                    } catch (Exception ex){
                        redCanGo = true;
                    }
                    if (redCanGo) {
                        highlightSquare(row, col);
                        clickedSquare = true;
                        savedRow = row;
                        savedCol = col;
                        redTurn = false;
                    }
                    else {

                    }


                } else {
                    System.out.println("Clicked at: (" + e.getX() + ", " + e.getY() + ")");
                }
            }
        });

        add(border, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void highlightSquare(int row, int col) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i % 2 == 0) == (j % 2 == 0)) {
                    squares[i][j].setBackground(Color.WHITE);

                } else {
                    squares[i][j].setBackground(Color.BLACK);
                }
            }
        }
        if ((row % 2 == 0) == (col % 2 == 0)) {
            squares[row][col].setBackground(Color.YELLOW);
        }

    }

    private JPanel getjPanel() {
        JPanel border = new JPanel(new GridLayout(8, 8));
        //border.setBorder(BorderFactory.createLineBorder(Color.GRAY, 90, FALSE));

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JPanel square = new JPanel();
                squares[row][col] = square;
                square.setSize(80, 80);
                if ((row % 2 == 0) == (col % 2 == 0)) {
                    square.setBackground(Color.WHITE);
                    if (row < 3) {
                        square.add(new JLabel(redPiece));
                    } else if (row > 4) {
                        square.add(new JLabel(blackPiece));
                    }
                } else {
                    square.setBackground(Color.BLACK);
                }
                checker.setPosition(CheckerPiece.CheckerColour.RED, row, col);
                border.add(square);
            }
        }
        return border;
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

    private JLabel getLabel(JPanel square) {
        Component[] components = square.getComponents();
        for (Component component : components) {
            if (component instanceof JLabel) {
                return (JLabel)component;
            }
        }
        return null;
    }

//    private boolean validMove (int startRow, int startCol, int endRow, int endCol) {
//        // Size of board would be 8
//        // Captures are handled in a different method, this just checks if a move is valid
//        // The != "" represents an empty board piece, one with no checker on it, would be different in actual code
//        if (endRow < 0 || endRow >= 8 || endCol < 0 || endCol >= 8 || hasLabel(squares[startRow][startCol])) {
//            return false;
//        }
//        JPanel pieceToMove = squares[startRow][startCol];
//
//        // Check if the piece to move is a blank spot or not owned by the current player
//        if (hasLabel(pieceToMove) || pieceToMove.getColor != currentPlayer) {
//            return false;
//        }
//
//        // To check for diagonals and junk
//        int rowDiff = endRow - startRow;
//        int colDiff = endCol - startCol;
//        boolean validMove = (pieceToMove.getColor == Red && rowDiff > 0) || (pieceToMove.getColor == Black && rowDiff < 0)
//                || pieceToMove.isKing;
//
//        if (validMove && colDiff == 1 && Math.abs(rowDiff) == 1) {
//            return true;
//        }
//
//        if (validMove && colDiff == 2 && Math.abs(rowDiff) == 2) {
//            int midRow = (startRow + endRow) / 2;
//            int midCol = (startCol + endCol) / 2;
//            char middlePiece =  board[midRow][midCol];
//            return middlePiece != "" && middlePiece != currentPlayer;
//        }
//        return false;
//    }
}

