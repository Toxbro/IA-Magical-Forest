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
    
    public void main(String[] args){
        map = new Map(this, 3);
        player = new Player(map);
        gui = new GUIMain();
    }
    
    public void putEntity(int row, int col, Entity entity){
        gui.putEntity(row, col, entity);
    }
    
    public void removeEntity(int row, int col, Entity entity){
        gui.removeEntity(row, col, entity);
    }
    
    public void newLevel(){
        map = new Map(this, map.getSize()+1);
        gui.chargeLevel(map.getSize());
    }
    
    public void movePlayer(Direction dir){
        map.setPlayerCell(map.getCellByDirection(map.getPlayerCell(), dir));
    }
}
