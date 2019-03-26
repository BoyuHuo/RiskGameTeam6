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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.paint.Color;
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


    @FXML
    private void cardButtonOnClicked() {

        for (int i = 0; i <selectedCards.size() ; i++) {
            System.out.println(selectedCards.get(i));
        }



        System.out.println( GameManager.getInstance().getActivePlayer().isValidSet(selectedCards));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        populateList();
        initializeList();

    }

    StringBuilder builder;

    private void populateList() {

        HashMap<CardType,Integer> playerCardList=GameManager.getInstance().getActivePlayer().getCards();


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

    public void addStringDataToList(String cardName, int count){
        for (int i = 0; i <count ; i++) {
            cardData.add(cardName+" "+i+1);
        }
    }


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
                        if(name.toLowerCase().contains("cavalry"))
                            selectedCards.add(CardType.CAVALRY);
                        if(name.toLowerCase().contains("artillery"))
                            selectedCards.add(CardType.ARTILLERY);
                        if(name.toLowerCase().contains("infantry"))
                            selectedCards.add(CardType.INFANTRY);
                    }



                });



    }
}

