package RiskGame.model.entity;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;


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
        private ChoiceBox<String> cbContinentList;

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
     */
    public void setPlayerInfo(String string, Color color,boolean activeStatue) {
            label1.setText(string);
            if(activeStatue)
            label1.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
            rectangle.setFill(color);
            cbContinentList.setVisible(true);
           /* noOfPlayers.removeAll(noOfPlayers);
            noOfPlayers.addAll(2,3,4,5,6,7,8);
            continentList.getItems().addAll(noOfPlayers);*/
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

            cbContinentList.setVisible(false);
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