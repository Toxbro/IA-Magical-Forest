/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import gui.magical.forest.GUIMain;
import ia.magical.forest.environment.Direction;
import ia.magical.forest.environment.Entity;
import ia.magical.forest.environment.Map;
import player.Player;

/**
 *
 * @author quentin
 */
public class Controller {
    
    private Map map;
    private Player player;
    private GUIMain gui;
    
<<<<<<< HEAD
    public void main(String[] args){
        map = new Map(this, 3);
        player = new Player(map);
=======
    public Controller(){
        map = new Map(3);
        player = new Player(this);
>>>>>>> origin/Player
        gui = new GUIMain();
    }
    
    public static void main(String[] args){
        Controller controller = new Controller();
    }
    
    public void putEntity(int row, int col, Entity entity){
        //gui.putEntity(row, col, entity);
    }
    
<<<<<<< HEAD
    public void removeEntity(int row, int col, Entity entity){
        gui.removeEntity(row, col, entity);
    }
    
    public void newLevel(){
        map = new Map(this, map.getSize()+1);
        gui.chargeLevel(map.getSize());
    }
    
    public void movePlayer(Direction dir){
        map.setPlayerCell(map.getCellByDirection(map.getPlayerCell(), dir));
=======
    public void initLvl(int size){
        //gui.chargeLevel(size);
    }
    
    public void movePlayer(Direction dir){
        //map.movePlayer(dir);
    }
    
    /**
     * @return the map
     */
    public Map getMap() {
        return map;
    }

    /**
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return the gui
     */
    public GUIMain getGui() {
        return gui;
>>>>>>> origin/Player
    }
}
