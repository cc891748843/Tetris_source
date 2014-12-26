package service;

public interface GameTetris {
	
	/**
	 * 方向向上
	 */
	public boolean keyUp();
	
	/**
	 * 方向向下
	 * @return 
	 */
	public boolean keyDown();
	
	/**
	 * 方向向右
	 */
	public boolean keyRight();
	
	/**
	 * 方向向左
	 */
	public boolean keyLeft();
	
	/**
	 * 三角，作弊
	 */
	public boolean keyFunUp();
	
	/**
	 * 大叉
	 */
	public boolean keyFunDown();
	
	/**
	 * 圆圈
	 */
	public boolean keyFunRight();
	
	/**
	 * 方块
	 */
	public boolean keyFunLeft();
	
	/**
	 * 启动主线程，开始游戏
	 */
	public void startGame();
	
	/**
	 * 游戏主要行为
	 */
	public void mainAction();
}
