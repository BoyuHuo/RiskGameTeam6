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

public class newGameScreenController implements Initializable {
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

    public void setPlayers(Map<String, Player> playersList ){
        this.players=players;
    }
    private File mapFile;

    @FXML
    private void mapFileChooser() throws IOException {
        FileChooser mapFileChooser = new FileChooser();
        mapFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".map", "*.map"));
        mapFile = mapFileChooser.showOpenDialog(null);
        if (mapFile != null) {

            MapManager mapManager = new MapManager();
            gameMap = mapManager.LoadMap(mapFile.toString());
            if(gameMap!=null){
                mapName.setText(mapFile.getName().toString());
            } else{
                showAlertDialog("Please load a valid map");

            }

        }
    }

    private void showAlertDialog(String alertType) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(alertType);
        alert.showAndWait();
    }

    public void setPlayersDetails(HashMap<String,Player> playersList,File gameMapfile) {

        players=playersList;
        this.mapFile=gameMapfile;

        for(Map.Entry<String, Player > entry: playersList.entrySet()){
            playersName.append(entry.getKey()+"\n");
        }

        playerList.setText(playersName.toString());
        mapName.setText(gameMapfile.getName());
        MapManager mapManager = new MapManager();
        gameMap = mapManager.LoadMap(mapFile.toString());
    }

    private void showMap(GameMap gameMap) throws IOException {

        Player p1=new Player();
        Player p2 = new Player();
        p1.setName("Peter");
        p2.setName("Lee");
        players=new HashMap<>();
        players.put(p1.getName(),p1);
        players.put(p2.getName(),p2);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/loadMapScreen.fxml"));
        loader.load();
        loadMapScreenController controller = loader.getController();
        controller.setMap(gameMap);
        createMapScene = new Scene(loader.getRoot(), 1000,600);

        Stage createMapSceneStage = (Stage) mapName.getScene().getWindow();
        createMapSceneStage.setScene(createMapScene);
        createMapSceneStage.show();
    }


    public void clickEditPlayerDetails(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/editPlayerDetailsScreen1.fxml"));
        loader.load();
        editPlayerDetailsController controller = loader.getController();
        controller.setMapDetails(mapFile);
        Scene editPlayerScene = new Scene(loader.getRoot(), 1000,600);

        Stage newGameScreenStage = (Stage)mapName.getScene().getWindow();
        newGameScreenStage.setScene(editPlayerScene);
        newGameScreenStage.show();


    }

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

    @FXML
    public void clickStartButton(ActionEvent event) throws IOException {

        GameManager.getInstance().setMap(this.gameMap);
        GameManager.getInstance().setPlayers(players);
        GameManager.getInstance().NewGame();

        Parent gameScreen = FXMLLoader.load(getClass().getResource("/view/gameScreen.fxml"));
        Scene gameScene = new Scene(gameScreen, 1000,900);
        Stage gameStage = (Stage) hyperLinkBack.getScene().getWindow();
        gameStage.setScene(gameScene);
        gameStage.show();
    }
}
