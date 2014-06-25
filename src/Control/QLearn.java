package Control;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class QLearn extends Observable{
	
	//���ڂ̐��i��Ԑ��j
	public static final int X = 10;
	public static final int Y = 10;

	//�s����
	static public final int ACTION = 4;

	public static double[][][] value = new double[X][Y][ACTION];	//Q値
	//START�n�_
	private final Point START = new Point(0,0);
	public Point state = new Point(0,0);
	public final Point goal = new Point(9,0);				//ゴール
	Point[] action = {
		new Point(0, -1),	//↑
		new Point(1, 0),	//→
		new Point(0, 1),	//↓	
		new Point(-1, 0)	//←
	};
	
	//�R�̈ʒu
	public static Point[] precipice_zone = {
		new Point(1,0),
		new Point(2,0),
		new Point(3,0),
		new Point(4,0),
		new Point(5,0),
		new Point(6,0),
		new Point(7,0),
		new Point(8,0),
	};
	final double alpha = 0.50;
	final double gamma = 0.30; 
	final double reward = 100.0;
	final int episodecount = 100;
	final int stepcount = 2000;
	
	//�R���X�g���N�^
	public QLearn(){
		
		//Q�l��0.0�ŏ�����
		for(int i=0; i<X; i++){
			for(int j=0; j<Y; j++){
				for(int k=0; k<ACTION; k++){
					value[i][j][k] = 0.0;
				}
			}
		}
		
		//START�n�_��������
		state.setLocation(START);
	}
	
	//���[�v�J�n
	public void start(){
		
		Random rnd = new Random();
		int eCount = 0;//episode�����J�E���g����
		int sCount = 0;//step�����J�E���g����
	
		while(true){		
			//�s����I��
			int r = rnd.nextInt(100);
			int a;//�s��
			if(r <= 10){
				//���܂ɂ̓����_���I��
				a = rnd.nextInt(ACTION);
			}else{
				//�ʏ�́AQ�l���ő剻����s����I��
				a = getMaxQAction(state);	
			}
			
			Point after = stateTrans(state, action[a]);//a���������Ƃ̏��
			if(after.x < 0 || after.y < 0 || after.x >= X || after.y >= Y){
				a += 2;
				a = a%4;
				after = stateTrans(state, action[a]);//a���������Ƃ̏��
			}
			
			//Q値更新
			int p = getMaxQAction(after);						//after�ł̍ő�Q�l���o���s��
			double afterQ = value[after.x][after.y][p];			//after�ł̍ő�Q�l
			value[state.x][state.y][a] = (1-alpha)*value[state.x][state.y][a] + 
				alpha*(gamma*afterQ - value[state.x][state.y][a]);
			
			sCount++;
			//�I�[��Ԃɓ��B�����Ƃ��̂݁A��V��^����
			if(after.equals(goal)){
				value[state.x][state.y][a] += alpha*reward;
			}else if(Arrays.asList(precipice_zone).contains(after)){
				//�R�ɗ��������̏���
				value[state.x][state.y][a] += alpha*(-100.0);
			}
			
			//�s�������s
			state = stateTrans(state, action[a]);
			
			boolean endEpisode = false;
			//�I�[�ɒ�������Astate�����Z�b�g
			if(state.equals(goal)){
				eCount++;					//1episode����
				System.out.println("Goal in! episode: " + eCount + " step: " + sCount);
				state.setLocation(START);
				sCount = 0;					//step�������Z�b�g	
				endEpisode = true;
			}else if(Arrays.asList(precipice_zone).contains(state)){
				eCount++;
				System.out.println("Fall Down! episode: " + eCount + " step: " + sCount);
				state.setLocation(START);
				sCount = 0;
				endEpisode = true;
			}
			
			//1episode���I��������
			if(endEpisode){
				//10episode���ɏ������~�߂�
				if(eCount >= 10 && eCount%10 == 0){
					InputStreamReader isr = new InputStreamReader(System.in);
			        BufferedReader br = new BufferedReader(isr);
			        try {
						String buf = br.readLine();
					} catch (IOException e) {
						// TODO �����������ꂽ catch �u���b�N
						e.printStackTrace();
					}
					
				}
			}
			//�I�u�U�[�o�ɒʒm���āA��ʂ��ĕ`��
			setChanged();
			notifyObservers();
			
			if(eCount >= episodecount){
				break;
			}
			
		}
		
	}
	
	//���state�ɂ����čs��action���Ƃ�����̏�Ԃ�Ԃ�
	public Point stateTrans(Point state, Point action){
		Point after = new Point(0,0);
		after.x = state.x + action.x;
		after.y = state.y + action.y;
		return after;
	}
	
	//���state�ɂ����āA�ő��Q�l�ƂȂ�s����Ԃ�
	public int getMaxQAction(Point state){
		
		double maxQ = -1.0;
		int index = 0;
		for(int i=0; i<ACTION; i++){
			double q = value[state.x][state.y][i];
			
			Point after = stateTrans(state, action[i]);
			if(after.x < 0 || after.y < 0 || after.x >= X || after.y >= Y){
				continue;
			}
			
			//�ő�Q�l�ƂȂ�s�����L��
			if(q > maxQ){
				index = i;
				maxQ = q;
			}else if(q == maxQ){
				Random rnd = new Random();
				int r = rnd.nextInt()%2;
				if(r==1){
					index = i;
					maxQ = q;
				}
			}
		}
		return index;
	}
}
