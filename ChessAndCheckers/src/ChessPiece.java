import javax.swing.*;

public class ChessPiece extends JLabel {
    private ChessRank rank;
    private final Colour colour;
    private boolean isUpgraded;

    public ChessPiece(ChessRank rank, Colour colour, ImageIcon imageIcon) {
        this.rank = rank;
        this.colour = colour;
        this.isUpgraded = false;
        this.setIcon(imageIcon);
    }

    public ChessRank getChessRank() { return rank; }

    public Colour getColour() { return colour; }

    public void Upgrade() {
        if (rank == ChessRank.PAWN) {
            rank = ChessRank.QUEEN;
        }
        isUpgraded = true;
    }

    public boolean isUpgraded() { return isUpgraded; }
}
