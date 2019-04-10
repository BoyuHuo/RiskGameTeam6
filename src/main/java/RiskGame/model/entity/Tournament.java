package RiskGame.model.entity;

import RiskGame.model.service.imp.GameManager;

import java.util.List;
import java.util.Map;

/**
 * This is tournament class, which is used to do the tournament work!
 *
 * @author Baiyu Huo
 * @version v1.0.0
 */
public class Tournament {
    private String[][] result;
    private List<GameMap> maps;
    private Map<String, Player> players;
    private int maximumTurn;
    private int gameLoop;

    /**
     * The constractor for tournament instance
     * @param maps the map list that will be run the game.
     * @param players the players list that will be play in each run of the game
     * @param gameLoop the number of time that will be played in each map
     * @param maximumTurn the maximum number of turn for each match
     */
    public Tournament(List<GameMap> maps, Map<String, Player> players, int gameLoop, int maximumTurn) {
        this.maps = maps;
        this.players = players;
        this.gameLoop = gameLoop;
        this.maximumTurn = maximumTurn;
    }

    /**
     * lunch the for each game match and get the result
     * @param mapNum the index number of map play
     * @return result the string value of the winner strategy
     * @throws InterruptedException thread interrupted exception
     */
    public String luncheTheMatch(int mapNum) throws InterruptedException {
        GameManager.getInstance().cleanUp();
        String r = "";
        GameManager.getInstance().setMap(maps.get(mapNum));
        GameManager.getInstance().setPlayers(players);
        GameManager.getInstance().newGame();
        while (true) {
            switch (GameManager.getInstance().getGamePhase()) {
                case "Reinforcements":
                    System.out.println("r");
                    Thread thread = GameManager.getInstance().getActivePlayer().excuteReinforceStrategy(0);
                    thread.join();
                    break;
                case "Attack":
                    System.out.println("a");
                    Thread thread2 = GameManager.getInstance().getActivePlayer().excuteAttackStrategy(0);
                    thread2.join();
                    break;
                case "Fortification":
                    System.out.println("f");
                    Thread thread3 = GameManager.getInstance().getActivePlayer().excuteFortifyStrategy(0);
                    thread3.join();
                    break;
                case "Start Up":
                    System.out.println("s");
                    Thread thread4 = GameManager.getInstance().getActivePlayer().excuteStartupStrategy(0);
                    thread4.join();
                    break;
            }
            System.out.println(GameManager.getInstance().getActivePlayer().getName());



            if (GameManager.getInstance().isGameOver()) {
                if (GameManager.getInstance().getActivePlayer().getStrategy() instanceof AggressiveStrategy) {
                    r = "aggressive";
                } else if (GameManager.getInstance().getActivePlayer().getStrategy() instanceof BenevolentStrategy) {
                    r = "benevolent";
                } else if (GameManager.getInstance().getActivePlayer().getStrategy() instanceof CheaterStrategy) {
                    r = "cheater";
                } else if (GameManager.getInstance().getActivePlayer().getStrategy() instanceof  RandomStrategy){
                    r = "random";
                }
                break;
            }
            if (GameManager.getInstance().getTotalTurn() > maximumTurn) {
                r = "Draw";
                break;
            }
        }
        return r;
    }

    /**
     * run all the game at once, and then fullfill all the results for the result table
     * @return boolean the result flag to describe if this tourament is launched successuflly or not
     */
    public boolean start() {
        result = new String[maps.size()][gameLoop];
        for (int i = 0; i < result.length; i++) {
            System.out.println(1);
            for (int j = 0; j < result[i].length; j++) {
                try {
                    result[i][j] = luncheTheMatch(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }


    /**
     * the getter method for the result table
     * @return result the result table
     */
    public String[][] getResult() {
        return result;
    }

    /**
     * the setter method for result table, actually it will set a the result for result table
     * @param result
     */
    public void setResult(String[][] result) {
        this.result = result;
    }


    /**
     * the getter method for maps.
     * @return maps maps list
     */
    public List<GameMap> getMaps() {
        return maps;
    }

    /**
     * the setter method for maps, it used to set up all the map in the tourament mode
     * @param maps the map list
     */
    public void setMaps(List<GameMap> maps) {
        this.maps = maps;
    }

    /**
     * the getter method for players, it will return all the players who will play in the tournament
     * @return players the players list
     */
    public Map<String, Player> getPlayers() {
        return players;
    }

    /**
     * the setter method for players, it will set the all the players in the game
     * @param players the players list
     */
    public void setPlayers(Map<String, Player> players) {
        this.players = players;
    }
}
