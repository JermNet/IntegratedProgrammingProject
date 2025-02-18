import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ChessGameGUI extends JFrame{
    private JPanel[][] squares = new JPanel[8][8];
    private JPanel checkerPanel = new JPanel();
    private boolean clickedSquare = false;
    private int savedRow = 0, savedCol = 0;
    private String blackPiecePath = "src/blackcircle.png";
    private ImageIcon blackPiece = new ImageIcon(blackPiecePath);
    private String redPiecePath = "src/redcircle.png";
    private ImageIcon redPiece = new ImageIcon(redPiecePath);
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
                        if (clickedSquare && validMove(savedRow, savedCol, row, col)) {
                            squares[savedRow][savedCol].removeAll();
                            squares[savedRow][savedCol].setBackground(Color.WHITE);
                            squares[savedRow][savedCol].repaint();
                            //squares[savedRow][savedCol].setCol
                            squares[row][col].add(new JLabel(redPiece));
                            clickedSquare = false;
                            redTurn = false;
                            System.out.println(clickedSquare + " " + redTurn);
                        }
                        boolean redCanGo;
                        try {
                            redCanGo = redTurn && getLabel(squares[row][col]).getIcon().equals(redPiece);
                            System.out.println(redCanGo);
                        } catch (Exception ex){
                            redCanGo = false;
                        }
                        if (redCanGo) {
                            highlightSquare(row, col);
                            clickedSquare = true;
                            savedRow = row;
                            savedCol = col;
                            System.out.println(clickedSquare);
                        }
                    }

                    if (!redTurn) {
                        if (clickedSquare && validMove(savedRow, savedCol, row, col)) {
                            squares[savedRow][savedCol].removeAll();
                            squares[savedRow][savedCol].setBackground(Color.WHITE);
                            squares[savedRow][savedCol].repaint();
                            //squares[savedRow][savedCol].setCol
                            squares[row][col].add(new JLabel(blackPiece));
                            clickedSquare = false;
                            redTurn = true;
                            System.out.println(clickedSquare + " " + blackPiece);
                        }
                        boolean blackCanGo;
                        try {
                            blackCanGo = !redTurn && getLabel(squares[row][col]).getIcon().equals(blackPiece);
                            System.out.println(blackCanGo);
                        } catch (Exception ex){
                            blackCanGo = false;
                        }
                        if (blackCanGo) {
                            highlightSquare(row, col);
                            clickedSquare = true;
                            savedRow = row;
                            savedCol = col;
                            System.out.println(clickedSquare);
                        }
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
                        CheckerPiece piece = new CheckerPiece(CheckerPiece.CheckerColour.RED, redPiece);
                        square.add(piece);
                    } else if (row > 4) {
                        CheckerPiece piece = new CheckerPiece(CheckerPiece.CheckerColour.BLACK, blackPiece);
                        square.add(piece);
                    }
                } else {
                    square.setBackground(Color.BLACK);
                }
                //checker.setPosition(CheckerPiece.CheckerColour.RED, row, col);
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

    private boolean validMove (int startRow, int startCol, int endRow, int endCol) {
        // Size of board would be 8
        // Captures are handled in a different method, this just checks if a move is valid
        // The != "" represents an empty board piece, one with no checker on it, would be different in actual code
        for (Component component : squares[endRow][endCol].getComponents()) {
            System.out.println(component.toString());
        }
        if (endRow < 0 || endRow >= 8 || endCol < 0 || endCol >= 8 || hasLabel(squares[endRow][endCol])) {
            System.out.println(endRow + " " + endCol);

            return false;
        }
        JPanel pieceToMove = squares[startRow][startCol];

        // Check if the piece to move is a blank spot or not owned by the current player
//        if (hasLabel(pieceToMove) || pieceToMove.getColor != currentPlayer) {
//            return false;
//        }

        // To check for diagonals and junk
        int rowDiff = endRow - startRow;
        int colDiff = Math.abs(endCol - startCol);
        boolean validMove = (getLabel(pieceToMove).getIcon().equals(redPiece) && rowDiff == 1) ||
                (getLabel(pieceToMove).getIcon().equals(blackPiece) && rowDiff == -1);
        //|| pieceToMove.isKing;

        if (validMove && colDiff == 1) {
            return true;
        }

        if ((getLabel(pieceToMove).getIcon().equals(redPiece) && rowDiff == 2) || (getLabel(pieceToMove).getIcon().equals(blackPiece) && rowDiff == -2)) {
            if (colDiff == 2) {
                int midRow = (startRow + endRow) / 2;
                int midCol = (startCol + endCol) / 2;
                JPanel middlePiece = squares[midRow][midCol];

                if (middlePiece != null) {
                    JLabel middle = getLabel(middlePiece);
                    if ((getLabel(pieceToMove).getIcon().equals(redPiece) && middle.getIcon().equals(blackPiece)) || (getLabel(pieceToMove).getIcon().equals(blackPiece) && middle.getIcon().equals(redPiece))) {
                        if (redTurn) {
                            redPoints ++;
                            System.out.println(redPoints);
                        } else {
                            blackPoints ++;
                            System.out.println(blackPoints);
                        }
                        middlePiece.removeAll();
                        middlePiece.repaint();
                        checkWinCondition();
                        return true;
                    }
                }
            }


        }
        return false;
    }

    private void checkWinCondition() {
        int redCount = 0;
        int blackCount = 0;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JLabel pieceLabel = getLabel(squares[row][col]);
                if (pieceLabel != null) {
                    ImageIcon piece = (ImageIcon) pieceLabel.getIcon();
                    if (piece.equals(redPiece)) {
                        redCount++;
                    } else if (piece.equals(blackPiece)) {
                        blackCount++;
                    }
                }
            }
        }

        if (redCount == 0) {
            JOptionPane.showMessageDialog(this, "Black Wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            //resetGame();
        } else if (blackCount == 0) {
            JOptionPane.showMessageDialog(this, "Red Wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            //resetGame();
        }
    }

}

