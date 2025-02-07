import javax.swing.*;
import java.awt.*;

public class CheckerPiece extends JLabel {
    //Enum for which player/colour the piece belongs to
    enum CheckerColour { RED, BLACK }
    private CheckerColour checkerColour;
    private int row, col;
    private boolean isKing;

    // Constructor
    public CheckerPiece(CheckerColour checkerColour, int row, int col, boolean isKing) {
        this.checkerColour = checkerColour;
        this.row = row;
        this.col = col;
        this.isKing = isKing;
    }

    public CheckerPiece() {

    }

    public CheckerColour getCheckerColour() { return checkerColour; }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public boolean isKing() { return isKing; }

    // To be determined at board setup
    public void setPosition(CheckerColour checkerColour, int row, int col) {
        this.row = row;
        this.col = col;
    }

    public JLabel draw() {
        JLabel panel = new JLabel();
        panel.setSize(20, 20);
        panel.setBackground(Color.BLUE);
        return panel;
    }
}