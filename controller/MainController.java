import javafx.stage.Stage;

/**
 * The MainController class is solely responsible for initializing the
 * View and Model objects.
 */
public class MainController {
    private final MainView view;
    private final Stage primaryStage;
    private final MainModel model;

    public MainController(Stage primaryStage, MainView view) {
        this.view = view;
        this.primaryStage = primaryStage;
        model = new MainModel();
    }

    /* Initializes the UI and sets event handlers for every UI element.*/
    public void createUI() {
        view.createUI(primaryStage);
        EventController eventController = new EventController(model, view);
        eventController.init();
    }
}
