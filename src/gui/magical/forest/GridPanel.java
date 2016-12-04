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
 *
 * @author Maxime
 */
public class GridPanel extends JPanel{
    public int[] etat;
    private Border border;
    private GridBagConstraints gbc = new GridBagConstraints();
    private gui.magical.forest.GUIMain main;
    private int taille;
    private int[][] listNumCell;
    private Boolean isGamer=false;
    private int[] initPosGamer;
    private int[] posActuGamer;
    private int level;
    public void initialize(gui.magical.forest.GUIMain main, int level, int[][] listItem, int[] gamer){
        //taille de la grille = 2 + level + 2
        /* 2 => première grille = 3x3 | deuxième grille = 4x4
        *  2 = le contour qui accueil les arbres
        */
        this.level = level;
        this.taille = 2+level+2;
        this.etat = new int[taille*taille];
        this.listNumCell = setNumCell(taille);
        this.initPosGamer = gamer;
        this.posActuGamer = gamer;
        this.setMain(main);
        //initialisation de l'état des cellules
        for (int i=0; i<(taille*taille); i++){
                //toutes initialisés à 0 car au démarrage il n'y a rien
                etat[i]=0;
        }	
        setLayout(new GridBagLayout());
        //construction et ajout des cellules à la grille
        for (int row = 0; row < taille; row++) {
            for (int col = 0; col < taille; col++) {
                gbc.gridx = col;
                gbc.gridy = row;
                if(row == 0 || col == 0 || row == (taille - 1) || col == (taille - 1)){
                    CellForestPanel cfp = new CellForestPanel(this,col,row);
                    add(cfp, gbc); 
                }
                else{
                    if(row == taille -2 && col != taille -2){
                        CellPanel cp = new CellPanel(this,col,row,getType(row, col, listItem));
                        border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                        cp.setBorder(border);
                        add(cp,gbc);
                    }else if(row != taille -2 && col == taille -2){
                        CellPanel cp = new CellPanel(this,col,row,getType(row, col, listItem));
                        border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                        cp.setBorder(border);
                        add(cp,gbc);
                    }else if(row == taille -2 && col == taille -2){
                        CellPanel cp = new CellPanel(this,col,row,getType(row, col, listItem));
                        border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                        cp.setBorder(border);
                        add(cp,gbc);
                    }else{
                        CellPanel cp = new CellPanel(this,col,row,getType(row, col, listItem));
                        border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                        cp.setBorder(border);
                        add(cp,gbc);
                    } 
                }  
            }
        }
        //on place le joueur sur la grille
        //etape 1 : on récupère la liste des composants de la grille
        //etape 2 : on récupère la cellule en rapport avec le gamer
        //on ajoute +1,+1 aux coordonnées du gamer pour prendre en compte la fôret
        //etape 3 : on rend le gamer visible
        Component[] lc = this.getComponents();
        CellPanel cpGame = (CellPanel) lc[listNumCell[gamer[0]+1][gamer[1]+1]];
        if(!isGamer){
            cpGame.getComponent(0).setVisible(true);
            isGamer = true;
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
    
    public int getType(int row, int col, int[][] item){
        for(int i = 0; i<item.length;i++){
            for(int j=0;j<item.length;j++){
                if(col == i+1 && row == j+1){
                    return item[i][j];
                }
            }
        }
        return 0;
    }
    
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
    
    public void moveGamer(int[] newPos){
        Component[] lc = this.getComponents();
        CellPanel exCP = (CellPanel) lc[this.listNumCell[this.posActuGamer[0]+this.level][this.posActuGamer[1]+this.level]];
        CellPanel newCP = (CellPanel) lc[this.listNumCell[newPos[0]+this.level][newPos[1]+this.level]];
        exCP.getComponent(0).setVisible(false);
        newCP.getComponent(0).setVisible(true);
        this.posActuGamer = newPos;
    }
    
    public void deadGamer(){
        moveGamer(this.initPosGamer);
    }
    
    public void killMonster(int[] posMonster){
        Component[] lc = this.getComponents();
        CellPanel monster = (CellPanel) lc[this.listNumCell[posMonster[0]+this.level][posMonster[1]+this.level]];
        monster.getComponent(1).setVisible(false);
        try{
            CellPanel poopUP  = (CellPanel) lc[this.listNumCell[posMonster[0]+this.level][posMonster[1]+this.level-1]];
            poopUP.getComponent(1).setVisible(false);
        }catch(Exception e){
            
        }
        try{
            CellPanel poopDOWN  = (CellPanel) lc[this.listNumCell[posMonster[0]+this.level][posMonster[1]+this.level+1]];
            poopDOWN.getComponent(1).setVisible(false);
        }catch(Exception e){
            
        }
        try{
            CellPanel poopLEFT  = (CellPanel) lc[this.listNumCell[posMonster[0]+this.level-1][posMonster[1]+this.level]];
            poopLEFT.getComponent(1).setVisible(false);
        }catch(Exception e){
            
        }
        try{
            CellPanel poopRIGHT  = (CellPanel) lc[this.listNumCell[posMonster[0]+this.level+1][posMonster[1]+this.level]];
            poopRIGHT.getComponent(1).setVisible(false);
        }catch(Exception e){
            
        }
    }
}
