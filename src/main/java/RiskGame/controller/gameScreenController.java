package RiskGame.controller;
import RiskGame.model.entity.GameMap;
import RiskGame.model.entity.Player;
import RiskGame.model.entity.Territory;
import RiskGame.model.service.imp.GameManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;

import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
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
        private AnchorPane gameMapPane;

        @FXML
        private Text soldiersNumbers;

        @FXML
        private Button certifyDicesNumber;

        private HashMap<String,Label> playerList;

        @FXML
        private Button endRound;

        private Group rectangleGroups = new Group() ;

        private GameMap gameMap;

        private Rectangle territorySquare = null ;

        private Line l1;

        private int mode=0;

        Territory sourceTerrotory=null;
        int armyNumber=0;

        private HashMap<String,Color> continentColor=new HashMap<String,Color>();

        private String family = "Helvetica";
        private double size = 25;

        // Show the card window
        @FXML
        private void newButtonOnClicked() {
                try {
                        Parent anotherRoot = FXMLLoader.load(getClass().getResource("/view/cardScreen.fxml"));
                        Stage anotherStage = new Stage();
                        anotherStage.setTitle("My cards");
                        anotherStage.setScene(new Scene(anotherRoot, 1000.0, 600.0));
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
                        soldiersNumbers.setText(soldierNum);
                }catch (Exception e){
                        e.printStackTrace();
                }
        }

        @FXML
        private void endRoundClick(){
                GameManager.getInstance().nextRound();
                Update();
        }


        // Certify the number of dices
        @FXML
        private void certifyDices(String certifyDices){

        }


        public void initGameWindow(){
                 initPlayers();

                onMouseClick();

                Update();
        }

        public void initPlayers(){
            if(playerList==null) {
                playerList = new HashMap<>();
            }
            int count=0;
            for (Player p: GameManager.getInstance().getPlayers().values()) {
                Label label=new Label(p.getName()+" :"+p.getArmies());
                label.setBackground(new Background(new BackgroundFill(Color.valueOf(p.getColor()), CornerRadii.EMPTY, Insets.EMPTY)));
                playerList.put(p.getName(),label);
                players.addColumn(count, label);
            }
            highLightActivePlayer();
        }

        private void updatePlayers(){
            for (Player p: GameManager.getInstance().getPlayers().values()){
                playerList.get(p.getName()).setText(p.getName()+" :"+p.getArmies());
            }
            highLightActivePlayer();
        }

    public void highLightActivePlayer(){
        playerList.get(GameManager.getInstance().getActivePlayer().getName()).setText("--> "+GameManager.getInstance().getActivePlayer().getName()+" :"+GameManager.getInstance().getActivePlayer().getArmies());
        }

        private void onMouseClick() {


                gameMapPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {

                                switch (GameManager.getInstance().getGamePhase()) {
                                        case "Start Up":
                                                setupArmyTerrotory(event.getX(),event.getY());
                                                break;
                                        case "Reinforcements":
                                                setupArmyTerrotory(event.getX(),event.getY());
                                                break;
                                        case "Attack":

                                                break;
                                        case "Fortification":

                                                fortifyArmy(event.getX(),event.getY());

                                                break;
                                }
                        }
                });
        }

    private void fortifyArmy(double x, double y) {

        Territory territory=clickedTerrotory( x,  y);
        Player activePlayer=GameManager.getInstance().getActivePlayer();

        if(mode==0){

            if(territory!=null) {

                if(territory.getBelongs().equals(activePlayer)){

                    if(transferArmyNumberDialog(territory)){
                        mode=1;
                    }else {
                        mode=0;
                    }
                    sourceTerrotory=territory;

                } else {
                    showAlertDialog(territory.getName()+" terrotory does not belongs to "+activePlayer.getName());
                }

            } else {
                showAlertDialog("Select a terrotory!");
            }
        } else  if(mode==1) {

            if(territory!=null) {

                if(territory.getBelongs().equals(activePlayer)){

                    if(!sourceTerrotory.immigrantArimies(armyNumber,territory)){
                        showAlertDialog(sourceTerrotory.getName()+" can't move armies to  "+territory.getName());
                    };
                    Update();
                    mode=0;

                } else {
                    showAlertDialog(territory.getName()+" terrotory does not belongs to "+activePlayer.getName());
                }

            } else {
                showAlertDialog("Select a terrotory!");
                mode=0;
            }
        }





    }


    private boolean transferArmyNumberDialog(Territory territory) {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter army");
        dialog.setHeaderText("Transfer number of armies from: "+territory.getName());
        dialog.setContentText("Number:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {

            if (result.get().equalsIgnoreCase("")) {
                showAlertDialog("Enter number of army");
                return false;
            }
            if (Integer.parseInt(result.get())>territory.getArmies()) {
                showAlertDialog("Enter appropriate number of army!!");
                return false;
            }
            armyNumber=Integer.parseInt(result.get());
            return true;
        } else {
            return false;
        }

    }

    private Territory clickedTerrotory(double x, double y) {
        HashMap<String,Territory> territories=GameManager.getInstance().getMap().getTerritories();
        for(Map.Entry<String, Territory> entry :territories.entrySet()) {

            double _x = entry.getValue().getX();
            double _y = entry.getValue().getY();
            if (x >= _x && y >= _y && x <= _x + 50 && y <= _y + 40){
                return territories.get(entry.getKey());
            }

        }
        return null;
    }

    private void setupArmyTerrotory(double x, double y) {

            Territory territory=clickedTerrotory( x,  y);

           if(territory!=null) {
               Player activeplayer=GameManager.getInstance().getActivePlayer();
               if(territory.getBelongs().equals(activeplayer)){
                   territory.increaseArmies(activeplayer);
               } else {
                   showAlertDialog(territory.getName()+" terrtory does not belongs to "+activeplayer.getName());
               }

           } else {
                            showAlertDialog("Select a terrotory!");
           }
           Update();
    }


        private void showAlertDialog(String message) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
        }

        public void Update(){
                phase.setText(GameManager.getInstance().getGamePhase());
                updatePlayers();
                drawMap();
        }





        @Override
        public void initialize(URL location, ResourceBundle resources) {

                gameMap=GameManager.getInstance().getMap();
                initGameWindow();
        }

        private void drawMap() {

                gameMapPane.getChildren().add(rectangleGroups);

                for (Map.Entry<String, Territory> entry :gameMap.getTerritories().entrySet() ) {
                        DFS(entry.getValue(), new ArrayList<>());
                }

                for (Map.Entry<String, Territory> entry :gameMap.getTerritories().entrySet() ) {
                        String key=entry.getKey();
                        Territory territory=entry.getValue();
                        territorySquare = new Rectangle();
                        setTerrotorySquareProperties( territory.getX(),territory.getY(),territorySquare,territory.getBelongs().getColor());
                        rectangleGroups.getChildren().add( territorySquare ) ;
                        if(!continentColor.containsKey(territory.getContinent().getName())){
                            continentColor.put(territory.getContinent().getName(),generateRandomColor());
                        }

                        setContinentSquareProperties( territory.getX(),territory.getY(),continentColor.get(territory.getContinent().getName()));
                        setLabelProperties(entry);

                        //setLine(entry);
                        territorySquare=null;
                }
                rectangleGroups=new Group();
        }

    private void setContinentSquareProperties(int x, int y, Color color) {
        Rectangle rectangle=new Rectangle();
        rectangle.setArcHeight(10);
        rectangle.setArcWidth(10);
        rectangle.setX( x ) ;
        rectangle.setY( y-20 ) ;
        rectangle.setWidth( 55 ) ;
        rectangle.setHeight( 20 ) ;
        rectangle.setFill( color) ;
        rectangle.setStroke( Color.BLACK ) ;
        rectangleGroups.getChildren().add( rectangle ) ;
    }

    private void setLabelProperties(Map.Entry<String, Territory> entry) {
        Label continentName = new Label();
        continentName.setLayoutX((entry.getValue().getX() + 5));
        continentName.setLayoutY((entry.getValue().getY() - 20));
        continentName.setText(entry.getValue().getName());
        continentName.setTextFill(Color.WHITE);
        continentName.setStyle("-fx-font-weight: bold;");


        Label armyAssigned = new Label();
        armyAssigned.setLayoutX((entry.getValue().getX() + 22.5));
        armyAssigned.setLayoutY((entry.getValue().getY() + 15));
        armyAssigned.setText(String.valueOf(entry.getValue().getArmies()));
        armyAssigned.setTextFill(Color.BLACK);
        armyAssigned.setStyle("-fx-font-weight: bold;");

        gameMapPane.getChildren().add(armyAssigned);
        gameMapPane.getChildren().add(continentName);
    }

    private void setLine(Map.Entry<String, Territory> entry) {
        Line line =new Line();
        line.setStartX(entry.getValue().getX());
        line.setStartY(entry.getValue().getY()+20);
        line.setEndX(entry.getValue().getX()+55);
        line.setEndY(entry.getValue().getY()+20);
        rectangleGroups.getChildren().add(line);
    }


    private void DFS(Territory t, ArrayList<String> connectedTerrs) {
                for (String key : t.getNeighbors().keySet()) {
                        Territory neightbor = t.getNeighbors().get(key);


                        if (!connectedTerrs.contains(neightbor.getName())) {
                                connectedTerrs.add(neightbor.getName());
                                l1 = new Line();
                                l1.setStartX((t.getX())+20);
                                l1.setStartY((t.getY())+20);

                                l1.setEndX((neightbor.getX())+20);
                                l1.setEndY((neightbor.getY())+20);
                                rectangleGroups.getChildren().add( l1 ) ;
                                DFS(neightbor, connectedTerrs);
                        }
                        l1=null;
                }
        }

        private void setTerrotorySquareProperties( double starting_point_x, double starting_point_y,Rectangle square, String color)
        {

                square.setArcHeight(10);
                square.setArcWidth(10);
                square.setX( starting_point_x ) ;
                square.setY( starting_point_y ) ;
                square.setWidth( 55 ) ;
                square.setHeight( 40 ) ;
                square.setFill( Color.valueOf(color)) ;
                square.setStroke( Color.BLACK ) ;


        }

    private Color generateRandomColor() {
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);


        return Color.rgb(r,g,b);
    }

}
