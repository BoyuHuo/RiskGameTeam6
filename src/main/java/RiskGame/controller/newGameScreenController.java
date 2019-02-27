package RiskGame.controller;
import RiskGame.Main;
import RiskGame.model.entity.GameMap;
import RiskGame.model.service.imp.MapManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class newGameScreenController implements Initializable {

    @FXML
    private Label lblPath;
    private Scene createMapScene;

    @FXML
    private Hyperlink hyperLinkBack;



    @FXML
    private void mapFileChooser() throws IOException {
        FileChooser mapFileChooser = new FileChooser();
        mapFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".map", "*.map"));
        File mapFile = mapFileChooser.showOpenDialog(null);
        if(mapFile!=null)
        {
            lblPath.setText(mapFile.toString());
            MapManager mapManager=new MapManager();
            GameMap gameMap=mapManager.LoadMap(mapFile.toString());
         /*   Ga

            showMap(gameMap);*/

        }
    }

    private void showMap(GameMap gameMap) throws IOException {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/loadMapScreen.fxml"));
        loader.load();
        loadMapScreenController controller = loader.getController();
        controller.setMap(gameMap);
        createMapScene = new Scene(loader.getRoot(), 600,400);

        Stage createMapSceneStage = (Stage) lblPath.getScene().getWindow();
        createMapSceneStage.setScene(createMapScene);
        createMapSceneStage.show();
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

    public void clickEditPlayerDetails(ActionEvent event) throws IOException
    {
        Parent editPlayerScreen = FXMLLoader.load(getClass().getResource("/view/editPlayerDetailsScreen1.fxml"));
        Scene editPlayerScene = new Scene(editPlayerScreen, 610,400);
        Stage editPlayerStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        editPlayerStage.setScene(editPlayerScene);
        editPlayerStage.show();

    }
    @FXML
    private void hyperBack() throws IOException
    {
        Parent editPlayerScreen = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        Scene editPlayerScene = new Scene(editPlayerScreen, 610,400);
        Stage editPlayerStage = (Stage)hyperLinkBack.getScene().getWindow();
        editPlayerStage.setScene(editPlayerScene);
        editPlayerStage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {


    }
}
