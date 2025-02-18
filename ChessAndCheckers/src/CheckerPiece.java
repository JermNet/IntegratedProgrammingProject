import javax.swing.*;
import java.awt.*;

public class CheckerPiece extends JLabel {

    //Enum for which player/colour the piece belongs to
    enum CheckerColour { RED, BLACK }
    private CheckerColour checkerColour;
    private boolean isKing;

    // Constructor
    public CheckerPiece(CheckerColour checkerColour, ImageIcon imageIcon) {
        this.checkerColour = checkerColour;
        this.isKing = false;
        this.setIcon(imageIcon);
    }

    public CheckerPiece() {

    }

    public CheckerColour getCheckerColour() { return checkerColour; }

    public void PromoteKing() {
        isKing = true;
    }

}