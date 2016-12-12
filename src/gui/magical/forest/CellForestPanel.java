/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.magical.forest;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe représentant une cellule exterieur (où le joueur ne peut pas aller) de la forêt (/la grille)
 * @author Maxime
 */
public class CellForestPanel extends JPanel{
    //la forêt / la grille
    private GridPanel gp;
    /**
     * Constructeur de la classe qui permet de créer une cellule de type forêt (pour l'affiche d'un arbre sur l'interface grahpique)
     * @param gp = la forêt (/la grille)
     */
    public CellForestPanel(GridPanel gp){
        this.gp = gp;
        JPanel jpT = new JPanel();
        JLabel tree = new JLabel(new ImageIcon(((new ImageIcon("../ressources/tree.png")).getImage()).getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
        jpT.setOpaque(false);
        jpT.add(tree);
        jpT.setVisible(true); 
        add(jpT);
        
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
    
}
