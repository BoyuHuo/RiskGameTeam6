package RiskGame.controller;

import RiskGame.model.entity.GameMap;
import RiskGame.model.entity.ListData;
import RiskGame.model.entity.Player;
import RiskGame.model.entity.Territory;
import RiskGame.model.service.imp.GameManager;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.net.URL;
import java.util.*;
/**
 *
 * This is the implementation of controller for game screen. It implements all the required functionality that is required
 * for the game play including startup, reinforcement, attack, fortification phases.
 *
 * @author Karan Sharma
 * @version v1.0.0
 */
public class GameScreenController implements Initializable, Observer {

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
        @FXML
        private ListView<String> continentList;

        @FXML
        private ListView<String> playerListView;

        @FXML
        private ChoiceBox<Integer> cbAttacker;

        @FXML
        private  ChoiceBox<Integer> cbDefend;

        @FXML
        private Button btnOK;
        @FXML
        private Button btnAllIn;
        @FXML
        private  Button Attack;

        @FXML
        private TextArea txtAreaStatus;


        private final ObservableList<String> playerData= FXCollections.observableArrayList();

        private final ObservableList<String> continentData= FXCollections.observableArrayList();

        private Group rectangleGroups = new Group() ;

        private GameMap gameMap;

        private Rectangle territorySquare = null ;

        private Line l1;

        private int mode=0;

        private Territory sourceTerrotory=null;
        int armyNumber=0;

        private HashMap<String,Color> continentColor=new HashMap<String,Color>();
        private ArrayList<String> continent=new ArrayList<>();
        boolean isvalidlineStart=false;
        boolean isValidlineEnd=false;
        Territory destT;
        String destName;
        Territory sourceTerritory;
        String sourceTerritoryName;

        private ObservableList attackDice = FXCollections.observableArrayList();
        private ObservableList defendDice = FXCollections.observableArrayList();
    private int r;

    /**
     *<p>
     * This method implements New Button button that loads the card screen.
     *</p>
     */
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
    /**
     *<p>
     * This method implements the notification text.
     *</p>
     * @param notice string for notification
     */
        @FXML
        private void notice(String notice){
                try {
                        notification.setText(notice);
                }catch (Exception e){
                        e.printStackTrace();
                }
        }
    /**
     *<p>
     * This method updates game phase text.
     *</p>
     * @param gamePhase game phase
     */
        @FXML
        private void gamePhase(String gamePhase){
                try {
                        phase.setText(gamePhase);
                }catch (Exception e){
                        e.printStackTrace();
                }
        }

        @FXML
        private void playerInfo(String playerInfo){

        }


    /**
     *<p>
     * This method sets the number of soldiers field.
     *</p>
     */
        @FXML
        private void soldierNum(String soldierNum){
                try {
                        soldiersNumbers.setText(soldierNum);
                }catch (Exception e){
                        e.printStackTrace();
                }
        }

    /**
     *<p>
     * This method implements the logic to end the current round.
     *</p>
     */
        @FXML
        private void endRoundClick(){
                GameManager.getInstance().nextRound();
                Update();
        }


        @FXML
        private void certifyDices(String certifyDices){

        }

    /**
     *<p>
     * This method initiates the game window.
     *</p>
     */
        public void initGameWindow(){
                initPlayers();

                onMouseClick();

                Update();
        }

    /**
     *<p>
     * This method initializes players.
     *</p>
     */
        public void initPlayers(){

            playerData.clear();


            for (Player p: GameManager.getInstance().getPlayers().values()) {
                playerData.add(p.getName());
            }

            playerListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                @Override
                public ListCell<String> call(ListView<String> param) {
                    ListCell<String> cell=new ListCell<String>() {
                        @Override
                        protected void updateItem(String playerName, boolean b) {
                            //System.out.println(playerName);
                            if(playerName!=null) {
                                ListData data=new ListData();

                                if(playerName.equalsIgnoreCase(GameManager.getInstance().getActivePlayer().getName())) {
                                    data.setPlayerInfo(playerName + " :" +
                                            GameManager.getInstance().getPlayers().get(playerName).getArmies(), Color.valueOf(GameManager.getInstance().getActivePlayer().getColor()),true);
                                }   else {
                                    data.setPlayerInfo(playerName + " :" +
                                            GameManager.getInstance().getPlayers().get(playerName).getArmies(), Color.valueOf(GameManager.getInstance().getPlayers().get(playerName).getColor()),false);

                                }
                                    setGraphic(data.getBox());
                            }

                        }
                    };
                    return cell;
                }
            });

            playerListView.setItems(playerData);
        }

    /**
     *<p>
     * This method implements mouse click events.
     *</p>
     */
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
                                                attackTerritory();
                                                break;
                                        case "Fortification":

                                                fortifyArmy(event.getX(),event.getY());

                                                break;
                                }
                        }
                });
        }

    /**
     *<p>
     * This method implements Next Button.
     *</p>
     * @param x x-coordinates of source territory
     * @param y y-coordinates of source territory
     */
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

                    if(!GameManager.getInstance().getActivePlayer().immigrantArimies(armyNumber,sourceTerritory,destT));
                        showAlertDialog(sourceTerrotory.getName()+" can't move armies to  "+territory.getName());
                    } else{
                        GameManager.getInstance().nextRound();
                    }

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


    /**
     *<p>
     * This method implements dialog box that allows user to transfer armies from one
     * territory to other.
     *</p>
     * @param territory Territory class object.
     * @return returns if it's a valid army assignment.
     */
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

    /**
     *<p>
     * This method implements functionality that returns the territory that was clicked on the screen.
     *</p>
     * @param x x-coordinates of source territory
     * @param y y-coordinates of source territory
     * @return returns territory object
     */
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

    /**
     *<p>
     * This method implements functionality that assigns the armies to a territory.
     *</p>
     * @param x x-coordinates of source territory
     * @param y y-coordinates of source territory
     */
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

    /**
     *<p>
     * This is a generic method used for alert dialog boxes.
     *</p>
     * @param message message that needs to be shown in the alert box.
     *
     */
        private void showAlertDialog(String message) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Update");
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
        }

    /**
     *<p>
     * This method updates the game screen view.
     *</p>
     */
        public void Update(){
                phase.setText(GameManager.getInstance().getGamePhase());
                //updatePlayers();
                initPlayers();
                drawMap();
                btnOK.setDisable(true);
        }

    @FXML
    TextArea textAreaStatus;
        @Override
        public void initialize(URL location, ResourceBundle resources) {

                gameMap=GameManager.getInstance().getMap();
                initGameWindow();
                setContinentList();
                diceDropDownLoad();
                txtAreaStatus.setText("HELLO\nHELLO\nHELLO\nHELLO\nHELLO\nHELLO\nHELLO\nHELLO\nHELLO\nHELLO\nHELLO\nHELLO\nHELLO\nHELLO\nHELLO\nHELLO\nHELLO\nHELLO\nHELLO\n");


                GameManager.getInstance().addObserver(this);

        }

    /**
     *<p>
     * This method initializes the continent list.
     *</p>
     */
    private void setContinentList() {
        continentData.clear();
        continentData.addAll(continent);

        continentList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                ListCell<String> cell=new ListCell<String>() {
                    @Override
                    protected void updateItem(String continent, boolean b) {

                        if(continent!=null) {
                            ListData data=new ListData();
                            data.setInfo(continent,continentColor.get(continent));
                            setGraphic(data.getBox());
                        }

                    }
                };
                return cell;
            }
        });

        continentList.setItems(continentData);
    }

    /**
     *<p>
     * This method renders the created map on the game screen view.
     *</p>
     */
    private void drawMap() {

        rectangleGroups=new Group();
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
                    continent.add(territory.getContinent().getName());
                }

                setContinentSquareProperties( territory.getX(),territory.getY(),continentColor.get(territory.getContinent().getName()));
                setLabelProperties(entry);

                territorySquare=null;
        }
        rectangleGroups=new Group();
        }

    /**
     *<p>
     * This method sets the continent square properties.
     *</p>
     * @param x x-coordinates of mouse pointer
     * @param y y-coordinates of mouse pointer
     * @param color object of the Color class
     */
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

    /**
     *<p>
     * This method sets the continentName and armyAssigned labels.
     *</p>
     * @param entry territory list
     */
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

    /**
     *<p>
     * This method implements the DFS algorithm.
     *</p>
     * @param t Territory object
     * @param connectedTerrs list of connected territory
     */
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

    /**
     *<p>
     * This method sets the territory square properties.
     *</p>
     * @param starting_point_x x-coordinates of mouse pointer
     * @param starting_point_y y-coordinates of mouse pointer
     * @param color color code
     */
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
    /**
     *<p>
     * This method generates random color.
     *</p>
     * @return the rgb color code
     */
    private Color generateRandomColor() {
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);

        return Color.rgb(r,g,b);
    }

    /**
     *<p>
     * This method is responsible for implementing back button.
     *</p>
     * @param x x-coordinate of mouse pointer.
     * @param y y-coordinate of mouse pointer.
     * @return checks if the mouse click is in the required limits and returns true or false.
     */
    private boolean checkCoordinates(double x, double y) {


        for(Map.Entry<String, Territory> entry :gameMap.getTerritories().entrySet()) {
            double _x = entry.getValue().getX();
            double _y = entry.getValue().getY();

            if (x >= _x && y >= _y &&
                    x <= _x + 55 && y <= _y + 40)
                return true;
        }
        return false;
    }


    /**
     *
     * This is method Build 2
     */

    Line newLine;

    public void attackTerritory() {

        gameMapPane.getChildren().add(rectangleGroups);
            gameMapPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    newLine = new Line();

                    System.out.println("Pressed" + "X:" + event.getX() + " Y: " + event.getY());
                    newLine.setStartX(event.getX());
                    newLine.setStartY(event.getY());

                    isvalidlineStart = checkCoordinates(event.getX(), event.getY());

                    double x= event.getX();
                    double y=event.getY();

                    for(Map.Entry<String, Territory> entry :gameMap.getTerritories().entrySet()) {

                        double _x = entry.getValue().getX();
                        double _y = entry.getValue().getY();

                        if (x >= _x && y >= _y &&
                                x <= _x + 55 && y <= _y + 40){
                            sourceTerritory=entry.getValue();

                        }
                    }
                    System.out.println("IsValid SUh" + isvalidlineStart);

                    event.setDragDetect(true);
                }
            });
                 gameMapPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                }
            });
                 gameMapPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                        isValidlineEnd = checkCoordinates(event.getX(), event.getY());


                        double x= event.getX();
                        double y=event.getY();

                        for(Map.Entry<String, Territory> entry :gameMap.getTerritories().entrySet()) {

                            double _x = entry.getValue().getX();
                            double _y = entry.getValue().getY();

                            if (x >= _x && y >= _y &&
                                    x <= _x + 55 && y <= _y + 40){
                                destT=entry.getValue();
                            }
                        }
                    System.out.println("IsValidEnf SUh" + isvalidlineStart);
                        if (isvalidlineStart && isValidlineEnd) {
                            newLine.setEndX(event.getX());
                            newLine.setEndY(event.getY());
                            newLine.setStroke(Color.RED);
                            if(destT.getArmies()==0) {
                                List<Integer> choices = new ArrayList<>();

                                for(int i=1;i<=sourceTerritory.getArmies();i++)
                                {
                                    choices.add(i);
                                }

                                ChoiceDialog<Integer> dialog = new ChoiceDialog<>(1, choices);
                                dialog.setTitle("Attack Status");
                                dialog.setHeaderText("You have attacked: "+destT.getName()+"\n"+"Minimum number of armies you can deploy: "+destT.getCaptureDiceNum());

                                 dialog.setContentText("Choose number of armies to deploy");

                                Optional<Integer> noOfArmies = dialog.showAndWait();

                                System.out.println(noOfArmies);
                                if (noOfArmies.isPresent()){
                                    boolean result= sourceTerritory.captureTerritory(destT,noOfArmies.get());

                                    if(result)
                                    {
                                        showAlertDialog("Attack was Successful");
                                    }
                                    else
                                    {
                                        showAlertDialog("Attack was not successful");
                                    }
                                }
                                Update();
                            }
                            else {
                                    rectangleGroups.getChildren().add(newLine);
                                    btnOK.setDisable(false);
                            }

                            System.out.println("Draw line" );


                        }
                    newLine = null;
                        event.setDragDetect(false);

                }
            });

    }


    public void diceDropDownLoad()
    {
        attackDice.removeAll(attackDice);
        attackDice.addAll(0,1,2,3);
        cbAttacker.getItems().addAll(attackDice);
        cbAttacker.getSelectionModel().selectFirst();
        defendDice.removeAll(defendDice);
        defendDice.addAll(0,1,2);
        cbDefend.getItems().addAll(defendDice);
        cbDefend.getSelectionModel().selectFirst();

    }
    @FXML
    public void clickOKButton(ActionEvent event)
    {
        int result=0;
        int attackerDiceNumber=cbAttacker.getValue();
        int defenderDiceNumber=cbAttacker.getValue();
        int sourceArmyCount = sourceTerritory.getArmies();

        if(attackerDiceNumber>0 && defenderDiceNumber>0)
        {
            if(sourceArmyCount<3)
            {
                result = GameManager.getInstance().getActivePlayer().launchAttack(sourceTerritory,destT, attackerDiceNumber, defenderDiceNumber);
                switch (result)
                {
                    case 0: showAlertDialog("Attack Successful");
                            txtAreaStatus.setText("Attack Successful");

                    case -1: showAlertDialog("Attack Unsuccessful");
                            txtAreaStatus.setText("Not enough armies to attack");

                    case -2: showAlertDialog("Invalid Attack");
                            txtAreaStatus.setText("Attacking your own territory!");

                    case -3: showAlertDialog("Invalid Attack");
                        txtAreaStatus.setText("Not Attacking a neighbouring territory!");
                }
            }
            else
            {
                result = GameManager.getInstance().getActivePlayer().launchAttack(sourceTerritory,destT, sourceArmyCount, defenderDiceNumber);
                switch (result)
                {
                    case 0: showAlertDialog("Attack Successful");
                        txtAreaStatus.setText("Attack Successful");

                    case -1: showAlertDialog("Attack Unsuccessful");
                        txtAreaStatus.setText("Not enough armies to attack");

                    case -2: showAlertDialog("Invalid Attack");
                        txtAreaStatus.setText("Attacking your own territory!");

                    case -3: showAlertDialog("Invalid Attack");
                        txtAreaStatus.setText("Not Attacking a neighbouring territory!");
                }
            }


        }
        else
        {
            showAlertDialog("Not a valid attack. Select Armies again!");
        }
    }

   /* @FXML
    public void txtAreaStatus()throws
    {
        txtAreaStatus.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableList<String> observable, Object oldValue,
                                Object newValue) {
                txtAreaStatus.setScrollTop(Double.MAX_VALUE); //this will scroll to the bottom
                //use Double.MIN_VALUE to scroll to the top
            }
        });

    }
    */
    @FXML
    public void  clickBtnAllInButton(ActionEvent event)
    {
        boolean result;
        result=GameManager.getInstance().getActivePlayer().allInMode(sourceTerritory,destT);
        if(result)
        {
            showAlertDialog("Attack Successful");
        }
        else
        {
            showAlertDialog("Attack Unsuccessful");
        }
        Update();
    }


    @Override
    public void update(Observable observable, Object o) {
        Update();
    }
}
