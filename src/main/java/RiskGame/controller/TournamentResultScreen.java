package RiskGame.controller;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotResult;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * This is the implementation for the Tournament result screen. It is responsible for displaying the
 * results of the tournament game.
 */

public class TournamentResultScreen implements Initializable {

    @FXML
    TableView<String[]> tableView;

    @FXML
    AnchorPane anchorPane;
    private String[][] gameResultArray;
    private int noOfGames;
    private ArrayList<String> mapNames;

    /**
     * First method called when the tournament result screen is loaded.
     *
     * @param location  URL location
     * @param resources Associated resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {



        gameResultArray=new String[TournamentScreenController.mapsName.size()+1][TournamentScreenController.noOfGames+1];
        intiliazeValues();
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(gameResultArray));
        data.remove(0);//remove titles from data
        for (int i = 0; i < gameResultArray[0].length; i++) {
            TableColumn tc = new TableColumn(gameResultArray[0][i]);
            final int colNo = i;
            tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            tc.setPrefWidth(90);
            tableView.getColumns().add(tc);
        }
        tableView.setItems(data);

    }



    private void intiliazeValues() {

        for (int i = 0; i <1 ; i++) {
            for (int j = 0; j <TournamentScreenController.noOfGames+1 ; j++) {
                if(j==0) {
                    gameResultArray[i][j] = " ";
                }
                else {
                    gameResultArray[i][j]="Game "+j;
                }
            }
        }

        for (int i = 1; i <TournamentScreenController.mapsName.size()+1 ; i++) {
            for (int j = 0; j <1 ; j++) {
                gameResultArray[i][j]=TournamentScreenController.mapsName.get(i-1);

            }
        }

        for (int i = 0; i <TournamentScreenController.mapsName.size() ; i++) {
            for (int j = 0; j <TournamentScreenController.noOfGames ; j++) {
                gameResultArray[i+1][j+1]=TournamentScreenController.result[i][j];

            }
        }

    }


}

