package RiskGame.controller;

import RiskGame.model.entity.GameMap;
import RiskGame.model.service.imp.MapManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * This is the implementation for controller of the Tournament mode screen. It includes all the functionality
 * that is required for the tournament phase of the risk game.
 *
 * @author Sudhanva Muralidhar
 * @version v1.0.0
 *
 */
public class TournamentScreenController implements Initializable {

    @FXML
    ChoiceBox<Integer> cbMapSelection, cbPlayerSelection;

    @FXML
    ChoiceBox<String> cbComp1PlayerSelection, cbComp2PlayerSelection, cbComp3PlayerSelection, cbComp4PlayerSelection;

    @FXML
    Button btnSubmitMapSelection, btnSubmitPlayerSelection;

    @FXML
    Button btnloadMap1, btnloadMap2, btnloadMap3, btnloadMap4, btnloadMap5;

    @FXML
    TextField txtLoadMap1, txtLoadMap2, txtLoadMap3, txtLoadMap4, txtLoadMap5,txtGGameSelection,txtDGameSelection;

    @FXML
    Button btnPlay,btnValidateMap;

    @FXML
    AnchorPane anchorMapSelection, anchorChildMapSelection, anchorPlayerSelection, anchorChildPlayerSelection;

    @FXML
    Tab tbGameSelection, tbPlayerSelection;

    @FXML
    TabPane tpTournament;

    @FXML
    Label lblComp3, lblComp4, lblValidLoadMap1, lblValidLoadMap2, lblValidLoadMap3, lblValidLoadMap4, lblValidLoadMap5;

    private ObservableList numberOfMaps = FXCollections.observableArrayList();
    private ObservableList numberOfPlayers = FXCollections.observableArrayList();
    private ObservableList playerBehaviour = FXCollections.observableArrayList();
    private Scene tournamentScene;

    private File mapFile1, mapFile2, mapFile3, mapFile4, mapFile5;
    private GameMap gameMap1, gameMap2, gameMap3, gameMap4, gameMap5;
    int mapNumber;
    boolean mapValid=false, playerScreenValid=false;


    /**
     * First method called when the tournament mode is loaded.
     *
     * @param location  URL location
     * @param resources Associated resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        gameMap1 = new GameMap();
        gameMap2 = new GameMap();
        gameMap3 = new GameMap();
        gameMap4 = new GameMap();
        gameMap5 = new GameMap();
        tbPlayerSelection.setDisable(false);
        anchorPlayerSelection.setDisable(false);
        anchorChildPlayerSelection.setVisible(false);
        loadCbPlayerBehaviour();
        anchorChildMapSelection.setVisible(false);
        loadCbNumberOfMaps();
        disableMapChildPanelItems();
        cbMapSelection.getSelectionModel().selectFirst();
        cbPlayerSelection.getSelectionModel().selectFirst();
        initializePlayerBehavior();
        btnPlay.setDisable(true);
        btnValidateMap.setDisable(true);
    }

    /**
     *
     * This is the function responsible for disabling the child pane on the Map Selection tab of
     * tournament mode.
     * */
    private void disableMapChildPanelItems() {
        btnloadMap1.setVisible(false);
        txtLoadMap1.setVisible(false);
        btnloadMap2.setVisible(false);
        txtLoadMap2.setVisible(false);
        btnloadMap3.setVisible(false);
        txtLoadMap3.setVisible(false);
        btnloadMap4.setVisible(false);
        txtLoadMap4.setVisible(false);
        btnloadMap5.setVisible(false);
        txtLoadMap5.setVisible(false);
        txtLoadMap1.setDisable(true);
    }

    /**
     * This method is responsible for populating the number of maps choice box
     * on the Map Selection tab.
     */
    private void loadCbNumberOfMaps() {
        numberOfMaps.removeAll(numberOfMaps);
        numberOfMaps.addAll(1, 2, 3, 4, 5);
        cbMapSelection.getItems().addAll(numberOfMaps);
    }


    /**
     * This method is responsible for displaying the map selection buttons based on the number of
     * maps selected by the player.
     */
    @FXML
    private void btnClickSubmit(){
        anchorChildMapSelection.setVisible(true);
        disableMapChildPanelItems();
        mapNumber = cbMapSelection.getValue();

        switch (mapNumber) {
            case 1:
                btnloadMap1.setVisible(true);
                txtLoadMap1.setVisible(true);
                txtLoadMap1.setDisable(true);

                btnloadMap2.setVisible(false);
                txtLoadMap2.setVisible(false);
                btnloadMap3.setVisible(false);
                txtLoadMap3.setVisible(false);
                btnloadMap4.setVisible(false);
                txtLoadMap4.setVisible(false);
                btnloadMap5.setVisible(false);
                txtLoadMap5.setVisible(false);
                lblValidLoadMap2.setVisible(false);
                lblValidLoadMap3.setVisible(false);
                lblValidLoadMap4.setVisible(false);
                lblValidLoadMap5.setVisible(false);
                break;


            case 2:
                btnloadMap1.setVisible(true);
                txtLoadMap1.setVisible(true);
                txtLoadMap1.setDisable(true);
                btnloadMap2.setVisible(true);
                txtLoadMap2.setVisible(true);
                txtLoadMap2.setDisable(true);
                lblValidLoadMap2.setVisible(true);

                btnloadMap3.setVisible(false);
                txtLoadMap3.setVisible(false);
                btnloadMap4.setVisible(false);
                txtLoadMap4.setVisible(false);
                btnloadMap5.setVisible(false);
                txtLoadMap5.setVisible(false);
                lblValidLoadMap3.setVisible(false);
                lblValidLoadMap4.setVisible(false);
                lblValidLoadMap5.setVisible(false);
                break;
            case 3:
                btnloadMap1.setVisible(true);
                txtLoadMap1.setVisible(true);
                txtLoadMap1.setDisable(true);
                btnloadMap2.setVisible(true);
                txtLoadMap2.setVisible(true);
                txtLoadMap2.setDisable(true);
                btnloadMap3.setVisible(true);
                txtLoadMap3.setVisible(true);
                txtLoadMap3.setDisable(true);
                lblValidLoadMap2.setVisible(true);
                lblValidLoadMap3.setVisible(true);

                btnloadMap4.setVisible(false);
                txtLoadMap4.setVisible(false);
                btnloadMap5.setVisible(false);
                txtLoadMap5.setVisible(false);
                lblValidLoadMap4.setVisible(false);
                lblValidLoadMap5.setVisible(false);
                break;
            case 4:
                btnloadMap1.setVisible(true);
                txtLoadMap1.setVisible(true);
                txtLoadMap1.setDisable(true);
                btnloadMap2.setVisible(true);
                txtLoadMap2.setVisible(true);
                txtLoadMap2.setDisable(true);
                btnloadMap3.setVisible(true);
                txtLoadMap3.setVisible(true);
                txtLoadMap3.setDisable(true);
                btnloadMap4.setVisible(true);
                txtLoadMap4.setVisible(true);
                txtLoadMap4.setDisable(true);
                lblValidLoadMap4.setVisible(true);

                btnloadMap5.setVisible(false);
                txtLoadMap5.setVisible(false);
                lblValidLoadMap5.setVisible(false);
                break;
            case 5:
                btnloadMap1.setVisible(true);
                txtLoadMap1.setVisible(true);
                txtLoadMap1.setDisable(true);
                btnloadMap2.setVisible(true);
                txtLoadMap2.setVisible(true);
                txtLoadMap2.setDisable(true);
                btnloadMap3.setVisible(true);
                txtLoadMap3.setVisible(true);
                txtLoadMap3.setDisable(true);
                btnloadMap4.setVisible(true);
                txtLoadMap4.setVisible(true);
                txtLoadMap4.setDisable(true);
                btnloadMap5.setVisible(true);
                txtLoadMap5.setVisible(true);
                txtLoadMap5.setDisable(true);
                lblValidLoadMap2.setVisible(true);
                lblValidLoadMap3.setVisible(true);
                lblValidLoadMap4.setVisible(true);
                lblValidLoadMap5.setVisible(true);
                break;
        }

    }

    /**
     * This method is resposible for opening the file chooser window for user to load a pre existing map from
     * the local device.
     */
    @FXML
    private void clickBtnLoadMap1(){

        FileChooser mapFileChooser = new FileChooser();
        mapFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".map", "*.map"));
        mapFile1 = mapFileChooser.showOpenDialog(null);
        txtLoadMap1.setText(mapFile1.toString());
        btnValidateMap.setDisable(false);
    }

    /**
     * This method is resposible for opening the file chooser window for user to load a pre existing map from
     * the local device.
     */
    @FXML
    private void clickBtnLoadMap2(ActionEvent event) throws IOException {

        FileChooser mapFileChooser = new FileChooser();
        mapFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".map", "*.map"));
        mapFile2 = mapFileChooser.showOpenDialog(null);

        txtLoadMap2.setText(mapFile2.toString());
        btnValidateMap.setDisable(false);
    }

    /**
     * This method is resposible for opening the file chooser window for user to load a pre existing map from
     * the local device.
     */
    @FXML
    private void clickBtnLoadMap3(ActionEvent event) throws IOException {

        FileChooser mapFileChooser = new FileChooser();
        mapFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".map", "*.map"));
        mapFile3 = mapFileChooser.showOpenDialog(null);

        txtLoadMap3.setText(mapFile3.toString());
        btnValidateMap.setDisable(false);
    }

    /**
     * This method is resposible for opening the file chooser window for user to load a pre existing map from
     * the local device.
     */
    @FXML
    private void clickBtnLoadMap4(ActionEvent event) throws IOException {

        FileChooser mapFileChooser = new FileChooser();
        mapFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".map", "*.map"));
        mapFile4 = mapFileChooser.showOpenDialog(null);

        txtLoadMap4.setText(mapFile4.toString());
        btnValidateMap.setDisable(false);
    }

    /**
     * This method is resposible for opening the file chooser window for user to load a pre existing map from
     * the local device.
     */
    @FXML
    private void clickBtnLoadMap5(ActionEvent event) throws IOException {

        FileChooser mapFileChooser = new FileChooser();
        mapFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".map", "*.map"));
        mapFile5 = mapFileChooser.showOpenDialog(null);

        txtLoadMap5.setText(mapFile5.toString());
        btnValidateMap.setDisable(false);
    }

    /**
     * Method responsible for creating the alert box.
     * @param message the message that needs to be shown the user.
     */
    private void AlertDialog(String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * This method is responsible for adding the behaviour of players to the choice box.
     */
    private void loadCbPlayerBehaviour() {
        numberOfPlayers.removeAll(numberOfPlayers);
        numberOfPlayers.addAll(2, 3, 4);
        cbPlayerSelection.getItems().addAll(numberOfPlayers);
    }

    /**
     * Responsible for loading the choice boxes with the different behavior of the computer player.
     */
    private void initializePlayerBehavior() {
        playerBehaviour.removeAll(playerBehaviour);
        playerBehaviour.addAll("Aggressive", "Benevolent", "Random", "Cheater");
        cbComp1PlayerSelection.getItems().addAll(playerBehaviour);
        cbComp2PlayerSelection.getItems().addAll(playerBehaviour);
        cbComp3PlayerSelection.getItems().addAll(playerBehaviour);
        cbComp4PlayerSelection.getItems().addAll(playerBehaviour);

        cbComp1PlayerSelection.getSelectionModel().selectFirst();
        cbComp2PlayerSelection.getSelectionModel().selectFirst();
        cbComp3PlayerSelection.getSelectionModel().selectFirst();
        cbComp4PlayerSelection.getSelectionModel().selectFirst();
    }

    /**
     *Responsible to display the panel for the selecting the computer player behaviors.
     */
    @FXML
    private void clickBtnSubmitPlayerSelection() {


        int playerSelected = cbPlayerSelection.getValue();

        switch (playerSelected) {
            case 2:
                anchorChildPlayerSelection.setVisible(true);
                cbComp1PlayerSelection.setVisible(true);
                cbComp2PlayerSelection.setVisible(true);

                cbComp3PlayerSelection.setVisible(false);
                cbComp4PlayerSelection.setVisible(false);
                lblComp3.setVisible(false);
                lblComp4.setVisible(false);

                break;

            case 3:
                anchorChildPlayerSelection.setVisible(true);
                cbComp1PlayerSelection.setVisible(true);
                cbComp2PlayerSelection.setVisible(true);
                cbComp3PlayerSelection.setVisible(true);

                cbComp4PlayerSelection.setVisible(false);
                lblComp3.setVisible(true);
                lblComp4.setVisible(false);
                break;

            case 4:
                anchorChildPlayerSelection.setVisible(true);
                cbComp1PlayerSelection.setVisible(true);
                cbComp2PlayerSelection.setVisible(true);
                cbComp3PlayerSelection.setVisible(true);
                cbComp4PlayerSelection.setVisible(true);
                lblComp3.setVisible(true);
                lblComp4.setVisible(true);
        }

        playerScreenValid=true;
    }

    /**
     *This is method is responsible for validating the maps that are selected by the user
     * to play the game. The validity is indicated with either VALID(in green) or INVALID(in RED).
     */
    @FXML
    private void clickBtnValidMap() {

        MapManager mapManager = new MapManager();
        if (mapFile1 != null) {

            gameMap1 = mapManager.loadMap(mapFile1.toString());
            if (gameMap1 == null) {
                lblValidLoadMap1.setText("INVALID");
                lblValidLoadMap1.setTextFill(Color.RED);

            } else {
                lblValidLoadMap1.setText("VALID");
                lblValidLoadMap1.setTextFill(Color.GREEN);
            }
        }

        if (mapFile2 != null) {
            gameMap2 = mapManager.loadMap(mapFile2.toString());
            if (gameMap2 == null) {
                lblValidLoadMap2.setText("INVALID");
                lblValidLoadMap2.setTextFill(Color.RED);

            } else {
                lblValidLoadMap2.setText("VALID");
                lblValidLoadMap2.setTextFill(Color.GREEN);
            }
        }

        if (mapFile3 != null) {
            gameMap3 = mapManager.loadMap(mapFile3.toString());
            if (gameMap3 == null) {
                lblValidLoadMap3.setText("INVALID");
                lblValidLoadMap3.setTextFill(Color.RED);

            } else {
                lblValidLoadMap3.setText("VALID");
                lblValidLoadMap3.setTextFill(Color.GREEN);
            }
        }

        if (mapFile4 != null) {
            gameMap4 = mapManager.loadMap(mapFile4.toString());
            if (gameMap4 == null) {
                lblValidLoadMap4.setText("INVALID");
                lblValidLoadMap4.setTextFill(Color.RED);

            } else {
                lblValidLoadMap4.setText("VALID");
                lblValidLoadMap4.setTextFill(Color.GREEN);
            }
        }
        if (mapFile5 != null) {
            gameMap5 = mapManager.loadMap(mapFile5.toString());
            if (gameMap1 == null) {
                lblValidLoadMap5.setText("INVALID");
                lblValidLoadMap5.setTextFill(Color.RED);

            } else {
                lblValidLoadMap5.setText("VALID");
                lblValidLoadMap5.setTextFill(Color.GREEN);
            }
        }

        mapValid=true;
    }

    /**
     * This is method is responsible for starting the tournament and displaying the result of the game in the new
     * page.
     * @param event takes in the event of button click.
     * @throws IOException Input Output exception
     */
    @FXML
    private void clickBtnPlay(ActionEvent event) throws IOException {

        Parent tournamentMode = FXMLLoader.load(getClass().getResource("/view/tournamentResultScreen.fxml"));
        tournamentScene = new Scene(tournamentMode, 1000,600);
        Stage createMapSceneStage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        createMapSceneStage.setScene(tournamentScene);
        createMapSceneStage.show();

    }

    /**
     * This method is responsible for validating the game configurations selected by the user to
     * play the tournament before the game could be started. Only if its valid player is allowed to start the
     * tournament game play.
     */
    @FXML
    private void clickBtnSubmitGameConfig()
    {

        if(txtDGameSelection.getText().isEmpty() || txtGGameSelection.getText().isEmpty())
        {
            AlertDialog("Game Selection Configuration Empty!");
            return;
        }
        if((Integer.parseInt(txtGGameSelection.getText())>5 || Integer.parseInt(txtGGameSelection.getText())<0)
                && (Integer.parseInt(txtDGameSelection.getText())>50 || (Integer.parseInt(txtDGameSelection.getText())<10)))
        {
            AlertDialog("Game Selection Configuration Invalid!");
            return;
        }
        if(mapValid)
        {
            if(playerScreenValid) {
                if ((txtGGameSelection.getText().isEmpty()) && (txtDGameSelection.getText().isEmpty())) {
                    AlertDialog("Game Selection Configuration not completed!");
                } else {
                    btnPlay.setDisable(false);
                }
            }
            else
            {
                AlertDialog("Player Behaviour Selection not completed!");
            }
        }
        else
        {
            AlertDialog("Map Validation not done!");
        }
    }

}



