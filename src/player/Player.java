package player;


import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import alice.tuprolog.NoMoreSolutionException;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Theory;
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
    
    private File theory;
    private ArrayList<Cell> exploredCells; 
    private Cell initialCell; 
    private Cell currentCell;
    private Map map;
    
    public Player(Map map){
        this.map = map;
        initialCell = new Cell(0, 0);
        Prolog engine = new Prolog();
        engine.addTheory(new Theory(readFile("./Player.pl")));
    }

    private static String readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
        return stringBuilder.toString();
    }
    
    private void moveTo(String s){
        Direction.valueOf(s)
    }
    
    private int getOdds(String s){
        Direction.valueOf(s)
    }
    
    private void observe(){
        
    }
    
    private Cell getCell(Direction dir){
        switch(dir){
            case UP: return new Cell(currentCell.getRow()-1, currentCell.getCol());
            case DOWN: return new Cell(currentCell.getRow()+1, currentCell.getCol());
            case LEFT: return new Cell(currentCell.getRow(), currentCell.getCol()-1);
            case RIGHT: return new Cell(currentCell.getRow(), currentCell.getCol()+1);
        }
        return null;
    }
}
