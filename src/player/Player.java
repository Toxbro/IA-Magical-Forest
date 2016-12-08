package player;

import Main.Controller;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import ia.magical.forest.environment.Cell;
import ia.magical.forest.environment.Direction;
import ia.magical.forest.environment.Map;
import java.util.ArrayList;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

/**
 *
 * @author quentin
 */
public class Player {
    
    private ArrayList<Cell> exploredCells;
    private Cell initialCell;
    private Cell currentCell;
    private Cell lastCell;
    private Direction lastCellByDirection;
    private Controller main;
    private ArrayList<Cell> windyCells;
    private ArrayList<Cell> smellingCells;
    private ArrayList<Cell> crackCells;
    private ArrayList<Cell> potentialCrackCells;
    private ArrayList<Cell> monsterCells;
    private ArrayList<Cell> potentialMonsterCells;

    public Player(Controller main){
        this.main = main;
        initialCell = new Cell(0, 0);
        currentCell = initialCell;
        lastCell = currentCell;
        crackCells = new ArrayList<>();
        monsterCells = new ArrayList<>();
        windyCells = new ArrayList<>();
        smellingCells = new ArrayList<>();
        potentialCrackCells = new ArrayList<>();
        potentialMonsterCells = new ArrayList<>();
    }
    
    public void inference(){
        Action action;
        Direction direction;
        //Player on portal cell
        if (currentCell.isIsShiny()) {
            action = Action.PORTAL;
        }
        //Crack near
        else if (currentCell.isIsWindy()){
            //We already met some potential crack cells
            if (!potentialCrackCells.isEmpty()) {
                ArrayList<Cell> closeCells = main.getMap().getCloseCells(currentCell);
                ArrayList<Cell> lastWindyCloseCells = potentialCrackCells;
                ArrayList<Cell> toAdd = new ArrayList<>();
                potentialCrackCells.removeAll(lastWindyCloseCells);
                for (Cell closeCell : closeCells) {
                    if (lastWindyCloseCells.contains(closeCell)) {
                        toAdd.add(closeCell);
                    }
                }
                //We know the crack position
                if (toAdd.size() == 1) {
                    crackCells.addAll(toAdd);
                    //Removing all to avoid duplicate
                    windyCells.removeAll(main.getMap().getCloseCells(toAdd.get(0)));
                    windyCells.addAll(main.getMap().getCloseCells(toAdd.get(0)));
                }
                else{
                    potentialCrackCells.addAll(toAdd);
                    windyCells.add(currentCell);
                }
            }
            //First windy cell
            else{
                potentialCrackCells.addAll(main.getMap().getCloseCells(currentCell));
                potentialCrackCells.remove(lastCell);
                windyCells.add(currentCell);
            }
            action = Action.MOVE;
            direction = lastCellByDirection;
        }
        else if (currentCell.isIsSmelling()){
            boolean knowMonsterPosition = false;
            //We already met some potential monster cells
            if (!potentialMonsterCells.isEmpty()) {
                ArrayList<Cell> closeCells = main.getMap().getCloseCells(currentCell);
                ArrayList<Cell> lastSmellingCloseCells = potentialMonsterCells;
                ArrayList<Cell> toAdd = new ArrayList<>();
                potentialMonsterCells.removeAll(lastSmellingCloseCells);
                for (Cell closeCell : closeCells) {
                    if (lastSmellingCloseCells.contains(closeCell)) {
                        toAdd.add(closeCell);
                    }
                }
                //We know the monster position
                if (toAdd.size() == 1) {
                    monsterCells.addAll(toAdd);
                    //Removing all to avoid duplicate
                    monsterCells.removeAll(main.getMap().getCloseCells(toAdd.get(0)));
                    monsterCells.addAll(main.getMap().getCloseCells(toAdd.get(0)));
                    knowMonsterPosition = true;
                }
                else{
                    potentialMonsterCells.addAll(toAdd);
                    smellingCells.add(currentCell);
                }
            }
            //First smelling cell
            else{
                potentialMonsterCells.addAll(main.getMap().getCloseCells(currentCell));
                potentialMonsterCells.remove(lastCell);
                smellingCells.add(currentCell);
            }
            if (knowMonsterPosition) {
                action = Action.HIT;
                direction = main.getMap().getDirectionFromCell(monsterCells.get(0), currentCell);
            }
            else{
                action = Action.MOVE;
                direction = lastCellByDirection;
            }
        }
    }
}
