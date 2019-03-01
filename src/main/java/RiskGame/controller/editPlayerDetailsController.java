package RiskGame.controller;

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
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class editPlayerDetailsController implements Initializable {
    private ObservableList noOfPlayers = FXCollections.observableArrayList();
    @FXML
    private ChoiceBox<Integer> cbSelectPlayers;
    @FXML
    private Button btnNext;
    @FXML
    private Hyperlink hyperBack;
    public static int numberOfPlayers;


    @FXML
    private void loadChoiceBox()
    {
        noOfPlayers.removeAll(noOfPlayers);
        noOfPlayers.addAll(1,2,3,4,5,6,7,8);
        cbSelectPlayers.getItems().addAll(noOfPlayers);
    }

    @FXML
    private void clickNext(ActionEvent event) throws IOException
    {
        if(cbSelectPlayers.getValue()!=null) {
            numberOfPlayers = cbSelectPlayers.getValue();
            Parent editPlayerScreen2 = FXMLLoader.load(getClass().getResource("/view/editPlayerDetailsScreen2.fxml"));
            Scene editPlayerScene2 = new Scene(editPlayerScreen2, 1000, 600);
            Stage editPlayerStage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            editPlayerStage2.setScene(editPlayerScene2);
            editPlayerStage2.show();
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
     *
     * Just an alert box to confirm the number of players selected by the user.
     *
     * However unable to invoke this function with btnNext.setOnAction(event1 -> confirmChoice(numberOfPlayers));
     * in the clickNext() function above. Need to check. Could be ignored.
     * @param numberOfPlayers
     *
     * Sudhanva Muralidhar
     * 02/23/2019
     */
    @FXML
    private void confirmChoice(Integer numberOfPlayers)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Number of Players: "+numberOfPlayers);
        alert.setContentText("Click OK to Proceed");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

        } else {

            alert.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        loadChoiceBox();

    }
}
