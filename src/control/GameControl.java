package control;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import service.GameService;
import service.GameTetris;
import ui.window.JFrameConfig;
import ui.window.JFrameGame;
import ui.window.JFrameSavePoint;
import ui.window.JPanelGame;
import config.DataInterfaceConfig;
import config.GameConfig;
import dao.Data;
import dto.GameDto;
import dto.Player;

/**
 * ������Ҽ����¼�
 * ���ƻ���
 * ������Ϸ�߼�
 * @author Administrator
 *
 */
public class GameControl {
	
	/**
	 * ���ݷ��ʽӿ�A
	 */
	private Data dataA;
	
	/**
	 * ���ݷ��ʽӿ�B
	 */
	private Data dataB;
	
	/**
	 * ��Ϸ�����
	 */
	private JPanelGame panelGame;
	
	/**
	 * ��Ϸ�߼���
	 */
	private  GameTetris gameService;
	
	/**
	 * ��Ϸ���ƴ���
	 */
	private JFrameConfig frameConfig;
	
	/**
	 * �����������
	 */
	private JFrameSavePoint frameSavePoint;
	
	/**
	 * ��Ϸ��Ϊ����
	 */
	private Map<Integer, Method> actionList;
	
	/**
	 * ��Ϸ����Դ
	 */
	private GameDto dto = null;
	
	/**
	 * ��Ϸ�߳� 
	 */
	private Thread gameThread = null;
	
	public GameControl() throws Exception{
		//������Ϸ����Դ
		this.dto = new GameDto();	
		//������Ϸ�߼��飨��װ��Ϸ����Դ��
		this.gameService = new GameService(dto);
		//�����ݽӿ�A������ݿ��¼
		//��������
		this.dataA = createDataObject(GameConfig.getDataConfig().getDataA());
		//�������ݿ��¼����Ϸ
		this.dto.setDbRecode(dataA.loadData());
		//�����ݽӿ�B������ݿ��¼
		this.dataB = createDataObject(GameConfig.getDataConfig().getDataB());
		// ���ñ��ش��̼�¼����Ϸ
		this.dto.setDiskRecode(dataB.loadData());
		//������Ϸ���
		this.panelGame = new JPanelGame(this, dto);
		//��ȡ�û�����
		this.setControlConfig();
		//��ʼ���û����ô���
		this.frameConfig = new JFrameConfig(this);
		//��ʼ�������¼����
		this.frameSavePoint = new JFrameSavePoint(this);
		//��ʼ����Ϸ������(��װ��Ϸ���)
		new JFrameGame(this.panelGame);	
	}
	
	/**
	 * ��ȡ��������
	 */
	@SuppressWarnings("resource")
	private void setControlConfig() {
		//�����������뷽������ӳ������
		this.actionList = new HashMap<Integer, Method>();
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/control.dat"));
			@SuppressWarnings("unchecked")
			HashMap<Integer, String> cfgSet = (HashMap<Integer, String>)ois.readObject();
			Set<Entry<Integer, String>> entryset = cfgSet.entrySet();
			for(Entry<Integer, String> e : entryset) {
				actionList.put(e.getKey(), this.gameService.getClass().getMethod(e.getValue()));
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
	}
	
	/**
	 * �������ݶ���
	 * @param cfg
	 * @return
	 */
	private Data createDataObject(DataInterfaceConfig cfg) throws Exception {
		try {
			//��������
			Class<?> cls = Class.forName(cfg.getClassName());
			//��ù�����
			Constructor<?> ctr = cls.getConstructor(HashMap.class);
			//��������
			return (Data)ctr.newInstance(cfg.getParam());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * ������ҿ�����������Ϊ
	 * @param keyCode
	 */
	public void attionByKeyCode(int keyCode) throws Exception {
		try {
			if(this.actionList.containsKey(keyCode)) {
				this.actionList.get(keyCode).invoke(this.gameService);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		this.panelGame.repaint();
	}
	
	/**
	 * ��ʾ��ҿ��ƴ���
	 */
	public void showUserConfig() {
		this.frameConfig.setVisible(true);
	}

	/**
	 * �Ӵ��ڹر��¼�
	 */
	public void setOver() {
		this.panelGame.repaint();
		this.setControlConfig();
	}
	
	/**
	 * ��ʼ��ť�¼�
	 */
	public void start() {
		//��尴ť����Ϊ���ɵ��
		this.panelGame.buttonSwitch(false);
		//�رմ���
		this.frameConfig.setVisible(false);
		this.frameSavePoint.setVisible(false);
		//��Ϸ���ݳ�ʼ��
		this.gameService.startGame();
		//�����̶߳���
		this.gameThread = new MainThread();
		//�����߳� 
		this.gameThread.start();
		//ˢ����Ϸ����
		this.panelGame.repaint();
	}
	
	/**
	 * ��Ϸʧ��֮����
	 */
	public void afterLose(){
		//��ʾ�÷ִ���
		this.frameSavePoint.show(this.dto.getNowPoint());
		//ʹ��ť�����ٵ��
		this.panelGame.buttonSwitch(true);
	}
	
	/**
	 * �������
	 * @param name
	 */
	public void savePoint(String name) {
		Player pla = new Player(name, this.dto.getNowPoint());
		//�����¼�����ݿ�
		this.dataA.saveData(pla);
		//�����¼�����ش���
		this.dataB.saveData(pla);
		//�������ݿ��¼����Ϸ
		this.dto.setDbRecode(dataA.loadData());
		//���ô��̼�¼����Ϸ
		this.dto.setDiskRecode(dataB.loadData());
		//ˢ�»��� 
		this.panelGame.repaint();
	}	
	
	private class MainThread extends Thread {
		@Override
		public void run() {
			//ˢ�»���
			panelGame.repaint();
			//��ѭ��
			while(dto.isStart()){
				try {
					//�ȴ�0.5s
					Thread.sleep(ChangeSpeed());
					//�����ͣ����ô��ִ������Ϊ
					if(dto.isPause()){
						continue;
					}
					//����Ϊ
					gameService.mainAction();
					//ˢ�»���
					panelGame.repaint();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			afterLose();
		}
	}
	
	/**
	 * �������
	 * @return
	 */
	private int ChangeSpeed() {
		int speed;
		if(this.dto.getNowPoint() >= 15000){
			speed = 20;
		}
		if(this.dto.getNowPoint() >= 10000){
			speed = 50;
		}
		if(this.dto.getNowPoint() >= 8500){
			speed = 100;
		}
		if(this.dto.getNowPoint() >= 5000){
			speed = 150;
		}
		if(this.dto.getNowPoint() >= 2500){
			speed = 250;
		}
		else{
			speed = 500;
		}
		return speed;
		
	}
}
