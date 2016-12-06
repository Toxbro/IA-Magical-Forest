/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.magical.forest;

import ia.magical.forest.environment.Map;
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
    private Boolean isGamer=false;
    public void initialize(gui.magical.forest.GUIMain main, int level, Map map){
        //taille de la grille = 2 + level + 2
        /* 2 => première grille = 3x3 | deuxième grille = 4x4
        *  2 = le contour qui accueil les arbres
        */
        this.taille = 2+level+2;
        this.listNumCell = setNumCell(taille);        
        this.setMain(main);
        setLayout(new GridBagLayout());
        //construction et ajout des cellules à la grille
        for (int row = 0; row < taille; row++) {
            for (int col = 0; col < taille; col++) {
                //On check le type de la cellule : ça peut-être un portail ou un monstre ou une crevasse (ou rien)
                boolean isStargate = false;
                boolean isCrevasse = false;
                boolean isMonster = false;
                int type = 0;
                if(col == map.getPortalCell().getCol()+1 && row == map.getPortalCell().getRow()+1){
                    isStargate = true;
                }
//                for(int i=0; i<map.getCrevasseCells().size(); i++){
//                    if(col == map.getCrevasseCells().get(i).getCol()+1 && row == map.getCrevasseCells().get(i).getRow()+1){
//                        isCrevasse = true;
//                    }
//                }
//                for(int i=0; i<map.getMonsterCells().size(); i++){
//                    if(col == map.getMonsterCells().get(i).getCol()+1 && row == map.getMonsterCells().get(i).getRow()+1){
//                        isMonster = true;
//                    }
//                }
                
                if(isStargate){
                    type = 1;
                }
                else if (isCrevasse){
                    type = 2;
                }else if(isMonster){
                    type = 3;
                }
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
                        CellPanel cp = new CellPanel(this,type);
                        border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                        cp.setBorder(border);
                        add(cp,gbc);
                    }else if(row != taille -2 && col == taille -2){
                        CellPanel cp = new CellPanel(this,type);
                        border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                        cp.setBorder(border);
                        add(cp,gbc);
                    }else if(row == taille -2 && col == taille -2){
                        CellPanel cp = new CellPanel(this,type);
                        border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                        cp.setBorder(border);
                        add(cp,gbc);
                    }else{
                        CellPanel cp = new CellPanel(this,type);
                        border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                        cp.setBorder(border);
                        add(cp,gbc);
                    } 
                }  
            }
        }
        //on ajoute les éléments de la map à l'interface graphique
        addGamer(map);
        addStargate(map);
        addMonsters(map);
        addCrevasses(map);
        
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
     * Méthode qui permet de déplacer le joueur sur l'interface graphique
     * @param exCol = ancienne position X du joueur
     * @param exRow = ancienne position Y du joueur
     * @param col = nouvelle position X du joueur
     * @param row = nouvelle position Y du joueur
     */
    public void moveGamer(int exCol, int exRow, int col, int row){
        Component[] lc = this.getComponents();
        CellPanel exCP = (CellPanel) lc[this.listNumCell[exCol+1][exRow+1]];
        CellPanel newCP = (CellPanel) lc[this.listNumCell[col+1][row+1]];
        exCP.getComponent(0).setVisible(false);
        newCP.getComponent(0).setVisible(true);
    }
    /**
     * Méthode qui permet de retirer de l'interface graphique un monstre qui a été tué par le joueur
     * @param col = position X du monstre
     * @param row = position Y du monstre
     */
    public void killMonster(int col, int row){
        Component[] lc = this.getComponents();
        CellPanel monster = (CellPanel) lc[this.listNumCell[col+1][row+1]];
        monster.getComponent(2).setVisible(false);
        delPoopMonster(col, row);
    }
    /**
     * Méthode qui permet d'ajouter à l'interface graphique le gamer du niveau (généré par l'environemment)
     */
    public void addGamer(Map map){
        //on place le joueur sur la grille
        //etape 1 : on récupère la liste des composants de la grille
        //etape 2 : on récupère la cellule en rapport avec le gamer
        //on ajoute +1,+1 aux coordonnées du gamer pour prendre en compte la fôret
        //etape 3 : on rend le gamer visible
        Component[] lc = this.getComponents();
        CellPanel cpGame = (CellPanel) lc[listNumCell[map.getPlayerCell().getCol()+1][map.getPlayerCell().getRow()+1]];
        if(!isGamer){
            cpGame.getComponent(0).setVisible(true);
            isGamer = true;
        }
    }
    /**
     * Méthode qui permet d'ajouter à l'interface graphique les monstres du niveau (générés par l'environemment)
     * @param map = la map du niveau (générée par l'environemment)
     */
    public void addMonsters(Map map){
//        Component[] lc = this.getComponents();
//        for(int i=0; i<map.getMonsterCells().size(); i++){
//            CellPanel cpMonster = (CellPanel) lc[listNumCell[map.getMonsterCells().get(i).getCol()+1][map.getMonsterCells().get(i).getRow()+1]];
//            cpMonster.getComponent(2).setVisible(true);
//            addPoopMonster(map.getMonsterCells().get(i).getCol(), map.getMonsterCells().get(i).getRow());
//        }
        
    }
    /**
     * Méthode qui permet d'ajouter à l'interface graphique les crevasses du niveau (générées par l'environemment)
     * @param map = la map du niveau (générée par l'environemment)
     */
    public void addCrevasses(Map map){
//        Component[] lc = this.getComponents();
//        for(int i=0; i<map.getCrevasseCells().size(); i++){
//            CellPanel cpCrevasses = (CellPanel) lc[listNumCell[map.getCrevasseCells().get(i).getCol()+1][map.getCrevasseCells().get(i).getRow()+1]];
//            cpCrevasses.getComponent(2).setVisible(true);
//            addCloudCrevasse(map.getCrevasseCells().get(i).getCol(), map.getCrevasseCells().get(i).getRow());
//        }
    }
    /**
     * Méthode qui permet d'ajouter à l'interface graphique le portail du niveau (généré par l'environemment)
     */
    public void addStargate(Map map){
        Component[] lc = this.getComponents();
        CellPanel cpStargate = (CellPanel) lc[listNumCell[map.getPortalCell().getCol()+1][map.getPortalCell().getRow()+1]];
        cpStargate.getComponent(2).setVisible(true);
    }
    /**
     * Méthode qui permet d'ajouter à l'interface graphique les cases qui sentent mauvaises de la position d'un monstre passé en paramètre
     * @param posColMonster = pos X du monstre
     * @param posRowMonster = pos Y du monstre
     */
    public void addPoopMonster(int posColMonster, int posRowMonster){
        Component[] lc = this.getComponents();
        try{
            CellPanel poopUP  = (CellPanel) lc[this.listNumCell[posColMonster+1][posRowMonster]];
            poopUP.setNbPoop(1);
            if(poopUP.getNbPoop()>=1){
             poopUP.getComponent(1).setVisible(true);
            }
        }catch(Exception e){
            
        }
        try{
            CellPanel poopDOWN  = (CellPanel) lc[this.listNumCell[posColMonster+1][posRowMonster+2]];
            poopDOWN.setNbPoop(1);
            if(poopDOWN.getNbPoop()>=1){
             poopDOWN.getComponent(1).setVisible(true);
            }
        }catch(Exception e){
            
        }
        try{
            CellPanel poopLEFT  = (CellPanel) lc[this.listNumCell[posColMonster][posRowMonster+1]];
            poopLEFT.setNbPoop(1);
            if(poopLEFT.getNbPoop()>=1){
             poopLEFT.getComponent(1).setVisible(true);
            }
        }catch(Exception e){
            
        }
        try{
            CellPanel poopRIGHT  = (CellPanel) lc[this.listNumCell[posColMonster+2][posRowMonster+1]];
            poopRIGHT.setNbPoop(1);
            if(poopRIGHT.getNbPoop()>=1){
             poopRIGHT.getComponent(1).setVisible(true);
            }
        }catch(Exception e){
            
        }
    }
    /**
     * Méthode qui permet d'ajouter de l'interface graphique les cases venteuses de la position d'une crevasse passé en paramètre
     * @param posColCrevasse = pos X de la crevasse
     * @param posRowCrevasse = pos Y de la crevasse
     */
    public void addCloudCrevasse(int posColCrevasse, int posRowCrevasse){
        Component[] lc = this.getComponents();
        try{
            CellPanel cloudUP  = (CellPanel) lc[this.listNumCell[posColCrevasse+1][posRowCrevasse]];
            cloudUP.setNbCloud(1);
            if(cloudUP.getNbCloud()>=1){
             cloudUP.getComponent(3).setVisible(true);
            }
        }catch(Exception e){
            
        }
        try{
            CellPanel cloudDOWN  = (CellPanel) lc[this.listNumCell[posColCrevasse+1][posRowCrevasse+2]];
            cloudDOWN.setNbCloud(1);
            if(cloudDOWN.getNbCloud()>=1){
             cloudDOWN.getComponent(3).setVisible(true);
            }
        }catch(Exception e){
            
        }
        try{
            CellPanel cloudLEFT  = (CellPanel) lc[this.listNumCell[posColCrevasse][posRowCrevasse+1]];
            cloudLEFT.setNbCloud(1);
            if(cloudLEFT.getNbCloud()>=1){
             cloudLEFT.getComponent(3).setVisible(true);
            }
        }catch(Exception e){
            
        }
        try{
            CellPanel cloudRIGHT  = (CellPanel) lc[this.listNumCell[posColCrevasse+2][posRowCrevasse+1]];
            cloudRIGHT.setNbCloud(1);
            if(cloudRIGHT.getNbCloud()>=1){
             cloudRIGHT.getComponent(3).setVisible(true);
            }
        }catch(Exception e){
            
        }
    }
    /**
     * Méthode qui permet de retirer de l'interface graphique les cases qui sentent mauvaises de la position d'un monstre passé en paramètre
     * @param posColMonster = pos en X du monstre
     * @param posRowMonster = pos en Y du monstre
     */
    public void delPoopMonster(int posColMonster, int posRowMonster){
        Component[] lc = this.getComponents();
        try{
            CellPanel poopUP  = (CellPanel) lc[this.listNumCell[posColMonster+1][posRowMonster]];
            poopUP.setNbPoop(-1);
            if(poopUP.getNbPoop()<=0){
             poopUP.getComponent(1).setVisible(false);
            }
        }catch(Exception e){
            
        }
        try{
            CellPanel poopDOWN  = (CellPanel) lc[this.listNumCell[posColMonster+1][posRowMonster+2]];
            poopDOWN.setNbPoop(-1);
            if(poopDOWN.getNbPoop()<=0){
             poopDOWN.getComponent(1).setVisible(false);
            }
        }catch(Exception e){
            
        }
        try{
            CellPanel poopLEFT  = (CellPanel) lc[this.listNumCell[posColMonster][posRowMonster+1]];
            poopLEFT.setNbPoop(-1);
            if(poopLEFT.getNbPoop()<=0){
             poopLEFT.getComponent(1).setVisible(false);
            }
        }catch(Exception e){
            
        }
        try{
            CellPanel poopRIGHT  = (CellPanel) lc[this.listNumCell[posColMonster+2][posRowMonster+1]];
            poopRIGHT.setNbPoop(-1);
            if(poopRIGHT.getNbPoop()<=0){
             poopRIGHT.getComponent(1).setVisible(false);
            }
        }catch(Exception e){
            
        }
    }
    /**
     * Méthode qui permet de retirer de l'interface graphique les cases venteuses de la position d'une crevasse passé en paramètre
     * @param posColCrevasse = pos X de la crevasse
     * @param posRowCrevasse = pos Y de la crevasse
     */
    public void delCloudCrevasse(int posColCrevasse, int posRowCrevasse){
        Component[] lc = this.getComponents();
        try{
            CellPanel cloudUP  = (CellPanel) lc[this.listNumCell[posColCrevasse+1][posRowCrevasse]];
            cloudUP.setNbCloud(-1);
            if(cloudUP.getNbCloud()<=0){
             cloudUP.getComponent(3).setVisible(false);
            }
        }catch(Exception e){
            
        }
        try{
            CellPanel cloudDOWN  = (CellPanel) lc[this.listNumCell[posColCrevasse+1][posRowCrevasse+2]];
            cloudDOWN.setNbCloud(-1);
            if(cloudDOWN.getNbCloud()<=0){
             cloudDOWN.getComponent(3).setVisible(false);
            }
        }catch(Exception e){
            
        }
        try{
            CellPanel cloudLEFT  = (CellPanel) lc[this.listNumCell[posColCrevasse][posRowCrevasse+1]];
            cloudLEFT.setNbCloud(-1);
            if(cloudLEFT.getNbCloud()<=0){
             cloudLEFT.getComponent(3).setVisible(false);
            }
        }catch(Exception e){
            
        }
        try{
            CellPanel cloudRIGHT  = (CellPanel) lc[this.listNumCell[posColCrevasse+2][posRowCrevasse+1]];
            cloudRIGHT.setNbCloud(-1);
            if(cloudRIGHT.getNbCloud()<=0){
             cloudRIGHT.getComponent(3).setVisible(false);
            }
        }catch(Exception e){
            
        }
    }
}
