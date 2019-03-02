package RiskGame.controller;
import RiskGame.model.entity.GameMap;
import RiskGame.model.entity.Player;
import RiskGame.model.entity.Territory;
import RiskGame.model.service.imp.GameManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.*;

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
        private BorderPane gameMapPane;

        @FXML
        private Text soldiersNumbers;

        @FXML
        private Button certifyDicesNumber;

        private HashMap<String,Text> playerList;

        @FXML
        private Button endRound;

        private Group rectangleGroups = new Group() ;

        private GameMap gameMap;

        private Rectangle square = null ;

        private Line l1;

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
                gameMap=GameManager.getInstance().getMap();
                drawMap();
        }

        private void drawMap() {

                gameMapPane.getChildren().add(rectangleGroups);



                for (Map.Entry<String, Territory> entry :gameMap.getTerritories().entrySet() ) {
                        String key=entry.getKey();
                        Territory territory=entry.getValue();
                        territory.getContinent();
                        square = new Rectangle();
                        System.out.println(entry.getKey());
                       /* if(!continentColor.containsKey(territory.getContinent().getName())){
                                continentColor.put(territory.getContinent().getName(),generateRandomColor());
                        }*/

                        setSquareProperties( territory.getX(),territory.getY(),square) ;
                        //connectNeighbours(territory);
                        DFS(territory,new ArrayList<>());

                        rectangleGroups.getChildren().add( square ) ;
                        square=null;
                }
        }


        private void DFS(Territory t, ArrayList<String> connectedTerrs) {
                for (String key : t.getNeighbors().keySet()) {
                        Territory neightbor = t.getNeighbors().get(key);


                        if (!connectedTerrs.contains(neightbor.getName())) {
                                connectedTerrs.add(neightbor.getName());
                                l1 = new Line();
                                l1.setStartX((t.getX())+25);
                                l1.setStartY((t.getY())+25);

                                l1.setEndX((neightbor.getX())+25);
                                l1.setEndY((neightbor.getY())+25);
                                rectangleGroups.getChildren().add( l1 ) ;
                                DFS(neightbor, connectedTerrs);
                        }
                        l1=null;
                }
        }

        private void setSquareProperties( double starting_point_x, double starting_point_y,Rectangle square)
        {
                square.setX( starting_point_x ) ;
                square.setY( starting_point_y ) ;
                square.setWidth( 50 ) ;
                square.setHeight( 50 ) ;
                square.setFill( Color.TRANSPARENT ) ;
                square.setStroke( Color.BLACK ) ;


        }
}
