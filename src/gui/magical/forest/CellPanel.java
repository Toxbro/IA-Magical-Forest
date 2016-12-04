/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.magical.forest;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Maxime
 */
public class CellPanel extends JPanel{
    private GridPanel gp;
    private int coordX;
    private int coordY;
    
    public CellPanel(GridPanel gp, int x, int y, int type){
        this.gp = gp;
        this.coordX=x;
        this.coordY=y;
        
        setLayout(new GridLayout(1,2));
        
        JPanel jpGamer = new JPanel();
        JPanel jpItem = new JPanel();
        
        JLabel gamer = new JLabel(new ImageIcon(((new ImageIcon("ressources/gamer.png")).getImage()).getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
        JLabel item = new JLabel();
        switch(type){
            case 0:
                break;
            case 1:
                item = new JLabel(new ImageIcon(((new ImageIcon("ressources/stargate.png")).getImage()).getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
                break;
            case 2:
                item = new JLabel(new ImageIcon(((new ImageIcon("ressources/poop.png")).getImage()).getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
                break;
            case 3:
                item = new JLabel(new ImageIcon(((new ImageIcon("ressources/monster.png")).getImage()).getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
                break;
            case 4:
                item = new JLabel(new ImageIcon(((new ImageIcon("ressources/cloud.png")).getImage()).getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
                break;
            case 5:
                item = new JLabel(new ImageIcon(((new ImageIcon("ressources/crevasse.png")).getImage()).getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
                break;
            default:
                break;
        }
        
        jpGamer.setOpaque(false);
        jpItem.setOpaque(false);
        
        jpGamer.add(gamer);
        jpItem.add(item);
        
        jpGamer.setVisible(false);
        jpItem.setVisible(true);
        
        add(jpGamer);
        add(jpItem);
        
        
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
