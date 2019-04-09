package RiskGame.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * This is the implementation of controller for player screen. It implements the functionality to edit
 * player details. *
 *
 * @author Sudhanva Muralidhar
 * @version v1.0.0
 */
public class EditPlayerDetailsController implements Initializable {

    @FXML
    private ChoiceBox<Integer> cbSelectPlayers,cbNoOfComputerPlayers;
    @FXML
    private Button btnNext;
    @FXML
    private Hyperlink hyperBack;

    private ObservableList noOfHumanPlayers = FXCollections.observableArrayList();
    private ObservableList noOfComputerPlayers = FXCollections.observableArrayList();
    public static int numberOfPlayers;
    public static int numberOfComputerPlayers;

    private File gameMapFile;

    /**
     *<p>
     * This method loads the choice box.
     *</p>
     */
    private void loadChoiceBox()
    {
        noOfHumanPlayers.removeAll(noOfHumanPlayers);
        noOfHumanPlayers.addAll(2,3,4,5,6,7,8);
        cbSelectPlayers.getItems().addAll(noOfHumanPlayers);
    }

    private void loadComputerChoiceBox()
    {
        noOfComputerPlayers.removeAll(noOfComputerPlayers);
        noOfComputerPlayers.addAll(2,3,4,5,6,7,8);
        cbNoOfComputerPlayers.getItems().addAll(noOfComputerPlayers);
    }

    private void AlertDialog(String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    /**
     *<p>
     * This method implements Next Button to move to edit player screen.
     *</p>
     * @param event ActionEvent
     * @throws IOException Input out exception
     */
    @FXML
    private void clickNext(ActionEvent event) throws IOException
    {

        if((cbNoOfComputerPlayers.getValue()+cbNoOfComputerPlayers.getValue())>8)
        {
            AlertDialog("Invalid Player Selection!");
            return;
        }

        if(cbSelectPlayers.getValue()!=null && cbNoOfComputerPlayers.getValue()!=null) {
            numberOfPlayers = cbSelectPlayers.getValue();
            numberOfComputerPlayers=cbNoOfComputerPlayers.getValue();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/editPlayerDetailsScreen2.fxml"));
            loader.load();
            PlayerDetailsController controller = loader.getController();
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
     * @throws IOException Input out exception
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


    /**
     * Initialize method, first method called when screen is loaded.
     * @param location URL Location
     * @param resources Associated resources.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        loadChoiceBox();
        loadComputerChoiceBox();
        cbSelectPlayers.getSelectionModel().selectFirst();
        cbNoOfComputerPlayers.getSelectionModel().selectFirst();
    }
}
