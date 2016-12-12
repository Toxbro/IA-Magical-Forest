package player;

import Main.Controller;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import ia.magical.forest.environment.Direction;
import ia.magical.forest.environment.Entity;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

/**
 *
 * @author quentin
 */
public class Player{
    
    private ArrayList<Cell> exploredCells;
    private Cell currentCell;
    private Controller main;
    private ArrayList<Cell> crackCells;
    private HashMap<Cell, Double> potentialCrackCells;
    private ArrayList<Cell> monsterCells;
    private HashMap<Cell, Double> potentialMonsterCells;
    private Action action;
    private Direction direction;

    public Player(Controller main){
        this.main = main;
        currentCell = new Cell(0, 0);
        exploredCells = new ArrayList<>();
        crackCells = new ArrayList<>();
        monsterCells = new ArrayList<>();
        potentialCrackCells = new HashMap<>();
        potentialMonsterCells = new HashMap<>();
    }
     
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
    
    public void inference(){        
        
        //Player on portal cell
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
            System.out.println("safe direction : "+direction);
            
            if(direction == null){
                direction = getSafestDirection();
                System.out.println("safest direction : "+direction);
            }
            
            if(direction == null)
            {
                ArrayList<Cell> knownAdjCell = getAllAdjacentCells();
                if(!exploredCells.isEmpty())
                    knownAdjCell.retainAll(exploredCells);
                direction = getDirectionByCell(knownAdjCell.get(new Random().nextInt(knownAdjCell.size())));
                System.out.println("explored direction : "+direction);
            }
            
            action = Action.MOVE;
        }
    }
    
    private Direction getSafeDirection(){
        ArrayList<Cell> safeAdjCells = getAllAdjacentCells();
        System.out.println(safeAdjCells);
        safeAdjCells.removeAll(monsterCells);
        System.out.println(safeAdjCells);
        safeAdjCells.removeAll(crackCells); 
        System.out.println(safeAdjCells);
        safeAdjCells.removeAll(new ArrayList<Cell>(potentialCrackCells.keySet()));
        System.out.println(safeAdjCells);
        safeAdjCells.removeAll(new ArrayList<Cell>(potentialMonsterCells.keySet()));
        System.out.println(safeAdjCells);
        safeAdjCells.removeAll(exploredCells);
        if(safeAdjCells.isEmpty())
            return null;
        return getDirectionByCell(safeAdjCells.get(new Random().nextInt(safeAdjCells.size())));
    }
    
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
    
    private void thinkMonstersCracks(Entity smellOrWind){
        ArrayList<Cell> dangerCells;
        HashMap<Cell, Double> potentialDangerCells;
        
        if(smellOrWind == Entity.SMELL){
            dangerCells = monsterCells;
            potentialDangerCells = potentialMonsterCells;
        }
        else if(smellOrWind == Entity.WIND){
            dangerCells = crackCells;
            potentialDangerCells = potentialCrackCells;
        }
        else
            return;

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
    
//    private void explore() {
//        ArrayList<Cell> adjacent = getAllAdjacentCells();
//        ArrayList<Cell> unknown = new ArrayList<Cell>();
//        for(Cell c : adjacent) {
//            if(!exploredCells.contains(c)) {
//                grid.put(c, 0);
//                unknown.add(c);
//                cellTotal--;
//            }
//        }
//        if(cellTotal == 0)
//            isExploDone = true;
//        else
//            if(unknown.size() == 0){
//                move(getDir(currentCell, adjacent.get((int) (Math.random()*adjacent.size()))));
//            }
//            else
//                move(getDir(currentCell, unknown.get((int) (Math.random() * unknown.size()))));
//    }
    
    private Cell getCellByDirection(Direction dir){
        switch(dir){
            case UP: return new Cell(currentCell.getRow()-1, currentCell.getCol());
            case DOWN: return new Cell(currentCell.getRow()+1, currentCell.getCol());
            case LEFT: return new Cell(currentCell.getRow(), currentCell.getCol()-1);
            case RIGHT: return new Cell(currentCell.getRow(), currentCell.getCol()+1);
            default: return null;
        }
    }
    
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
    
    private ArrayList<Cell> getAllAdjacentCells(){
        ArrayList<Cell> result = new ArrayList<>();
        for(Direction dir : main.getPossibleDirection()){
            result.add(getCellByDirection(dir));
        }
        return result;
    }
    
    private boolean contains(HashMap<Cell, Double> list, Cell c){
        for (Map.Entry<Cell, Double> entry : list.entrySet()) {
            if(entry.getKey().equals(c))
                return true;
        }
        return false;        
    }
    
    private boolean contains(ArrayList<Cell> list, Cell c){
        for(Cell cell : list) {
            if(cell.equals(c))
                return true;
        }
        return false; 
    }
    
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
