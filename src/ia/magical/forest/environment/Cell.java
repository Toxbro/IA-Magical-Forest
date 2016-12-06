/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia.magical.forest.environment;

/**
 * Class representing a cell of the map
 * @author Thomas
 */
public class Cell {
    
    /**
     * The row of the cell
     */
    private int row;
    
    /**
     * The column of the cell
     */
    private int col;
    
    /**
     * If the cell contains a monster
     */
    private boolean hasMonster;
    
    /**
     * If the cell contains a crevasse
     */
    private boolean hasCrevasse;
    
    /**
     * If the cell is smelling (monster close)
     */
    private boolean isSmelling;
    
    /**
     * If the cell is windy (crevasse close)
     */
    private boolean isWindy;
    
    /**
     * If the cell is shiny 
     */
    private boolean isShiny = false;
    
    /**
     * Constructor of the class
     * @param row The row of the cell
     * @param col The column of the cell
     */
    public Cell(int row, int col){
        this.row = row;
        this.col = col;
    }
    
    /**
     * Overriding of the equals method used to compare two cells
     * @param o The object to compare to
     * @return If the given object is a cell and equals the current cell
     */
    @Override
    public boolean equals(Object o){
        return ((o instanceof Cell) && (((Cell)o).getRow() == this.getRow()) && (((Cell)o).getCol() == this.getCol()));
    }

    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return the col
     */
    public int getCol() {
        return col;
    }

    /**
     * @param col the col to set
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * @return the hasMonster
     */
    public boolean isHasMonster() {
        return hasMonster;
    }

    /**
     * @param hasMonster the hasMonster to set
     */
    public void setHasMonster(boolean hasMonster) {
        this.hasMonster = hasMonster;
    }

    /**
     * @return the hasCrevasse
     */
    public boolean isHasCrevasse() {
        return hasCrevasse;
    }

    /**
     * @param hasCrevasse the hasCrevasse to set
     */
    public void setHasCrevasse(boolean hasCrevasse) {
        this.hasCrevasse = hasCrevasse;
    }

    /**
     * @return the isSmelling
     */
    public boolean isIsSmelling() {
        return isSmelling;
    }

    /**
     * @param isSmelling the isSmelling to set
     */
    public void setIsSmelling(boolean isSmelling) {
        this.isSmelling = isSmelling;
    }

    /**
     * @return the isWindy
     */
    public boolean isIsWindy() {
        return isWindy;
    }

    /**
     * @param isWindy the isWindy to set
     */
    public void setIsWindy(boolean isWindy) {
        this.isWindy = isWindy;
    }
    
    /**
     * Overriding of the toString method to display information about the current cell
     * @return 
     */
    @Override
    public String toString(){
        return "["+this.row+","+this.col+"]";
    }

    /**
     * @return the isShiny
     */
    public boolean isIsShiny() {
        return isShiny;
    }

    /**
     * @param isShiny the isShiny to set
     */
    public void setIsShiny(boolean isShiny) {
        this.isShiny = isShiny;
    }
}
