package control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 
 * @author Administrator
 *
 */
public class PlayerControl extends KeyAdapter{
	
	private GameControl gameControl;
	
	public PlayerControl(GameControl gameControl){
		this.gameControl = gameControl;
	}

	/**
	 *  键盘按下事件
	 */
	public void keyPressed(KeyEvent e) {
//		//e.getKeyCode()获得键盘每个键的编码
		try {
			this.gameControl.attionByKeyCode(e.getKeyCode());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
