package Main;

import gui.magical.forest.GUIMain;
import ia.magical.forest.environment.Cell;
import ia.magical.forest.environment.Direction;
import ia.magical.forest.environment.Entity;
import ia.magical.forest.environment.Map;
import java.util.ArrayList;
import player.Player;

/**
 * The main class of the project, which launch the graphical interface, the map and the player.
 * @author Quentin ROLLIN
 */
public class Controller {
    
    /**
     * The map of the current level.
     */
    private Map map;
    
    /**
     * The player.
     */
    private Player player;
    
    /**
     * The graphical interface.
     */
    private GUIMain gui;
    
    /**
     * The score of the player.
     */
    private int score = 0;
    
    /**
     * The size of the map.
     */
    private int level = 3;
    
    /**
     * The method which is call when the program is launched.
     * @param args 
     */
    public static void main(String[] args){
        Controller controller = new Controller();
    }
    
    /**
     * Constructor of the class.
     * Launch the graphical interface, the player, the map and gives the map to the gui.
     */
    public Controller(){
        gui = new GUIMain(this);
        player = new Player(this);
        gui.chargeLevel(level);
        map = new Map(this, level);
    }
    
    /**
     * This method is an interface between the map and the gui.
     * @param row the row where to put the entity.
     * @param col the column where to put the entity.
     * @param entity the type of the entity to put in the gui.
     */
    public void putEntity(int row, int col, Entity entity){
        gui.putEntity(row, col, entity);
    }
    
    /**
     * This method allows the removing of the given entity at the given column and row.
     * @param row the row where to remove the entity.
     * @param col the column where to remove the entity.
     * @param entity the type of the entity to remove in the gui.
     */
    public void removeEntity(int row, int col, Entity entity){
        gui.removeEntity(row, col, entity);
    }
    
    /**
     * Load or reload the level, according to the given delta and the current level of the game.
     * @param delta the delta between the current level and the next one (1 if it is the next level, 0 to reload the current one).
     */
    public void newLevel(int delta){
        level += delta;
        player = new Player(this);
        gui.chargeLevel(level);
        map = new Map(this, level);        
    }
    
    /**
     * Allows the player to move in the level, refreshing his position in the map and on the gui.
     * @param dir the direction where the player wants to move.
     */
    public void movePlayer(Direction dir){
        map.setPlayerCell(map.getCellByDirection(map.getPlayerCell(), dir));
        gui.setConsoleText("Je me déplace vers "+dir.toString()+'.');
        editScore(-1);
    }
    
    /**
     * This method is called at each click on the button of the graphical interface.
     */
    public void playerAct(){
        player.act();
    }
    
    /**
     * Allows the player to throw a rock in the given direction, killing a monster if there is one on the adjacent cell in this direction.
     * @param dir The direction where the player wants to throw his rock.
     */
    public void hit(Direction dir){
        map.removeMonster(map.getCellByDirection(map.getPlayerCell(), dir));
        gui.setConsoleText("Je tire vers "+dir.toString()+'.');
        editScore(-10);
    }
    
    /**
     * Allows the player to go through a portal on his current cell, and to go to the next level.
     */
    public void throughPortal(){
        gui.setConsoleText("J'ai trouvé le portail !!");
        editScore(10*map.getSize());
        newLevel(1);
    }
    
    /**
     * This method kills the player if he goes on a cell occupied by a crack or a monster.
     * It also reloads the current level.
     */
    public void killPlayer(){
        gui.setConsoleText("Je suis mort.");
        editScore(-10*map.getSize());
        newLevel(0);
    }
    
    /**
     * Updates the displayed score of the player with the given delta.
     * @param delta The delta which should be applied to the displayed score.
     */
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
    
    /**
     * Returns a boolean corresponding to whether or not the current cell contains the given entity.
     * @param ent An entity, which could be a smell, wind, crack, monster, portal or player.
     * @return a boolean corresponding to whether or not the current cell contains the given entity.
     */
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
    
    /**
     * Returns all the possible direction the player can go.
     * @return an arraylist containing all the possible direction the player can take.
     */
    public ArrayList<Direction> getPossibleDirection(){
        ArrayList<Direction> result = new ArrayList<>();
        for(Cell c : map.getCloseCells(map.getPlayerCell())){
            result.add(map.getDirectionFromCell(map.getPlayerCell(), c));
        }
        return result;
    }
}
