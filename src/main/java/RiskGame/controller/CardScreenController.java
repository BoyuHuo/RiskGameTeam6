package RiskGame.controller;

import RiskGame.model.entity.Card;
import RiskGame.model.entity.CardType;
import RiskGame.model.entity.ListData;
import RiskGame.model.service.imp.GameManager;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.io.Console;
import java.net.URL;
import java.util.*;
import java.awt.Checkbox;

/**
 *
 * This is the implementation of the create map screen controller. It implements all the functionality.
 * that allows user to interact the card related function.
 *
 * @author Karan Sharma
 * @version v1.0.0
 */

public class CardScreenController  implements Initializable {

    @FXML
    private ListView<String> cardList;
    ArrayList<CardType> selectedCards=new ArrayList<>();
    private final ObservableList<String> cardData= FXCollections.observableArrayList();


    /**
     * This implements the functionality for Card button click.
     */

    @FXML
    private void cardButtonOnClicked() {


        if(!GameManager.getInstance().getActivePlayer().cardTrade(selectedCards,GameManager.getInstance().getActivePlayer())){

            showAlertDialog("Not a valid set");
        } else{
            Stage stage = (Stage) cardList.getScene().getWindow();
            stage.close();
        }


    }


    /**
     * <p>
     * This is a generic method used for alert dialog boxes.
     * </p>
     *
     * @param message message that needs to be shown in the alert box.
     */
    private void showAlertDialog(String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Initialize method, first method called when screen is loaded.
     * @param location URL Location
     * @param resources Associated resources.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        populateList();
        initializeList();

    }

    StringBuilder builder;

    /**
     * This method is responsible for populating values corresponding to type of cards.
     */
    private void populateList() {

        HashMap<CardType,Integer> playerCardList=GameManager.getInstance().getActivePlayer().getCards();

        if(playerCardList!=null){

        for (Map.Entry<CardType,Integer> card: playerCardList.entrySet() ) {

            if(card.getKey()==CardType.INFANTRY){
                addStringDataToList("Infantry",card.getValue());
            }

            if(card.getKey()==CardType.CAVALRY){
                addStringDataToList("Cavalry",card.getValue());
            }

            if(card.getKey()==CardType.ARTILLERY){
                addStringDataToList("Artillery",card.getValue());
            }

        }
        }

    }

    /**
     *
     * This is method is responsible for adding card details to the observable list.
     * @param cardName Name of the card
     * @param count card count
     */
    public void addStringDataToList(String cardName, int count){
        for (int i = 0; i <count ; i++) {
            cardData.add(cardName+" "+(i+1));
        }
    }

    /**
     * This method is responsible for initializing the the card list.
     */
    private void initializeList() {

        cardList.setItems(cardData);
        cardList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        cardList.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {

                    ObservableList<String> selectedItems = cardList.getSelectionModel().getSelectedItems();

                     builder = new StringBuilder("Selected items :");
                     selectedCards=new ArrayList<>();
                    for (String name : selectedItems) {

                        builder.append(name + "\n");
                        if(name!=null) {
                            if (name.toLowerCase().contains("cavalry"))
                                selectedCards.add(CardType.CAVALRY);
                            if (name.toLowerCase().contains("artillery"))
                                selectedCards.add(CardType.ARTILLERY);
                            if (name.toLowerCase().contains("infantry"))
                                selectedCards.add(CardType.INFANTRY);
                        }
                    }
                });



    }
}

