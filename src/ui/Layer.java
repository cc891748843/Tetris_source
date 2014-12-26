package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import config.FrameConfig;
import config.GameConfig;
import dto.GameDto;

abstract public class Layer {
	
	/**
	 * �ڱ߾�
	 */
	protected static final int PADDING;
	
	/**
	 * �߿���
	 */
	protected static final int BORDER;
	
	/**
	 * ������Ƭ�Ŀ��
	 */
	protected static final int IMG_NUMBER_W = Img.IMG_NUMBER.getWidth(null) / 10;
	/**
	 * ������Ƭ�ĸ߶� 
	 */
	private static final int IMG_NUMBER_H = Img.IMG_NUMBER.getHeight(null);

	
	/**
	 * ����ֵ�۸߶�
	 */
	protected static final int IMG_RECT_H= Img.IMG_RECT.getHeight(null);
	
	/**
	 * ����ֵ��ͼƬ���
	 */
	private static final int IMG_RECT_W = Img.IMG_RECT.getWidth(null);
	
	//��ʼ������ֵ�ۿ��
	private final int rect_w;
	
	static {
		//�����Ϸ����
		FrameConfig fCfg = GameConfig.getFrameConfig();
		PADDING = fCfg.getPadding();
		BORDER = fCfg.getBorder();
	}
	
	int WINDOW_W = Img.WINDOW_IMG.getWidth(null);
	int WINDOW_H = Img.WINDOW_IMG.getHeight(null);
	
	private static final Font DEF_FONT = new Font("����", Font.BOLD, 20);
	
	protected final int x;
	// �������Ͻ�x����

	protected final int y;
	//�������Ͻ�y����
	
	protected final int w;
	//���ڿ��
	
	protected final int h;
	//���ڸ߶�
	
	/**
	 * ��Ϸ����
	 */
	protected GameDto dto = null;
	
	protected Layer(int x, int y, int w, int h){
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.rect_w = this.w - (PADDING << 1);
	}
	
	/*
	 * ���ƴ���
	 * */
	protected void createWindow(Graphics g){
		//������ݼ�ctrl+shift+o
		//����
			g.drawImage(Img.WINDOW_IMG, x, y, x+BORDER, y+BORDER, 0, 0, BORDER, BORDER, null);
			//drawImageǰ���ĸ���ʾ�������ָ꣬����������panel�е�λ�ã�����������ʾ�Ľ�ȡ�ı�ͼ��λ��
			//����
			g.drawImage(Img.WINDOW_IMG, x+BORDER, y ,x+w-BORDER, y+BORDER, BORDER, 0, WINDOW_W-BORDER, BORDER, null);
			//����
			g.drawImage(Img.WINDOW_IMG, w+x-BORDER, y ,x+w, y+BORDER, WINDOW_W-BORDER, 0, WINDOW_W, BORDER, null);
			//����
			g.drawImage(Img.WINDOW_IMG, x, y+BORDER ,x+BORDER, y+h-BORDER, 0, BORDER, BORDER, WINDOW_H-BORDER, null);
			//��
			g.drawImage(Img.WINDOW_IMG, x+BORDER, y+BORDER ,x+w-BORDER, y+h-BORDER, BORDER, BORDER, WINDOW_W-BORDER, WINDOW_H-BORDER, null);
			//����
			g.drawImage(Img.WINDOW_IMG, x+w-BORDER, y+BORDER ,x+w, y+h-BORDER, WINDOW_W-BORDER, BORDER, WINDOW_W, WINDOW_H-BORDER, null);
			//����
			g.drawImage(Img.WINDOW_IMG, x, y+h-BORDER ,x+BORDER, y+h, 0, WINDOW_H-BORDER, BORDER, WINDOW_H, null);
			//����
			g.drawImage(Img.WINDOW_IMG, x+BORDER, y+h-BORDER ,x+w-BORDER, y+h, BORDER, WINDOW_H-BORDER, WINDOW_W-BORDER, WINDOW_H, null);
			//����
			g.drawImage(Img.WINDOW_IMG, x+w-BORDER, y+h-BORDER ,x+w, y+h, WINDOW_W-BORDER, WINDOW_H-BORDER, WINDOW_W, WINDOW_H, null);
	}
	/**
	 * ˢϴ��Ϸ��������
	 * 
	 * @param g
	 */
	abstract public void paint(Graphics g);
	
	public void setDto(GameDto dto){
		this.dto = dto;
	}
	
	/**
	 * ���л�ͼ
	 */
	protected void drawImageAtCenter(Image img, Graphics g) {
		int imgW = img.getWidth(null);
		int imgH = img.getHeight(null);
		g.drawImage(img, this.x + (this.w - imgW >> 1), this.y + (this.h - imgH >> 1), null);
	}
	
	/**
	 * ��ʾ����
	 * @param x ���Ͻ�x����
	 * @param y ���Ͻ�����
	 * @param num Ҫ��ʾ������
	 * @param g ���ʶ���
	 * @param bitCount ����λ��
	 */
	protected void drawNumberLeftPad(int x, int y, int num, int maxCount, Graphics g) {
		//��Ҫ��ӡ������ת�����ַ���
		String strNum = Integer.toString(num);
		//ѭ���������������
		for(int i = 0; i < maxCount; i++){
			//�ж��Ƿ������������
			if(maxCount - i <= strNum.length()){
				//����������ַ����е��±�
				int idx = i - maxCount + strNum.length();
				//������number�е�ÿһλȡ��
				int bit = strNum.charAt(idx) - '0';
				//��������
				g.drawImage(Img.IMG_NUMBER,
						this.x + x + IMG_NUMBER_W * i, this.y + y, 
						this.x + x + IMG_NUMBER_W * (i + 1), this.y + y + IMG_NUMBER_H,
						bit * IMG_NUMBER_W, 0, 
						(bit + 1) * IMG_NUMBER_W , IMG_NUMBER_H, null);
			}
		}
	}
	
	/**
	 * ����ֵ��
	 * @param title
	 * @param number
	 * @param value
	 * @param maxValue
	 * @param g
	 */
	protected void drawRect(int y, String title, String number, double percent, Graphics g){
		//����ֵ��ʼ��
		int rect_x = this.x + PADDING;
		int rect_y = this.y + y;
		//���Ʊ���
		g.setColor(Color.BLACK);
		g.fillRect(rect_x, rect_y, this.rect_w, IMG_RECT_H+ 4);
		g.setColor(Color.WHITE);
		g.fillRect(rect_x + 1, rect_y + 1, this.rect_w - 2, IMG_RECT_H+ 2);
		g.setColor(Color.BLACK);
		g.fillRect(rect_x + 2, rect_y + 2, this.rect_w - 4, IMG_RECT_H);
		g.setColor(Color.GREEN);		
		//������
		int w = (int)((percent * (this.rect_w - 4)));
		//�����ɫ
		int subIdx = (int)(percent * IMG_RECT_W) - 1;
		//����ֵ��
		g.drawImage(Img.IMG_RECT, 
					rect_x + 2, rect_y + 2, 
					rect_x + w + 2, rect_y + 2 + IMG_RECT_H, 
					subIdx, 0, 
					subIdx + 1, IMG_RECT_H, null);
		g.setColor(Color.WHITE);
		g.setFont(DEF_FONT);
		g.drawString(title, rect_x + 4, rect_y + 22);
		if(number != null){
			g.drawString(number, rect_x + 232, rect_y + 22);
		}
	}
}
