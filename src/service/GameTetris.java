package service;

public interface GameTetris {
	
	/**
	 * ��������
	 */
	public boolean keyUp();
	
	/**
	 * ��������
	 * @return 
	 */
	public boolean keyDown();
	
	/**
	 * ��������
	 */
	public boolean keyRight();
	
	/**
	 * ��������
	 */
	public boolean keyLeft();
	
	/**
	 * ���ǣ�����
	 */
	public boolean keyFunUp();
	
	/**
	 * ���
	 */
	public boolean keyFunDown();
	
	/**
	 * ԲȦ
	 */
	public boolean keyFunRight();
	
	/**
	 * ����
	 */
	public boolean keyFunLeft();
	
	/**
	 * �������̣߳���ʼ��Ϸ
	 */
	public void startGame();
	
	/**
	 * ��Ϸ��Ҫ��Ϊ
	 */
	public void mainAction();
}
