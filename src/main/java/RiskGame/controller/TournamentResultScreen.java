package RiskGame.controller;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotResult;
import javafx.scene.control.TableView;

import java.net.URL;
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

    /**
     * First method called when the tournament result screen is loaded.
     *
     * @param location  URL location
     * @param resources Associated resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String[][] staffArray = {{" ","G1 ", "G2", "G3"},
                {"M1","a", "b", "c"},
                {"M2","d", "e", "f"}};
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(staffArray));
        data.remove(0);//remove titles from data
        for (int i = 0; i < staffArray[0].length; i++) {
            TableColumn tc = new TableColumn(staffArray[0][i]);
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


}

