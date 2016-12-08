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
    
    public Controller(){
        map = new Map(3);
        player = new Player(this);
        gui = new GUIMain();
    }
    
    public static void main(String[] args){
        Controller controller = new Controller();
    }
    
    public void putEntity(int row, int col, Entity entity){
        //gui.putEntity(row, col, entity);
    }
    
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
    }
}
