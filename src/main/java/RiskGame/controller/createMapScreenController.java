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
    private Scene createMapScene;
    private AnchorPane myPanel;
    private Group rectangleGroups = new Group();
    private Rectangle square = null;

    private ArrayList<String> continentArray= new ArrayList<>();

    private ObservableList<String> continentsNames = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> cbContinents;

    @FXML
    private TextField authorTextArea;

    private int mode;

    private File mapFile;

    private Rectangle territorySquare = null ;

    private HashMap<String,Color> continentColor=new HashMap<String,Color>();


    private boolean isvalidlineStart, isValidlineEnd;

    private Territory sourceT;
    private Territory destT;
    private String sourceName;
    private String destName;
    private GameMap gameMap =new GameMap();
    private ArrayList<HashMap<String, Double>> countries = new ArrayList<>();

    private ArrayList<Territory> territoryArrayList = new ArrayList<>();

    private Line l1;

    /**
     *<p>
     * This method is responsible for loading an existing map from the user's system.
     *</p>
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
            cbContinents.getItems().clear();
            for(Map.Entry<String,Continent> entery: gameMap.getContinents().entrySet())
            cbContinents.getItems().add(entery.getKey());
            drawMap();
        }


    }


    @FXML
    private void deleteTerrotory(ActionEvent event) throws IOException {

        mode=4;
        if(mode==4){

            createMapPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    if(checkCoordinates(event.getX(), event.getY())) {

                        double x= event.getX();
                        double y=event.getY();


                        for(Map.Entry<String, Territory> entry :gameMap.getTerritories().entrySet()) {

                            double _x = entry.getValue().getX();
                            double _y = entry.getValue().getY();
                            if (x >= _x && y >= _y &&
                                    x <= _x + 55 && y <= _y + 40){
                                gameMap.removeTerrtory(entry.getKey());
                                drawMap();
                                break;
                            }


                        }

                    } else{
                        showAlertDialog("Click inside a valid terrotory!!");
                    }



                }
            });
        }

    }


    /**
     *<p>
     * This method is responsible for drawing loading an existing map from the user's system.
     *</p>
     */
    private void drawMap() {

        clearAllLabels();
        rectangleGroups.getChildren().clear();

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
    }

    private void clearAllLabels() {

        for (Node node : createMapPane.getChildren()) {
            if (node instanceof Label) {
                // clear
                ((Label)node).setText("");
            }
        }

    }

    /**
     *<p>
     * This method is responsible for generating random color.
     *</p>

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
     * This method is responsible for setting the properties pertaining to the square drawn for territories during create map screen.
     *</p>
     * @param starting_point_x x coordinate of mouse position
     * @param starting_point_y y coordinate of mouse position
     * @param square object of class rectangle
     */
    private void setTerrotorySquareProperties( double starting_point_x, double starting_point_y,Rectangle square)
    {

        square.setArcHeight(10);
        square.setArcWidth(10);
        square.setX( starting_point_x ) ;
        square.setY( starting_point_y ) ;
        square.setFill(Color.TRANSPARENT);
        square.setWidth( 55 ) ;
        square.setHeight( 40 ) ;
        square.setStroke( Color.BLACK ) ;


    }


    /**
     *<p>
     * This method is responsible for setting the properties pertaining to the square drawn fot continents during create map screen.
     *</p>
     * @param x x coordinate of mouse position
     * @param y y coordinate of mouse position
     * @param color object of class rectangle
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
        rectangle.setStroke( Color.TRANSPARENT ) ;
        rectangleGroups.getChildren().add( rectangle ) ;
    }

    /**
     *<p>
     * This method is responsible for setting the label properties.
     *</p>
     * @param entry x map entry list
     */
    private void setLabelProperties(Map.Entry<String, Territory> entry) {
        Label continentName = new Label();
        continentName.setLayoutX((entry.getValue().getX() + 5));
        continentName.setLayoutY((entry.getValue().getY() - 20));
        continentName.setText(entry.getValue().getName());
        continentName.setTextFill(Color.BLACK);
        continentName.setStyle("-fx-font-weight: bold;");
        createMapPane.getChildren().add(continentName);
    }

    /**
     *<p>
     * This method is responsible for implementing Depth First Search algorithm logic.
     *</p>
     * @param t object of the class Territory
     * @param connectedTerrs contains a list of connected territories
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
     * This method is responsible for implementing new game button.
     *</p>
     * @param event ActionEvent
     * @throws IOException
     */
    @FXML
    private void clickNewGameButton(ActionEvent event) throws IOException {
        Parent newGameScreen = FXMLLoader.load(getClass().getResource("/view/newGameScreen.fxml"));
        Scene newGameScene = new Scene(newGameScreen, 1000, 600);
        Stage newGameScreenStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        newGameScreenStage.setScene(newGameScene);
        newGameScreenStage.show();
    }


    /**
     *<p>
     * This method helps user to select maps from system.
     *</p>
     */
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

    /**
     *<p>
     * This method is responsible for implementing create map button.
     *</p>
     * @param event ActionEvent
     * @throws IOException
     */
    @FXML
    private void clickCreateMapButton(ActionEvent event) throws IOException {
        Parent createMap = FXMLLoader.load(getClass().getResource("/view/createMapScreen.fxml"));
        createMapScene = new Scene(createMap, 1000, 600);
        Stage createMapSceneStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        createMapSceneStage.setScene(createMapScene);
        createMapSceneStage.show();

    }

    /**
     *<p>
     * This method is responsible for implementing creating territory squares on the map screen.
     *</p>
     * @param event ActionEvent
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

    /**
     *<p>
     * This method is responsible for creating sqaure shape to create map.
     *</p>
     * @param starting_point_x x coordinate of mouse pointer.
     * @param starting_point_y  y coordinate of mouse pointer.
     * @param square Object of rectangle class.
     */
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

    /**
     *<p>
     * This method is responsible for connecting the territories by dragging mouse from one territory to other.
     *</p>
     * @param event ActionEvent
     */
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
                                x <= _x + 55 && y <= _y + 40){
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


    /**
     *<p>
     * This method is responsible for implementing back button.
     *</p>
     * @param event ActionEvent
     * @throws IOException
     */
    @FXML
    private void clickBack(ActionEvent event) throws IOException {
        Parent editPlayerScreen = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        Scene editPlayerScene = new Scene(editPlayerScreen, 1000, 600);
        Stage editPlayerStage = (Stage) backBt.getScene().getWindow();
        editPlayerStage.setScene(editPlayerScene);
        editPlayerStage.show();
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

    @FXML
    public void addContinents(ActionEvent event) {
        mode=3;
        if(mode==3)
        showContinentAlertDialog();
    }

    /**
     *<p>
     * This method is responsible for saving the created map.
     *</p>
     * @param event ActionEvent
     * @throws IOException throws input output exception
     */
    @FXML
    public void saveMap(ActionEvent event) throws IOException {

        if(authorTextArea.getText().equalsIgnoreCase("")){

            showAlertDialog("Enter author name");
        } else {
            MapManager mapManager=new MapManager();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Map");
            File file = fileChooser.showSaveDialog((Stage)backBt.getScene().getWindow());
            gameMap.setAuthor(authorTextArea.getText());
            gameMap.setImage("Dummy Image");
            if (file != null) {

                if(gameMap.getContinents().size()>0){

                    if(!mapManager.CreateMap(file.getPath(),gameMap)) {
                        showAlertDialog("Please create a valid map");
                    }
                }
                else {
                        showAlertDialog("Add atleast one continent!!");
                }
            }
        }
    }

    private boolean showInputTextDialog(Double x, Double y) {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter Terrotory Name");
        dialog.setHeaderText(null);
        dialog.setContentText("Terrotory Name");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {

            if (result.get().equalsIgnoreCase("")) {
                showAlertDialog("Enter Country name");
                return false;
            }

            Label continentName = new Label();
            continentName.setLayoutX((x + 5));
            continentName.setLayoutY((y - 20));
            continentName.setText(result.get());
            continentName.setTextFill(Color.BLACK);
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


    /**
     *<p>
     * This method implements a dialog box for user to input country details and control number.
     *</p>
     * @return returns the Result class object containing continent name and control number.
     */
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



    /**
     *<p>
     * This method gets the coordinates for the mouse click on the create map screen.
     *</p>
     * @param event ActionEvent
     *
     */
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

    /**
     *<p>
     * This is nested classes that is responsible for creating an object that contains
     * conitent name and control number.
     *</p>
     */
    private static class Results {

        String continentName;
        int  controlNumber;

        public Results(String continentName, int controlNumber) {
            this.continentName = continentName;
            this.controlNumber = controlNumber;
        }
    }

}
