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
 * @version 1v.0.0
 */


public class mainScreenController implements Initializable {

    @FXML
    private Button btnExit;


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

   @FXML
   private void clickButtonExit()
   {
       Platform.exit();
   }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }


}
