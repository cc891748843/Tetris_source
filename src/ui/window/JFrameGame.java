package ui.window;

import javax.swing.JFrame;

import util.FrameUtil;

import config.FrameConfig;
import config.GameConfig;

@SuppressWarnings("serial")
public class JFrameGame extends JFrame {
	/**
	 * @param args
	 */
	public JFrameGame(JPanelGame panelGame){
		//�����Ϸ����
		FrameConfig fCfg = GameConfig.getFrameConfig();
		//���ñ���
		this.setTitle(fCfg.getTitle());
		//����Ĭ�Ϲر����ԣ��������
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//���ô��ڴ�С
		this.setSize(fCfg.getWidth(),fCfg.getHeight());
		//�������û��ı䴰�ڴ�С
		this.setResizable(false);
		//����
		FrameUtil.setFrameCenter(this);
		//����Ĭ��Panel
		this.setContentPane(panelGame);
		
		this.setVisible(true);
	}
}
