package RiskGame.controller;
import RiskGame.model.entity.Player;
import RiskGame.model.service.imp.GameManager;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class gameScreenController implements Initializable {

        @FXML
        private Button myCards;

        @FXML
        private Text notification;

        @FXML
        private Text phase;

        @FXML
        private GridPane players;

        @FXML
        private TextField soldiersNumbers;

        @FXML
        private Button certifyDicesNumber;

        private HashMap<String,Text> playerList;

        private Button endRound;


        // Show the card window
        @FXML
        private void newButtonOnClicked() {
                try {
                        Parent anotherRoot = FXMLLoader.load(getClass().getResource("/view/cardScreen.fxml"));
                        Stage anotherStage = new Stage();
                        anotherStage.setTitle("My cards");
                        anotherStage.setScene(new Scene(anotherRoot, 1000.0, 600.0));
                    /*    Scene s=new Scene(anotherRoot,600,400);
                        s.getStylesheets().add(
                                getClass().getResource("/css/gameScreenController_CSS.css")
                                        .toExternalForm());*/
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
                        soldiersNumbers.setText(soldierNum);
                }catch (Exception e){
                        e.printStackTrace();
                }
        }

        @FXML
        private void endRoundClick(){
                GameManager.getInstance().nextPlayer();
                Update();
        }


        // Certify the number of dices
        @FXML
        private void certifyDices(String certifyDices){

        }


        public void initGameWindow(){
                playerList=new HashMap<>();
                int count=0;
                for (Player p: GameManager.getInstance().getPlayers().values()) {
                        Text t=new Text(p.getName());
                        playerList.put(p.getName(),t);
                        players.addColumn(count, t);
                }

                Update();
        }

        public void Update(){
                phase.setText(GameManager.getInstance().getGamePhase());
                int count=0;
                for(Text t:playerList.values()) {
                        count++;
                        t.setUnderline(false);
                        if(t.getText().equals(GameManager.getInstance().getActivePlayer().getName())) {
                                t.setUnderline(true);
                        }

                }


        }



        @Override
        public void initialize(URL location, ResourceBundle resources) {
                initGameWindow();
        }
}
