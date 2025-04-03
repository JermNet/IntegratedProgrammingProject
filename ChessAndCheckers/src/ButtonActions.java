import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

public class ButtonActions {
    public void helpClick() {
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

    public void checkerClick(JFrame frame) {
        frame.dispose();
        new CheckersGameGUI();
    }

    public void chessClick(JFrame frame) {
        frame.dispose();
        new ChessGameGUI();
    }

    public void randomClick(JFrame frame) {
        Random rand = new Random();
        System.out.println(rand);

        // The reason I did it this ways was to have the potential to expand this board with more games, we could do it through this concept.
        int randomNum = rand.nextInt(100);

        if (randomNum%2 == 0) {
            frame.dispose();
            new CheckersGameGUI();
        }
        if (randomNum%2== 1) {
            frame.dispose();
            new ChessGameGUI();
        }
    }
}
