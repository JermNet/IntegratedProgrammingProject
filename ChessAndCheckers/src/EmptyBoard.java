import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class EmptyBoard extends JFrame {
    private final JPanel[][] squares = new JPanel[8][8];

    public EmptyBoard() {
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
                new CheckersGameGUI();
            }});

        chessGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ChessGameGUI();
            }});

        add(border, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
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
                } else {
                    square.setBackground(Color.BLACK);
                }
                //checker.setPosition(CheckerPiece.CheckerColour.RED, row, col);
                border.add(square);
            }
        }
        return border;
    }
}
