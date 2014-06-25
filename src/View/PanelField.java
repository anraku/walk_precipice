package View;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.*;

import java.util.*;

import Control.QLearn;
import Model.MainApp;


public class PanelField extends JPanel implements Observer,MouseListener{

	//ウインドウサイズ
	static final int W = 400;
	static final int H = 400;

	//親フレーム
	QLearn parent;
	
	//�R���X�g���N�^
	public PanelField(MainApp parent){
		this.parent = parent.qLearn;
		setPreferredSize(new Dimension(W,H));
		setBackground(Color.WHITE);
	}
	
	//��ʂ̕`��
	public void paintComponent(Graphics g){
		
		//�w�i
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, W, H);
		
		int x = W / QLearn.X;
		int y = H / QLearn.Y;
		
		//�R�̈ʒu�����F�ɂ���
		g.setColor(Color.YELLOW);
		for(Point p : QLearn.precipice_zone){
			g.fillRect(p.x*x, p.y*y, x, y);
		}
		
		//�e�_�̍ő�Q�l�ɑ΂��ă}�X�ɐF��t����
		for(int i=0; i<QLearn.X; i++){
			for(int j=0; j<QLearn.Y; j++){
				double QMax = -100;
				for(int a=0; a<QLearn.ACTION; a++){
					if(QMax < QLearn.value[i][j][a]){
						QMax = QLearn.value[i][j][a];
					}
				}
				
				if(QMax < 1){
					g.setColor(Color.LIGHT_GRAY);
				}
				else if(QMax < 10){
					g.setColor(Color.GREEN);
				}
				else{
					g.setColor(Color.RED);
				}
				
				g.fillRect(i*x, j*y, x, y);
			}
		}
		//�e�ʒu�ł̍œK����
		g.setColor(Color.BLACK);
		for(int i=0; i<QLearn.X; i++){
			for(int j=0; j<QLearn.Y; j++){
				
				int a = parent.getMaxQAction(new Point(i,j));
				if(a==0)	g.drawString("↑\n", i*x +x/2, j*y +y/2);
				else if(a==1)	g.drawString("→", i*x + x/2, j*y +y/2);
				else if(a==2)	g.drawString("↓", i*x+x/2, j*y+y/2);
				else if(a==3)	g.drawString("←", i*x+x/2, j*y+y/2);
				
			}
		}
		//�O���b�h
		g.setColor(Color.BLACK);
		for(int i=0; i<QLearn.X; i++){
			g.drawLine(0, i*y, W, i*y);
			g.drawLine(i*x, 0, i*x, H);
		}
		
		//���݂̏��
		g.setColor(Color.BLUE);
		Point start = parent.state;
		g.drawOval(start.x*x, start.y*y, x, y);
				
		//�I�[���
		g.setColor(Color.RED);
		Point goal = parent.goal;
		g.drawRect(goal.x*x, goal.y*y, x, y);
				
	}
	
	//QLearn���X�V���ꂽ�Ƃ��ɌĂяo�����
	public void update(Observable o, Object arg){
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
	}
}