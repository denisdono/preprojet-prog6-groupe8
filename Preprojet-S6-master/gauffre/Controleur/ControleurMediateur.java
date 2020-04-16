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

import Modele.Jeu;
import Vue.CollecteurEvenements;

public class ControleurMediateur implements CollecteurEvenements {
	Jeu jeu;
	Joueur[] joueurs;
	int joueurCourant;
	final int lenteurAttente = 50;
	int decompte;
	boolean joueurIA=false; //verifie s'il y a une IA
	public ControleurMediateur(Jeu j, boolean[] IA,int dif) {
		jeu = j;
		joueurs = new Joueur[IA.length];
		
		for (int i = 0; i < joueurs.length; i++)
			if (IA[i]) {
				joueurIA=true;
				joueurs[i] = new JoueurIA(i, jeu,dif);}
			else {
				joueurs[i] = new JoueurHumain(i, jeu);}
	}

	@Override
	public void clicSouris(int l, int c) {
		// Lors d'un clic, on le transmet au joueur courant.
		// Si un coup a effectivement été joué (humain, coup valide), on change de joueur.
		if (joueurs[joueurCourant].jeu(l, c))
			changeJoueur();
	}

	void changeJoueur() {
		joueurCourant = (joueurCourant + 1) % joueurs.length;
		decompte = lenteurAttente;
	}
	@Override
	public void boutonEvent(String role) {
        switch(role){
            case "Charger":jeu.load("file"); break;
            case "Retour":
            if(joueurIA) {
            	System.out.print("C'est une IA");
            	jeu.cntrZIA();
            }else {
            	jeu.cntrZH();
            	changeJoueur();
            }     		
            	break;//ici
            case "Sauvegarder":jeu.save("file"); break;
            case "Recommencer":jeu.restart();
        }
	}
	public void tictac() {
		if (jeu.enCours()) {
			if (decompte == 0) {
				// Lorsque le temps est écoulé on le transmet au joueur courant.
				// Si un coup a été joué (IA) on change de joueur.
				if (joueurs[joueurCourant].tempsEcoule()) {
					changeJoueur();
				} else {
				// Sinon on indique au joueur qui ne réagit pas au temps (humain) qu'on l'attend.
					System.out.println("On vous attend, joueur " + joueurs[joueurCourant].num());
					decompte = lenteurAttente;
				}
			} else {
				decompte--;
			}
		}
	}
}
