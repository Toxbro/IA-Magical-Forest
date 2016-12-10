/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Main.Controller;
import ia.magical.forest.environment.Cell;
import ia.magical.forest.environment.Map;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thomas
 */
public class MapTests {
    
    static Map map;
    
    public MapTests() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        map = new Map(new Controller(), 3);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testConstructor(){
        ArrayList<Cell> monsterCell = map.getMonsterCells();
        ArrayList<Cell> crevasseCell = map.getCrevasseCells();
        assertNotNull(monsterCell);
        assertNotNull(crevasseCell);
        assertNotEquals(monsterCell, crevasseCell);
        System.out.println("Monster cell : "+monsterCell);
        System.out.println("Crevasse cell : "+crevasseCell);
    }
    
    @Test
    public void testCloseCellsCenter(){
        ArrayList<Cell> returnCells = map.getCloseCells(map.getCells().get(4));
        assertEquals(4, returnCells.size());
    }
    
    @Test
    public void testCloseCellsTopLeft(){
        ArrayList<Cell> returnCells = map.getCloseCells(map.getCells().get(0));
        assertEquals(2, returnCells.size());
    }
    
    @Test
    public void testCloseCellsTopRight(){
        ArrayList<Cell> returnCells = map.getCloseCells(map.getCells().get(2));
        assertEquals(2, returnCells.size());
    }
    
    @Test
    public void testCloseCellsBottomLeft(){
        ArrayList<Cell> returnCells = map.getCloseCells(map.getCells().get(6));
        assertEquals(2, returnCells.size());
    }
    
    @Test
    public void testCloseCellsBottomRight(){
        ArrayList<Cell> returnCells = map.getCloseCells(map.getCells().get(8));
        assertEquals(2, returnCells.size());
    }
    
    @Test
    public void testCloseCellsTopCenter(){
        ArrayList<Cell> returnCells = map.getCloseCells(map.getCells().get(1));
        assertEquals(3, returnCells.size());
    }
    
    @Test
    public void testGetRandomCell(){
        Cell cell = map.getRandomCell();
        assertNotNull(cell);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
