package RiskGame.controller;

import RiskGame.model.entity.Continent;
import RiskGame.model.entity.GameMap;
import RiskGame.model.entity.Territory;
import RiskGame.model.service.imp.MapManager;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class createMapScreenController implements Initializable {


    @FXML
    private Label lblPath, lblCoordinates;
    @FXML
    AnchorPane createMapPane;
    @FXML
    private Button createTerrotoryBt, connectTerrotoryBt, createContinentsBt, saveBt, backBt, loadExistMapbt;
    Scene createMapScene;
    AnchorPane myPanel;
    Group rectangleGroups = new Group();
    Rectangle square = null;

    ArrayList<String> continentArray= new ArrayList<>();

    private ObservableList<String> continentsNames = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> cbContinents;

    int mode;

    private File mapFile;

    private Rectangle territorySquare = null ;

    private HashMap<String,Color> continentColor=new HashMap<String,Color>();


    boolean isvalidlineStart, isValidlineEnd;

    Territory sourceT;
    Territory destT;
    String sourceName;
    String destName;
    GameMap gameMap =new GameMap();
    ArrayList<HashMap<String, Double>> countries = new ArrayList<>();

    ArrayList<Territory> territoryArrayList = new ArrayList<>();

    Line l1;

    /**
     * This is the implementation for New Game Button.
     * <p>
     * Please note that I've used a depricated method call .toURL() below. Please provide your inputs in helping me
     * write this code better.
     *
     * @param event
     * @throws IOException
     */


    @FXML
    private void loadExistMap(ActionEvent event) throws IOException {
        FileChooser mapFileChooser = new FileChooser();
        mapFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".map", "*.map"));
        mapFile = mapFileChooser.showOpenDialog(null);
        if (mapFile != null) {
            MapManager mapManager = new MapManager();
            gameMap = mapManager.LoadMap(mapFile.toString());
        }
        drawMap();
    }


    private void drawMap() {
      //  createMapPane.getChildren().clear();
        rectangleGroups.getChildren().clear();
        //rectangleGroups = new Group();
        //createMapPane.getChildren().add(rectangleGroups);

        for (Map.Entry<String, Territory> entry :gameMap.getTerritories().entrySet() ) {
            DFS(entry.getValue(), new ArrayList<>());
        }

        for (Map.Entry<String, Territory> entry :gameMap.getTerritories().entrySet() ) {
            String key=entry.getKey();
            Territory territory=entry.getValue();
            territorySquare = new Rectangle();
            setTerrotorySquareProperties( territory.getX(),territory.getY(),territorySquare);
            rectangleGroups.getChildren().add( territorySquare ) ;

            if(!continentColor.containsKey(territory.getContinent().getName())){
                continentColor.put(territory.getContinent().getName(),generateRandomColor());
            }

            setContinentSquareProperties( territory.getX(),territory.getY(),continentColor.get(territory.getContinent().getName()));
            setLabelProperties(entry);

            //setLine(entry);
            territorySquare=null;
        }
        //rectangleGroups=new Group();
    }


    private Color generateRandomColor() {
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);

        return Color.rgb(r,g,b);
    }

    private void setTerrotorySquareProperties( double starting_point_x, double starting_point_y,Rectangle square)
    {

        square.setArcHeight(10);
        square.setArcWidth(10);
        square.setX( starting_point_x ) ;
        square.setY( starting_point_y ) ;
        square.setWidth( 55 ) ;
        square.setHeight( 40 ) ;
        square.setStroke( Color.BLACK ) ;


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


       /* Label armyAssigned = new Label();
        armyAssigned.setLayoutX((entry.getValue().getX() + 22.5));
        armyAssigned.setLayoutY((entry.getValue().getY() + 15));
        armyAssigned.setText(String.valueOf(entry.getValue().getArmies()));
        armyAssigned.setTextFill(Color.BLACK);
        armyAssigned.setStyle("-fx-font-weight: bold;");*/

        //createMapPane.getChildren().add(armyAssigned);
        createMapPane.getChildren().add(continentName);
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

    @FXML
    private void clickNewGameButton(ActionEvent event) throws IOException {
        Parent newGameScreen = FXMLLoader.load(getClass().getResource("/view/newGameScreen.fxml"));
        Scene newGameScene = new Scene(newGameScreen, 1000, 600);
        Stage newGameScreenStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        newGameScreenStage.setScene(newGameScene);
        newGameScreenStage.show();
    }


    @FXML
    private void mapFileChooser() {
        FileChooser mapFileChooser = new FileChooser();
        mapFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".map", "*.map"));
        File mapFile = mapFileChooser.showOpenDialog(null);
        if (mapFile != null) {
            lblPath.setText(mapFile.toString());
        }
        //return mapFile;
    }

    @FXML
    private void clickCreateMapButton(ActionEvent event) throws IOException {
        Parent createMap = FXMLLoader.load(getClass().getResource("/view/createMapScreen.fxml"));
        createMapScene = new Scene(createMap, 1000, 600);
        Stage createMapSceneStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        createMapSceneStage.setScene(createMapScene);
        createMapSceneStage.show();

    }

    /**
     * This is a redundant class which prints the co-ordinates of the mouse clicked event.
     * <p>
     * Whenever user clicks on the Build Map button a new blank stage opens up.
     * When you Click on this blank screen, in the previous screen (where you clicked the Build Map button) I'm displaying
     * the co-ordinates of the mouse click.
     * - Sudhanva Muralidhar
     * 19 February 2019
     *
     * @param event Ignore
     */


    @FXML
    public void createTerrotory(ActionEvent event) {

        mode=0;
        if(mode==0) {



            createMapPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    lblCoordinates.setText("X:" + event.getX() + " Y: " + event.getY());
                    lblCoordinates.setVisible(false);

                    square = new Rectangle();

                    if (showInputTextDialog(event.getX(), event.getY())) {
                        setSquareProperties(event.getX(), event.getY(), square);
                        //setContinentSquareProperties( event.getX(), event.getY());
                        //setLabelProperties(entry);
                        rectangleGroups.getChildren().add(square);
                    }

                }
            });

            createMapPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    square = null;
                }
            });
        }

    }


    private void setSquareProperties(double starting_point_x, double starting_point_y, Rectangle square) {
        square.setArcHeight(10);
        square.setArcWidth(10);
        square.setX(starting_point_x);
        square.setY(starting_point_y);
        square.setWidth(55);
        square.setHeight(40);
        square.setFill(Color.TRANSPARENT); // set color to transparent
        square.setStroke(Color.BLACK);


        Rectangle rectangle=new Rectangle();
        rectangle.setArcHeight(10);
        rectangle.setArcWidth(10);
        rectangle.setX( starting_point_x ) ;
        rectangle.setY( starting_point_y-20 ) ;
        rectangle.setWidth( 55 ) ;
        rectangle.setHeight( 20 ) ;
        rectangle.setFill( Color.TRANSPARENT) ;
        rectangle.setStroke( Color.BLACK ) ;
        rectangleGroups.getChildren().add( rectangle ) ;

        HashMap<String, Double> newHash = new HashMap<>();
        newHash.put("x", starting_point_x);
        newHash.put("y", starting_point_y);

        countries.add(newHash);


    }
    @FXML
    public void cbChangeContinent(ActionEvent event) {





    }


    @FXML
    public void connectTerrotory(ActionEvent event) {

        mode=1;

        if(mode==1) {
            createMapPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    lblCoordinates.setText("X:" + event.getX() + " Y: " + event.getY());

                    l1 = new Line();

                    System.out.println("Pressed" + "X:" + event.getX() + " Y: " + event.getY());
                    l1.setStartX(event.getX());
                    l1.setStartY(event.getY());

                    isvalidlineStart = checkCoordinates(event.getX(), event.getY());

                    double x= event.getX();
                    double y=event.getY();

                    for(Map.Entry<String, Territory> entry :gameMap.getTerritories().entrySet()) {

                        double _x = entry.getValue().getX();
                        double _y = entry.getValue().getY();

                        if (x >= _x && y >= _y &&
                                x <= _x + 75 && y <= _y + 75){
                            sourceT=new Territory();
                            sourceT.setX((int) Math.round(_x));
                            sourceT.setY((int) Math.round(_y));
                            sourceT.setName(entry.getValue().getName());
                            sourceName=entry.getValue().getName();

                        }

                    }


                    System.out.println("IsValid" + isvalidlineStart);
                    event.setDragDetect(true);

                }


            });


            createMapPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                }
            });


            createMapPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    if(mode==1) {

                    isValidlineEnd = checkCoordinates(event.getX(), event.getY());


                    double x= event.getX();
                    double y=event.getY();

                    for(Map.Entry<String, Territory> entry :gameMap.getTerritories().entrySet()) {

                        double _x = entry.getValue().getX();
                        double _y = entry.getValue().getY();

                        if (x >= _x && y >= _y &&
                                x <= _x + 75 && y <= _y + 75){
                            destT=new Territory();
                            destT.setX((int) Math.round(_x));
                            destT.setY((int) Math.round(_y));
                            destT.setName(entry.getValue().getName());
                            destName=entry.getValue().getName();

                        }

                    }



                    if (isvalidlineStart && isValidlineEnd) {
                        l1.setEndX(event.getX());
                        l1.setEndY(event.getY());
                        rectangleGroups.getChildren().add(l1);
                        //System.out.println(sourceT.getName()+"->"+destT.getName());
                        gameMap.getTerritories().get(destName).addNeibor(sourceT);
                       // System.out.println(destT.getName()+"->"+sourceT.getName());
                        gameMap.getTerritories().get(sourceName).addNeibor(destT);



                    }

                    l1 = null;
                    event.setDragDetect(false);

                }

                }
            });

        }
    }

    @FXML
    private void clickBack(ActionEvent event) throws IOException {
        Parent editPlayerScreen = FXMLLoader.load(getClass().getResource("/view/newGameScreen.fxml"));
        Scene editPlayerScene = new Scene(editPlayerScreen, 1000, 600);
        Stage editPlayerStage = (Stage) backBt.getScene().getWindow();
        editPlayerStage.setScene(editPlayerScene);
        editPlayerStage.show();
       /* FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/loadMapScreen.fxml"));
        loader.load();
        loadMapScreenController controller = loader.getController();
        controller.checkMap(map);
        createMapScene = new Scene(loader.getRoot(), 600,400);

        Stage createMapSceneStage = (Stage)backBt.getScene().getWindow();
        createMapSceneStage.setScene(createMapScene);
        createMapSceneStage.show();*/
    }


    private boolean checkCoordinates(double x, double y) {


        for(Map.Entry<String, Territory> entry :gameMap.getTerritories().entrySet()) {
            double _x = entry.getValue().getX();
            double _y = entry.getValue().getY();

            if (x >= _x && y >= _y &&
                    x <= _x + 75 && y <= _y + 75)
                return true;
        }
        return false;
    }

    @FXML
    public void addContinents(ActionEvent event) {
        mode=3;
        if(mode==3)
        showContinentAlertDialog();
    }


    @FXML
    public void saveMap(ActionEvent event) throws IOException {
            MapManager mapManager=new MapManager();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Map");
            File file = fileChooser.showSaveDialog((Stage)backBt.getScene().getWindow());

            if (file != null) {
                mapManager.CreateMap(file.getPath(),gameMap);
            }
    }

    private boolean showInputTextDialog(Double x, Double y) {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter Country Name");
        dialog.setHeaderText(null);
        dialog.setContentText("Name:");
        dialog.setContentText("Control Number");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {

            if (result.get().equalsIgnoreCase("")) {
                showAlertDialog("Country");
                return false;
            }
           /* Label label = new Label();
            label.setLayoutX((x + 25) - 12.5);
            label.setLayoutY((y + 25) - 12.5);
            label.setText(result.get());

            createMapPane.getChildren().add(label);*/


            Label continentName = new Label();
            continentName.setLayoutX((x + 5));
            continentName.setLayoutY((y - 20));
            continentName.setText(result.get());
            continentName.setTextFill(Color.WHITE);
            continentName.setStyle("-fx-font-weight: bold;");

            createMapPane.getChildren().add(continentName);

            Territory territory=new Territory(result.get(),(int) Math.round(x),(int) Math.round(y));
            gameMap.getTerritories().put(territory.getName(),territory);
            territoryArrayList.add(territory);

            return true;
        } else {
            return false;
        }

    }

    private void showAlertDialog(String alertType) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText("Enter "+alertType+" name");
        alert.showAndWait();
    }



    private Results showContinentAlertDialog() {

        Dialog<Results> dialog = new Dialog<>();
        dialog.setTitle("Enter Country Details");
        //dialog.setHeaderText("Please specify");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField nameField = new TextField("");
        TextField controlNumber = new TextField("");
        Label  nameLabel = new Label("Name");
        Label  controlLabel = new Label("Control Number");

        //dialogPane.setContent();


        dialogPane.setContent(new HBox(8,new VBox(16, nameLabel, controlLabel),new VBox(8, nameField, controlNumber)));


        Platform.runLater(nameField::requestFocus);
        dialog.setResultConverter((ButtonType button) -> {




            if (button == ButtonType.OK) {


                if (nameField.getText().equalsIgnoreCase("")||
                        controlNumber.getText().equalsIgnoreCase("")) {
                    showAlertDialog("Continent");
                    return null;
                }

                Continent continent= new Continent();
                continent.setName(nameField.getText());
                continent.setCtrNum(Integer.parseInt(controlNumber.getText()));
                gameMap.getContinents().put(nameField.getText(),continent);

                cbContinents.getItems().add(nameField.getText());

                return new Results(nameField.getText(),
                        Integer.parseInt(controlNumber.getText()));
            }
            return null;
        });
        Optional<Results> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((Results results) -> {
            System.out.println(
                    results.continentName + " " + results.controlNumber);
        });

        return  null;
    }




    @FXML
    public void getMouseClick(ActionEvent event) {
        myPanel = new AnchorPane();
        Scene sc = new Scene(myPanel);
        sc.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {

                    lblCoordinates.setText("X:" + event.getSceneY() + " Y: " + event.getSceneY());
                }
            }
        });

        Stage newStage = new Stage();
        newStage.setScene(sc);
        newStage.show();


    }

    Continent continent;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        createMapPane.getChildren().add(rectangleGroups);
        cbContinents.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println(newVal);
            mode=3;
            if(mode==3) {

                createMapPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        continent=new Continent();
                        double x= event.getX();
                        double y=event.getY();

                        for(Map.Entry<String, Territory> entry :gameMap.getTerritories().entrySet()) {

                            double _x = entry.getValue().getX();
                            double _y = entry.getValue().getY();





                            if (x >= _x && y >= _y &&
                                    x <= _x + 75 && y <= _y + 75){

                                continent.setName(newVal);
                                entry.getValue().setContinent(continent);
                                System.out.println(entry.getValue().getName()+entry.getValue().getContinent().getName());
                                if(!continentColor.containsKey(entry.getValue().getContinent().getName())){
                                    continentColor.put(entry.getValue().getContinent().getName(),generateRandomColor());
                                }
                                setContinentSquareProperties( entry.getValue().getX(),entry.getValue().getY(),continentColor.get(entry.getValue().getContinent().getName()));
                                setLabelProperties(entry);
                            }


                        }


                    }

                });
            }
        });

    }


    private static class Results {

        String continentName;
        int  controlNumber;

        public Results(String continentName, int controlNumber) {
            this.continentName = continentName;
            this.controlNumber = controlNumber;
        }
    }

}
