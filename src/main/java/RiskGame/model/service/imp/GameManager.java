package RiskGame.model.service.imp;

import RiskGame.model.entity.GameMap;
import RiskGame.model.entity.Player;
import RiskGame.model.entity.Territory;
import RiskGame.model.service.IGameManager;
import com.sun.scenario.effect.impl.prism.ps.PPSBlend_ADDPeer;

import java.util.*;

public class GameManager extends Observable implements IGameManager {

    enum phase {STARTUP, REINFORCEMENTS, ATTACK, FORTIFICATION}


    private Map<String, Player> players;
    private Iterator playerIterator;
    private Player activePlayer;
    private GameMap map;
    private phase gamePhase;
    private static GameManager instance;


    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    private GameManager() {
    }

    public void NewGame() {
        GameManager.getInstance().ramdomAssignTerritoryToPlayer();
        initArmies();
        start();
    }

    private void initArmies(){
        int armiesNum = 0;
        switch (players.values().size()) {
            case 2:
                armiesNum = 45;
                break;
            case 3:
                armiesNum = 35;
                break;
            case 4:
                armiesNum = 30;
                break;
            case 5:
                armiesNum = 25;
                break;
            case 6:
                armiesNum = 20;
                break;
            default:
                break;
        }
        for(Player p: players.values()){
            p.setArmies(armiesNum);
        }
    }

    public void start() {
        startUpPhase();

    }

    public void gameOver() {

    }

    public void startUpPhase() {
        gamePhase = phase.STARTUP;
        nextPlayer();

    }

    public void nextRound() {
        switch (gamePhase){
            case STARTUP:
                Player p = (Player) players.values().toArray()[players.values().size()-1];
                if(p.equals(activePlayer)){
                    nextPhase();
                    nextPlayer();
                }
                else{
                    nextPlayer();
                }
                break;
            case ATTACK:case REINFORCEMENTS: nextPhase();break;
            case FORTIFICATION: nextPhase();nextPlayer(); break;
            default:break;
        }
    }

    public void nextPlayer() {
        if (playerIterator == null || !playerIterator.hasNext()) {
            playerIterator = players.values().iterator();
        }
        activePlayer = (Player) playerIterator.next();
    }

    public void nextPhase() {
        int tag = gamePhase.ordinal();
        tag++;
        if (tag >= phase.values().length) {
            tag = 1;
        }
        gamePhase = phase.values()[tag];
    }

    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public void setPlayers(Map<String, Player> players) {
        this.players = players;
    }

    public Map<String, Player> getPlayers() {
        return this.players;
    }

    public void addPlayer(Player p) {
        if (players == null || players.isEmpty()) {
            players = new HashMap<>();
        }
        players.put(p.getName(), p);
    }

    public void removePlayer(Player p) {
        players.remove(p.getName());
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public String getGamePhase() {
        String result = "";
        switch (gamePhase) {
            case STARTUP:
                result = "Start Up";
                break;
            case REINFORCEMENTS:
                result = "Reinforcements";
                break;
            case ATTACK:
                result = "Attack";
                break;
            case FORTIFICATION:
                result = "Fortification";
                break;
        }

        return result;


    }

    public void setGamePhase(phase gamePhase) {
        this.gamePhase = gamePhase;
    }

    public Iterator getPlayerIterator() {
        return this.playerIterator;
    }

    public void setPlayerIterator(Iterator iterator) {
        this.playerIterator = iterator;
    }


    public void cleanUp() {
        this.map = new GameMap();
        this.players.clear();
    }

    public void ramdomAssignTerritoryToPlayer() {
        Random generator = new Random();
        Object[] keys = map.getTerritories().keySet().toArray();

        Iterator<Player> tempIterator = players.values().iterator();
        while (keys.length > 0) {
            if (!tempIterator.hasNext()) {
                tempIterator = players.values().iterator();
            }
            int randomTag = generator.nextInt(keys.length);
            Player player = tempIterator.next();

            map.getTerritories().get(keys[randomTag]).setBelongs(player);
            keys = deleteInArray(randomTag, keys);


        }
    }

    private Object[] deleteInArray(int index, Object array[]) {
        Object[] arrNew = new Object[array.length - 1];
        for (int i = index; i < array.length - 1; i++) {
            array[i] = array[i + 1];
        }
        System.arraycopy(array, 0, arrNew, 0, arrNew.length);
        return arrNew;
    }


}
