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
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class gameScreenController{

        @FXML
        private Button myCards;

        @FXML
        private Text notification;

        @FXML
        private Text phase;

        @FXML
        private Pane players;

        @FXML
        private Text soldiersNumber;

        @FXML
        private Button certifyDicesNumber;


        // Show the card window
        @FXML
        private void newButtonOnClicked() {
                try {
                        Parent anotherRoot = FXMLLoader.load(getClass().getResource("/view/cardScreen.fxml"));
                        Stage anotherStage = new Stage();
                        anotherStage.setTitle("My cards");
                        Scene s=new Scene(anotherRoot,600,400);
                        s.getStylesheets().add(
                                getClass().getResource("/css/gameScreenController_CSS.css")
                                        .toExternalForm());
                        anotherStage.show();
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }


        // Get and show the notice
        @FXML
        private void notice(String notice){
                try {
                        notification.setText(notice);
                }catch (Exception e){
                        e.printStackTrace();
                }
        }


        // Get and show the game phase
        @FXML
        private void gamePhase(String gamePhase){
                try {
                        phase.setText(gamePhase);
                }catch (Exception e){
                        e.printStackTrace();
                }
        }



        // Get and show players information
        @FXML
        private void playerInfo(String playerInfo){

        }


        // Get and show the soldiers number
        @FXML
        private void soldierNum(String soldierNum){
                try {
                        soldiersNumber.setText(soldierNum);
                }catch (Exception e){
                        e.printStackTrace();
                }
        }


        // Certify the number of dices
        @FXML
        private void certifyDices(String certifyDices){

        }




}
