package RiskGame.model.service.imp;

import RiskGame.model.entity.Continent;
import RiskGame.model.entity.GameMap;
import RiskGame.model.entity.Territory;
import RiskGame.model.service.IMapManager;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MapManager implements IMapManager {
    @Override
    public GameMap LoadMap(String url) {
        String encoding = "GBK";
        GameMap gameMap = new GameMap();
        int mode = 0; // 0: not start ,  1: gameMap , 2: continents , 3: territories
        try {
            File file = new File(url);
            if (file.isFile() && file.exists()) { // exist or not
                InputStreamReader read = new InputStreamReader(
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

/*                    System.out.print(mode);
                    System.out.println(lineTxt);*/
                    TranslateTxt2Map(lineTxt, mode, gameMap);
                }
                GenerateTheRelationshipOfTerr(gameMap);

                bufferedReader.close();
                read.close();
            } else {
                System.out.println("Can't find the file in this url");
            }
        } catch (Exception e) {
            System.out.println("Wrong content");
            e.printStackTrace();
        }
        return gameMap;
    }


    @Override
    public boolean CreateMap(String url, GameMap gameMap) {
        File file = new File(url);
        try {
            file.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write("[Map]\r\n");
            if (!gameMap.getAuthor().isEmpty()) {
                out.write("auther=" + gameMap.getAuthor()+"\r\n");
            }
            if (!gameMap.getImage().isEmpty()) {
                out.write("image=" + gameMap.getImage()+"\r\n");
            }
            if (!gameMap.getWarn().isEmpty()) {
                out.write("warn=" + gameMap.getWarn()+"\r\n");
            }
            if (!gameMap.getWrap().isEmpty()) {
                out.write("wrap=" + gameMap.getWrap()+"\r\n");
            }
            if (!gameMap.getScroll().isEmpty()) {
                out.write("scroll=" + gameMap.getScroll()+"\r\n");
            }
            out.write("[Continent]\r\n");
            if(gameMap.getContinents().size()>0){
                for(Continent c : gameMap.getContinents().values()){
                    out.write(c.getName()+"="+c.getCtrNum()+"\r\n");
                }
            }
            out.write("[Territory]\r\n");
            if(gameMap.getTerritories().size()>0){
                for (Territory t: gameMap.getTerritories().values()){
                    out.write(t.getName()+","+t.getX()+","+t.getY()+","+t.getContinent().getName());
                    for (Territory neibor: t.getNeighbors().values()){
                        out.write(","+neibor.getName());
                    }
                    out.write("\r\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean IsValided(GameMap gameMap) {
        boolean validedTerritories = false;
        boolean validedContinent = true;
        validedTerritories = IsConnectedTerritories(gameMap.getTerritories());
        for (Continent continent : gameMap.getContinents().values()) {
            validedContinent = IsConnectedContinents(continent) && validedContinent;
        }

        return validedContinent||validedTerritories;
    }

    private boolean IsConnectedTerritories(HashMap<String, Territory> graph) {
        ArrayList<String> territoriesTag = new ArrayList<String>();

        if (graph.keySet().iterator().hasNext()) {
            String key = graph.keySet().iterator().next();
            DFS(graph.get(key), territoriesTag);
        }
        if (territoriesTag.size() == graph.size()) {
            return true;
        }
        return false;
    }

    private boolean IsConnectedContinents(Continent continent) {
        ArrayList<String> territoriesTag = new ArrayList<String>();
        continent.getTerritories();
        if (continent.getTerritories().keySet().iterator().hasNext()) {
            String key = continent.getTerritories().keySet().iterator().next();
            DFS_Continent(continent.getTerritories().get(key), territoriesTag, continent.getName());
        }
        if (territoriesTag.size() == continent.getTerritories().size()) {
            return true;
        }
        return false;
    }

    private void DFS(Territory t, ArrayList<String> connectedTerrs) {
        for (String key : t.getNeighbors().keySet()) {
            Territory neightbor = t.getNeighbors().get(key);
            if (!connectedTerrs.contains(neightbor.getName())) {
                connectedTerrs.add(neightbor.getName());
                DFS(neightbor, connectedTerrs);
            }

        }
    }

    private void DFS_Continent(Territory t, ArrayList<String> connectedTerrs, String continentName) {
        for (String key : t.getNeighbors().keySet()) {
            Territory neightbor = t.getNeighbors().get(key);
            if (continentName.equals(neightbor.getContinent().getName())) {
                if (!connectedTerrs.contains(neightbor.getName())) {
                    connectedTerrs.add(neightbor.getName());
                    DFS(neightbor, connectedTerrs);
                }
            }
        }
    }


    private void TranslateTxt2Map(String lineTxt, int mode, GameMap gameMap) {
        if (mode == 0) {
            return;
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

    }

    private void GenerateTheRelationshipOfTerr(GameMap gameMap) {
        for (String key : gameMap.getTerritories().keySet()) {
            for (String neighborKey : gameMap.getTerritories().get(key).getNeighbors().keySet()) {
                gameMap.getTerritories().get(key).getNeighbors().put(neighborKey, gameMap.getTerritories().get(neighborKey));
            }
        }
    }


    //test
/*    public static void main(String[] args) {
        MapManager mapManager = new MapManager();
        GameMap map = mapManager.LoadMap("D://1.map");
        map.print();

    }*/


}
