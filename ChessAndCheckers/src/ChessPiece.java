import javax.swing.*;

public class ChessPiece extends JLabel {
    private ChessRank rank;
    private boolean isUpgraded;

    public ChessPiece(ChessRank rank, ImageIcon imageIcon) {
        this.rank = rank;
        this.isUpgraded = false;
        this.setIcon(imageIcon);
    }

    public ChessRank getChessRank() { return rank; }

    public void Upgrade() {
        if (rank == ChessRank.PAWN) {
            rank = ChessRank.QUEEN;
        }
        isUpgraded = true;
    }

    public boolean isUpgraded() { return isUpgraded; }
}
