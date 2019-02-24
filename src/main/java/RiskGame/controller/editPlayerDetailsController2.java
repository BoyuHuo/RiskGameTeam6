package RiskGame.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class editPlayerDetailsController2 implements Initializable {

    @FXML
    TextField txtPlayer1,txtPlayer2,txtPlayer3,txtPlayer4,txtPlayer5,txtPlayer6,txtPlayer7,txtPlayer8;
    @FXML
    Label lblPlayer1,lblPlayer2,lblPlayer3,lblPlayer4,lblPlayer5,lblPlayer6,lblPlayer7,lblPlayer8;
    @FXML
    Button btnBack, btnSave;

    editPlayerDetailsController playerDetails = new editPlayerDetailsController();
    ArrayList<String> playerName = new ArrayList();


    @FXML
    private void clickButtonSave(ActionEvent event) throws IOException
    {
        switch (playerDetails.numberOfPlayers)
        {
            case 1: playerName.add(0,txtPlayer1.getText());

            case 2: playerName.add(0,txtPlayer1.getText());
                    playerName.add(1,txtPlayer2.getText());

            case 3: playerName.add(0,txtPlayer1.getText());
                    playerName.add(1,txtPlayer2.getText());
                    playerName.add(2,txtPlayer3.getText());

            case 4: playerName.add(0,txtPlayer1.getText());
                    playerName.add(1,txtPlayer1.getText());
                    playerName.add(2,txtPlayer1.getText());
                    playerName.add(3,txtPlayer1.getText());

            case 5: playerName.add(0,txtPlayer1.getText());
                    playerName.add(1,txtPlayer1.getText());
                    playerName.add(2,txtPlayer1.getText());
                    playerName.add(3,txtPlayer1.getText());
                    playerName.add(4,txtPlayer1.getText());

            case 6: playerName.add(0,txtPlayer1.getText());
                    playerName.add(1,txtPlayer1.getText());
                    playerName.add(2,txtPlayer1.getText());
                    playerName.add(3,txtPlayer1.getText());
                    playerName.add(4,txtPlayer1.getText());
                    playerName.add(5,txtPlayer1.getText());

            case 7: playerName.add(0,txtPlayer1.getText());
                    playerName.add(1,txtPlayer1.getText());
                    playerName.add(2,txtPlayer1.getText());
                    playerName.add(3,txtPlayer1.getText());
                    playerName.add(4,txtPlayer1.getText());
                    playerName.add(5,txtPlayer1.getText());
                    playerName.add(6,txtPlayer1.getText());

            case 8: playerName.add(0,txtPlayer1.getText());
                    playerName.add(1,txtPlayer1.getText());
                    playerName.add(2,txtPlayer1.getText());
                    playerName.add(3,txtPlayer1.getText());
                    playerName.add(4,txtPlayer1.getText());
                    playerName.add(5,txtPlayer1.getText());
                    playerName.add(6,txtPlayer1.getText());
                    playerName.add(7,txtPlayer1.getText());
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Player Details");
        alert.setHeaderText(null);
        alert.setContentText("Player Details Saved!");
        alert.showAndWait();

        btnSave.setDisable(true);
        btnBack.setDisable(false);
    }

    @FXML
    private void clickButtonBack(ActionEvent event) throws IOException
    {
        Parent editPlayerScreen2 = FXMLLoader.load(getClass().getResource("/view/newGameScreen.fxml"));
        Scene editPlayerScene2 = new Scene(editPlayerScreen2, 610,430);
        Stage editPlayerStage2 = (Stage)btnSave.getScene().getWindow();
        editPlayerStage2.setScene(editPlayerScene2);
        editPlayerStage2.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        btnBack.setDisable(true);
        switch (playerDetails.numberOfPlayers)
        {
            case 1: txtPlayer2.setDisable(true);
                    txtPlayer3.setDisable(true);
                    txtPlayer4.setDisable(true);
                    txtPlayer5.setDisable(true);
                    txtPlayer6.setDisable(true);
                    txtPlayer7.setDisable(true);
                    txtPlayer8.setDisable(true);

                    lblPlayer2.setDisable(true);
                    lblPlayer3.setDisable(true);
                    lblPlayer4.setDisable(true);
                    lblPlayer5.setDisable(true);
                    lblPlayer6.setDisable(true);
                    lblPlayer7.setDisable(true);
                    lblPlayer8.setDisable(true);



            case 2:
                txtPlayer3.setDisable(true);
                txtPlayer4.setDisable(true);
                txtPlayer5.setDisable(true);
                txtPlayer6.setDisable(true);
                txtPlayer7.setDisable(true);
                txtPlayer8.setDisable(true);

                lblPlayer3.setDisable(true);
                lblPlayer4.setDisable(true);
                lblPlayer5.setDisable(true);
                lblPlayer6.setDisable(true);
                lblPlayer7.setDisable(true);
                lblPlayer8.setDisable(true);

            case 3:
                txtPlayer4.setDisable(true);
                txtPlayer5.setDisable(true);
                txtPlayer6.setDisable(true);
                txtPlayer7.setDisable(true);
                txtPlayer8.setDisable(true);

                lblPlayer4.setDisable(true);
                lblPlayer5.setDisable(true);
                lblPlayer6.setDisable(true);
                lblPlayer7.setDisable(true);
                lblPlayer8.setDisable(true);

            case 4:
                txtPlayer5.setDisable(true);
                txtPlayer6.setDisable(true);
                txtPlayer7.setDisable(true);
                txtPlayer8.setDisable(true);

                lblPlayer5.setDisable(true);
                lblPlayer6.setDisable(true);
                lblPlayer7.setDisable(true);
                lblPlayer8.setDisable(true);

            case 5:
                txtPlayer6.setDisable(true);
                txtPlayer7.setDisable(true);
                txtPlayer8.setDisable(true);

                lblPlayer6.setDisable(true);
                lblPlayer7.setDisable(true);
                lblPlayer8.setDisable(true);

            case 6:
                txtPlayer7.setDisable(true);
                txtPlayer8.setDisable(true);

                lblPlayer7.setDisable(true);
                lblPlayer8.setDisable(true);

            case 7:
                txtPlayer8.setDisable(true);
                lblPlayer8.setDisable(true);

        }
    }

}
