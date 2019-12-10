import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;

public final class KeypadTabPane extends TabPane {
    private final FullKeypadPane fullKeypadPane;

    public KeypadTabPane() {
        super();

        fullKeypadPane = new FullKeypadPane();

        Tab fullKeypadTab = new Tab();
        fullKeypadTab.setGraphic(new ImageView("full_keypad.png"));
        Tab bitTogglingKeypadTab = new Tab();
        bitTogglingKeypadTab.setGraphic(new ImageView("bit_keypad.png"));
        fullKeypadTab.setClosable(false);
        bitTogglingKeypadTab.setClosable(false);
        bitTogglingKeypadTab.setDisable(true);

        fullKeypadTab.setContent(fullKeypadPane);
        getTabs().addAll(fullKeypadTab, bitTogglingKeypadTab);
    }

    public FullKeypadPane getFullKeypadPane() {
        return fullKeypadPane;
    }
}
