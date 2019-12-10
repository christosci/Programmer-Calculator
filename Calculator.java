import javafx.application.Application;
import javafx.stage.Stage;

public class Calculator extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainView view = new MainView();
        MainController controller = new MainController(primaryStage, view);

        controller.createUI();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
