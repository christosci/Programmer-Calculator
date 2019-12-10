import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainView {
    private final MainPane mainPane;
    private Scene scene;

    public MainView() {
        mainPane = new MainPane();
    }

    public void createUI(Stage primaryStage) {
        mainPane.init();
        scene = new Scene(mainPane);
        scene.getStylesheets().add("stylesheet.css");

        primaryStage.getIcons().add(new Image("icon.png"));
        primaryStage.setTitle("Calculator");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public MainPane getMainPane() {
        return mainPane;
    }

    public Scene getScene() {
        return scene;
    }
}
