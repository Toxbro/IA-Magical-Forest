/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import gui.magical.forest.GUIMain;
import ia.magical.forest.environment.Cell;
import ia.magical.forest.environment.Direction;
import ia.magical.forest.environment.Entity;
import ia.magical.forest.environment.Map;
import java.util.ArrayList;
import player.Player;

/**
 *
 * @author quentin
 */
public class Controller {
    
    private Map map;
    private Player player;
    private GUIMain gui;
    private int score = 0;
    private int level = 3;
    
    public static void main(String[] args){
        Controller controller = new Controller();
    }
    
    public Controller(){
        gui = new GUIMain(this);
        player = new Player(this);
        gui.chargeLevel(level);
        map = new Map(this, level);
    }
    
    public void putEntity(int row, int col, Entity entity){
        gui.putEntity(row, col, entity);
    }
    
    public void removeEntity(int row, int col, Entity entity){
        gui.removeEntity(row, col, entity);
    }
    
    public void newLevel(int delta){
        level += delta;
        player = new Player(this);
        gui.chargeLevel(level);
        map = new Map(this, level);        
    }
    
    public void movePlayer(Direction dir){
        map.setPlayerCell(map.getCellByDirection(map.getPlayerCell(), dir));
        gui.setConsoleText("Je me déplace vers "+dir.toString()+'.');
        editScore(-1);
    }
    
    public void playerAct(){
        player.act();
    }
    
    public void hit(Direction dir){
        map.removeMonster(map.getCellByDirection(map.getPlayerCell(), dir));
        gui.setConsoleText("Je tire vers "+dir.toString()+'.');
        editScore(-10);
    }
    
    public void throughPortal(){
        gui.setConsoleText("J'ai trouvé le portail !!");
        editScore(10*map.getSize());
        newLevel(1);
    }
    
    public void killPlayer(){
        gui.setConsoleText("Je suis mort.");
        editScore(-10*map.getSize());
        newLevel(0);
    }
    
    public void editScore(int delta){
        score += delta;
        gui.setScore(score);
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
    
    public boolean isCellSth(Entity ent){
        switch(ent){
            case CRACK: return map.getPlayerCell().isHasCrevasse();
            case WIND: return map.getPlayerCell().isIsWindy();            
            case MONSTER: return map.getPlayerCell().isHasMonster();
            case SMELL: return map.getPlayerCell().isIsSmelling();
            case PORTAL: return map.getPlayerCell().isIsShiny();
            default: return false;
        }
    }
    
    public ArrayList<Direction> getPossibleDirection(){
        ArrayList<Direction> result = new ArrayList<>();
        for(Cell c : map.getCloseCells(map.getPlayerCell())){
            result.add(map.getDirectionFromCell(map.getPlayerCell(), c));
        }
        System.out.println("Player can move to : "+result);
        return result;
    }
}
