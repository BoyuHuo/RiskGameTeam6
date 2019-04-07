package RiskGame.model.entity;

import RiskGame.model.service.imp.GameManager;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public class Game implements Serializable {



     private Map<String, Player> players;
     private Iterator playerIterator;
     private Player activePlayer;
     private GameMap map;
     private GameManager.phase gamePhase;
     private String message = "";
     private boolean gameOver;
     public static int cardSet=0;

    public Map<String, Player> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, Player> players) {
        this.players = players;
    }
    public Iterator getPlayerIterator() {
        return playerIterator;
    }

    public void setPlayerIterator(Iterator playerIterator) {
        this.playerIterator = playerIterator;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public GameManager.phase getGamePhase() {
        return gamePhase;
    }

    public void setGamePhase(String gamePhase) {


        GameManager.phase result = null;
        switch (gamePhase) {
            case "Start Up":
                result = GameManager.phase.STARTUP;
                break;
            case "Reinforcements":
                result = GameManager.phase.REINFORCEMENTS;
                break;
            case "Attack":
                result = GameManager.phase.ATTACK;
                break;
            case "Fortification":
                result = GameManager.phase.FORTIFICATION;
                break;
        }
        this.gamePhase = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public static int getCardSet() {
        return cardSet;
    }

    public static void setCardSet(int cardSet) {
        Game.cardSet = cardSet;
    }
}
