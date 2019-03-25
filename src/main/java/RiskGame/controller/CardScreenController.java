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

    private final ObservableList<String> cardData= FXCollections.observableArrayList();


    @FXML
    private void cardButtonOnClicked() {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        populateList();
        initializeList();

    }

    private void populateList() {

        HashMap<CardType,Integer> playerCardList=GameManager.getInstance().getActivePlayer().getCards();
        playerCardList=new HashMap<>();
        playerCardList.put(CardType.INFANTRY,2);
        playerCardList.put(CardType.ARTILLERY,2);
        playerCardList.put(CardType.CAVALRY,3);

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
            cardData.add(cardName);
        }
    }


    private void initializeList() {

        cardList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                ListCell<String> cell=new ListCell<String>() {
                    @Override
                    protected void updateItem(String cardName, boolean b) {
                        //System.out.println(playerName);
                        if(cardName!=null) {
                            ListData data=new ListData();

                            data.setCardInfo(cardName);

                            setGraphic(data.getBox());
                        }

                    }
                };
                return cell;
            }
        });

/*
        cardList.setItems(cardData);

        cardList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        cardList.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {

                    ObservableList<String> selectedItems = cardList.getSelectionModel().getSelectedItems();

                    System.out.println(new_val);
                    StringBuilder builder = new StringBuilder("Selected items :");

                    for (String name : selectedItems) {
                        System.out.println(new_val);
                        builder.append(name + "\n");
                    }



                });*/


        /*cardList.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                ObservableList<String> selectedItems =  cardList.getSelectionModel().getSelectedItems();

                for(String s : selectedItems){
                    System.out.println("selected item " + s);
                }

            }

        });*/



    }
}

