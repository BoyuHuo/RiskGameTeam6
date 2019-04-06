package RiskGame.controller;
import javafx.application.Platform;
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
import java.util.ResourceBundle;

/**
 *
 * This is the implementation of controller for main screen. This screen is the first screen when user opens the game.
 * It contains implementation for the New Game, Load Game and exit buttons.
 *
 * @author Sudhanva Muralidhar
 * @version v1.0.0
 */
public class MainScreenController implements Initializable {

    @FXML
    private Button btnExit;
    private Scene createMapScene;
    private Scene tournamentScene;

    /**
     *<p>
     * This method navigates to the new Game screen.
     *</p>
     * @param event ActionEvent
     */
    @FXML
    private void clickNewGameButton(ActionEvent event) throws IOException
    {
        Parent newGameScreen = FXMLLoader.load(getClass().getResource("/view/newGameScreen.fxml"));
        Scene newGameScene = new Scene(newGameScreen, 1000,600);
        Stage newGameScreenStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        newGameScreenStage.setScene(newGameScene);
        newGameScreenStage.show();
   }

    /**
     * Method implements to exit the game safely.
     */
   @FXML
   private void clickButtonExit()
   {
       Platform.exit();
   }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }

    /**
     * This method implements the functionality to navigate the scene to create map screen
     * @param event Action Event
     * @throws IOException input output exception
     */

    @FXML
    private void clickCreateMapButton(ActionEvent event) throws IOException {
        Parent createMap = FXMLLoader.load(getClass().getResource("/view/createMapScreen.fxml"));
        createMapScene = new Scene(createMap, 1000,600);
        Stage createMapSceneStage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        createMapSceneStage.setScene(createMapScene);
        createMapSceneStage.show();
    }


    @FXML
    private void clickBtnTournamentMode(ActionEvent event) throws IOException {

        Parent tournamentMode = FXMLLoader.load(getClass().getResource("/view/tournamentMainScreen.fxml"));
        tournamentScene = new Scene(tournamentMode, 1000,600);
        Stage createMapSceneStage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        createMapSceneStage.setScene(tournamentScene);
        createMapSceneStage.show();
    }

}
