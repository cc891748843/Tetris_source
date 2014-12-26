package ui;

import java.awt.Graphics;

import config.GameConfig;

public class LayerPoint extends Layer {
	
	
	/**
	 * �������λ��
	 */
	private static final int POINT_BIT = 5;
	
	/**
	 * ��������
	 */
	private static final int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();
	
	/**
	 * ����x����
	 */
	private final int comX;
	
	/**
	 * ����y����
	 */
	private final int pointY;
	
	/**
	 * ����y����
	 */
	private final int rmLineY;
	
	/**
	 * ����ֵy����
	 */
	private final int expY;
	
	public LayerPoint(int x, int y, int w, int h){
		super(x,y,w,h);
		//��ʼ����ͨ��x����
		this.comX = this.w - IMG_NUMBER_W * POINT_BIT - PADDING;
		//��ʼ��������ʾ��y����
		this.pointY = PADDING;
		//��ʼ��������ʾ��y����
		this.rmLineY = this.pointY + Img.IMG_POINT.getHeight(null) + PADDING;
		//��ʼ������ֵ��ʾ��y����
		this.expY = this.rmLineY + Img.IMG_RMLINE.getHeight(null) + PADDING;
	}
	
	public void paint(Graphics g){
		this.createWindow(g);
		//���ڱ��⣨������
		g.drawImage(Img.IMG_POINT, this.x+PADDING, this.y+ pointY, null);
		//��ʾ����
		this.drawNumberLeftPad(comX, pointY ,this.dto.getNowPoint(), POINT_BIT, g);		
		//���ڱ��⣨���У�
		g.drawImage(Img.IMG_RMLINE, this.x+PADDING, this.y+ rmLineY, null);
		//��ʾ����
		this.drawNumberLeftPad(comX, rmLineY ,this.dto.getNowRemoveLine(), POINT_BIT, g);	
		//����ֵ��
		int rmLine = this.dto.getNowRemoveLine();
		this.drawRect(expY, "��һ��", null, (double)(rmLine % LEVEL_UP) / (double)LEVEL_UP, g);
		//TODO		
	}
}
