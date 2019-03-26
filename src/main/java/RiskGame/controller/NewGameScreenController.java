package RiskGame.controller;

import RiskGame.model.entity.GameMap;
import RiskGame.model.entity.Player;
import RiskGame.model.service.imp.GameManager;
import RiskGame.model.service.imp.MapManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 *
 * This class contains implementation for the load map, create map, edit player details and start game buttons functionality.
 * It contains all the methods that are used to load a map from local system, or create a map using the game screen and start
 * playing the game.
 *
 * @author Karan Sharma
 * @version v1.0.0
 */

public class NewGameScreenController implements Initializable {
    @FXML
    private Text mapName;
    private Scene createMapScene;

    @FXML
    private Hyperlink hyperLinkBack;


    @FXML
    private Text playerList;

    private GameMap gameMap = new GameMap();
    private Map<String, Player> players = new HashMap<>();
    private StringBuilder playersName=new StringBuilder();
    private File mapFile;


    /**
     *<p>
     * This method sets the player.
     *</p>
     * @param playersList list of player.
     */
    public void setPlayers(Map<String, Player> playersList ){
        this.players=players;
    }

    /**
     *<p>
     * This method allows user to load map from the local device.
     *</p>
     * @throws IOException
     */
    @FXML
    private void mapFileChooser() throws IOException {
        FileChooser mapFileChooser = new FileChooser();
        mapFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".map", "*.map"));
        mapFile = mapFileChooser.showOpenDialog(null);
        if (mapFile != null) {

            MapManager mapManager = new MapManager();
            gameMap = mapManager.loadMap(mapFile.toString());
            if(gameMap!=null){
                mapName.setText(mapFile.getName().toString());
            } else{
                showAlertDialog("Please load a valid map");

            }

        }
    }

    /**
     * This method implements the alert box.
     * @param alertType sets the text for the alert box.
     */
    private void showAlertDialog(String alertType) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(alertType);
        alert.showAndWait();
    }

    /**
     *<p>
     * This method sets the player details.
     *</p>
     * @param playersList list of playera.
     * @param gameMapfile File class object
     */
    public void setPlayersDetails(HashMap<String,Player> playersList,File gameMapfile) {

        players=playersList;
        this.mapFile=gameMapfile;

        for(Map.Entry<String, Player > entry: playersList.entrySet()){
            playersName.append(entry.getKey()+"\n");
        }

        playerList.setText(playersName.toString());
        mapName.setText(gameMapfile.getName());
        MapManager mapManager = new MapManager();
        gameMap = mapManager.loadMap(mapFile.toString());
    }

    /**
     * This method implements the functionality to navigate to the edit player details screen.
     * @param event Action Even
     * @throws IOException input output exception
     */
    public void clickEditPlayerDetails(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/editPlayerDetailsScreen1.fxml"));
        loader.load();
        EditPlayerDetailsController controller = loader.getController();
        controller.setMapDetails(mapFile);
        Scene editPlayerScene = new Scene(loader.getRoot(), 1000,600);

        Stage newGameScreenStage = (Stage)mapName.getScene().getWindow();
        newGameScreenStage.setScene(editPlayerScene);
        newGameScreenStage.show();
    }


    /**
     *<p>
     * This method implements the back functionality to navigate user back to the main screen.
     *</p>
     * @throws IOException
     */
    @FXML
    private void hyperBack() throws IOException {
        Parent editPlayerScreen = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        Scene editPlayerScene = new Scene(editPlayerScreen, 1000,600);
        Stage editPlayerStage = (Stage)hyperLinkBack.getScene().getWindow();
        editPlayerStage.setScene(editPlayerScene);
        editPlayerStage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     *<p>
     * This method implements the functionality for the start button, navigates user to the game screen.
     *</p>
     * @param event Action Event
     * @throws IOException throws input output exceptions
     */
    @FXML
    public void clickStartButton(ActionEvent event) throws IOException {

        GameManager.getInstance().setMap(this.gameMap);
        GameManager.getInstance().setPlayers(players);
        GameManager.getInstance().newGame();

        Parent gameScreen = FXMLLoader.load(getClass().getResource("/view/gameScreen.fxml"));
        Scene gameScene = new Scene(gameScreen, 1000,900);
        Stage gameStage = (Stage) hyperLinkBack.getScene().getWindow();
        gameStage.setScene(gameScene);
        gameStage.show();
    }
}
