package dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import config.GameConfig;

import entity.GameAct;

public class GameDto {
	
	/**
	 * ��Ϸ����
	 */
	public static final int GAMEZONE_W = GameConfig.getSystemConfig().getMaxX() + 1;
	
	/**
	 * ��Ϸ�߶�
	 */
	public static final int GAMEZONE_H = GameConfig.getSystemConfig().getMaxY() + 1;
	
	/**
	 * ���ݿ��¼
	 */
	private List<Player> dbRecode;
	
	/**
	 * ���ؼ�¼
	 */
	private List<Player> diskRecode;
	
	/**
	 * ֻ��ʾ�л�û�У��ѻ��ķ���
	 */
	private boolean[][] gameMap;
	
	/**
	 * ���䷽��
	 */
	private GameAct gameAct;
	
	/**
	 * ��һ������
	 */
	private int next;
	
	/**
	 * �ȼ�
	 */
	private int nowLevel;
	
	/**
	 * �ȼ�
	 */
	private int nowPoint;
	
	/**
	 * ����
	 */
	private int nowRemoveLine;
	
	/**
	 * ��Ϸ�Ƿ��ǿ�ʼ״̬
	 */
	private boolean start;
	
	/**
	 * ��ͣ
	 */
	private boolean pause;
	
	/**
	 * ������
	 */
	public GameDto() {
		dtoInit();
	}

	/**
	 * dto��ʼ��
	 */
	public void dtoInit() {
		this.gameMap = new boolean[GAMEZONE_W][GAMEZONE_H ];
		this.nowLevel = 0;
		this.nowPoint = 0;
		this.nowRemoveLine = 0;
		this.pause = false;
	}
	
	public List<Player> getDbRecode() {
		return dbRecode;
	}

	public void setDbRecode(List<Player> dbRecode) {
		this.dbRecode = this.setFillRecode(dbRecode);	
	}

	public List<Player> getDiskRecode() {
		return diskRecode;
	}

	public void setDiskRecode(List<Player> diskRecode) {
		this.diskRecode = this.setFillRecode(diskRecode);
	}

	private List<Player> setFillRecode(List<Player> players) {
		//����������ǿգ���ô�ʹ���
		if (players == null) {
			players = new ArrayList<Player>();
		}
		//�����¼��С��5����ô�ͼӵ�5λ��
		while(players.size() < 5) {
			players.add(new Player("NoData", 0));
		}
		Collections.sort(players);
		return players;
	}
	
	public boolean[][] getGameMap() {
		return gameMap;
	}

	public void setGameMap(boolean[][] gameMap) {
		this.gameMap = gameMap;
	}

	public GameAct getGameAct() {
		return gameAct;
	}

	public void setGameAct(GameAct gameAct) {
		this.gameAct = gameAct;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getNowLevel() {
		return nowLevel;
	}

	public void setNowLevel(int nowLevel) {
		this.nowLevel = nowLevel;
	}

	public int getNowPoint() {
		return nowPoint;
	}

	public void setNowPoint(int nowPoint) {
		this.nowPoint = nowPoint;
	}

	public int getNowRemoveLine() {
		return nowRemoveLine;
	}

	public void setNowRemoveLine(int nowRemoveLine) {
		this.nowRemoveLine = nowRemoveLine;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public boolean isPause() {
		return pause;
	}

	public void changePause() {
		this.pause = !this.pause;
	}
}