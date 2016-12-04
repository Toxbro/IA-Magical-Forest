/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.magical.forest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Maxime
 */
public class CellForestPanel extends JPanel{

    private GridPanel gp;
    private int coordX;
    private int coordY;
    
    public CellForestPanel(GridPanel gp, int x, int y){
        this.gp = gp;
        this.coordX=x;
        this.coordY=y;
        
        JPanel jpT = new JPanel();
        JLabel tree = new JLabel(new ImageIcon(((new ImageIcon("ressources/tree.png")).getImage()).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)));
        jpT.setOpaque(false);
        jpT.add(tree);
        jpT.setVisible(true);
        add(jpT);
        
    }
        
    public GridPanel getGp() {
        return gp;
    }

    public void setGp(GridPanel gp) {
        this.gp = gp;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }
    
}
