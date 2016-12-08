/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ia.magical.forest.environment;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class representing the map
 * @author Thomas
 */
public class Map {
    
    /**
     * The size of the map
     */
    private int size;
    
    /**
     * List of all the cells in the current map
     */
    private ArrayList<Cell> cells;
    
    /**
     * The player
     */
    //private Player player;
    
    /**
     * The monster cell
     */
    private Cell monsterCell;
    
    /**
     * The crevasse cell
     */
    private Cell crevasseCell;
    
    /**
     * Smelling cells
     */
    private ArrayList<Cell> smellingCells;
    
    /**
     * Windy cells
     */
    private ArrayList<Cell> windyCells;
    
    /**
     * Player cell
     */
    private Cell playerCell;
    
    /**
     * Portal cell
     */
    private Cell portalCell;
    
    /**
     * Constructor of the class
     * @param size Size of the map
     */
    public Map(int size){
        this.size = size;
        cells = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells.add(new Cell(i, j));
            }
        }
        
        //Special cells initialization
        monsterCell = getRandomCell();
        crevasseCell = monsterCell;
        while (crevasseCell == monsterCell) {
            crevasseCell = getRandomCell();
        }
        
        //Setting boolean
        smellingCells = getCloseCells(monsterCell);
        windyCells = getCloseCells(crevasseCell);
        for (Cell cell : smellingCells) {
            cell.setIsSmelling(true);
            cell.setHasMonster(false);
            cell.setHasCrevasse(false);
        }
        for (Cell cell : windyCells) {
            cell.setIsWindy(true);
            cell.setHasMonster(false);
            cell.setHasCrevasse(false);
        }
        
        monsterCell.setHasMonster(true);
        monsterCell.setHasCrevasse(false);
        
        crevasseCell.setHasMonster(false);
        crevasseCell.setHasCrevasse(true);
        
        portalCell = monsterCell;
        while ((portalCell == monsterCell) || (portalCell == crevasseCell)) {
            portalCell = getRandomCell();
        }
        portalCell.setIsShiny(true);
        
        playerCell = monsterCell;
        while ((playerCell == monsterCell) || (playerCell == crevasseCell)) {
            playerCell = getRandomCell();
        }
        System.out.println("Monster cell : "+monsterCell);
        System.out.println("Crevasse cell : "+crevasseCell);
        System.out.println("Portal cell : "+portalCell);
        System.out.println("Player cell : "+playerCell);
    }
    
    /**
     * Method called to get a random number
     * @param max The maximum limit of the random
     * @return Random number
     */
    private int getRand(int max){
        Random ran = new Random();
        return ran.nextInt(max);
    }
    
    /**
     * Method called to get a random cell
     * @return Random cell
     */
    public Cell getRandomCell(){
        int row = getRand(size);
        int col = getRand(size);
        return cells.get(cells.indexOf(new Cell(row,col)));
    }
    
    /**
     * Method called to get neighboring cells
     * @param cell The cell to look around
     * @return Neighboring cells
     */
    public ArrayList<Cell> getCloseCells(Cell cell){
        ArrayList<Cell> closeCells = new ArrayList<>();
        int posOfCell = getCells().indexOf(cell);
        
        //Row upside
        if (posOfCell-size >= 0) {
            closeCells.add(cells.get(posOfCell-size));
        }
        
        //Left column
        if (posOfCell-1 >= 0) {
            Cell previousCell = cells.get(posOfCell-1);
            //Test if same row
            if (previousCell.getRow() == cell.getRow()) {
                closeCells.add(previousCell);
            }
        }
        
        //Right column
        if (posOfCell+1 < cells.size()) {
            Cell nextCell = cells.get(posOfCell+1);
            //Test if same row
            if (nextCell.getRow() == cell.getRow()) {
                closeCells.add(nextCell);
            }
        }
        
        //Bottom row
        if (posOfCell+size < cells.size()) {
            closeCells.add(cells.get(posOfCell+size));
        }
        return closeCells;
    }
    
    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }
    
    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }
    
    /**
     * @return the cells
     */
    public ArrayList<Cell> getCells() {
        return cells;
    }
    
    /**
     * @param cells the cells to set
     */
    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }
    
    /**
     * @return the monsterCell
     */
    public Cell getMonsterCell() {
        return monsterCell;
    }
    
    /**
     * @param monsterCell the monsterCell to set
     */
    public void setMonsterCell(Cell monsterCell) {
        this.monsterCell = monsterCell;
    }
    
    /**
     * @return the crevasseCell
     */
    public Cell getCrevasseCell() {
        return crevasseCell;
    }
    
    /**
     * @param crevasseCell the crevasseCell to set
     */
    public void setCrevasseCell(Cell crevasseCell) {
        this.crevasseCell = crevasseCell;
    }
    
    /**
     * Method called to get a cell by a direction from another cell
     * @param cell Initial cell
     * @param direction Direction of the next cell
     * @return The cell if exists, null else
     */
    public Cell getCellByDirection(Cell cell, Direction direction){
        int posOfCell = getCells().indexOf(cell);
        switch(direction){
            case UP:
                if (posOfCell-size >= 0) {
                    return cells.get(posOfCell-size);
                }
                break;
                
            case DOWN:
                if (posOfCell+size < cells.size()) {
                    return cells.get(posOfCell+size);
                }
                break;
                
            case LEFT:
                if (posOfCell-1 >= 0) {
                    Cell previousCell = cells.get(posOfCell-1);
                    //Test if same row
                    if (previousCell.getRow() == cell.getRow()) {
                        return previousCell;
                    }
                }
                break;
                
            case RIGHT:
                if (posOfCell+1 < cells.size()) {
                    Cell nextCell = cells.get(posOfCell+1);
                    //Test if same row
                    if (nextCell.getRow() == cell.getRow()) {
                        return nextCell;
                    }
                }
                break;
        }
        return null;
    }
    
    /**
     * Method called to get the direction from a cell to another
     * @param start Cell starting from
     * @param end Targeted cell
     * @return The direction if possible, null else
     */
    public Direction getDirectionFromCell(Cell start, Cell end){
        Direction direction;
        int posOfCellStart = getCells().indexOf(start);
        int posOfCellEnd = getCells().indexOf(end);
        int dif = posOfCellEnd - posOfCellStart;
        if ((dif == -1) && (start.getRow() == end.getRow())) {
            direction = Direction.LEFT;
        }
        else if ((dif == 1) && (start.getRow() == end.getRow())){
            direction = Direction.RIGHT;
        }
        else if (dif == size){
            direction = Direction.DOWN;
        }
        else if (dif == -size){
            direction = Direction.UP;
        }
        else{
            direction = null;
        }
        return direction;
    }

    /**
     * @return the playerCell
     */
    public Cell getPlayerCell() {
        return playerCell;
    }

    /**
     * @return the portalCell
     */
    public Cell getPortalCell() {
        return portalCell;
    }
}
