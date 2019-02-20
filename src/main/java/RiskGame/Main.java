package RiskGame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent mainScreen = FXMLLoader.load(getClass().getResource("/view/sample.fxml"));
        primaryStage.setTitle("The Risk Game");
        primaryStage.setScene(new Scene(mainScreen, 610, 400));
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
