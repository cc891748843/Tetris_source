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
	 * 随机数生成器
	 */
	private Random random = new Random();
	
	/**
	 * 方块种类个数
	 */
	private static final int MAX_TYPE = GameConfig.getSystemConfig().getTypeConfig().size() - 1;
	
	/**
	 * 升级行数
	 */
	private static final int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();

	/**
	 * 连续消行加分
	 */
	private static final Map<Integer, Integer> PLUS_POINT = GameConfig.getSystemConfig().getPlusPoint();
	
	public GameService(GameDto dto){
		this.dto = dto;
	}

	
	/**
	 * 控制器方向键（上）
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
	 * 方块操作（下）
	 */
	public boolean keyDown() {
		if(this.dto.isPause()){
			return true;
		}
		synchronized(this.dto){
			//方块向下移动，并判断是否移动陈工
			if(this.dto.getGameAct().move(0, 1, this.dto.getGameMap())){
				return false;
			}
			//获得游戏地图对象
			boolean[][] map = this.dto.getGameMap();
			//获得方块对象
			Point[] act = this.dto.getGameAct().getActPoints();
			//将方块堆积到地图数组
			for(int i= 0; i< act.length; i++){
				map[act[i].x][act[i].y] = true;
			}
			//判断消行，并计算获得经验值
			int exp = this.plusExp();
			//如果消行
			if(exp > 0){
				//增加经验值
				this.plusPoint(exp);
			}
			//创建下一个方块
			this.dto.getGameAct().init(this.dto.getNext());
			//随机生成下一个方块
			this.dto.setNext(random.nextInt(MAX_TYPE));
			//检查游戏是否失败
			if(this.isLose()){
				//把游戏开始状态关闭
				this.dto.setStart(false);
			}
			return true;
		}
	}
	
	/**
	 * 检查游戏是否失败
	 */
	private boolean isLose() {
		//会的现在的活动方块
		Point[] actPoints = this.dto.getGameAct().getActPoints();
		//获得现在的游戏地图
		boolean[][] map = this.dto.getGameMap();
		for(int i = 0;i < actPoints.length;i++){
			if(map[actPoints[i].x][actPoints[i].y]){
				return true;
			}
		}
		return false;
	}


	/**
	 * 方块操作（左）
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
	 * 方块操作（右）
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
	 * 作弊键
	 */
	public boolean keyFunUp() {
		this.plusPoint(4);
		return true;
	}

	/**
	 * 瞬间下落
	 */
	public boolean keyFunDown() {
		if(this.dto.isPause()){
			return true;
		}
		//快速下落
		while(!this.keyDown());
		return true;
	}

	/**
	 * 暂停
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
	 * 消行操作
	 */
	private int plusExp() {
		//获得游戏地图
		boolean[][] map = this.dto.getGameMap();
		int exp = 0;
		//扫描游戏地图，查看是否可消行
		for(int y = 0; y < GameDto.GAMEZONE_H; y++){
			//判断是否可消行
			if(isCanRemoveLine(y, map)){
				//如果可消行，就进行消行操作
				this.removeLine(y, map);
				//增加经验值
				exp++;
			}
		}
		return exp;
	}
	
	/**
	 * 消行出事
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
	 * 判断某一行是否可消
	 * @param y
	 * @return
	 */
	private boolean isCanRemoveLine(int y, boolean[][] map) {
		//单行内对每一个单元格扫描
		for(int x = 0; x < GameDto.GAMEZONE_W; x++){
			if(!map[x][y]){
				//如果有一个方格为false，则直接跳到下一行
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 加分升级操作
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
		//随机生成下一个方块
		this.dto.setNext(random.nextInt(MAX_TYPE));
		//随机生成画面方块
		this.dto.setGameAct(new GameAct(random.nextInt(MAX_TYPE)));
		//把游戏状态设为开始
		this.dto.setStart(true);
		//dto初始化
		this.dto.dtoInit();
	}
	
	public void mainAction(){
		this.keyDown();
	}
}
