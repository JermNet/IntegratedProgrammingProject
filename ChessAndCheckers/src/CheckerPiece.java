import javax.swing.*;

public class CheckerPiece extends JLabel {

    private Colour colour;
    private boolean isKing;

    // Constructor
    public CheckerPiece(Colour colour, ImageIcon imageIcon) {
        this.colour = colour;
        this.isKing = false;
        this.setIcon(imageIcon);
    }

    public CheckerPiece() {

    }

    public Colour getCheckerColour() { return colour; }

    public void PromoteKing() {
        isKing = true;
    }

    public boolean isKing() { return isKing; }

}