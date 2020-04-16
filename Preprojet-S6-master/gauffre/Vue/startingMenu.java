package Vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import java.awt.Dimension;

public class startingMenu implements Runnable {
	
	boolean[] IA;
	int dif;
	public startingMenu(){
		IA = new boolean[2];	
	}
//	
//
	public static void demarrer() {
		SwingUtilities.invokeLater(new startingMenu());
	}
//
	@Override
	public void run() {
		JFrame frame = new JFrame("Menu de demarrage");
		JPanel mainPane = new JPanel();
		mainPane.setLayout(new BorderLayout());
		JPanel centerPane = new JPanel();
		centerPane.setLayout(new GridLayout(3,4));
		JLabel text = new JLabel("Bienvenue dans La gauffre");
		
		
		JRadioButton IAvsIA = new JRadioButton("IA vs IA");
		IAvsIA.setSelected(true);
		JRadioButton HumanvsIA = new JRadioButton("Human vs IA");
		JRadioButton HumanvsHuman = new JRadioButton("Human vs Human");
		ButtonGroup groupMode = new ButtonGroup();
	    groupMode.add(IAvsIA);
	    groupMode.add(HumanvsIA);
	    groupMode.add(HumanvsHuman);
	    
	    JRadioButton easy = new JRadioButton("Easy");
	    easy.setSelected(true);
		JRadioButton normal = new JRadioButton("Normal");
		JRadioButton hard = new JRadioButton("Hard");
	    ButtonGroup groupDif = new ButtonGroup();
	    groupDif.add(easy);
	    groupDif.add(normal);
	    groupDif.add(hard);
	    
	    SpinnerModel modelColone = new SpinnerNumberModel(10, 1, 20, 1);     
	    JSpinner spinnerColone = new JSpinner(modelColone);
	    SpinnerModel modelLigne = new SpinnerNumberModel(10, 1, 20, 1);   
	    JSpinner spinnerLigne = new JSpinner(modelLigne);
		
		
		
		
		
		
		
		JButton startButton = new JButton("start");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	        	//frame.setVisible(false);
	        	if(IAvsIA.isSelected()) {
	        		IA[0]=true;
	        		IA[1]=true;
	        	}
	        	else if(HumanvsIA.isSelected()) {
	        		IA[0]=false;
	        		IA[1]=true;
	        	}
	        	else if(HumanvsHuman.isSelected()) {
	        		IA[0]=false;
	        		IA[1]=false;
	        	}
	        	if(easy.isSelected()) {
	        		dif = 0;
	        	}
	        	else if(normal.isSelected()) {
	        		dif = 1;
	        	}
	        	else if(hard.isSelected()) {
	        		dif = 2;
	        	}
	        	Jeu j = new Jeu((int)spinnerLigne.getValue(),(int)spinnerColone.getValue());

	     		CollecteurEvenements control = new ControleurMediateur(j, IA, dif);
                        frame.setVisible(false);
	     		InterfaceGraphique.demarrer(j, control, frame);
	        }
	    });

		centerPane.add(new JLabel("Mode : "));
		centerPane.add(IAvsIA);
		centerPane.add(HumanvsIA);
		centerPane.add(HumanvsHuman);
		centerPane.add(new JLabel("Difficulté : "));
		centerPane.add(easy);
		centerPane.add(normal);
		centerPane.add(hard);
		centerPane.add(new JLabel("Colonne : "));
		centerPane.add(spinnerColone);
		centerPane.add(new JLabel("Ligne : "));
		centerPane.add(spinnerLigne);
		
		mainPane.add(text,BorderLayout.PAGE_START);
		mainPane.add(centerPane,BorderLayout.CENTER);
		mainPane.add(startButton,BorderLayout.PAGE_END);
		frame.add(mainPane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setVisible(true);
	}

}
