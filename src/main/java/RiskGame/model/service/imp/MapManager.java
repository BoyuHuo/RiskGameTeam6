package RiskGame.model.service.imp;

import RiskGame.model.entity.Continent;
import RiskGame.model.entity.GameMap;
import RiskGame.model.entity.Territory;
import RiskGame.model.service.IMapManager;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This is implement class for map manager interface, which contains all the implementation of map related logic function.
 *
 * @author Baiyu Huo
 * @version v1.0.0
 * @see IMapManager
 * @since v1.0.0
 */
public class MapManager implements IMapManager {

    /**
     * implementation
     * This is use for loading the map into games
     * It will convert a *.map file into a map instance, which contains all the information in the map.
     *
     * @param url the path of map file
     * @return gameMap the instance in the game, which represent the whole relationship between territories and continents within the map.
     */
    @Override
    public GameMap loadMap(String url) {
        String encoding = "GBK";
        String encodePath;
        GameMap gameMap = new GameMap();
        InputStreamReader read = null;
        int mode = 0; // 0: not start ,  1: gameMap , 2: continents , 3: territories
        try {
            encodePath = java.net.URLDecoder.decode(url, "utf-8");
            File file = new File(encodePath);
            if (file.isFile() && file.exists()) { // exist or not
                read = new InputStreamReader(
                        new FileInputStream(file), encoding);// encoding formate
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;

                while ((lineTxt = bufferedReader.readLine()) != null) {
                    switch (lineTxt) {
                        case "[Map]":
                            mode = 1;
                            break;
                        case "[Continents]":
                            mode = 2;
                            break;
                        case "[Territories]":
                            mode = 3;
                            break;
                    }
                    translateTxt2Map(lineTxt, mode, gameMap);
                }
                generateTheRelationshipOfTerr(gameMap);

                bufferedReader.close();
            } else {
                System.out.println("Can't find the file in this url");
            }
        } catch (Exception e) {
            System.out.println("Wrong content");
            return null;
        } finally {
            try {
                if(read!=null){
                    read.close();
                }
            } catch (IOException e) {
                return null;
            }
        }
        if(isValided(gameMap)){
            return gameMap;
        }else {
            return null;
        }
    }

    /**
     * implementation
     * This is use for creating a map file from a GameMap instance
     * It will convert a java instance into map file (*.map), which is in a formal Risk format.
     *
     * @param url     the path of map file
     * @param gameMap the instance that need to be saved
     * @return boolean  True: Create successful  False: Create fail
     */
    @Override
    public boolean createMap(String url, GameMap gameMap) {
        boolean validate = isValided(gameMap);
        if(validate == false){
            return false;
        }
        String encodePath;
        File file = null;
        BufferedWriter out = null;
        try {
            encodePath = java.net.URLDecoder.decode(url, "utf-8");
            file = new File(encodePath);
            file.createNewFile();
            out = new BufferedWriter(new FileWriter(file));
            out.write("[Map]\r\n");
            if (gameMap.getAuthor()!=null||!gameMap.getAuthor().isEmpty()) {
                out.write("author=" + gameMap.getAuthor() + "\r\n");
            }
            if (!gameMap.getImage().isEmpty()||gameMap.getImage()!=null) {
                out.write("image=" + gameMap.getImage() + "\r\n");
            }
            if (!gameMap.getWarn().isEmpty()||gameMap.getWrap()!=null) {
                out.write("warn=" + gameMap.getWarn() + "\r\n");
            }
            if (!gameMap.getWrap().isEmpty()||gameMap.getWarn()!=null) {
                out.write("wrap=" + gameMap.getWrap() + "\r\n");
            }
            if (!gameMap.getScroll().isEmpty()||gameMap.getScroll()!=null) {
                out.write("scroll=" + gameMap.getScroll() + "\r\n");
            }
            out.write("\r\n");
            out.write("[Continents]\r\n");
            if (gameMap.getContinents().size() > 0) {
                for (Continent c : gameMap.getContinents().values()) {
                    if(c.getCtrNum()<0){
                        return false;
                    }
                    out.write(c.getName() + "=" + c.getCtrNum() + "\r\n");
                }
            }
            out.write("\r\n");
            out.write("[Territories]\r\n");
            if (gameMap.getTerritories().size() > 0) {
                for (Territory t : gameMap.getTerritories().values()) {
                    if(t.getContinent()==null){
                        return false;
                    }
                    out.write(t.getName() + "," + t.getX() + "," + t.getY() + "," + t.getContinent().getName());
                    for (Territory neibor : t.getNeighbors().values()) {
                        out.write("," + neibor.getName());
                    }
                    out.write("\r\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    /**
     * Implementation
     * This is use for tell if the map is a valid map.
     *
     * @param gameMap the instance that need to be saved
     * @return boolean  True: A valid map  False: A invalid map
     */
    @Override
    public boolean isValided(GameMap gameMap) {
        boolean validedTerritories = false;
        boolean validedContinent = true;

        validedTerritories = isConnectedTerritories(gameMap.getTerritories());


        for (Continent continent : gameMap.getContinents().values()) {
            if(continent.getTerritories().size()<=0){
                return false;
            }
            if(continent.getCtrNum()<0){
                return false;
            }
            validedContinent = isConnectedContinents(continent) && validedContinent;
        }
        System.out.println(validedTerritories+":"+validedContinent);
        return validedContinent && validedTerritories;
    }

    /**
     * Implementation
     * This is use for tell if the map overall is a connected
     *
     * @param graph all the territories in the map
     * @return boolean  True: A connected graph  False: A unconnected graph
     */
    private boolean isConnectedTerritories(HashMap<String, Territory> graph) {
        ArrayList<String> territoriesTag = new ArrayList<String>();
        Iterator iterator = graph.values().iterator();
        if (iterator.hasNext()) {
            Territory territory = (Territory)iterator.next();

            DFS(territory, territoriesTag);
        } else{
            return false;
        }
        System.out.println(territoriesTag.size()+" and "+graph.size());
        if (territoriesTag.size() == graph.size()) {
            return true;
        }
        return false;
    }

    /**
     * Implementation
     * This is use for tell if the continent is a connected sub-graph
     *
     * @param continent the continent which contains a sub-graph
     * @return boolean  True: A connected graph  False: A unconnected graph
     */
    private boolean isConnectedContinents(Continent continent) {
        ArrayList<String> territoriesTag = new ArrayList<String>();
        Iterator cIterator = continent.getTerritories().keySet().iterator();

        if (cIterator.hasNext()) {
            String key = (String)cIterator.next();
            DFS_Continent(continent.getTerritories().get(key), territoriesTag, continent.getName());
        }
        System.out.println(territoriesTag.size()+" vs "+continent.getTerritories().size());
        if (territoriesTag.size() == continent.getTerritories().size()) {
            return true;
        }
        return false;
    }

    /**
     * Implementation
     * DFS for travel all the neighbors of all territories
     *
     * @param t the continent which contains a sub-graph
     * @param connectedTerrs key of all Territories
     */
    private void DFS(Territory t, ArrayList<String> connectedTerrs) {
        connectedTerrs.add(t.getName());
        for (String key : t.getNeighbors().keySet()) {
            Territory neightbor = t.getNeighbors().get(key);
            if (!connectedTerrs.contains(neightbor.getName())) {
                DFS(neightbor, connectedTerrs);
            }

        }
    }

    /**
     * Implementation
     * DFS for travel all the neighbors of territories for each continent
     *
     * @param t the continent which contains a sub-graph
     * @param connectedTerrs key of all Territories
     * @param continentName the name of under testing continent
     */
    private void DFS_Continent(Territory t, ArrayList<String> connectedTerrs, String continentName) {
        connectedTerrs.add(t.getName());
        for (String key : t.getNeighbors().keySet()) {
            Territory neightbor = t.getNeighbors().get(key);
            if (continentName.equals(neightbor.getContinent().getName())) {
                if (!connectedTerrs.contains(neightbor.getName())) {
                    DFS_Continent(neightbor, connectedTerrs,continentName);
                }
            }
        }
    }


    /**
     * Implementation
     * DFS for travel all the neighbors of territories for each continent
     *
     * @param lineTxt the text line from the map file
     * @param mode which stage of reading now
     * @param gameMap the gameMap instance that need to be fulfilled
     */
    private boolean translateTxt2Map(String lineTxt, int mode, GameMap gameMap) {
        if (mode == 0) {
            return true;
        }
        if (mode == 1) {
            if (lineTxt.contains("=")) {
                String[] valuePair = lineTxt.split("=");
                switch (valuePair[0]) {
                    case "image":
                        gameMap.setImage(valuePair[1]);
                        break;
                    case "wrap":
                        gameMap.setWrap(valuePair[1]);
                        break;
                    case "scroll":
                        gameMap.setScroll(valuePair[1]);
                        break;
                    case "author":
                        gameMap.setAuthor(valuePair[1]);
                        break;
                    case "warn":
                        gameMap.setWarn(valuePair[1]);
                        break;
                }
            }
        }
        if (mode == 2) {
            if (lineTxt.contains("=")) {
                String[] valuePair = lineTxt.split("=");
                Continent continent = new Continent(valuePair[0], Integer.parseInt(valuePair[1]));
                gameMap.getContinents().put(continent.getName(), continent);
            }
        }
        if (mode == 3) {
            if (lineTxt.contains(",")) {
                String[] valuePair = lineTxt.split(",");
                Territory territory = new Territory();
                territory.setName(valuePair[0]);
                territory.setX(Integer.parseInt(valuePair[1]));
                territory.setY(Integer.parseInt(valuePair[2]));
                territory.setContinent(gameMap.getContinents().get(valuePair[3]));
                for (int i = 4; i < valuePair.length; i++) {
                    territory.getNeighbors().put(valuePair[i], new Territory());
                }
                gameMap.getTerritories().put(territory.getName(), territory);
                gameMap.getContinents().get(valuePair[3]).getTerritories().put(territory.getName(), territory);
            }
        }
        return true;

    }

    /**
     * Implementation
     * Use for fulfill the relations between each territory
     * Because it is a undirectional graph
     *
     * @param gameMap the gameMap instance that need to be fulfilled
     */
    private void generateTheRelationshipOfTerr(GameMap gameMap) {
        for (String key : gameMap.getTerritories().keySet()) {
            for (String neighborKey : gameMap.getTerritories().get(key).getNeighbors().keySet()) {
                gameMap.getTerritories().get(key).getNeighbors().put(neighborKey, gameMap.getTerritories().get(neighborKey));
            }
        }
    }



}
