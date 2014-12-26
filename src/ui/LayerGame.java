package ui;

import java.awt.Graphics;
import java.awt.Point;

import config.GameConfig;
import entity.GameAct;

public class LayerGame extends Layer {
	
	/**
	 * ��λ��ƫ����
	 */
	private static final int SIZE_ROL = GameConfig.getFrameConfig().getSize_Rol();
	
	private static final int LOSE_IDX = GameConfig.getFrameConfig().getLoseIdx();
	
	public LayerGame(int x, int y, int w, int h){
		super(x,y,w,h);
	}

	public void paint(Graphics g){
		this.createWindow(g);
		GameAct act = this.dto.getGameAct();
		if (act != null){
			//��÷������鼯��
			Point[] points = act.getActPoints();
			//���ƻ����
			this.drawMainAct(points, g);
		}
		this.drawMap(g);
		//��ͣ
		if(this.dto.isPause()){
			this.drawImageAtCenter(Img.PAUSE, g);
		}
	}	
	

	/**
	 * ���ƻ����
	 * @param g
	 */
	private void drawMainAct(Point[] points, Graphics g) {
		//��÷������ͱ��(0~6)
		int typeCode = this.dto.getGameAct().getTypeCode();
		//���Ʒ���
		for(int i = 0;i < points.length; i++){
			drawActByPoint(points[i].x, points[i].y, typeCode + 1, g);			
		}
	}
	
	/**
	 * ���Ƶ�ͼ
	 * @param g
	 */
	private void drawMap(Graphics g) {
		//���Ƶ�ͼ
		boolean[][] map = this.dto.getGameMap();
		//���㵱ǰͼƬ�ѻ���ɫ
		int lv = this.dto.getNowLevel();
		int imgIdx = lv == 0 ? 0 : lv % 7 + 1;	
		for(int x = 0; x < map.length; x++){
			for(int y = 0; y < map[x].length; y++){
				if(map[x][y]){
					drawActByPoint(x, y, imgIdx, g);
				}

			}
		}
	}

	private void drawActByPoint(int x, int y, int imgIdx, Graphics g){
		imgIdx = this.dto.isStart() ? imgIdx : LOSE_IDX;
		g.drawImage(Img.GAME_ACT, 
				this.x + (x << SIZE_ROL) + BORDER,
				this.y + (y << SIZE_ROL) + BORDER,
				this.x + (x +1 << SIZE_ROL) + BORDER, 
				this.y + (y +1 << SIZE_ROL) + BORDER,
				imgIdx << SIZE_ROL, 0, (imgIdx + 1) << SIZE_ROL, 1 << SIZE_ROL, null);
	}
}
