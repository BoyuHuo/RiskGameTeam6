package RiskGame.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TournamentMapSelection implements Initializable {

    @FXML
    ChoiceBox<Integer> cbMapSelection;

    @FXML
    Button btnSubmitMapSelection;

    @FXML
    Button btnloadMap1,btnloadMap2,btnloadMap3,btnloadMap4,btnloadMap5;

    @FXML
    TextField txtLoadMap1,txtLoadMap2,txtLoadMap3,txtLoadMap4,txtLoadMap5;

    @FXML
    Button btnNextMapSelection;

    @FXML
    AnchorPane anchorMapSelection,anchorChildMapSelection;

    @FXML
    Tab tbGameSelection,tbPlayerSelection;

    @FXML
    TabPane tpTournament;

    private ObservableList numberOfMaps = FXCollections.observableArrayList();
    private File mapFile1,mapFile2,mapFile3,mapFile4,mapFile5;
    private Scene playerSelectionScene;
    private boolean goingBack;


        /**
     * First method called when the pages is loaded.
     * @param location URL location
     * @param resources Associated resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        anchorChildMapSelection.setVisible(false);
        loadCbNumberOfMaps();
        disableChildPanelItems();
        tbPlayerSelection.setDisable(false);
        tbGameSelection.setDisable(false);
        tbPlayerSelection.setDisable(true);
        tbGameSelection.setDisable(true);
        cbMapSelection.getSelectionModel().selectFirst();
        goingBack=false;


    }

    private void disableChildPanelItems()
    {
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

    private void loadCbNumberOfMaps()
    {
        numberOfMaps.removeAll(numberOfMaps);
        numberOfMaps.addAll(1,2,3,4,5);
        cbMapSelection.getItems().addAll(numberOfMaps);
    }


    @FXML
    private void btnClickSubmit(ActionEvent event) throws IOException
    {
        anchorChildMapSelection.setVisible(true);
        disableChildPanelItems();
        int mapNumber=cbMapSelection.getValue();

        switch (mapNumber)
        {
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
    private void clickBtnLoadMap1(ActionEvent event) throws IOException{

        FileChooser mapFileChooser = new FileChooser();
        mapFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".map", "*.map"));
        mapFile1 = mapFileChooser.showOpenDialog(null);

        txtLoadMap1.setText(mapFile1.toString());

    }

    @FXML
    private void clickBtnLoadMap2(ActionEvent event) throws IOException{

        FileChooser mapFileChooser = new FileChooser();
        mapFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".map", "*.map"));
        mapFile2 = mapFileChooser.showOpenDialog(null);

        txtLoadMap2.setText(mapFile2.toString());

    }

    @FXML
    private void clickBtnLoadMap3(ActionEvent event) throws IOException{

        FileChooser mapFileChooser = new FileChooser();
        mapFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".map", "*.map"));
        mapFile3 = mapFileChooser.showOpenDialog(null);

        txtLoadMap3.setText(mapFile3.toString());

    }

    @FXML
    private void clickBtnLoadMap4(ActionEvent event) throws IOException{

        FileChooser mapFileChooser = new FileChooser();
        mapFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".map", "*.map"));
        mapFile4 = mapFileChooser.showOpenDialog(null);

        txtLoadMap4.setText(mapFile4.toString());

    }


    @FXML
    private void clickBtnLoadMap5(ActionEvent event) throws IOException{

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
    private void clickBtnNext(ActionEvent event) throws IOException{

      // goingBack=true;
       tpTournament.getSelectionModel().select(tbPlayerSelection);
    }




}
