/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Patterns.Observateur;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.*;

/**
 *
 * @author dodee
 */
public class MenuInGame implements Observateur {

    Jeu jeu;
    JPanel panel;
    CollecteurEvenements ctrl;
    JLabel info, lose,manches;
    JFrame mainWindow;
    public MenuInGame(Jeu j, CollecteurEvenements c, JFrame win) {
        mainWindow = win;
        ctrl = c;
        int nbBout = 5;
        this.jeu = j;
        jeu.ajouteObservateur(this);
        String[] nomBout = new String[nbBout];
        nomBout[0] = "Charger";
        nomBout[1] = "Sauvegarder";
        nomBout[2] = "Retour";
        nomBout[3] = "Recommencer";
        nomBout[4] = "Quitter";
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(150,300));
        panel.setBackground(Color.LIGHT_GRAY);
        for(int i = 0; i < nbBout; i++){
            JButton b = new JButton(nomBout[i]);
            b.setMaximumSize(new Dimension(150, 40));
            final String role = nomBout[i];
            b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(role == "Quitter")
                    mainWindow.dispatchEvent(new WindowEvent(mainWindow, WindowEvent.WINDOW_CLOSING));
                else
                    ctrl.boutonEvent(role);
            }          
            });
            panel.add(b);
        }
        manches = new JLabel("J1 : 0 - J2 : 0");
        info = new JLabel("Joueur 1 a toi");
        lose = new JLabel();
        panel.add(manches);
        panel.add(info);
        panel.add(lose);
    }
    
    @Override
    public void miseAJour() {
        int SJ1=0;
    	int SJ2=0;
        if(jeu.isLoadBug()){
            info.setText("Impossible de charger");
        } else{
        int numJ = (jeu.getJoueur()+1);
        if(jeu.enCours()){
            info.setText("Joueur "+numJ+" a toi");
        } else{
            lose.setText("Joueur "+numJ+" a gagné");
            SJ1=jeu.getScore(0);
            SJ2=jeu.getScore(1); 
            manches.setText("J1 : "+SJ1+" - J2 : "+SJ2);

        }
        }
    }

    public JPanel getPanel() {
        return panel;
    }
    
    
}
