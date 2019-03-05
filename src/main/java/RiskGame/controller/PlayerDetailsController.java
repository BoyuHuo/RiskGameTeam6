package RiskGame.controller;

import RiskGame.model.entity.Player;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;

/**
 *
 * This is the implementation of controller for second player screen that implements the logic for player details
 * being saved.
 *
 * @author Sudhanva Muralidhar
 * @version v.0.0
 */
public class PlayerDetailsController implements Initializable {

    @FXML
    TextField txtPlayer1,txtPlayer2,txtPlayer3,txtPlayer4,txtPlayer5,txtPlayer6,txtPlayer7,txtPlayer8;
    @FXML
    Label lblPlayer1,lblPlayer2,lblPlayer3,lblPlayer4,lblPlayer5,lblPlayer6,lblPlayer7,lblPlayer8;
    @FXML
    Button btnBack, btnSave;

    private EditPlayerDetailsController playerDetails = new EditPlayerDetailsController();
    private ArrayList<String> playerName = new ArrayList();
    private HashMap<String, Player> playerList=new HashMap<>();
    private Player player;
    private Scene newGameSceenScene;
    private File gameMapFile;

    /**
     *<p>
     * This method implements Save Button to save player details.
     *</p>
     * @param event ActionEvent
     * @throws IOException
     */
    @FXML
    private void clickButtonSave(ActionEvent event) throws IOException
    {
        switch (EditPlayerDetailsController.numberOfPlayers)
        {
            case 1: playerName.add(0,txtPlayer1.getText());
                    createPlayer(txtPlayer1.getText());
                    break;

            case 2:
                    createPlayer(txtPlayer1.getText());
                    createPlayer(txtPlayer2.getText());
                    playerName.add(0,txtPlayer1.getText());
                    playerName.add(1,txtPlayer2.getText());
                    break;

            case 3: playerName.add(0,txtPlayer1.getText());
                    playerName.add(1,txtPlayer2.getText());
                    playerName.add(2,txtPlayer3.getText());
                    createPlayer(txtPlayer1.getText());
                    createPlayer(txtPlayer2.getText());
                    createPlayer(txtPlayer3.getText());
                    break;

            case 4: playerName.add(0,txtPlayer1.getText());
                    playerName.add(1,txtPlayer1.getText());
                    playerName.add(2,txtPlayer1.getText());
                    playerName.add(3,txtPlayer1.getText());
                    createPlayer(txtPlayer1.getText());
                    createPlayer(txtPlayer2.getText());
                    createPlayer(txtPlayer3.getText());
                    createPlayer(txtPlayer4.getText());
                    break;

            case 5: playerName.add(0,txtPlayer1.getText());
                    playerName.add(1,txtPlayer1.getText());
                    playerName.add(2,txtPlayer1.getText());
                    playerName.add(3,txtPlayer1.getText());
                    playerName.add(4,txtPlayer1.getText());
                    createPlayer(txtPlayer1.getText());
                    createPlayer(txtPlayer2.getText());
                    createPlayer(txtPlayer3.getText());
                    createPlayer(txtPlayer4.getText());
                    createPlayer(txtPlayer5.getText());
                    break;

            case 6: playerName.add(0,txtPlayer1.getText());
                    playerName.add(1,txtPlayer1.getText());
                    playerName.add(2,txtPlayer1.getText());
                    playerName.add(3,txtPlayer1.getText());
                    playerName.add(4,txtPlayer1.getText());
                    playerName.add(5,txtPlayer1.getText());
                    createPlayer(txtPlayer1.getText());
                    createPlayer(txtPlayer2.getText());
                    createPlayer(txtPlayer3.getText());
                    createPlayer(txtPlayer4.getText());
                    createPlayer(txtPlayer5.getText());
                    createPlayer(txtPlayer6.getText());
                break;

            case 7: playerName.add(0,txtPlayer1.getText());
                    playerName.add(1,txtPlayer1.getText());
                    playerName.add(2,txtPlayer1.getText());
                    playerName.add(3,txtPlayer1.getText());
                    playerName.add(4,txtPlayer1.getText());
                    playerName.add(5,txtPlayer1.getText());
                    playerName.add(6,txtPlayer1.getText());
                    createPlayer(txtPlayer1.getText());
                    createPlayer(txtPlayer2.getText());
                    createPlayer(txtPlayer3.getText());
                    createPlayer(txtPlayer4.getText());
                    createPlayer(txtPlayer5.getText());
                    createPlayer(txtPlayer6.getText());
                    createPlayer(txtPlayer7.getText());
                    break;

            case 8: playerName.add(0,txtPlayer1.getText());
                    playerName.add(1,txtPlayer1.getText());
                    playerName.add(2,txtPlayer1.getText());
                    playerName.add(3,txtPlayer1.getText());
                    playerName.add(4,txtPlayer1.getText());
                    playerName.add(5,txtPlayer1.getText());
                    playerName.add(6,txtPlayer1.getText());
                    playerName.add(7,txtPlayer1.getText());
                    createPlayer(txtPlayer1.getText());
                    createPlayer(txtPlayer2.getText());
                    createPlayer(txtPlayer3.getText());
                    createPlayer(txtPlayer4.getText());
                    createPlayer(txtPlayer5.getText());
                    createPlayer(txtPlayer6.getText());
                    createPlayer(txtPlayer7.getText());
                    createPlayer(txtPlayer8.getText());
                    break;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Player Details");
        alert.setHeaderText(null);
        alert.setContentText("Player Details Saved!");
        alert.showAndWait();

        btnSave.setDisable(true);
        btnBack.setDisable(false);

        showGameScreen();
    }

    /**
     *<p>
     * This method loads the new game screen.
     *</p>
     * @throws IOException
     */
    private void showGameScreen() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/newGameScreen.fxml"));
        loader.load();
        NewGameScreenController controller = loader.getController();
        controller.setPlayersDetails(playerList,gameMapFile);
        newGameSceenScene = new Scene(loader.getRoot(), 1000,600);

        Stage newGameScreenStage = (Stage)btnSave.getScene().getWindow();
        newGameScreenStage.setScene(newGameSceenScene);
        newGameScreenStage.show();
    }

    /**
     *<p>
     * This method sets the game map file.
     *</p>
     * @param gameMapFile File class object
     */
    public  void setMapDetails(File gameMapFile) {

        this.gameMapFile=gameMapFile;
    }

    /**
     *<p>
     * This method creates a player and populate the playerList.
     *</p>
     * @param text PLayer Name
     */
    private void createPlayer(String text) {
         player=new Player();
         player.setName(text);
         player.setColor(randomColor());
         playerList.put(text,player);

    }

    /**
     *<p>
     * This method generates random color.
     *</p>
     * @return color code
     */
    private String randomColor(){
        Random random = new Random();

        // create a big random number - maximum is ffffff (hex) = 16777215 (dez)
        int nextInt = random.nextInt(0xffffff + 1);

        // format it as hexadecimal string (with hashtag and leading zeros)
        String colorCode = String.format("#%06x", nextInt);

        return colorCode;
    }

    /**
     *<p>
     * This method implements back Button to go back to new game screen.
     *</p>
     * @param event ActionEvent
     * @throws IOException
     */
    @FXML
    private void clickButtonBack(ActionEvent event) throws IOException
    {
        Parent editPlayerScreen2 = FXMLLoader.load(getClass().getResource("/view/newGameScreen.fxml"));
        Scene editPlayerScene2 = new Scene(editPlayerScreen2, 1000,600);
        Stage editPlayerStage2 = (Stage)btnSave.getScene().getWindow();
        editPlayerStage2.setScene(editPlayerScene2);
        editPlayerStage2.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        btnBack.setDisable(true);
        switch (EditPlayerDetailsController.numberOfPlayers)
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
