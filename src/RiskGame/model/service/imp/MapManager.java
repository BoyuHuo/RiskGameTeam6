package RiskGame.model.service.imp;

import RiskGame.model.entity.Continent;
import RiskGame.model.entity.GameMap;
import RiskGame.model.entity.Territory;
import RiskGame.model.service.IMapManager;

import java.io.*;

public class MapManager implements IMapManager {
    @Override
    public GameMap LoadMap(String url) {
        String encoding = "GBK";
        GameMap gameMap = new GameMap();
        int mode = 0; // 0: not start ,  1: gameMap , 2: continents , 3: territories
        try {
            File file = new File(url);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);// 考虑到编码格式
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
                    translateTxt2Map(lineTxt,mode,gameMap);
                }
                bufferedReader.close();
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

        return gameMap;
    }

    private void translateTxt2Map(String lineTxt, int mode, GameMap gameMap) {
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
                gameMap.getContinents().put(continent.getName(),continent);
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
                for (int i=4; i<valuePair.length;i++){
                    territory.getNeighbors().put(valuePair[i],new Territory());
                }
                gameMap.getTerritories().put(territory.getName(),territory);
                gameMap.getContinents().get(valuePair[3]).getTerritories().add(territory);
            }
        }

    }


    public static void main(String[] args) {
        MapManager mapManager = new MapManager();
        GameMap map = mapManager.LoadMap("D://1.map");
        map.print();

    }

    @Override
    public boolean createMap() {
        return false;
    }

    @Override
    public boolean isValided(GameMap gameMap) {
        return false;
    }


}
