package semestralka.aplikace;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import semestralka.dbs.DBConnector;


public class MainApp extends Application {

    private static final Logger LOG = Logger.getLogger(MainApp.class.getName());
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        LOG.log(Level.SEVERE, "Application running...");
        
        DBConnector.setInstance("semestralka_aplikace_jar_1.0PU");
        
        Font.loadFont(MainApp.class.getResource("/files/AvenirNextLTPro-DemiCn.ttf").toExternalForm(), 0);
        Font.loadFont(MainApp.class.getResource("/files/AvenirNextLTPro-MediumCn.ttf").toExternalForm(), 0);

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainScreen.fxml"));
 
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/mainscreen.css");

        primaryStage.setTitle("MCES");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
//        primarystage.initStyle(StageStyle.UNDECORATED);
        Image icon = new Image(MainApp.class.getResourceAsStream("/files/icon.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.show();
        
    }
    
    @Override
    public void stop() {
        DBConnector.getInstance().end();
        LOG.log(Level.SEVERE, "Application closed...");
        System.exit(0);
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
