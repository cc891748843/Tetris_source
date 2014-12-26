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
	 * 数字图片260 43
	 */
	public static final Image IMG_NUMBER = (Image) new ImageIcon("graphics/string/num.png").getImage();
	
	/**
	 * 标题图片
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
	 * 阴影
	 */
	public static final Image SHADW=(Image) new ImageIcon("graphics/game/shadw.png").getImage();
	
	/**
	 * 开始按钮
	 */
	public static ImageIcon BTN_START = new ImageIcon("graphics/string/strat.png");
	
	/**
	 * 设置按钮
	 */
	public static ImageIcon BTN_CONFIG = new ImageIcon("graphics/string/config.png");
	
	/**
	 * 暂停图片
	 */
	public static Image PAUSE = new ImageIcon("graphics/string/pause.png").getImage();
	
	
	/**
	 * 下一个图片数组
	 */
	public static Image[] NEXT_ACT;
	
	public static List<Image> BG_LIST;
	
	static {
		 //下一个方块图片
		NEXT_ACT = new Image[GameConfig.getSystemConfig().getTypeConfig().size()];
		for(int i = 0; i < NEXT_ACT.length; i++){
			NEXT_ACT[i] = new ImageIcon("graphics/game/" + i + ".png").getImage();
		}
		
		//背景图片数组
		File dir = new File("graphics/background");
		File[] files = dir.listFiles();
		BG_LIST = new ArrayList<Image>();
		for(File file : files) {
			//假如不是文件夹
			if(!file.isDirectory()){
				BG_LIST.add(new ImageIcon(file.getPath()).getImage());
			}
		}
	}
}
