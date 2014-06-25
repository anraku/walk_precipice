package Model;

import javax.swing.JFrame;

import Control.QLearn;
import View.PanelField;

public class MainApp extends JFrame {
	
	public QLearn qLearn;
	
	//初期設定
	public MainApp(){
		
		qLearn = new QLearn();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Reinforcement Learning (Q Learning)");
		
		PanelField panel = new PanelField(this);
		getContentPane().add(panel);
		

		pack();
		
		qLearn.addObserver(panel);
	}

	//メインメソッド
	public static void main(String[] args){

		MainApp app = new MainApp();
		app.setVisible(true);
		app.qLearn.start();
		
	}
}
