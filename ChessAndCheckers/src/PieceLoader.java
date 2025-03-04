import javax.swing.ImageIcon;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PieceLoader {
    private static final String ASSETS_PATH = "src/assets/";
    private final Map<String, ImageIcon> pieces = new HashMap<>();

    public PieceLoader() {
        loadImages();
    }

    private void loadImages() {
        File directory = new File(ASSETS_PATH);
        if (directory.exists() && directory.isDirectory()) {
            System.out.println("DIRECTORY EXISTS");
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".png") || name.endsWith(".jpg"));
            if (files != null) {
                System.out.println("FILES EXISTS");
                for (File file : files) {
                    System.out.println(file.getName());
                    String name = file.getName().replace(".png", "").replace(".jpg", "");
                    pieces.put(name, new ImageIcon(file.getPath()));
                    System.out.println(pieces.get("blackpe"));
                }
            }
        } else {
        }
    }

    public ImageIcon getPiece(String name) {
        return pieces.get(name);
    }
}
