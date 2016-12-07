/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.magical.forest;

import Main.Controller;
import ia.magical.forest.environment.Entity;
import ia.magical.forest.environment.Map;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Classe principale de l'interface graphique qui permet d'initialiser les composants de l'interface ainsi que le graphique du niveau (forêt)
 * @author Maxime
 */
public class GUIMain extends javax.swing.JFrame {
    
    private int level;
    private Controller ctrl;
    /**
     * Variable qui représente la classe principale du projet
     * et qui permet de faire le lien avec les autres classes du projet
     */

    /**
     * Creates new form GUIMain
     */
    public GUIMain() {
        initComponents();
        console.setLineWrap(true);
        jtpScore.setOpaque(true);
        StyledDocument doc = jtpScore.getStyledDocument();		
        MutableAttributeSet right = new SimpleAttributeSet();		
        StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
        doc.setParagraphAttributes(0, 0, right, false);
        this.setExtendedState(this.MAXIMIZED_BOTH);
        //chargeLevel(main.level, main.map);
        //Map map = new Map(4);
        //chargeLevel(4, map);
        //int[] monster = {0,2};
        //this.forest.killMonster(monster);
//        for (int i=100000; i<101001; i++){
//            setConsoleText(Integer.toString(i));
//        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbMove = new javax.swing.JButton();
        jlScore = new javax.swing.JLabel();
        jtpScore = new javax.swing.JTextPane();
        jScrollForest = new javax.swing.JScrollPane();
        forest = new gui.magical.forest.GridPanel();
        jSPConsol = new javax.swing.JScrollPane();
        console = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Magical Forest - Level X");

        jbMove.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jbMove.setText("Move");
        jbMove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbMoveActionPerformed(evt);
            }
        });

        jlScore.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jlScore.setText("Score : ");

        jtpScore.setEditable(false);
        jtpScore.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jtpScore.setText("0");

        javax.swing.GroupLayout forestLayout = new javax.swing.GroupLayout(forest);
        forest.setLayout(forestLayout);
        forestLayout.setHorizontalGroup(
            forestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        forestLayout.setVerticalGroup(
            forestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 404, Short.MAX_VALUE)
        );

        jScrollForest.setViewportView(forest);

        console.setEditable(false);
        console.setColumns(20);
        console.setRows(5);
        jSPConsol.setViewportView(console);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jlScore)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtpScore, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbMove)
                .addContainerGap(420, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollForest)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSPConsol, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlScore, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbMove, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtpScore, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollForest)
                    .addComponent(jSPConsol)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbMoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbMoveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbMoveActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUIMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUIMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUIMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUIMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GUIMain gui = new GUIMain();
                gui.setVisible(true);
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea console;
    private gui.magical.forest.GridPanel forest;
    private javax.swing.JScrollPane jSPConsol;
    private javax.swing.JScrollPane jScrollForest;
    private javax.swing.JButton jbMove;
    private javax.swing.JLabel jlScore;
    private javax.swing.JTextPane jtpScore;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
        this.setTitle("Magical Forest - Level "+level);
    }
    
    public void chargeLevel(int taille){
        this.forest.initialize(this, taille);
        setLevel(taille-2);
        
    }
    
    public void putEntity(int row, int col, Entity entity){
        if(entity == Entity.PLAYER){
            this.forest.addGamer(col, row);
        }else if(entity == Entity.PORTAL){
            this.forest.addStargate(col, row);
        }else if(entity == Entity.CRACK){
            this.forest.addCrevasse(col, row);
        }else if(entity == Entity.MONSTER){
            this.forest.addMonster(col, row);
        }else if(entity == Entity.SMELL){
            this.forest.addPoop(col, row);
        }else if(entity == Entity.WIND){
            this.forest.addCloud(col, row);
        }       
    }
    
    public void removeEntity(int row, int col, Entity entity){
        if(entity == Entity.MONSTER){
            this.forest.delMonster(col, row);
        }
        else if(entity == Entity.SMELL){
            this.forest.delPoop(col, row);
        }
    }
    
    /**
     * @return the jtpScore
     */
    public int getScore() {
        return Integer.parseInt(jtpScore.getText());
    }

    /**
     * @param jtpScore the jtpScore to set
     */
    public void setScore(int score) {
        this.jtpScore.setText(Integer.toString(score));
    }
    
    public void setConsoleText(String Text){
        console.append(Text+"\n");
    }
}
