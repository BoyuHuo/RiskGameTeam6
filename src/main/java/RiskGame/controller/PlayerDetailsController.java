package RiskGame.controller;

import RiskGame.model.entity.Player;
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
 * @version v1.0.0
 */
public class PlayerDetailsController implements Initializable {

    @FXML
    TextField txtPlayer1,txtPlayer2,txtPlayer3,txtPlayer4,txtPlayer5,txtPlayer6,txtPlayer7,txtPlayer8;

    @FXML
    TextField txtComp1,txtComp2,txtComp3,txtComp4,txtComp5,txtComp6,txtComp7,txtComp8;

    @FXML
    ChoiceBox<String> cbComp1,cbComp2,cbComp3,cbComp4,cbComp5,cbComp6,cbComp7,cbComp8;

    @FXML
    Label lblPlayer1,lblPlayer2,lblPlayer3,lblPlayer4,lblPlayer5,lblPlayer6,lblPlayer7,lblPlayer8;

    @FXML
    Label lblComp1,lblComp2,lblComp3,lblComp4,lblComp5,lblComp6,lblComp7,lblComp8;

    @FXML
    Button btnBack, btnSave;

    private EditPlayerDetailsController playerDetails = new EditPlayerDetailsController();
    private ArrayList<String> playerName = new ArrayList();
    private HashMap<String, Player> playerList=new HashMap<>();
    private Player player;
    private Scene newGameSceenScene;
    private File gameMapFile;

    EditPlayerDetailsController editPlayer = new EditPlayerDetailsController();
    private ObservableList playerBehaviour = FXCollections.observableArrayList();
    /**
     *<p>
     * This method implements Save Button to save player details.
     *</p>
     * @param event ActionEvent
     * @throws IOException input output exception
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
                    playerName.add(txtPlayer1.getText());
                    playerName.add(txtPlayer2.getText());
                    break;

            case 3: playerName.add(txtPlayer1.getText());
                    playerName.add(txtPlayer2.getText());
                    playerName.add(txtPlayer3.getText());
                    createPlayer(txtPlayer1.getText());
                    createPlayer(txtPlayer2.getText());
                    createPlayer(txtPlayer3.getText());
                    break;

            case 4: playerName.add(txtPlayer1.getText());
                    playerName.add(txtPlayer2.getText());
                    playerName.add(txtPlayer3.getText());
                    playerName.add(txtPlayer4.getText());
                    createPlayer(txtPlayer1.getText());
                    createPlayer(txtPlayer2.getText());
                    createPlayer(txtPlayer3.getText());
                    createPlayer(txtPlayer4.getText());
                    break;

            case 5: playerName.add(txtPlayer1.getText());
                    playerName.add(txtPlayer2.getText());
                    playerName.add(txtPlayer3.getText());
                    playerName.add(txtPlayer4.getText());
                    playerName.add(txtPlayer5.getText());
                    createPlayer(txtPlayer1.getText());
                    createPlayer(txtPlayer2.getText());
                    createPlayer(txtPlayer3.getText());
                    createPlayer(txtPlayer4.getText());
                    createPlayer(txtPlayer5.getText());
                    break;

            case 6: playerName.add(txtPlayer1.getText());
                    playerName.add(txtPlayer2.getText());
                    playerName.add(txtPlayer3.getText());
                    playerName.add(txtPlayer4.getText());
                    playerName.add(txtPlayer5.getText());
                    playerName.add(txtPlayer6.getText());
                    createPlayer(txtPlayer1.getText());
                    createPlayer(txtPlayer2.getText());
                    createPlayer(txtPlayer3.getText());
                    createPlayer(txtPlayer4.getText());
                    createPlayer(txtPlayer5.getText());
                    createPlayer(txtPlayer6.getText());
                break;

            case 7: playerName.add(txtPlayer1.getText());
                    playerName.add(txtPlayer2.getText());
                    playerName.add(txtPlayer3.getText());
                    playerName.add(txtPlayer4.getText());
                    playerName.add(txtPlayer5.getText());
                    playerName.add(txtPlayer6.getText());
                    playerName.add(txtPlayer7.getText());
                    createPlayer(txtPlayer1.getText());
                    createPlayer(txtPlayer2.getText());
                    createPlayer(txtPlayer3.getText());
                    createPlayer(txtPlayer4.getText());
                    createPlayer(txtPlayer5.getText());
                    createPlayer(txtPlayer6.getText());
                    createPlayer(txtPlayer7.getText());
                    break;

            case 8: playerName.add(txtPlayer1.getText());
                    playerName.add(txtPlayer2.getText());
                    playerName.add(txtPlayer3.getText());
                    playerName.add(txtPlayer4.getText());
                    playerName.add(txtPlayer5.getText());
                    playerName.add(txtPlayer6.getText());
                    playerName.add(txtPlayer7.getText());
                    playerName.add(txtPlayer8.getText());
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

        switch (EditPlayerDetailsController.numberOfComputerPlayers)
        {
            case 1: playerName.add(txtComp1.getText());
                createPlayer(txtComp1.getText());
                break;

            case 2:
                createPlayer(txtComp1.getText());
                createPlayer(txtComp2.getText());
                playerName.add(txtComp1.getText());
                playerName.add(txtComp2.getText());
                break;

            case 3: playerName.add(txtComp1.getText());
                playerName.add(txtComp2.getText());
                playerName.add(txtComp3.getText());
                createPlayer(txtComp1.getText());
                createPlayer(txtComp2.getText());
                createPlayer(txtComp3.getText());
                break;

            case 4:
                playerName.add(txtComp1.getText());
                playerName.add(txtComp2.getText());
                playerName.add(txtComp3.getText());
                playerName.add(txtComp4.getText());
                createPlayer(txtComp1.getText());
                createPlayer(txtComp2.getText());
                createPlayer(txtComp3.getText());
                createPlayer(txtComp4.getText());
                break;

            case 5:
                playerName.add(txtComp1.getText());
                playerName.add(txtComp2.getText());
                playerName.add(txtComp3.getText());
                playerName.add(txtComp4.getText());
                playerName.add(txtComp5.getText());
                createPlayer(txtComp1.getText());
                createPlayer(txtComp2.getText());
                createPlayer(txtComp3.getText());
                createPlayer(txtComp4.getText());
                createPlayer(txtComp5.getText());
                break;

            case 6:
                playerName.add(txtComp1.getText());
                playerName.add(txtComp2.getText());
                playerName.add(txtComp3.getText());
                playerName.add(txtComp4.getText());
                playerName.add(txtComp5.getText());
                playerName.add(txtComp6.getText());

                createPlayer(txtComp1.getText());
                createPlayer(txtComp2.getText());
                createPlayer(txtComp3.getText());
                createPlayer(txtComp4.getText());
                createPlayer(txtComp5.getText());
                createPlayer(txtComp6.getText());
                break;

            case 7:
                playerName.add(txtComp1.getText());
                playerName.add(txtComp2.getText());
                playerName.add(txtComp3.getText());
                playerName.add(txtComp4.getText());
                playerName.add(txtComp5.getText());
                playerName.add(txtComp6.getText());
                playerName.add(txtComp7.getText());
                createPlayer(txtComp1.getText());
                createPlayer(txtComp2.getText());
                createPlayer(txtComp3.getText());
                createPlayer(txtComp4.getText());
                createPlayer(txtComp5.getText());
                createPlayer(txtComp6.getText());
                createPlayer(txtComp7.getText());
                break;

            case 8:
                playerName.add(txtComp1.getText());
                playerName.add(txtComp2.getText());
                playerName.add(txtComp3.getText());
                playerName.add(txtComp4.getText());
                playerName.add(txtComp5.getText());
                playerName.add(txtComp6.getText());
                playerName.add(txtComp7.getText());
                playerName.add(txtComp7.getText());
                createPlayer(txtComp1.getText());
                createPlayer(txtComp2.getText());
                createPlayer(txtComp3.getText());
                createPlayer(txtComp4.getText());
                createPlayer(txtComp5.getText());
                createPlayer(txtComp6.getText());
                createPlayer(txtComp7.getText());
                createPlayer(txtComp8.getText());
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
    private void loadPlayerBehaviour()
    {
        playerBehaviour.removeAll(playerBehaviour);
        playerBehaviour.addAll("Aggressive","Cheater","Benevolent","Random");
        cbComp1.getItems().addAll(playerBehaviour);
        cbComp2.getItems().addAll(playerBehaviour);
        cbComp3.getItems().addAll(playerBehaviour);
        cbComp4.getItems().addAll(playerBehaviour);
        cbComp5.getItems().addAll(playerBehaviour);
        cbComp6.getItems().addAll(playerBehaviour);
        cbComp7.getItems().addAll(playerBehaviour);
        cbComp8.getItems().addAll(playerBehaviour);


    }

    /**
     *<p>
     * This method loads the new game screen.
     *</p>
     * @throws IOException input output exception
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
     * @throws IOException input output exception
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


    /**
     * First method called when the pages is loaded.
     * @param location URL location
     * @param resources Associated resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        btnBack.setDisable(true);
        loadPlayerBehaviour();
        cbComp1.getSelectionModel().selectFirst();
        cbComp2.getSelectionModel().selectFirst();
        cbComp3.getSelectionModel().selectFirst();
        cbComp4.getSelectionModel().selectFirst();
        cbComp5.getSelectionModel().selectFirst();
        cbComp6.getSelectionModel().selectFirst();
        cbComp7.getSelectionModel().selectFirst();
        cbComp8.getSelectionModel().selectFirst();


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
                    break;


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
                break;
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
                break;
            case 4:
                txtPlayer5.setDisable(true);
                txtPlayer6.setDisable(true);
                txtPlayer7.setDisable(true);
                txtPlayer8.setDisable(true);

                lblPlayer5.setDisable(true);
                lblPlayer6.setDisable(true);
                lblPlayer7.setDisable(true);
                lblPlayer8.setDisable(true);
                break;
            case 5:
                txtPlayer6.setDisable(true);
                txtPlayer7.setDisable(true);
                txtPlayer8.setDisable(true);

                lblPlayer6.setDisable(true);
                lblPlayer7.setDisable(true);
                lblPlayer8.setDisable(true);
                break;
            case 6:
                txtPlayer7.setDisable(true);
                txtPlayer8.setDisable(true);

                lblPlayer7.setDisable(true);
                lblPlayer8.setDisable(true);
                break;
            case 7:
                txtPlayer8.setDisable(true);
                lblPlayer8.setDisable(true);
                break;
        }

        switch (EditPlayerDetailsController.numberOfComputerPlayers)
        {
            case 2:
                txtComp3.setDisable(true);
                txtComp4.setDisable(true);
                txtComp5.setDisable(true);
                txtComp6.setDisable(true);
                txtComp7.setDisable(true);
                txtComp8.setDisable(true);

                lblComp3.setDisable(true);
                lblComp4.setDisable(true);
                lblComp5.setDisable(true);
                lblComp6.setDisable(true);
                lblComp7.setDisable(true);
                lblComp8.setDisable(true);

                cbComp3.setDisable(true);
                cbComp4.setDisable(true);
                cbComp5.setDisable(true);
                cbComp6.setDisable(true);
                cbComp7.setDisable(true);
                cbComp8.setDisable(true);

                break;

            case 3:
                txtComp4.setDisable(true);
                txtComp5.setDisable(true);
                txtComp6.setDisable(true);
                txtComp7.setDisable(true);
                txtComp8.setDisable(true);

                lblComp4.setDisable(true);
                lblComp5.setDisable(true);
                lblComp6.setDisable(true);
                lblComp7.setDisable(true);
                lblComp8.setDisable(true);

                cbComp4.setDisable(true);
                cbComp5.setDisable(true);
                cbComp6.setDisable(true);
                cbComp7.setDisable(true);
                cbComp8.setDisable(true);

                break;
            case 4:
                txtComp5.setDisable(true);
                txtComp6.setDisable(true);
                txtComp7.setDisable(true);
                txtComp8.setDisable(true);

                lblComp5.setDisable(true);
                lblComp6.setDisable(true);
                lblComp7.setDisable(true);
                lblComp8.setDisable(true);

                cbComp5.setDisable(true);
                cbComp6.setDisable(true);
                cbComp7.setDisable(true);
                cbComp8.setDisable(true);
                break;

            case 5:
                txtComp6.setDisable(true);
                txtComp7.setDisable(true);
                txtComp8.setDisable(true);

                lblComp6.setDisable(true);
                lblComp7.setDisable(true);
                lblComp8.setDisable(true);

                cbComp6.setDisable(true);
                cbComp7.setDisable(true);
                cbComp8.setDisable(true);
                break;

            case 6:
                txtComp7.setDisable(true);
                txtComp8.setDisable(true);

                lblComp7.setDisable(true);
                lblComp8.setDisable(true);

                cbComp7.setDisable(true);
                cbComp8.setDisable(true);
                break;

            case 7:
                txtComp8.setDisable(true);
                lblComp8.setDisable(true);

                cbComp8.setDisable(true);
                break;
        }
    }

}
