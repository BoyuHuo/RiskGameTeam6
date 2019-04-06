package RiskGame.model.entity;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * This class implements the functionality to list data for list view on the game screen
 *
 *
 * @author Karan Sharma
 * @version v1.0.0
 * *
 */
public class ListData{
        @FXML
        private HBox hBox;
        @FXML
        private Label label1;
        @FXML
        private Rectangle rectangle;
        @FXML
        private Label continentList;

        List<String> continents=new ArrayList<>();
        ObservableList<String> list = FXCollections.observableArrayList();

    /**
     * Constructor for the List Data class.
     */
    public ListData()
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/listViewItem.fxml"));
            fxmlLoader.setController(this);
            try
            {
                fxmlLoader.load();
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }

    /**
     * This method sets the players visual information.
     *
     * @param string sets the label with the text
     * @param color sets the color of the player text
     * @param activeStatue indicates if the player is active
     * @param continentList the list of continent that player control
     */
    public void setPlayerInfo(String string, Color color, boolean activeStatue, List<Continent> continentList) {
            label1.setText(string);
            if(activeStatue)
            label1.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
            rectangle.setFill(color);
            updateCotinentList(continentList);
    }

    /**
     *
     * This method updates the information for the continent list to string list.
     * @param continentLists sets the label with the passed string.
     */

    private void updateCotinentList(List<Continent> continentLists) {
        StringBuilder stringBuilder=new StringBuilder();
        for (int i=0;i<continentLists.size();i++) {
            continents.add(continentLists.get(i).getName());

            if(i==continentLists.size()-1){
                stringBuilder.append(continentLists.get(i).getName());
            }
            else{
                stringBuilder.append(continentLists.get(i).getName()+",");
            }
        }
        continentList.setText(stringBuilder.toString());
    }

    /**
     *
     * This method sets the information for the continent list.
     * @param string sets the label with the passed string.
     * @param color sets the color of the rectangle
     */

    public void setInfo(String string, Color color) {
            label1.setText(string);

            rectangle.setFill(color);
        }

    /**
     * This method sets the cards visual information.
     *
     * @param string sets the label with the text
     */
    public void setCardInfo(String string) {
        label1.setText(string);

    }

    /**
     * This method returns the horizontal box present in the layout.
     * @return horizontal box
     */
    public HBox getBox()
        {
            return hBox;
        }
    }