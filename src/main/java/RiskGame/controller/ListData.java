package RiskGame.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class ListData{
        @FXML
        private HBox hBox;
        @FXML
        private Label label1;
        @FXML
        private Rectangle rectangle;

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

        public void setPlayerInfo(String string, Color color,boolean activeStatue) {
            label1.setText(string);
            if(activeStatue)
            label1.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
            rectangle.setFill(color);
        }

        public void setInfo(String string, Color color) {
            label1.setText(string);

            rectangle.setFill(color);
        }

        public HBox getBox()
        {
            return hBox;
        }
    }