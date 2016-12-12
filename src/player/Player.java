package player;

import Main.Controller;
import ia.magical.forest.environment.Direction;
import ia.magical.forest.environment.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Player's knowledges and decision making processes.
 * @author Quentin ROLLIN
 */
public class Player{
    
    /**
     * The list of all the already explored cells.
     * The coordinates of the cells are relatives compared to the player's initial cell.
     */
    private ArrayList<Cell> exploredCells;
    
    /**
     * The current cell of the player.
     */
    private Cell currentCell;
    
    /**
     * The controller.
     */
    private Controller main;
    
    /**
     * A list of all known (or almost certain) cracks cells.
     */
    private ArrayList<Cell> crackCells;
    
    /**
     * A hashmap of potential cracks cells, in addition to their respective probability.
     */
    private HashMap<Cell, Double> potentialCrackCells;
    
    /**
     * A list of all known (or almost certain) monsters cells.
     */
    private ArrayList<Cell> monsterCells;
    
    /**
     * A hashmap of potential monsters cells, in addition to their respective probability.
     */
    private HashMap<Cell, Double> potentialMonsterCells;
    
    /**
     * The action the player choosed to do.
     */
    private Action action;
    
    /**
     * The direction the player choosed to go or to throw a rock.
     */
    private Direction direction;

    /**
     * The constructor of the player initialize all the lists and hashmap, in addition to creating the initial cell.
     * @param main The controller which create the player.
     */
    public Player(Controller main){
        this.main = main;
        currentCell = new Cell(0, 0);
        exploredCells = new ArrayList<>();
        crackCells = new ArrayList<>();
        monsterCells = new ArrayList<>();
        potentialCrackCells = new HashMap<>();
        potentialMonsterCells = new HashMap<>();
    }
     
    /**
     * This method call the inference process, where the player decide his next action and direction, en call the controller methods according to this action.
     */
    public void act(){
        inference();
        switch(action){
            case MOVE: {
                potentialCrackCells.remove(currentCell);
                potentialMonsterCells.remove(currentCell);
                exploredCells.add(currentCell);
                currentCell = getCellByDirection(direction);
                main.movePlayer(direction);
                break;
            }
            case PORTAL: main.throughPortal();
            break;
        }
    }
    
    /**
     * The inference process interpretes the current situation of the player, and then decides on the next action to do.
     * To do so, it first checks wether or not the player is already on the portal cell.
     * Then, he thinks about the probability of each adjacent containing a crack or a monster, according to his own cell smell or wind.
     * Last, he decides what to do.
     */
    public void inference(){
        if (main.isCellSth(Entity.PORTAL)) {
            action = Action.PORTAL;
        }
        else{
            if (main.isCellSth(Entity.WIND) && !contains(exploredCells, currentCell))
                thinkMonstersCracks(Entity.WIND);
            if (main.isCellSth(Entity.SMELL) && !contains(exploredCells, currentCell))
                thinkMonstersCracks(Entity.SMELL);
            
            System.out.println("monsterCells : "+monsterCells);
            System.out.println("crackCells : "+crackCells);
            System.out.println("potentialMonsterCells : "+potentialMonsterCells);
            System.out.println("potentialCrackCells : "+potentialCrackCells);
            
            direction = getSafeDirection();
            
            if(direction == null){
                direction = getSafestDirection();
            }
            
            if(direction == null)
            {
                ArrayList<Cell> knownAdjCell = getAllAdjacentCells();
                if(!exploredCells.isEmpty())
                    knownAdjCell.retainAll(exploredCells); //At this point, the player must choose an already explored cell to move to.
                direction = getDirectionByCell(knownAdjCell.get(new Random().nextInt(knownAdjCell.size()))); //this choice is randomly made.
            }
            
            action = Action.MOVE;
        }
    }
    
    /**
     * This method takes all the adjacents cells, minus the already explored ones, potential monster and crack ones, and certain monster and cracks ones.
     * It then chooses randomly a cell among this selection, and return its direction.
     * If there is none left, it returns null.
     * @return the direction of a safe cell, if such cell exists.
     */
    private Direction getSafeDirection(){
        ArrayList<Cell> safeAdjCells = getAllAdjacentCells();
        safeAdjCells.removeAll(monsterCells);
        safeAdjCells.removeAll(crackCells); 
        safeAdjCells.removeAll(new ArrayList<Cell>(potentialCrackCells.keySet()));
        safeAdjCells.removeAll(new ArrayList<Cell>(potentialMonsterCells.keySet()));
        safeAdjCells.removeAll(exploredCells);
        if(safeAdjCells.isEmpty())
            return null;
        return getDirectionByCell(safeAdjCells.get(new Random().nextInt(safeAdjCells.size())));
    }
    
    /**
     * This method only considers cells that potentially contain a monster or a crack.
     * To do so, it finds the cell with the least probable existence of a monster or a crack, and only if this probability of existence is less than 25% (50/200). 
     * If there is multiple choices, it chooses randomly.
     * If the cell is smelly, which means there is a nearby monster, it shot in that direction before moving.
     * @return the direction of an almost safe close cell, according to its probability of containing an hazard.
     */
    private Direction getSafestDirection(){
        ArrayList<Cell> unknownAdjCells = getAllAdjacentCells();
        unknownAdjCells.removeAll(monsterCells);
        unknownAdjCells.removeAll(crackCells);
        unknownAdjCells.removeAll(exploredCells);
        
        if(unknownAdjCells.isEmpty())
            return null;

        ArrayList<Cell> temp = new ArrayList<>(potentialCrackCells.keySet());
        temp.removeAll(new ArrayList<Cell>(potentialMonsterCells.keySet()));
        temp.addAll(new ArrayList<Cell>(potentialMonsterCells.keySet()));
        unknownAdjCells.retainAll(temp);
        

        double probability = Double.MAX_VALUE;
        ArrayList<Cell> bestChoice = new ArrayList<>();
        for(Cell c : unknownAdjCells){
            double tempProb = 0;
            if(contains(potentialMonsterCells, c)){
                tempProb += potentialMonsterCells.get(c);
            }
            if(contains(potentialCrackCells, c)){
                tempProb += potentialCrackCells.get(c);
            }
            if(tempProb < probability){
                bestChoice.clear();
                bestChoice.add(c);
                probability = tempProb;
            }
            else if(tempProb == probability)
                bestChoice.add(c);
        }
        if(probability < 50){
            return getDirectionByCell(bestChoice.get(new Random().nextInt(bestChoice.size())));
        }
        else if(main.isCellSth(Entity.SMELL)){
            Cell target = bestChoice.get(new Random().nextInt(bestChoice.size()));
            Direction dir = getDirectionByCell(target);
            potentialMonsterCells.remove(target);
            monsterCells.remove(target);
            main.hit(dir);
            return dir;
        }
        return null;
    }
    
    /**
     * This method update the probability of existence of monster or crack in nearby cells.
     * If an adjacent cell has a probability superior than 70%, the cell is considered as containing an hazard.
     * @param smellOrWind The state of the current cell, if it is smelly or windy (this method is called twice if the cell is both).
     */
    private void thinkMonstersCracks(Entity smellOrWind){
        ArrayList<Cell> dangerCells;
        HashMap<Cell, Double> potentialDangerCells;
        
        if(smellOrWind == Entity.SMELL){
            dangerCells = monsterCells;
            potentialDangerCells = potentialMonsterCells;
        }
        else{
            dangerCells = crackCells;
            potentialDangerCells = potentialCrackCells;
        }

        ArrayList<Cell> adjUnknownCells = getAllAdjacentCells();
        adjUnknownCells.removeAll(exploredCells);

        for(Cell c : adjUnknownCells){
            if(contains(potentialDangerCells,c))
                potentialDangerCells.put(c, potentialDangerCells.get(c)+100.0/adjUnknownCells.size());
            else
                potentialDangerCells.put(c, 100.0/adjUnknownCells.size());
        }

        for (Map.Entry<Cell, Double> entry : potentialDangerCells.entrySet()) {
            if(entry.getValue() >= 70 && !contains(dangerCells, entry.getKey())){
                dangerCells.add(entry.getKey()); 
            }
        }
        potentialDangerCells.keySet().removeAll(dangerCells);
    }
    
    /**
     * This method create a new instance of the cell in the considered direction, according to the current position of the player.
     * @param dir the considered direction.
     * @return a new instance of the cell in the considered direction.
     */
    private Cell getCellByDirection(Direction dir){
        switch(dir){
            case UP: return new Cell(currentCell.getRow()-1, currentCell.getCol());
            case DOWN: return new Cell(currentCell.getRow()+1, currentCell.getCol());
            case LEFT: return new Cell(currentCell.getRow(), currentCell.getCol()-1);
            case RIGHT: return new Cell(currentCell.getRow(), currentCell.getCol()+1);
            default: return null;
        }
    }
    
    /**
     * This method is the opposite of {@link #getCellByDirection(ia.magical.forest.environment.Direction)}. It returns the direction of the considered cell, according to the current one.
     * @param c The considered cell.
     * @return the direction of the considered cell, or null if the cell is not adjacent to the current one.
     */
    private Direction getDirectionByCell(Cell c){
        if(currentCell.getCol() == c.getCol() && currentCell.getRow()+1 == c.getRow())
            return Direction.DOWN;
        else if(currentCell.getCol() == c.getCol() && currentCell.getRow()-1 == c.getRow())
            return Direction.UP;
        else if(currentCell.getCol()+1 == c.getCol() && currentCell.getRow() == c.getRow())
            return Direction.RIGHT;
        else if(currentCell.getCol()-1 == c.getCol() && currentCell.getRow() == c.getRow())
            return Direction.LEFT;
        else {
            return null;
        }
    }
    
    /**
     * Returns all the adjacent possible cells.
     * @return all the adjacent possible cells.
     */
    private ArrayList<Cell> getAllAdjacentCells(){
        ArrayList<Cell> result = new ArrayList<>();
        for(Direction dir : main.getPossibleDirection()){
            result.add(getCellByDirection(dir));
        }
        return result;
    }
    
    /**
     * Check wether or not the given hashmap contains the given cell.
     * @param list An hashmap of cells and double.
     * @param c A cell.
     * @return True if list contains c, false if not.
     */
    private boolean contains(HashMap<Cell, Double> list, Cell c){
        for (Map.Entry<Cell, Double> entry : list.entrySet()) {
            if(entry.getKey().equals(c))
                return true;
        }
        return false;        
    }
    
    /**
     * Check wether or not the given arraylist contains the given cell.
     * @param list An arraylist of cells.
     * @param c A cell.
     * @return True if list contains c, false if not.
     */
    private boolean contains(ArrayList<Cell> list, Cell c){
        for(Cell cell : list) {
            if(cell.equals(c))
                return true;
        }
        return false; 
    }
    
    /**
     * This class presents a small cell object, with coordinates relative to the initial cell of the player.
     */
    private class Cell{
        private int row;
        private int col;
        
        public Cell(int row, int col){
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }
        
        @Override
        public String toString(){
            return "["+this.row+","+this.col+"]";
        }
        
        @Override
        public boolean equals(Object o){
            return ((o instanceof Cell) && (((Cell)o).getRow() == this.getRow()) && (((Cell)o).getCol() == this.getCol()));
        }
        
        @Override
        public int hashCode(){
            return 1000*col+row;
        }
    }
}
