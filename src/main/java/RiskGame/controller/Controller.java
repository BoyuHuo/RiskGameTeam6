package RiskGame.controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    private Label lblPath, lblCoordinates;
    Scene createMapScene;
    AnchorPane myPanel;

    /**
     * This is the implementation for New Game Button.
     *
     * Please note that I've used a depricated method call .toURL() below. Please provide your inputs in helping me
     * write this code better.
     * @param event
     * @throws IOException
     */
    @FXML
    private void clickNewGameButton(ActionEvent event) throws IOException
    {
        Parent newGameScreen = FXMLLoader.load(getClass().getResource("/view/newGameScreen.fxml"));
        Scene newGameScene = new Scene(newGameScreen, 610,400);
        Stage newGameScreenStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        newGameScreenStage.setScene(newGameScene);
        newGameScreenStage.show();
   }


   @FXML
   private void mapFileChooser()
   {
       FileChooser mapFileChooser = new FileChooser();
       mapFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".map", "*.map"));
       File mapFile = mapFileChooser.showOpenDialog(null);
       if(mapFile!=null)
       {
            lblPath.setText(mapFile.toString());
       }
       //return mapFile;
   }

   @FXML
   private void clickCreateMapButton(ActionEvent event) throws IOException
   {
       Parent createMap = FXMLLoader.load(getClass().getResource("/view/createMapScreen.fxml"));
       createMapScene = new Scene(createMap, 610,400);
       Stage createMapSceneStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
       createMapSceneStage.setScene(createMapScene);
       createMapSceneStage.show();

   }

    /**
     * This is a redundant class which prints the co-ordinates of the mouse clicked event.
     *
     * Whenever user clicks on the Build Map button a new blank stage opens up.
     * When you Click on this blank screen, in the previous screen (where you clicked the Build Map button) I'm displaying
     * the co-ordinates of the mouse click.
     * - Sudhanva Muralidhar
     * 19 February 2019
     *
     * @param event Ignore
     */

   @FXML
   public void getMouseCoordinates(ActionEvent event)
   {
       myPanel = new AnchorPane();
       Scene sc = new Scene(myPanel);
       sc.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
               if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {

                   lblCoordinates.setText("X:"+event.getSceneY()+" Y: "+event.getSceneY());
               }
           }});

       Stage newStage = new Stage();
       newStage.setScene(sc);
       newStage.show();



   }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
