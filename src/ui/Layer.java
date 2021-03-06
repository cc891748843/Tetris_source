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
	 * 内边距
	 */
	protected static final int PADDING;
	
	/**
	 * 边框宽度
	 */
	protected static final int BORDER;
	
	/**
	 * 数字切片的宽度
	 */
	protected static final int IMG_NUMBER_W = Img.IMG_NUMBER.getWidth(null) / 10;
	/**
	 * 数字切片的高度 
	 */
	private static final int IMG_NUMBER_H = Img.IMG_NUMBER.getHeight(null);

	
	/**
	 * 矩形值槽高度
	 */
	protected static final int IMG_RECT_H= Img.IMG_RECT.getHeight(null);
	
	/**
	 * 矩形值槽图片宽度
	 */
	private static final int IMG_RECT_W = Img.IMG_RECT.getWidth(null);
	
	//初始化经验值槽宽度
	private final int rect_w;
	
	static {
		//获得游戏配置
		FrameConfig fCfg = GameConfig.getFrameConfig();
		PADDING = fCfg.getPadding();
		BORDER = fCfg.getBorder();
	}
	
	int WINDOW_W = Img.WINDOW_IMG.getWidth(null);
	int WINDOW_H = Img.WINDOW_IMG.getHeight(null);
	
	private static final Font DEF_FONT = new Font("黑体", Font.BOLD, 20);
	
	protected final int x;
	// 窗口左上角x坐标

	protected final int y;
	//窗口左上角y坐标
	
	protected final int w;
	//窗口宽度
	
	protected final int h;
	//窗口高度
	
	/**
	 * 游戏数据
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
	 * 绘制窗口
	 * */
	protected void createWindow(Graphics g){
		//引包快捷键ctrl+shift+o
		//左上
			g.drawImage(Img.WINDOW_IMG, x, y, x+BORDER, y+BORDER, 0, 0, BORDER, BORDER, null);
			//drawImage前面四个表示两个坐标，指的是在容器panel中的位置，后面两个表示的截取的本图的位置
			//中上
			g.drawImage(Img.WINDOW_IMG, x+BORDER, y ,x+w-BORDER, y+BORDER, BORDER, 0, WINDOW_W-BORDER, BORDER, null);
			//右上
			g.drawImage(Img.WINDOW_IMG, w+x-BORDER, y ,x+w, y+BORDER, WINDOW_W-BORDER, 0, WINDOW_W, BORDER, null);
			//左中
			g.drawImage(Img.WINDOW_IMG, x, y+BORDER ,x+BORDER, y+h-BORDER, 0, BORDER, BORDER, WINDOW_H-BORDER, null);
			//中
			g.drawImage(Img.WINDOW_IMG, x+BORDER, y+BORDER ,x+w-BORDER, y+h-BORDER, BORDER, BORDER, WINDOW_W-BORDER, WINDOW_H-BORDER, null);
			//右中
			g.drawImage(Img.WINDOW_IMG, x+w-BORDER, y+BORDER ,x+w, y+h-BORDER, WINDOW_W-BORDER, BORDER, WINDOW_W, WINDOW_H-BORDER, null);
			//左下
			g.drawImage(Img.WINDOW_IMG, x, y+h-BORDER ,x+BORDER, y+h, 0, WINDOW_H-BORDER, BORDER, WINDOW_H, null);
			//中下
			g.drawImage(Img.WINDOW_IMG, x+BORDER, y+h-BORDER ,x+w-BORDER, y+h, BORDER, WINDOW_H-BORDER, WINDOW_W-BORDER, WINDOW_H, null);
			//右下
			g.drawImage(Img.WINDOW_IMG, x+w-BORDER, y+h-BORDER ,x+w, y+h, WINDOW_W-BORDER, WINDOW_H-BORDER, WINDOW_W, WINDOW_H, null);
	}
	/**
	 * 刷洗游戏具体内容
	 * 
	 * @param g
	 */
	abstract public void paint(Graphics g);
	
	public void setDto(GameDto dto){
		this.dto = dto;
	}
	
	/**
	 * 正中绘图
	 */
	protected void drawImageAtCenter(Image img, Graphics g) {
		int imgW = img.getWidth(null);
		int imgH = img.getHeight(null);
		g.drawImage(img, this.x + (this.w - imgW >> 1), this.y + (this.h - imgH >> 1), null);
	}
	
	/**
	 * 显示数字
	 * @param x 左上角x坐标
	 * @param y 左上角坐标
	 * @param num 要显示的数字
	 * @param g 画笔对象
	 * @param bitCount 数字位数
	 */
	protected void drawNumberLeftPad(int x, int y, int num, int maxCount, Graphics g) {
		//把要打印的数字转换成字符串
		String strNum = Integer.toString(num);
		//循环绘制数字左对齐
		for(int i = 0; i < maxCount; i++){
			//判断是否满足绘制条件
			if(maxCount - i <= strNum.length()){
				//获得数字在字符串中的下标
				int idx = i - maxCount + strNum.length();
				//把数字number中的每一位取出
				int bit = strNum.charAt(idx) - '0';
				//绘制数字
				g.drawImage(Img.IMG_NUMBER,
						this.x + x + IMG_NUMBER_W * i, this.y + y, 
						this.x + x + IMG_NUMBER_W * (i + 1), this.y + y + IMG_NUMBER_H,
						bit * IMG_NUMBER_W, 0, 
						(bit + 1) * IMG_NUMBER_W , IMG_NUMBER_H, null);
			}
		}
	}
	
	/**
	 * 绘制值槽
	 * @param title
	 * @param number
	 * @param value
	 * @param maxValue
	 * @param g
	 */
	protected void drawRect(int y, String title, String number, double percent, Graphics g){
		//各种值初始化
		int rect_x = this.x + PADDING;
		int rect_y = this.y + y;
		//绘制背景
		g.setColor(Color.BLACK);
		g.fillRect(rect_x, rect_y, this.rect_w, IMG_RECT_H+ 4);
		g.setColor(Color.WHITE);
		g.fillRect(rect_x + 1, rect_y + 1, this.rect_w - 2, IMG_RECT_H+ 2);
		g.setColor(Color.BLACK);
		g.fillRect(rect_x + 2, rect_y + 2, this.rect_w - 4, IMG_RECT_H);
		g.setColor(Color.GREEN);		
		//求出宽度
		int w = (int)((percent * (this.rect_w - 4)));
		//求出颜色
		int subIdx = (int)(percent * IMG_RECT_W) - 1;
		//绘制值槽
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
