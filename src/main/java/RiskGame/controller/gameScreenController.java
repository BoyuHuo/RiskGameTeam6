package RiskGame.controller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class gameScreenController{

        @FXML
        private Button myCards;

        @FXML
        private void newButtonOnClicked() {
                try {
                        Parent anotherRoot = FXMLLoader.load(getClass().getResource("/view/cardScreen.fxml"));
                        Stage anotherStage = new Stage();
                        anotherStage.setTitle("My cards");
                        anotherStage.setScene(new Scene(anotherRoot, 600.0, 400.0));
                        anotherStage.show();
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }




}
