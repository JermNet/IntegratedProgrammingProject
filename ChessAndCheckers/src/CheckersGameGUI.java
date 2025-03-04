import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class CheckersGameGUI extends JFrame{
    private final JPanel[][] squares = new JPanel[8][8];
    private boolean clickedSquare = false;
    private int savedRow = 0, savedCol = 0;
    private final PieceLoader loader = new PieceLoader();

    private final ImageIcon blackPiece = loader.getPiece("blackcircle");
    private final ImageIcon redPiece = loader.getPiece("redcircle");
    private final ImageIcon blackKing = loader.getPiece("blackking");
    private final ImageIcon redKing = loader.getPiece("redking");
    boolean redCanGo;
    boolean blackCanGo;

    private boolean redTurn = true;
    private int blackPoints = 0, redPoints = 0;

    public CheckersGameGUI() {
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
                        movePiece(row, col, CheckerColour.RED);
                    }

                    if (!redTurn) {
                        movePiece(row, col, CheckerColour.BLACK);
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

    public void movePiece(int row, int col, CheckerColour colour) {
        if (clickedSquare && validMove(savedRow, savedCol, row, col)) {

            CheckerPiece checkerPiece = getPiece(squares[savedRow][savedCol]);

            squares[savedRow][savedCol].removeAll();
            squares[savedRow][savedCol].setBackground(Color.WHITE);
            squares[savedRow][savedCol].repaint();

            squares[row][col].add(checkerPiece);
            squares[row][col].repaint();
            CheckerPiece piece = getPiece(squares[row][col]);
            if (!piece.isKing()) {
                piece = makeKing(piece, row);
                if (piece != null) {
                    squares[row][col].removeAll();
                    squares[row][col].add(piece);
                    squares[row][col].repaint();
                }
            }
            clickedSquare = false;
            redTurn = !redTurn;
        }
        try {
            if (colour == CheckerColour.RED) {
                redCanGo = redTurn && getPiece(squares[row][col]).getCheckerColour() == colour;
            } else {
                blackCanGo = !redTurn && getPiece(squares[row][col]).getCheckerColour() == colour;
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
                    squares[i][j].setBackground(Color.BLACK);
                }
            }
        }
        if ((row % 2 == 0) == (col % 2 == 0)) {
            squares[row][col].setBackground(Color.YELLOW);
        }

    }

    private CheckerPiece makeKing(CheckerPiece piece, int row) {
        if ((row == 7 && piece.getCheckerColour() == CheckerColour.RED  || (row == 0 && piece.getCheckerColour() == CheckerColour.BLACK))) {
            piece.PromoteKing();
            if (piece.getCheckerColour() == CheckerColour.BLACK) {
                piece.setIcon(blackKing);
            } else {
                piece.setIcon(redKing);
            }
            return piece;
        }
        return null;
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
                    if (row < 3) {
                        CheckerPiece piece = new CheckerPiece(CheckerColour.RED, redPiece);
                        square.add(piece);
                    } else if (row > 4) {
                        CheckerPiece piece = new CheckerPiece(CheckerColour.BLACK, blackPiece);
                        System.out.println(blackPiece);
                        square.add(piece);
                    }
                } else {
                    square.setBackground(Color.BLACK);
                }
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

    private CheckerPiece getPiece(JPanel square) {
        Component[] components = square.getComponents();
        for (Component component : components) {
            if (component instanceof CheckerPiece) {
                return (CheckerPiece)component;
            }
        }
        return null;
    }

    private boolean validMove (int startRow, int startCol, int endRow, int endCol) {
        if (endRow < 0 || endRow >= 8 || endCol < 0 || endCol >= 8 || hasLabel(squares[endRow][endCol])) {
            return false;
        }
        CheckerPiece pieceToMove = getPiece(squares[startRow][startCol]);
        int rowDiff = endRow - startRow;
        int colDiff = Math.abs(endCol - startCol);

        boolean validMove = (pieceToMove.getCheckerColour() == CheckerColour.RED && rowDiff == 1) || (pieceToMove.getCheckerColour() == CheckerColour.BLACK && rowDiff == -1)
                || (pieceToMove.isKing() && (rowDiff == 1 || rowDiff == -1));

        if (validMove && colDiff == 1) {
            return true;
        }

        if ((pieceToMove.getCheckerColour().equals(CheckerColour.RED) && rowDiff == 2) || (pieceToMove.getCheckerColour().equals(CheckerColour.BLACK) && rowDiff == -2)
                || (pieceToMove.isKing() && rowDiff == 2 || rowDiff == -2)) {
            if (colDiff == 2) {
                int midRow = (startRow + endRow) / 2;
                int midCol = (startCol + endCol) / 2;
                CheckerPiece middlePiece = getPiece(squares[midRow][midCol]);
                JPanel middlePiece2 = squares[midRow][midCol];

                if (middlePiece != null) {
                    if ((pieceToMove.getCheckerColour().equals(CheckerColour.RED) && middlePiece.getCheckerColour().equals(CheckerColour.BLACK))
                            || (pieceToMove.getCheckerColour().equals(CheckerColour.BLACK) && middlePiece.getCheckerColour().equals(CheckerColour.RED))) {
                        if (redTurn) {
                            redPoints ++;
                            System.out.println(redPoints);
                        } else {
                            blackPoints ++;
                            System.out.println(blackPoints);
                        }
                        middlePiece2.removeAll();
                        middlePiece2.repaint();
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
                CheckerPiece piece = getPiece(squares[row][col]);
                if (piece != null) {
                    if (piece.getCheckerColour().equals(CheckerColour.RED)) {
                        redCount++;
                    } else if (piece.getCheckerColour().equals(CheckerColour.BLACK)) {
                        blackCount++;
                    }
                }
            }
        }

        if (redCount == 0) {
            JOptionPane.showMessageDialog(this, "Black Wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            new EmptyBoard();
            this.dispose();
        } else if (blackCount == 0) {
            JOptionPane.showMessageDialog(this, "Red Wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            new EmptyBoard();
            this.dispose();
        }
    }
}