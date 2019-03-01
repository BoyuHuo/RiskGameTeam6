package RiskGame.controller;

import RiskGame.model.entity.Continent;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class createMapScreenController implements Initializable {


    @FXML
    private Label lblPath, lblCoordinates;
    @FXML
    AnchorPane createMapPane;
    @FXML
    private Button createTerrotoryBt, connectTerrotoryBt, createContinentsBt, saveBt, backBt;
    Scene createMapScene;
    AnchorPane myPanel;
    Group rectangleGroups = new Group();
    Rectangle square = null;

    ArrayList<Continent> continentArray= new ArrayList<>();
    private ObservableList continentsNames = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<Integer> cbContinents;

    int mode;

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

    ArrayList<HashMap<String, Double>> countries = new ArrayList<>();

    private void setSquareProperties(double starting_point_x, double starting_point_y, Rectangle square) {
        square.setX(starting_point_x);
        square.setY(starting_point_y);
        square.setWidth(75);
        square.setHeight(75);
        square.setFill(Color.TRANSPARENT); // set color to transparent
        square.setStroke(Color.BLACK);

        HashMap<String, Double> newHash = new HashMap<>();
        newHash.put("x", starting_point_x);
        newHash.put("y", starting_point_y);

        countries.add(newHash);

    }

    Line l1;

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


            //connectTerrotoryBt.setDisable(false);
            //createTerrotoryBt.setDisable(true);
        }

    }

    boolean isvalidlineStart, isValidlineEnd;

    @FXML
    public void connectTerrotory(ActionEvent event) {

        mode=1;

        if(mode==1) {
            createMapPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    lblCoordinates.setText("X:" + event.getX() + " Y: " + event.getY());
                    //lblCoordinates.setVisible(false);

                    l1 = new Line();

                    System.out.println("Pressed" + "X:" + event.getX() + " Y: " + event.getY());
                    l1.setStartX(event.getX());
                    l1.setStartY(event.getY());

                    isvalidlineStart = checkCoordinates(event.getX(), event.getY());
                    System.out.println("IsValid" + isvalidlineStart);
                    event.setDragDetect(true);

                }


            });


            createMapPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    //l1.setEndX(event.getX());
                    //l1.setEndY(event.getY());
                    //rectangleGroups.getChildren().add( l1 ) ;
                    //System.out.println("Drag"+"X:"+event.getX()+" Y: "+event.getY());


                }
            });


            createMapPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {


                    isValidlineEnd = checkCoordinates(event.getX(), event.getY());

                    System.out.println("IsValidend" + event.getX() + "sdfdsf" + event.getY());
                    if (isvalidlineStart && isValidlineEnd) {
                        l1.setEndX(event.getX());
                        l1.setEndY(event.getY());
                        rectangleGroups.getChildren().add(l1);


                    }

                    l1 = null;
                    event.setDragDetect(false);

                }
            });


            //connectTerrotoryBt.setDisable(true);
            //createContinentsBt.setDisable(false);
            //saveBt.setDisable(true);

        }
    }

    @FXML
    private void clickBack(ActionEvent event) throws IOException {
        Parent editPlayerScreen = FXMLLoader.load(getClass().getResource("/view/newGameScreen.fxml"));
        Scene editPlayerScene = new Scene(editPlayerScreen, 1000, 600);
        Stage editPlayerStage = (Stage) backBt.getScene().getWindow();
        editPlayerStage.setScene(editPlayerScene);
        editPlayerStage.show();
    }

    private boolean checkCoordinates(double x, double y) {

        for (int i = 0; i < countries.size(); i++) {
            double _x = countries.get(i).get("x");
            double _y = countries.get(i).get("y");

            if (x >= _x && y >= _y &&
                    x <= _x + 50 && y <= _y + 50)
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
    public void saveMap(ActionEvent event) {

    }

    private boolean showInputTextDialog(Double x, Double y) {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter Country Name");
        dialog.setHeaderText(null);
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {

            if (result.get().equalsIgnoreCase("")) {
                showAlertDialog("Country");
                return false;
            }
            Label label = new Label();
            label.setLayoutX((x + 25) - 12.5);
            label.setLayoutY((y + 25) - 12.5);
            label.setText(result.get());

            createMapPane.getChildren().add(label);

            return true;
        } else {
            return false;
        }

    }

    private void showAlertDialog(String alertType) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText("Enter "+alertType+"name");
        alert.showAndWait();
    }



    private boolean showContinentAlertDialog() {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter Country Name");
        dialog.setHeaderText(null);
        dialog.setContentText("Name:");


        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {

            if (result.get().equalsIgnoreCase("")) {
                showAlertDialog("Continent");
                return false;
            }
            Continent continent= new Continent();
            continent.setName(result.get());



            continentsNames.removeAll(continentsNames);
            continentArray.add(continent);
            continentsNames.add(result.get());
            cbContinents.getItems().addAll(continentsNames);
            return true;
        } else {
            return false;
        }
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*connectTerrotoryBt.setDisable(true);
        createContinentsBt.setDisable(true);
        saveBt.setDisable(true);*/
        createMapPane.getChildren().add(rectangleGroups);
    }
}
