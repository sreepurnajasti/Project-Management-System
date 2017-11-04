package screensframework;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author SreePurna
 */
public class ScreensFramework extends Application {
    public static ScreensController mainContainer = new ScreensController();    
    /**
     * This is for console screen
     */
    public static String screen1ID = "main";
    public static String screen1File = "Screen1.fxml";
    
    /**
     * This is for action items screen
     */
    public static String screen2ID = "screen2";
    public static String screen2File = "Screen2.fxml";

    /**
     * This is for members screen
     */
    public static String screen3ID = "screen3";
    public static String screen3File = "Screen3.fxml";

    /**
     * This is for teams screen
     */
    public static String screen4ID = "screen4";
    public static String screen4File = "Screen4.fxml";
    
    
    @Override
    public void start(Stage primaryStage) {
        

        mainContainer.loadScreen(ScreensFramework.screen1ID, ScreensFramework.screen1File);
        mainContainer.loadScreen(ScreensFramework.screen2ID, ScreensFramework.screen2File);
        mainContainer.loadScreen(ScreensFramework.screen3ID, ScreensFramework.screen3File);
        mainContainer.loadScreen(ScreensFramework.screen4ID, ScreensFramework.screen4File);
        
        mainContainer.setScreen(ScreensFramework.screen1ID);
        
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        new dbpackage.ConnectionDb();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
