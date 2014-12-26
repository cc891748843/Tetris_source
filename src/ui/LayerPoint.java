package ui;

import java.awt.Graphics;

import config.GameConfig;

public class LayerPoint extends Layer {
	
	
	/**
	 * 分数最大位数
	 */
	private static final int POINT_BIT = 5;
	
	/**
	 * 升级行数
	 */
	private static final int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();
	
	/**
	 * 消行x坐标
	 */
	private final int comX;
	
	/**
	 * 分数y坐标
	 */
	private final int pointY;
	
	/**
	 * 消行y坐标
	 */
	private final int rmLineY;
	
	/**
	 * 经验值y坐标
	 */
	private final int expY;
	
	public LayerPoint(int x, int y, int w, int h){
		super(x,y,w,h);
		//初始化共通的x坐标
		this.comX = this.w - IMG_NUMBER_W * POINT_BIT - PADDING;
		//初始化分数显示的y坐标
		this.pointY = PADDING;
		//初始化消行显示的y坐标
		this.rmLineY = this.pointY + Img.IMG_POINT.getHeight(null) + PADDING;
		//初始化经验值显示的y坐标
		this.expY = this.rmLineY + Img.IMG_RMLINE.getHeight(null) + PADDING;
	}
	
	public void paint(Graphics g){
		this.createWindow(g);
		//窗口标题（分数）
		g.drawImage(Img.IMG_POINT, this.x+PADDING, this.y+ pointY, null);
		//显示分数
		this.drawNumberLeftPad(comX, pointY ,this.dto.getNowPoint(), POINT_BIT, g);		
		//窗口标题（消行）
		g.drawImage(Img.IMG_RMLINE, this.x+PADDING, this.y+ rmLineY, null);
		//显示消行
		this.drawNumberLeftPad(comX, rmLineY ,this.dto.getNowRemoveLine(), POINT_BIT, g);	
		//绘制值槽
		int rmLine = this.dto.getNowRemoveLine();
		this.drawRect(expY, "下一级", null, (double)(rmLine % LEVEL_UP) / (double)LEVEL_UP, g);
		//TODO		
	}
}
