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
    TextField txtLoadMap1, txtLoadMap2, txtLoadMap3, txtLoadMap4, txtLoadMap5;

    @FXML
    Button btnNextMapSelection, btnNextPlayerSelection;

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
    // private int tournamentScreenNumber=1;


    /**
     * First method called when the pages is loaded.
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


    }

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

    private void loadCbNumberOfMaps() {
        numberOfMaps.removeAll(numberOfMaps);
        numberOfMaps.addAll(1, 2, 3, 4, 5);
        cbMapSelection.getItems().addAll(numberOfMaps);
    }


    @FXML
    private void btnClickSubmit(ActionEvent event) throws IOException {
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
                break;


            case 2:
                btnloadMap1.setVisible(true);
                txtLoadMap1.setVisible(true);
                txtLoadMap1.setDisable(true);
                btnloadMap2.setVisible(true);
                txtLoadMap2.setVisible(true);
                txtLoadMap2.setDisable(true);

                btnloadMap3.setVisible(false);
                txtLoadMap3.setVisible(false);
                btnloadMap4.setVisible(false);
                txtLoadMap4.setVisible(false);
                btnloadMap5.setVisible(false);
                txtLoadMap5.setVisible(false);
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

                btnloadMap4.setVisible(false);
                txtLoadMap4.setVisible(false);
                btnloadMap5.setVisible(false);
                txtLoadMap5.setVisible(false);
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

                btnloadMap5.setVisible(false);
                txtLoadMap5.setVisible(false);
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
                break;
        }

    }

    @FXML
    private void clickBtnLoadMap1(ActionEvent event) throws IOException {

        FileChooser mapFileChooser = new FileChooser();
        mapFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".map", "*.map"));
        mapFile1 = mapFileChooser.showOpenDialog(null);

        txtLoadMap1.setText(mapFile1.toString());

        // else
        // {
        //  txtLoadMap1.setText("Error");
        //  }

    }

    @FXML
    private void clickBtnLoadMap2(ActionEvent event) throws IOException {

        FileChooser mapFileChooser = new FileChooser();
        mapFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".map", "*.map"));
        mapFile2 = mapFileChooser.showOpenDialog(null);

        txtLoadMap2.setText(mapFile2.toString());

    }

    @FXML
    private void clickBtnLoadMap3(ActionEvent event) throws IOException {

        FileChooser mapFileChooser = new FileChooser();
        mapFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".map", "*.map"));
        mapFile3 = mapFileChooser.showOpenDialog(null);

        txtLoadMap3.setText(mapFile3.toString());

    }

    @FXML
    private void clickBtnLoadMap4(ActionEvent event) throws IOException {

        FileChooser mapFileChooser = new FileChooser();
        mapFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".map", "*.map"));
        mapFile4 = mapFileChooser.showOpenDialog(null);

        txtLoadMap4.setText(mapFile4.toString());

    }


    @FXML
    private void clickBtnLoadMap5(ActionEvent event) throws IOException {

        FileChooser mapFileChooser = new FileChooser();
        mapFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".map", "*.map"));
        mapFile5 = mapFileChooser.showOpenDialog(null);

        txtLoadMap5.setText(mapFile5.toString());

    }


    private void AlertDialog(String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void clickBtnNext(ActionEvent event) throws IOException {

        tpTournament.getSelectionModel().select(tbPlayerSelection);
    }


    //Tab Player Selection Section

    private void loadCbPlayerBehaviour() {
        numberOfPlayers.removeAll(numberOfPlayers);
        numberOfPlayers.addAll(2, 3, 4);
        cbPlayerSelection.getItems().addAll(numberOfPlayers);
    }

    private void initializePlayerBehavior() {
        playerBehaviour.removeAll(playerBehaviour);
        playerBehaviour.addAll("Aggressive", "Benevolent", "Random", "Cheater");
        cbComp1PlayerSelection.getItems().addAll(playerBehaviour);
        cbComp2PlayerSelection.getItems().addAll(playerBehaviour);
        cbComp3PlayerSelection.getItems().addAll(playerBehaviour);
        cbComp4PlayerSelection.getItems().addAll(playerBehaviour);
    }

    @FXML
    private void btnSubmitPlayerSelection(ActionEvent event) throws IOException {


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
    }

    @FXML
    private void clickBtnPlayerNext(ActionEvent event) {

        tbGameSelection.setDisable(false);
        tpTournament.getSelectionModel().select(tbGameSelection);
    }

    @FXML
    private void clickBtnValidMap(ActionEvent event) {

        if (mapFile1 != null) {
            MapManager mapManager = new MapManager();
            gameMap1 = mapManager.loadMap(mapFile1.toString());
            if (gameMap1 == null) {
                lblValidLoadMap1.setText("INVALID");
                lblValidLoadMap1.setTextFill(Color.RED);
                return;
            }

        }
    }

    @FXML
    private void clickBtnPlay(ActionEvent event) throws IOException {
        Parent tournamentMode = FXMLLoader.load(getClass().getResource("/view/tournamentResultScreen.fxml"));
        tournamentScene = new Scene(tournamentMode, 1000,600);
        Stage createMapSceneStage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        createMapSceneStage.setScene(tournamentScene);
        createMapSceneStage.show();

    }

}



