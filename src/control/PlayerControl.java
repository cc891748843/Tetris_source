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
	 *  ���̰����¼�
	 */
	public void keyPressed(KeyEvent e) {
//		//e.getKeyCode()��ü���ÿ�����ı���
		try {
			this.gameControl.attionByKeyCode(e.getKeyCode());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
