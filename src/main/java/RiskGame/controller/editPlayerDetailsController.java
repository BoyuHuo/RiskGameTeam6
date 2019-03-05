package RiskGame.controller;

import RiskGame.model.entity.GameMap;
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
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * This is the implementation of controller for player screen. It implements the functionality to edit
 * player details. *
 *
 * @author Sudhanva Muralidhar
 * @version 1v.0.0
 */

public class editPlayerDetailsController implements Initializable {
    private ObservableList noOfPlayers = FXCollections.observableArrayList();
    @FXML
    private ChoiceBox<Integer> cbSelectPlayers;
    @FXML
    private Button btnNext;
    @FXML
    private Hyperlink hyperBack;
    public static int numberOfPlayers;
    private File gameMapFile;

    /**
     *<p>
     * This method loads the choice box.
     *</p>
     */
    @FXML
    private void loadChoiceBox()
    {
        noOfPlayers.removeAll(noOfPlayers);
        noOfPlayers.addAll(2,3,4,5,6,7,8);
        cbSelectPlayers.getItems().addAll(noOfPlayers);
    }


    /**
     *<p>
     * This method implements Next Button to move to edit player screen.
     *</p>
     * @param event ActionEvent
     * @throws IOException
     */
    @FXML
    private void clickNext(ActionEvent event) throws IOException
    {
        if(cbSelectPlayers.getValue()!=null) {
            numberOfPlayers = cbSelectPlayers.getValue();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/editPlayerDetailsScreen2.fxml"));
            loader.load();
            editPlayerDetailsController2 controller = loader.getController();
            controller.setMapDetails(gameMapFile);
            Scene editPlayerScene = new Scene(loader.getRoot(), 1000,600);

            Stage newGameScreenStage = (Stage)btnNext.getScene().getWindow();
            newGameScreenStage.setScene(editPlayerScene);
            newGameScreenStage.show();

        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Player Details");
            alert.setHeaderText(null);
            alert.setContentText("Select number of players!");
            alert.showAndWait();
        }
    }


    /**
     *<p>
     * This method implements Back Button to get to new game screen.
     *</p>
     * @param event ActionEvent
     * @throws IOException
     */
    @FXML
    private void clickHyperBack(ActionEvent event) throws IOException
    {
        Parent editPlayerScreen = FXMLLoader.load(getClass().getResource("/view/newGameScreen.fxml"));
        Scene editPlayerScene = new Scene(editPlayerScreen, 1000,600);
        Stage editPlayerStage = (Stage)btnNext.getScene().getWindow();
        editPlayerStage.setScene(editPlayerScene);
        editPlayerStage.show();
    }


    /**
     *<p>
     * This method sets map details.
     *</p>
     * @param  gameMapFile File class object
     */
    public  void setMapDetails(File gameMapFile)
    {
        this.gameMapFile=gameMapFile;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        loadChoiceBox();

    }
}
