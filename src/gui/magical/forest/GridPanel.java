/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.magical.forest;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

/**
 * Classe représentant la forêt (la grille) de l'interface graphique
 * @author Maxime
 */
public class GridPanel extends JPanel{
    private Border border;
    private GridBagConstraints gbc = new GridBagConstraints();
    private gui.magical.forest.GUIMain main;
    private int taille;
    private int[][] listNumCell;
    
    public void initialize(gui.magical.forest.GUIMain main, int tailleForet){
        /*taille de la grille = taille de la Foret + 2
        *  2 = le contour qui accueil les arbres
        */
        this.taille = tailleForet+2;
        this.listNumCell = setNumCell(taille);        
        this.setMain(main);
        setLayout(new GridBagLayout());
        //construction et ajout des cellules à la grille
        for (int row = 0; row < taille; row++) {
            for (int col = 0; col < taille; col++) {
                gbc.gridx = col;
                gbc.gridy = row;
                //construction de la forêt graphique exterieur (les arbres)
                if(row == 0 || col == 0 || row == (taille - 1) || col == (taille - 1)){
                    CellForestPanel cfp = new CellForestPanel(this);
                    add(cfp, gbc); 
                }
                //construction de la fôret graphique interieur (où le joueur progresse)
                else{
                    if(row == taille -2 && col != taille -2){
                        CellPanel cp = new CellPanel(this);
                        border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                        cp.setBorder(border);
                        add(cp,gbc);
                    }else if(row != taille -2 && col == taille -2){
                        CellPanel cp = new CellPanel(this);
                        border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                        cp.setBorder(border);
                        add(cp,gbc);
                    }else if(row == taille -2 && col == taille -2){
                        CellPanel cp = new CellPanel(this);
                        border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                        cp.setBorder(border);
                        add(cp,gbc);
                    }else{
                        CellPanel cp = new CellPanel(this);
                        border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                        cp.setBorder(border);
                        add(cp,gbc);
                    } 
                }  
            }
        }        
    }

    /**
     * @return the main
     */
    public gui.magical.forest.GUIMain getMain() {
        return main;
    }

    /**
     * @param main the main to set
     */
    public void setMain(gui.magical.forest.GUIMain main) {
        this.main = main;
    }
    
    /**
     * Méthode qui permet d'initialiser le numéro des cellules
     * @param taille
     * @return  une liste de dimension 2 avec les numéros de cellule des cases
     */
    public int[][] setNumCell(int taille){
        int numCell = 0;
        int[][] listNC = new int[taille][taille];
        for(int i=0; i<taille; i++){
            for (int j=0; j<taille; j++){
                listNC[j][i] = numCell;
                numCell++;
            }
        }
        return listNC;
    }
    
    /**
     * Méthode qui permet d'ajouter à l'interface graphique le gamer du niveau
     * @param col = position X du joueur
     * @param row = position Y du joueur
     */
    public void addGamer(int col, int row){
        Component[] lc = this.getComponents();
        CellPanel cpGame = (CellPanel) lc[listNumCell[col+1][row+1]];
        cpGame.getComponent(0).setVisible(true);
    }
    /**
     * Méthode qui permet d'enlever à l'interface graphique le gamer du niveau
     * @param col = position X du joueur
     * @param row = position Y du joueur
     */
    public void delGamer(int col, int row){
        Component[] lc = this.getComponents();
        CellPanel cpGame = (CellPanel) lc[listNumCell[col+1][row+1]];
        cpGame.getComponent(0).setVisible(false);
    }
    
    
    /**
     * Méthode qui permet d'ajouter à l'interface graphique le portail du niveau
     * 
     * @param col = position X du portail
     * @param row = position Y du portail
     */
    public void addStargate(int col, int row){
        Component[] lc = this.getComponents();
        CellPanel cpStargate = (CellPanel) lc[listNumCell[col+1][row+1]];
        cpStargate.setItem(1);
        cpStargate.getComponent(2).setVisible(true);
    }  

    /**
     * Méthode qui permet d'ajouter un monstre à l'interface graphique
     * @param col = position X du monstre
     * @param row = position Y du monstre
     */
    public void addMonster(int col, int row){
        Component[] lc = this.getComponents();
        CellPanel cpMonster = (CellPanel) lc[listNumCell[col+1][row+1]];
        cpMonster.setItem(3);
        cpMonster.getComponent(2).setVisible(true);       
    }

    /**
     * Méthode qui ajoute une case odorante à l'interface graphique
     * @param col = position X de la case odorante
     * @param row = position Y de la case odorante
     */
    public void addPoop(int col, int row){
        Component[] lc = this.getComponents();
        CellPanel poop = (CellPanel) lc[this.listNumCell[col+1][row+1]];
        poop.getComponent(1).setVisible(true);        

        //CellPanel cpStargate = (CellPanel) lc[listNumCell[map.getPortalCell().getCol()+1][map.getPortalCell().getRow()+1]];
        //cpStargate.getComponent(2).setVisible(true);
    }
    
    /**
     * Méthode qui permet de retirer un monstre de l'interface graphique (un monstre qui a été tué par le joueur)
     * @param col = position X du monstre
     * @param row = position Y du monstre
     */
    public void delMonster(int col, int row){
        Component[] lc = this.getComponents();
        CellPanel monster = (CellPanel) lc[this.listNumCell[col+1][row+1]];
        monster.getComponent(2).setVisible(false);
    }

    /**
     * Méthode qui permet d'enlever une case odorante de l'interface grahpique
     * @param col = position X de la case odorante
     * @param row = position Y de la case odorante
     */
    public void delPoop(int col, int row){
        Component[] lc = this.getComponents();
        CellPanel poop = (CellPanel) lc[this.listNumCell[col+1][row+1]];
        poop.getComponent(1).setVisible(false);
    }

    /**
     * Méthode qui permet d'ajouter une crevasse à l'interface graphique
     * @param col = position X de la crevasse
     * @param row = position Y de la crevasse
     */
    public void addCrevasse(int col, int row){
        Component[] lc = this.getComponents();
        CellPanel cpCrevasses = (CellPanel) lc[listNumCell[col+1][row+1]];
        cpCrevasses.setItem(2);
        cpCrevasses.getComponent(2).setVisible(true);
    }

    /**
     * Méthode qui permet d'ajouter une case venteuse à l'interface graphique
     * @param col = position X de la case venteuse
     * @param row = position Y de la case venteuse
     */
    public void addCloud(int col, int row){
        Component[] lc = this.getComponents();
        CellPanel cloud = (CellPanel) lc[this.listNumCell[col+1][row+1]];
        cloud.getComponent(3).setVisible(true);
    } 
}
