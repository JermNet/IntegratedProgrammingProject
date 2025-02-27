import javax.swing.*;
import java.awt.*;

public class CheckerPiece extends JLabel {

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

    public boolean isKing() { return isKing; }

}