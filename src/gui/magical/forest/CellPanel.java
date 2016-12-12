/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.magical.forest;

import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe représentant une cellule intérieur (où le joueur se déplace) de la forêt (/la grille)
 * @author Maxime
 */
public class CellPanel extends JPanel{
    /**
     * Cell representation :
     *  Gamer|Poop      0|1
     *  Item |Cloud     2|3
     * 
     * Item = Stargate or Monster or Crevasse
     */
    private GridPanel gp;
    private JLabel item;
    private JPanel jpGamer;
    private JPanel jpPoop;
    private JPanel jpItem;
    private JPanel jpCloud;
    /**
     * Constructeur de la classe qui permet de créer une cellule
     * @param gp = la forêt (/la grille)
     * @param type = représente l'item qui ira dans la sous case n°2 de la cellule
     */
    public CellPanel(GridPanel gp){
        //la forêt / la grille
        this.gp = gp;
        //layout de la cellule
        setLayout(new GridLayout(2,2));
        
        jpGamer = new JPanel();
        jpPoop = new JPanel();
        jpItem = new JPanel();
        jpCloud = new JPanel();
        
        JLabel gamer = new JLabel(new ImageIcon(((new ImageIcon("../ressources/gamer.png")).getImage()).getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
        JLabel poop = new JLabel(new ImageIcon(((new ImageIcon("../ressources/poop.png")).getImage()).getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
        JLabel cloud = new JLabel(new ImageIcon(((new ImageIcon("../ressources/cloud.png")).getImage()).getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
        
        jpGamer.setOpaque(false);
        jpPoop.setOpaque(false);
        jpItem.setOpaque(false);
        jpCloud.setOpaque(false);
        
        jpGamer.add(gamer);
        jpPoop.add(poop);
        jpCloud.add(cloud);
        
        jpGamer.setVisible(false);
        jpPoop.setVisible(false);
        jpItem.setVisible(false);
        jpCloud.setVisible(false);
        
        add(jpGamer);
        add(jpPoop);
        add(jpItem);
        add(jpCloud);
    }
    /**
     * getter de la forêt / la grille
     * @return la forêt / la grille
     */
    public GridPanel getGp() {
        return gp;
    }
    /**
     * setter de la forêt / la grille
     */
    public void setGp(GridPanel gp) {
        this.gp = gp;
    }

    /**
     * Méthode qui permet d'initialiser l'image en fonction de l'item
     * @param type : l'item de la case
     */
    public void setItem(int type) {
        switch(type){
            case 0:
                break;
            case 1:
                this.item = new JLabel(new ImageIcon(((new ImageIcon("../ressources/stargate.png")).getImage()).getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
                this.jpItem.add(this.item);
                this.jpItem.setVisible(true);
                break;
            case 2:
                this.item = new JLabel(new ImageIcon(((new ImageIcon("../ressources/crevasse.png")).getImage()).getScaledInstance(25,25, java.awt.Image.SCALE_SMOOTH)));
                this.jpItem.add(this.item);
                this.jpItem.setVisible(true);
                break;
            case 3:
                this.item = new JLabel(new ImageIcon(((new ImageIcon("../ressources/monster.png")).getImage()).getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH)));
                this.jpItem.add(this.item);
                this.jpItem.setVisible(true);
                break;
            default:
                break;
        }
    }
}
