package player;

import Main.Controller;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import ia.magical.forest.environment.Direction;
import ia.magical.forest.environment.Entity;
import java.util.ArrayList;
import java.util.HashMap;
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
    private Cell initialCell;
    private Cell currentCell;
    private Cell lastCell;
    private Direction lastCellByDirection;
    private Controller main;
    private ArrayList<Cell> crackCells;
    private HashMap<Cell, Double> potentialCrackCells;
    private ArrayList<Cell> monsterCells;
    private HashMap<Cell, Double> potentialMonsterCells;
    private Action action;
    private Direction direction;

    public Player(Controller main){
        this.main = main;
        initialCell = new Cell(0, 0);
        currentCell = initialCell;
        lastCell = currentCell;
        exploredCells = new ArrayList<>();
        crackCells = new ArrayList<>();
        monsterCells = new ArrayList<>();
        potentialCrackCells = new HashMap<>();
        potentialMonsterCells = new HashMap<>();
    }
     
    public void act(){
        inference();
        switch(action){
            case MOVE: main.movePlayer(direction);
            case PORTAL: main.throughPortal();
            case HIT: main.hit(direction);
        }
    }
    
    public void inference(){        
                
        //Player on portal cell
        if (main.isCellSth(Entity.PORTAL)) {
            action = Action.PORTAL;
        }
        else if (main.isCellSth(Entity.WIND) && !exploredCells.contains(currentCell))
            thinkMonstersCracks(Entity.WIND);
        else if (main.isCellSth(Entity.SMELL) && !exploredCells.contains(currentCell))
            thinkMonstersCracks(Entity.SMELL);
        else if(!exploredCells.contains(currentCell)){
            potentialCrackCells.remove(currentCell);
            potentialMonsterCells.remove(currentCell);
        }
        
        ArrayList<Cell> adjCells = getAllAdjacentCells();
        ArrayList<Direction> ways = new ArrayList<>();
        for(Cell c : adjCells){
            if(!potentialCrackCells.containsKey(c) && !potentialMonsterCells.containsKey(c) && !crackCells.contains(c) && ! monsterCells.contains(c))
                ways.add(getDirectionByCell(c));
        }
        action = Action.MOVE;
        direction = ways.get(new Random().nextInt(ways.size()));
    }
    
    private void thinkMonstersCracks(Entity smellOrWind){
        ArrayList<Cell> dangerCells;
        HashMap<Cell, Double> potentialDangerCells;
        
        if(smellOrWind == Entity.SMELL){
            dangerCells = monsterCells;
            potentialDangerCells = potentialMonsterCells;

        }
        else {
            dangerCells = crackCells;
            potentialDangerCells = potentialCrackCells;
        }

        ArrayList<Cell> adjUnknownCells = getAllAdjacentCells();
        adjUnknownCells.removeAll(exploredCells);

        for(Cell c : adjUnknownCells){
            if(potentialDangerCells.containsKey(c))
                potentialDangerCells.put(c, potentialDangerCells.get(c)+100.0/adjUnknownCells.size());
            else
                potentialDangerCells.put(c, 100.0/adjUnknownCells.size());
        }

        for (java.util.Map.Entry<Cell, Double> entry : potentialDangerCells.entrySet()) {
            if(entry.getValue() >= 90 && !dangerCells.contains(entry.getKey()))
                dangerCells.add(entry.getKey());                
        }
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
        System.out.println("");
        if(currentCell.getCol() == c.getCol() && currentCell.getRow() == c.getRow()+1)
            return Direction.DOWN;
        else if(currentCell.getCol() == c.getCol() && currentCell.getRow() == c.getRow()-1)
            return Direction.UP;
        else if(currentCell.getCol() == c.getCol()+1 && currentCell.getRow() == c.getRow())
            return Direction.RIGHT;
        else if(currentCell.getCol() == c.getCol()-1 && currentCell.getRow() == c.getRow())
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
    }
}
