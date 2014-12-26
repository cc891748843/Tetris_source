package service;

import java.awt.Point;
import java.util.Map;
import java.util.Random;

import config.GameConfig;

import dto.GameDto;
import entity.GameAct;

public class GameService implements GameTetris{

	private GameDto dto;
	
	/**
	 * �����������
	 */
	private Random random = new Random();
	
	/**
	 * �����������
	 */
	private static final int MAX_TYPE = GameConfig.getSystemConfig().getTypeConfig().size() - 1;
	
	/**
	 * ��������
	 */
	private static final int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();

	/**
	 * �������мӷ�
	 */
	private static final Map<Integer, Integer> PLUS_POINT = GameConfig.getSystemConfig().getPlusPoint();
	
	public GameService(GameDto dto){
		this.dto = dto;
	}

	
	/**
	 * ��������������ϣ�
	 */
	public boolean keyUp() {
		if(this.dto.isPause()){
			return true;
		}
		synchronized(this.dto){
			this.dto.getGameAct().round(this.dto.getGameMap());
		}
		return true;
	}
	
	/**
	 * ����������£�
	 */
	public boolean keyDown() {
		if(this.dto.isPause()){
			return true;
		}
		synchronized(this.dto){
			//���������ƶ������ж��Ƿ��ƶ��¹�
			if(this.dto.getGameAct().move(0, 1, this.dto.getGameMap())){
				return false;
			}
			//�����Ϸ��ͼ����
			boolean[][] map = this.dto.getGameMap();
			//��÷������
			Point[] act = this.dto.getGameAct().getActPoints();
			//������ѻ�����ͼ����
			for(int i= 0; i< act.length; i++){
				map[act[i].x][act[i].y] = true;
			}
			//�ж����У��������þ���ֵ
			int exp = this.plusExp();
			//�������
			if(exp > 0){
				//���Ӿ���ֵ
				this.plusPoint(exp);
			}
			//������һ������
			this.dto.getGameAct().init(this.dto.getNext());
			//���������һ������
			this.dto.setNext(random.nextInt(MAX_TYPE));
			//�����Ϸ�Ƿ�ʧ��
			if(this.isLose()){
				//����Ϸ��ʼ״̬�ر�
				this.dto.setStart(false);
			}
			return true;
		}
	}
	
	/**
	 * �����Ϸ�Ƿ�ʧ��
	 */
	private boolean isLose() {
		//������ڵĻ����
		Point[] actPoints = this.dto.getGameAct().getActPoints();
		//������ڵ���Ϸ��ͼ
		boolean[][] map = this.dto.getGameMap();
		for(int i = 0;i < actPoints.length;i++){
			if(map[actPoints[i].x][actPoints[i].y]){
				return true;
			}
		}
		return false;
	}


	/**
	 * �����������
	 */
	public boolean keyLeft() {
		if(this.dto.isPause()){
			return true;
		}
		synchronized(this.dto){
			this.dto.getGameAct().move(-1, 0, this.dto.getGameMap());	
		}
		return true;
	}
	
	/**
	 * ����������ң�
	 */
	public boolean keyRight() {
		if(this.dto.isPause()){
			return true;
		}
		synchronized(this.dto){
			this.dto.getGameAct().move(1, 0, this.dto.getGameMap());
		}
		return true;
	}
	
	/**
	 * ���׼�
	 */
	public boolean keyFunUp() {
		this.plusPoint(4);
		return true;
	}

	/**
	 * ˲������
	 */
	public boolean keyFunDown() {
		if(this.dto.isPause()){
			return true;
		}
		//��������
		while(!this.keyDown());
		return true;
	}

	/**
	 * ��ͣ
	 */
	public boolean keyFunRight() {
		if(this.dto.isStart()){
			this.dto.changePause();
		}
		return true;
	}

	public boolean keyFunLeft() {
		return true;
	}
	
	/**
	 * ���в���
	 */
	private int plusExp() {
		//�����Ϸ��ͼ
		boolean[][] map = this.dto.getGameMap();
		int exp = 0;
		//ɨ����Ϸ��ͼ���鿴�Ƿ������
		for(int y = 0; y < GameDto.GAMEZONE_H; y++){
			//�ж��Ƿ������
			if(isCanRemoveLine(y, map)){
				//��������У��ͽ������в���
				this.removeLine(y, map);
				//���Ӿ���ֵ
				exp++;
			}
		}
		return exp;
	}
	
	/**
	 * ���г���
	 * @param y
	 * @param map
	 */
	private void removeLine(int rowNumber, boolean[][] map) {
		for(int x = 0; x < GameDto.GAMEZONE_W;x++){
			for(int y = rowNumber; y > 0;y--){
				map[x][y] = map[x][y-1];
			}
			map[x][0] = false;
		}
	}


	/**
	 * �ж�ĳһ���Ƿ����
	 * @param y
	 * @return
	 */
	private boolean isCanRemoveLine(int y, boolean[][] map) {
		//�����ڶ�ÿһ����Ԫ��ɨ��
		for(int x = 0; x < GameDto.GAMEZONE_W; x++){
			if(!map[x][y]){
				//�����һ������Ϊfalse����ֱ��������һ��
				return false;
			}
		}
		return true;
	}
	
	/**
	 * �ӷ���������
	 * @param exp
	 */
	private void plusPoint(int exp) {
		int lv = this.dto.getNowLevel();
		int rmLine = this.dto.getNowRemoveLine();
		int point = this.dto.getNowPoint();
		if(rmLine % LEVEL_UP + exp >= LEVEL_UP){
			this.dto.setNowLevel(++lv);
		}
		this.dto.setNowRemoveLine(rmLine + exp);
		this.dto.setNowPoint(point + PLUS_POINT.get(exp));
	}

	public void startGame() {
		//���������һ������
		this.dto.setNext(random.nextInt(MAX_TYPE));
		//������ɻ��淽��
		this.dto.setGameAct(new GameAct(random.nextInt(MAX_TYPE)));
		//����Ϸ״̬��Ϊ��ʼ
		this.dto.setStart(true);
		//dto��ʼ��
		this.dto.dtoInit();
	}
	
	public void mainAction(){
		this.keyDown();
	}
}
