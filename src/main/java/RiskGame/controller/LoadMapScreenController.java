package RiskGame.controller;

import RiskGame.model.entity.GameMap;
import RiskGame.model.entity.Territory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class LoadMapScreenController implements Initializable {

    @FXML
    AnchorPane createMapPane;
    @FXML
    Button btBack;

    private GameMap gameMap;
    private Group rectangleGroups = new Group() ;
    private Rectangle square = null ;
    private Line l1;
    private HashMap<String,Color> continentColor=new HashMap<String,Color>();


     public void setMap(GameMap gameMap) {
        this.gameMap = gameMap;
        createMapPane.getChildren().add(rectangleGroups);



        for (Map.Entry<String, Territory> entry :gameMap.getTerritories().entrySet() ) {
            String key=entry.getKey();
            Territory territory=entry.getValue();
            territory.getContinent();
            square = new Rectangle();


            if(!continentColor.containsKey(territory.getContinent().getName())){
                continentColor.put(territory.getContinent().getName(),generateRandomColor());
            }



            setSquareProperties( territory.getX(),territory.getY(),square,continentColor.get(territory.getContinent().getName())) ;
            //connectNeighbours(territory);
            DFS(territory,new ArrayList<>());

            rectangleGroups.getChildren().add( square ) ;
            square=null;
        }
    }


    public void checkMap(GameMap map) {

        createMapPane.getChildren().add(rectangleGroups);

        for (Map.Entry<String, Territory> entry :map.getTerritories().entrySet() ) {
            String key=entry.getKey();
            Territory territory=entry.getValue();
            territory.getContinent();
            square = new Rectangle();

            if(!continentColor.containsKey(territory.getContinent().getName())){
                continentColor.put(territory.getContinent().getName(),generateRandomColor());
            }

            setSquareProperties( territory.getX(),territory.getY(),square,continentColor.get(territory.getContinent().getName())) ;
            DFS(territory,new ArrayList<>());

            rectangleGroups.getChildren().add( square ) ;
            square=null;
        }

    }

    private Color generateRandomColor() {
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);

        
        return Color.rgb(r,g,b);
    }

    @FXML
    private void clickBack(ActionEvent event) throws IOException
    {
        Parent editPlayerScreen = FXMLLoader.load(getClass().getResource("/view/newGameScreen.fxml"));
        Scene editPlayerScene = new Scene(editPlayerScreen, 1000,600);
        Stage editPlayerStage = (Stage)btBack.getScene().getWindow();
        editPlayerStage.setScene(editPlayerScene);
        editPlayerStage.show();
    }

    private void DFS(Territory t, ArrayList<String> connectedTerrs) {
        for (String key : t.getNeighbors().keySet()) {
            Territory neightbor = t.getNeighbors().get(key);


            if (!connectedTerrs.contains(neightbor.getName())) {
                connectedTerrs.add(neightbor.getName());
                l1 = new Line();
                l1.setStartX((t.getX())+25);
                l1.setStartY((t.getY())+25);

                l1.setEndX((neightbor.getX())+25);
                l1.setEndY((neightbor.getY())+25);
                rectangleGroups.getChildren().add( l1 ) ;
                DFS(neightbor, connectedTerrs);
            }
            l1=null;
        }
    }
    private void setSquareProperties( double starting_point_x, double starting_point_y,Rectangle square, Color color )
    {
        square.setX( starting_point_x ) ;
        square.setY( starting_point_y ) ;
        square.setWidth( 50 ) ;
        square.setHeight( 50 ) ;
        square.setFill( color ) ;
        square.setStroke( Color.BLACK ) ;


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
