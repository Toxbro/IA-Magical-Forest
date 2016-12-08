/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Main.Controller;
import ia.magical.forest.environment.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import player.Player;

/**
 *
 * @author Thomas
 */
public class PlayerTests {
    
    static Controller controller;
    static Player player;
    
    public PlayerTests() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        controller = new Controller();
        player = new Player(controller);
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
    
   

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
