package Controleur;
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

import java.util.Random;
import Modele.Jeu;

class JoueurIA extends Joueur {
	Random r;
	int niv;

	JoueurIA(int n, Jeu p,int dif) {
		super(n, p);
		r = new Random();
		niv=dif;
	}

	@Override
	boolean tempsEcoule() {
		// Pour cette IA, on selectionne aléatoirement une case libre
		boolean rep;
		switch (niv) {
		case 0:
			rep=IAfacil();
			break;
		case 1:
			rep=IAfacilniv2();
			break;
		case 2:
			rep=IAimpossible();
			break;
		default :
			rep=false;
		}
		return rep;
	}
	
	boolean IArandom() {
		int i, j;

		i = r.nextInt(plateau.hauteur());
		j = r.nextInt(plateau.largeur());
		while (!plateau.libre(i, j)) {
			i = r.nextInt(plateau.hauteur());
			j = r.nextInt(plateau.largeur());
		}
		plateau.jouer(i, j);
		return true;
	}
	
	boolean IAfacil() {
		int i, j;
		boolean autre=false;
		do {
			autre=false;
			i = r.nextInt(plateau.hauteur());
			j = r.nextInt(plateau.largeur());
			if (i==0 && j==0) {
				if (plateau.libre(0, 1)||plateau.libre(1, 0)) {
					autre=true;
				}
			}
		} while (!plateau.libre(i, j)|| autre);
		plateau.jouer(i, j);
		return true;
	}
	
	boolean IAfacilniv2() {
		int i, j;
		boolean autre=false;
		if (plateau.libre(0, 1) && !plateau.libre(1, 0)) {
			i=0;
			j=1;
		}
		else if (!plateau.libre(0, 1) && plateau.libre(1, 0)) {
				i=1;
				j=0;
		}
		else {
			do {
				autre=false;
				i = r.nextInt(plateau.hauteur());
				j = r.nextInt(plateau.largeur());
				if (i==0 && j==0) {
					if (plateau.libre(0, 1)||plateau.libre(1, 0)) {
						autre=true;
					}
				}
			} while (!plateau.libre(i, j)|| autre);
		}
		plateau.jouer(i, j);
		return true;
	}
	
	boolean IAimpossible() {
		int i, j;
		boolean libre=true;
		i=0;
		j=0;
		if (!plateau.libre(1,1)) {
			while (libre) {
				if (i<plateau.hauteur()) {
					if (!plateau.libre(i,0)) {
						libre=false;
					}
					else {
						i++;
					}
				}
				else {
					libre=false;
				}
				
				
			}
			libre=true;
			while (libre) {
				if (j<plateau.largeur()) {
					if (!plateau.libre(0,j)) {
						libre=false;
					}
					else {
						j++;
					}
				}
				else {
					libre=false;
				}
				
			}
			System.out.println("premier i j"+i+" "+j);
			if (i>j) {
				i=j;
				j=0;
			}
			else if(i==j) {
				i=j-1;
				j=0;
			}
			else {
				j=i;
				i=0;
			}
		}
		else {
			i=1;
			j=1;
		}
		System.out.println("i j"+i+" "+j);
		plateau.jouer(i, j);
		return true;
	}
}