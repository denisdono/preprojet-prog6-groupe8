package Vue;/*
 * Morpion pédagogique

 * Copyright (C) 2016 Guillaume Huard

 * Ce programme est libre, vous pouvez le redistribuer et/ou le
 * modifier selon les termes de la Licence Publique Générale GNU publiée par la
 * Free Software Foundation (version 2 ou bien toute autre version ultérieure
 * choisie par vous).

 * Ce programme est distribué car potentiellement utile, mais SANS
 * AUCUNE GARANTIE, ni explicite ni implicite, y compris les garanties de
 * commercialisation ou d'adaptation dans un but spécifique. Reportez-vous à la
 * Licence Publique Générale GNU pour plus de détails.

 * Vous devez avoir reçu une copie de la Licence Publique Générale
 * GNU en même temps que ce programme ; si ce n'est pas le cas, écrivez à la Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
 * États-Unis.

 * Contact: Guillaume.Huard@imag.fr
 *          Laboratoire LIG
 *          700 avenue centrale
 *          Domaine universitaire
 *          38401 Saint Martin d'Hères
 */

import Modele.Jeu;
import java.awt.*;

import javax.swing.*;

public class InterfaceGraphique implements Runnable {
	Jeu j;
	CollecteurEvenements control;
        JFrame startMenu;

	InterfaceGraphique(Jeu jeu, CollecteurEvenements c, JFrame m) {
		j = jeu;
		control = c;
                startMenu = m;
                
	}

	public static void demarrer(Jeu j, CollecteurEvenements control, JFrame m) {
		SwingUtilities.invokeLater(new InterfaceGraphique(j, control, m));
	}

	@Override
	public void run() {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            JFrame frame = new JFrame("Gauffre empoisonnée");
            frame.setSize((int)screenSize.getWidth()/2, (int)screenSize.getHeight()/2);

            frame.setLayout(new FlowLayout());
            NiveauGraphique niv = new NiveauGraphique(j);
            niv.setTaille(screenSize);
            niv.addMouseListener(new AdaptateurSouris(niv, control));
            MenuInGame menuInGame = new MenuInGame(j, control, frame);

            frame.add(niv);
            frame.add(menuInGame.getPanel());
           
             
            Timer chrono = new Timer( 16, new AdaptateurTemps(control));
            chrono.start();
            frame.pack();
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
            
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                startMenu.setVisible(true);
            }
             });

            frame.setVisible(true);
                
	}
}