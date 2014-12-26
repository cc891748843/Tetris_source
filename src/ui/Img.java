package ui;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import config.GameConfig;

public class Img {

	private Img(){}
	
	/**
	 * ����ͼƬ260 43
	 */
	public static final Image IMG_NUMBER = (Image) new ImageIcon("graphics/string/num.png").getImage();
	
	/**
	 * ����ͼƬ
	 */
	public static final Image IMG_LV=(Image) new ImageIcon("graphics/string/level.png").getImage();
	
	public static Image IMG_RECT = (Image) new ImageIcon("graphics/window/rect.png").getImage();
	
	
	public static Image IMG_POINT = (Image) new ImageIcon("graphics/string/point.png").getImage();
	public static Image IMG_RMLINE = (Image) new ImageIcon("graphics/string/rmline.png").getImage();
	public static Image WINDOW_IMG=(Image) new ImageIcon("graphics/window/Window.png").getImage();
	public static Image IMG_DB=(Image) new ImageIcon("graphics/string/db.png").getImage();
	public static Image IMG_DISK=(Image) new ImageIcon("graphics/string/disk.png").getImage();
	public static final Image GAME_ACT=(Image) new ImageIcon("graphics/game/rect.png").getImage();
	public static final Image IMG_ABOUT = (Image) new ImageIcon("graphics/string/sign.png").getImage();
	
	/**
	 * ��Ӱ
	 */
	public static final Image SHADW=(Image) new ImageIcon("graphics/game/shadw.png").getImage();
	
	/**
	 * ��ʼ��ť
	 */
	public static ImageIcon BTN_START = new ImageIcon("graphics/string/strat.png");
	
	/**
	 * ���ð�ť
	 */
	public static ImageIcon BTN_CONFIG = new ImageIcon("graphics/string/config.png");
	
	/**
	 * ��ͣͼƬ
	 */
	public static Image PAUSE = new ImageIcon("graphics/string/pause.png").getImage();
	
	
	/**
	 * ��һ��ͼƬ����
	 */
	public static Image[] NEXT_ACT;
	
	public static List<Image> BG_LIST;
	
	static {
		 //��һ������ͼƬ
		NEXT_ACT = new Image[GameConfig.getSystemConfig().getTypeConfig().size()];
		for(int i = 0; i < NEXT_ACT.length; i++){
			NEXT_ACT[i] = new ImageIcon("graphics/game/" + i + ".png").getImage();
		}
		
		//����ͼƬ����
		File dir = new File("graphics/background");
		File[] files = dir.listFiles();
		BG_LIST = new ArrayList<Image>();
		for(File file : files) {
			//���粻���ļ���
			if(!file.isDirectory()){
				BG_LIST.add(new ImageIcon(file.getPath()).getImage());
			}
		}
	}
}
