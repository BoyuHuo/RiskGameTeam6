package RiskGame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

/**
 * This is the main class which is the starting point of the Risk Game.
 *
 * @author Sudhanva Muralidhar
 * @version 1.0.0
 */

public class Main extends Application {

    /**
     *
     * This method loads the main screen of the risk game.
     *
     * @param primaryStage the first screen user is exposed to.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent mainScreen = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        primaryStage.setTitle("The Risk Game");
        primaryStage.setScene(new Scene(mainScreen, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     *
     * This methid launches the application.
     * @param args command line argument
     */
    public static void main(String[] args) {
        launch(args);
    }
}
