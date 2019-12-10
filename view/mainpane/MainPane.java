
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainPane extends VBox {
    private InputHistoryLabel inputHistoryLabel;
    private InputTextField inputTextField;
    private NumberSystemPane numberSystemPane;
    private Button wordButton;
    private KeypadTabPane keypadTabPane;

    public MainPane() {
        super();
        setSpacing(5);
        setPadding(new Insets(10, 0, 0, 0));
        requestFocus();
        setFocusTraversable(false);
    }
    public void init() {
        HBox menuPane = new HBox(10);
        menuPane.setAlignment(Pos.CENTER_LEFT);
        Button menuButton = new Button("☰");
        menuButton.setId("menu-button");
        Label programmerLabel = new Label("Programmer");
        programmerLabel.setId("programmer-label");
        menuPane.getChildren().addAll(menuButton, programmerLabel);

        inputHistoryLabel = new InputHistoryLabel();
        inputTextField = new InputTextField();
        numberSystemPane = new NumberSystemPane();
        keypadTabPane = new KeypadTabPane();

        HBox hbox = new HBox(20);
        wordButton = new Button("QWORD");
        Button msButton = new Button("MS");
        Button mButton = new Button("M▼");
        mButton.setDisable(true);
        wordButton.setId("word-button");
        msButton.setId("memory-button");
        mButton.setId("memory-button");
        hbox.getChildren().addAll(wordButton, msButton, mButton);
        AnchorPane anchor = new AnchorPane();
        anchor.getChildren().addAll(keypadTabPane, hbox);
        AnchorPane.setTopAnchor(hbox, 3.0);
        AnchorPane.setRightAnchor(hbox, 5.0);
        AnchorPane.setTopAnchor(keypadTabPane, 1.0);
        AnchorPane.setRightAnchor(keypadTabPane, 1.0);
        AnchorPane.setLeftAnchor(keypadTabPane, 1.0);
        AnchorPane.setBottomAnchor(keypadTabPane, 1.0);


        getChildren().addAll(menuPane, inputHistoryLabel, inputTextField, numberSystemPane, anchor);
    }

    public InputHistoryLabel getInputHistoryLabel() {
        return inputHistoryLabel;
    }

    public InputTextField getInputTextField() {
        return inputTextField;
    }

    public NumberSystemPane getNumberSystemPane() {
        return numberSystemPane;
    }

    public KeypadTabPane getKeypadTabPane() {
        return keypadTabPane;
    }

    public Button getWordButton() {
        return wordButton;
    }
}
