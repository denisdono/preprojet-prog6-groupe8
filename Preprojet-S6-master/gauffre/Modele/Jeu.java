package Modele;

/*
 * gauffre empoison�e
 * Copyright (C) 2020 Grondin Denis

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

 * Contact: denisg3105@gmail.com
 *          515 rue des r�sidences
 *          Domaine universitaire
 *          38401 Saint Martin d'Hères
 */

import Patterns.Observable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;


public class Jeu extends Observable {
	boolean enCours;
	boolean loadBug;
	int[][] plateau;
	int[][] sauv;
	int [] score;
	int coup;
	int ligne;
	int colonne;
	int joueur;
	

	public Jeu(int n,int m) {
		ligne=n;
		loadBug = false;
		colonne=m;
		plateau = new int[n][m];
		score = new int[2];
		score[0]=0;
		score[1]=0;
		enCours = true;
		coup=1;
		joueur=0;
		for (int i = 0; i < ligne; i++)
			for (int j = 0; j < colonne; j++)
				plateau[i][j] = -1;
	}

	public void jouer(int l, int c) {
		if (enCours && (plateau[l][c] == -1)) {
			for (int i = l; i < ligne; i++) {
				for (int j = c; j < colonne; j++) {
					if (plateau[i][j]<0) {
						plateau[i][j] = coup;
					}
				}
			}
			coup++;
			joueur=(joueur+1)%2;
			enCours = plateau[0][0]==-1;
			if(!enCours) {
				score[joueur]++;
				System.out.println("score :\n" +"joueur 1 a :"+score[0]+
						"\n"+"joueur 2 a :"+score[1]);
			}
			System.out.println("coup :"+coup);
			metAJour();
		}
	}
	
	public void cntrZH () {
		if (coup<1) {
			return;
		}
		coup--;
		for (int i = 0; i < ligne; i++) {
			for (int j = 0; j < colonne; j++) {
				if (plateau[i][j]==coup) {
					plateau[i][j] = -1;
				}
			}
		}
		metAJour();
	}
	
	public void cntrZIA () {
		if (coup<1) {
			return;
		}
		coup--;
		for (int i = 0; i < ligne; i++) {
			for (int j = 0; j < colonne; j++) {
				if (plateau[i][j]==coup || plateau[i][j]==coup-1 ) {
					plateau[i][j] = -1;
				}
				
			}
		}
		coup--;
		metAJour();
	}
	
	
	public void restart() {
		for (int i = 0; i < ligne; i++) {
			for (int j = 0; j < colonne; j++) {
				plateau[i][j] = -1;
			}
		}
		enCours= true;
		coup=1;
		metAJour();
	}
	
	public void save(String s){
		FileOutputStream save;
		try {
			save = new FileOutputStream(new File(s));
	    BufferedOutputStream bsave = new BufferedOutputStream(save);
	    bsave.write(ligne);
	    bsave.write(colonne);
	    bsave.write(coup);
	    bsave.write('\n');
		for (int i = 0; i < ligne; i++) {
			for (int j = 0; j < colonne; j++) {
				bsave.write(plateau[i][j]);
				System.out.print(plateau[i][j]);
			}
			bsave.write('\n');
			System.out.print("\n");
			
		}
		bsave.close();
		} catch (IOException e) {	
			System.err.println("Impossible de sauvegarder dans " + s);
			System.err.println(e.toString());
			System.exit(1);
		}
	}
	public void load(String s) {
        try {
             FileInputStream save = new FileInputStream(new File(s));
             BufferedInputStream bsave = new BufferedInputStream(save);
             int newligne=bsave.read();
             int newcolonne=bsave.read();
                     if(newligne > ligne || newcolonne > colonne){
                         loadBug = true;
                     } else {
                         ligne = newligne;
                         colonne=newcolonne;
                     }
             coup=bsave.read();
             //test
             System.out.println("coup"+coup);
             int val;
             bsave.read();//saute l'espace
             //plateau=new int [ligne][colonne];
             
		      for (int i = 0; i < ligne; i++) {
					for (int j = 0; j < colonne; j++) {
						val =bsave.read();
						if(val==255) {
							plateau[i][j]=-1;
						}else {
							plateau[i][j]=val;
						}
							
						
						System.out.print(plateau[i][j]);
				
					}
					System.out.print("\n");
					bsave.read();
			  }
		      bsave.close();
		      metAJour();
		  } catch (IOException e) {
		      e.printStackTrace();
		    }
	}
	
	
	public boolean libre(int i, int j) {
		return valeur(i, j) == -1;
	}

	public int valeur(int i, int j) {
		return plateau[i][j];
	}

	public boolean enCours() {
		return enCours;
	}

	public int largeur() {
		return colonne;
	}

	public int hauteur() {
		return ligne;
	}

	public int getJoueur() {
		return joueur;
	}
	public boolean isLoadBug() {
		return loadBug;
	}
	public int getScore(int i) {
		return score[i];
	}
}
