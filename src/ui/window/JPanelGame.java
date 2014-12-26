package ui.window;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import ui.Img;
import ui.Layer;

import config.FrameConfig;
import config.GameConfig;
import config.LayerConfig;
import control.GameControl;
import control.PlayerControl;
import dto.GameDto;

@SuppressWarnings("serial")
public class JPanelGame extends JPanel {

	private static final int BTN_SIZE_W = GameConfig.getFrameConfig().getButtonConfig().getButtonW();
	
	private static final int BTN_SIZE_H = GameConfig.getFrameConfig().getButtonConfig().getButtonH();
	
	private List<Layer> layers = null;
	
	private JButton btnStart;
	
	private JButton btnConfig;
	
	private GameControl gameControl = null;
	
	/**
	 * ���Ĵ���
	 */
	public JPanelGame(GameControl gameControl, GameDto dto) {
		//������Ϸ������
		this.gameControl = gameControl;
		//���ò��ֹ�����Ϊ���ɲ���
		this.setLayout(null);
		//��ʼ�����
		this.initComponent();
		//��ʼ����
		this.initLayer(dto);
		//��װ���̼�����
		this.addKeyListener(new PlayerControl(gameControl));
	}
	
	/**
	 * ��ʼ�����
	 */
	private void initComponent(){
		//��ʼ����ʼ��ť
		this.btnStart = new JButton(Img.BTN_START);
		//���ÿ�ʼ��ťλ��
		btnStart.setBounds(
				   GameConfig.getFrameConfig().getButtonConfig().getStartX(), 
				   GameConfig.getFrameConfig().getButtonConfig().getStartY(), 
				   BTN_SIZE_W, BTN_SIZE_H);
		//����ʼ��ť�����¼�����
		this.btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gameControl.start();
			}
			
		});
		//���Ӱ�ť�����
		this.add(btnStart);
		//��ʼ�����ð�ť
		this.btnConfig = new JButton(Img.BTN_CONFIG);
		//�������ð�ť��λ��
		this.btnConfig.setBounds(
					GameConfig.getFrameConfig().getButtonConfig().getUserConfigX(), 
					GameConfig.getFrameConfig().getButtonConfig().getUserConfigY(), 
					BTN_SIZE_W, BTN_SIZE_H);
		this.btnConfig.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				gameControl.showUserConfig();
				
			}
		});
		//���Ӱ�ť�����
		this.add(btnConfig);
	}
	
	/**
	 * ��ʼ����
	 */
	private void initLayer(GameDto dto){
		try {
			FrameConfig fCfg = GameConfig.getFrameConfig();
			//��ò�����
			List<LayerConfig> layersCfg = fCfg.getLayersConfig();
			//������Ϸ������
			layers = new ArrayList<Layer>(layersCfg.size());
			//�������в����
			for(LayerConfig layerCfg : layersCfg){
				//�����һ���ַ������������߷�����ʱ���÷���
				//��������
				Class<?> cls = Class.forName(layerCfg.getClassName());
				//��ù��캯��
				Constructor<?> ctr = cls.getConstructor(int.class, int.class, int.class, int.class);
				//���ù��캯����������
				Layer layer=(Layer)ctr.newInstance(
					layerCfg.getX(), layerCfg.getY(), layerCfg.getW(), layerCfg.getH()
				);
				//������Ϸ���ݶ���
				layer.setDto(dto);
				//�Ѵ�����Layer������뼯��
				layers.add(layer);
				
				
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	public void paintComponent(Graphics g){
		//���û��෽��
		super.paintComponent(g);
		//ѭ��ˢ����Ϸ���棬������Ϸ����
		for(int i=0;i < layers.size();i++){
			//ˢ�²㴰��
			layers.get(i).paint(g);
		}
		
		//���ؽ���,��ʾ�������ý���
		this.requestFocus();
	}

	/**
	 * ���ư�ť�Ƿ�ɵ�
	 */
	public void buttonSwitch(boolean onOff) {
		this.btnConfig.setEnabled(onOff);
		this.btnStart.setEnabled(onOff);
	}
}