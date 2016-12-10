/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ia.magical.forest.environment;

import Main.Controller;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class representing the map
 * @author Thomas
 */
public class Map {
    
    /**
     * The controller of the program
     */
    private Controller main;
    
    /**
     * The size of the map
     */
    private int size;
    
    /**
     * List of all the cells in the current map
     */
    private ArrayList<Cell> cells;
    
    /**
     * Monsters cells
     */
    private ArrayList<Cell> monsterCells;
    
    /**
     * The crevasse cell
     */
    private ArrayList<Cell> crevasseCells;
    
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
    public Map(Controller controller, int size){
        this.main = controller;
        this.size = size;
        cells = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells.add(new Cell(i, j));
            }
        }
        
        //Special cells initialization
        crevasseCells = new ArrayList<>();
        monsterCells = new ArrayList<>();
        smellingCells = new ArrayList<>();
        windyCells = new ArrayList<>();
        
        int numberOfObstacle = (int)(size/3);
        
        for (int i = 0; i < numberOfObstacle; i++) {
            Cell monsterCell;
            monsterCell = getRandomCell();
            while (monsterCells.contains(monsterCell) || crevasseCells.contains(monsterCell)) {
                monsterCell = getRandomCell();
            }
            monsterCells.add(monsterCell);
            Cell crevasseCell = monsterCell;
            while (crevasseCell == monsterCell || monsterCells.contains(crevasseCell) || crevasseCells.contains(crevasseCell)) {
                crevasseCell = getRandomCell();
            }
            crevasseCells.add(crevasseCell);
        }
        
        //Setting boolean
        for (Cell monsterCell : monsterCells) {
            ArrayList<Cell> closeCells = getCloseCells(monsterCell);
            for (Cell closeCell : closeCells) {
                if (!smellingCells.contains(closeCell)) {
                    smellingCells.add(closeCell);
                }
            }
            main.putEntity(monsterCell.getRow(), monsterCell.getCol(), Entity.MONSTER);
            monsterCell.setHasMonster(true);
            monsterCell.setHasCrevasse(false);
        }
        for (Cell crevasseCell : crevasseCells) {
            ArrayList<Cell> closeCells = getCloseCells(crevasseCell);
            for (Cell closeCell : closeCells) {
                if (!windyCells.contains(closeCell)) {
                    windyCells.add(closeCell);
                }
            }
            main.putEntity(crevasseCell.getRow(), crevasseCell.getCol(), Entity.CRACK);
            crevasseCell.setHasMonster(false);
            crevasseCell.setHasCrevasse(true);
        }
        for (Cell cell : smellingCells) {
            cell.setIsSmelling(true);
            cell.setHasMonster(false);
            cell.setHasCrevasse(false);
            main.putEntity(cell.getRow(), cell.getCol(), Entity.SMELL);
        }
        for (Cell cell : windyCells) {
            cell.setIsWindy(true);
            cell.setHasMonster(false);
            cell.setHasCrevasse(false);
            main.putEntity(cell.getRow(), cell.getCol(), Entity.WIND);
        }
        
        portalCell = monsterCells.get(0);
        while ((monsterCells.contains(portalCell)) || (monsterCells.contains(portalCell))) {
            portalCell = getRandomCell();
        }
        portalCell.setIsShiny(true);
        main.putEntity(portalCell.getRow(), portalCell.getCol(), Entity.PORTAL);
        
        playerCell = monsterCells.get(0);
        while ((monsterCells.contains(playerCell)) || (crevasseCells.contains(playerCell))) {
            playerCell = getRandomCell();
        }
        main.putEntity(playerCell.getRow(), playerCell.getCol(), Entity.PLAYER);
        System.out.println("Monster cells : "+monsterCells);
        System.out.println("Crevasse cells : "+crevasseCells);
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
    public ArrayList<Cell> getMonsterCells() {
        return monsterCells;
    }
    
    /**
     * @param monsterCell the monsterCell to set
     */
    public void setMonsterCell(ArrayList<Cell> monsterCells) {
        this.monsterCells = monsterCells;
    }
    
    /**
     * @return the crevasseCell
     */
    public ArrayList<Cell> getCrevasseCells() {
        return crevasseCells;
    }
    
    /**
     * @param crevasseCell the crevasseCell to set
     */
    public void setCrevasseCell(ArrayList<Cell> crevasseCells) {
        this.crevasseCells = crevasseCells;
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
     * @return the playerCell
     */
    public Cell getPlayerCell() {
        return playerCell;
    }
    
    /**
     * @param playerCell the playerCell to set
     */
    public void setPlayerCell(Cell playerCell) {
        if(monsterCells.contains(playerCell) || crevasseCells.contains(playerCell)){
            main.killPlayer();
        }
        else{
            main.removeEntity(this.playerCell.getRow(), this.playerCell.getCol(), Entity.PLAYER);
            this.playerCell = playerCell;        
            main.putEntity(this.playerCell.getRow(), this.playerCell.getCol(), Entity.PLAYER);
        }
    }
    
    /**
     * Method called when the player try to shoot a cell
     * @param cell The targeted cell
     */
    public void removeMonster(Cell cell){
        if (monsterCells.contains(cell)) {
            monsterCells.remove(cell);
            main.removeEntity(cell.getRow(), cell.getCol(), Entity.MONSTER);
            ArrayList<Cell> closeCells = getCloseCells(cell);
            for (Cell smellingCell : closeCells) {
                ArrayList<Cell> closeCells2 = getCloseCells(smellingCell);
                boolean cellToRemove = true;
                //Checking if close cells of the smelling cell is another monster (could be)
                for (Cell cell1 : closeCells2) {
                    if (monsterCells.contains(cell1)) {
                        cellToRemove = false;
                    }
                }
                if (cellToRemove) {
                    smellingCells.remove(smellingCell);
                    main.removeEntity(smellingCell.getRow(), smellingCell.getCol(), Entity.SMELL);
                }
            }
        }
    }
        

    /* Method called to get the direction from a cell to another
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
     * @return the portalCell
     */
    public Cell getPortalCell() {
        return portalCell;
    }
}
