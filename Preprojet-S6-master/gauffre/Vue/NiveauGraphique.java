package Vue;
/*
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
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;

public class NiveauGraphique extends JComponent implements Observateur {
	Jeu jeu;
	int largeurCase, hauteurCase;

	public NiveauGraphique(Jeu j) {
		jeu = j;
		jeu.ajouteObservateur(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D drawable = (Graphics2D) g;
        int lignes = jeu.hauteur();
        int colonnes = jeu.largeur();
        largeurCase = largeur() / colonnes;
        hauteurCase = hauteur() / lignes;

        g.clearRect(0, 0, largeur(), hauteur());
        
        // Grille
        
        // Coups
        for (int i=0; i<lignes; i++)
            for (int j=0; j<colonnes; j++)
                switch (jeu.valeur(i, j)) {
                    case -1:
                        g.setColor(Color.ORANGE);
                        g.fillRect(j*largeurCase, i*hauteurCase, largeurCase, hauteurCase);
                        g.setColor(new Color(204, 153, 0));
                        g.fillRect(j*largeurCase+5, i*hauteurCase+5, largeurCase-10, hauteurCase-10);

                        break;

                }
        g.setColor(Color.WHITE);
        for (int i=1; i<lignes;i++) {
            g.drawLine(0, i*hauteurCase, largeur(), i*hauteurCase);
        }
        for (int j=1; j<colonnes;j++) {
            g.drawLine(j*largeurCase, 0, j*largeurCase, hauteur());
        }
        if (!jeu.enCours()){
            g.setColor(Color.BLACK);
            g.setFont(new Font("Times New Roman", Font.PLAIN, hauteur()/12));
            g.drawString("GAME OVER!", largeur()/3, hauteur()/2);
           
        }
        else {
            g.setColor(Color.RED);
            g.fillOval(largeurCase/3,hauteurCase/3, largeurCase/3, hauteurCase/3);
        }
	}

	int largeur() {
		return getWidth();
	}

	int hauteur() {
		return getHeight();
	}

	public int largeurCase() {
		return largeurCase;
	}

	public int hauteurCase() {
		return hauteurCase;
	}

	@Override
	public void miseAJour() {
		repaint();
	}
           
        public void setTaille(Dimension screenSize) {
            this.setPreferredSize(new Dimension((int)(screenSize.getWidth()/2-250), (int)screenSize.getHeight()/2)); 
        }
}