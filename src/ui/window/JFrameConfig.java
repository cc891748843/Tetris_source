package ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import control.GameControl;

import util.FrameUtil;

@SuppressWarnings("serial")
public class JFrameConfig extends JFrame {
	
	private JButton btnOk= new JButton("ȷ��");
	
	private JButton btnCancel= new JButton("ȡ��");
	
	private JButton btnUser= new JButton("Ӧ��");
	
	private TextCtrl[] keyText = new TextCtrl[8];
	
	private JLabel errorMsg = new JLabel();
	
	private GameControl gameControl;
	
	private final static Image IMG_PSP = new ImageIcon("data/psp.jpg").getImage();
	
	private final static String[] METHOD_NAMES = {
		"keyRight","keyUp"
		,"keyLeft","keyDown"
		,"keyFunLeft","keyFunUp"
		,"keyFunRight","keyFunDown"
	};
	
	private final static String PATH = "data/control.dat";
	
	public JFrameConfig(GameControl gameControl) throws Exception {
		//�����Ϸ����������
		this.gameControl = gameControl;
		//���ò��ֹ�����Ϊ�߽粼��
		this.setLayout(new BorderLayout());
		this.setTitle("����");
		//��ʼ�����������
		this.initKeyText();
		//��������
		this.add(createMainPanel(), BorderLayout.CENTER);
		//��Ӱ�ť���
		this.add(this.createButtonPanel(), BorderLayout.SOUTH);
		//���ô��ڲ��ܸı��С
		this.setResizable(false);
		//���ô��ڴ�С
		this.setSize(680, 320);
		//����
		FrameUtil.setFrameCenter(this);
	}

	/**
	 * ��ʼ�����������
	 * @throws Exception 
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	private void initKeyText() throws Exception {
		int x = 5;
		int y = 23;
		int w = 65;
		int h = 25;
		for(int i = 0; i < 4; i++){
			keyText[i] = new TextCtrl(x, y, w, h, 0,METHOD_NAMES[i]);
			y+=30;
		}
		x = 600;
		y = 23;
		for(int i = 4; i < 8; i++){
			keyText[i] = new TextCtrl(x, y, w, h, 0,METHOD_NAMES[i]);
			y+=30;
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATH));
			@SuppressWarnings("unchecked")
			HashMap<Integer, String> cfgSet = (HashMap)ois.readObject();
			ois.close();
			Set<Entry<Integer, String>> entryset = cfgSet.entrySet();
			for (Entry<Integer, String> e : entryset) {
				for(TextCtrl tc : keyText){
					if(tc.getMethodName().equals(e.getValue())){
						tc.setKeyCode(e.getKey());
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * ������ť���
	 * @return
	 */
	private JPanel createButtonPanel() {
		//������ť��壬��ʽ����
		JPanel jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		//��ȷ����ť�����¼����� 
		this.btnOk.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					if(writeConfig()){
						setVisible(false);
						gameControl.setOver();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		this.errorMsg.setForeground(Color.RED);
		jp.add(this.errorMsg);
		jp.add(this.btnOk);
		this.btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setVisible(false);
				gameControl.setOver();
			}
		});
		jp.add(this.btnCancel);
		this.btnUser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					writeConfig();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		jp.add(this.btnUser);
		return jp;
	}
	
	/**
	 * ���������(ѡ����)
	 * @return
	 */
	private JTabbedPane createMainPanel() {
		JTabbedPane jtp = new JTabbedPane();
		jtp.addTab("��������", this.createControlPanel());
		jtp.addTab("Ƥ������", new JLabel("Ƥ��"));
		return jtp;
	}
	
	/**
	 * ��ҿ����������
	 * @return
	 */
	private JPanel createControlPanel() {
		JPanel jp = new JPanel(){
		
			{

			}
			public void paintComponent(Graphics g) {
				g.drawImage(IMG_PSP, 0, 0, null);
			}
		};
		//���ò��ֹ�����
		jp.setLayout(null);
		for(int i = 0; i < keyText.length; i++){
			jp.add(keyText[i]);
		}
		return jp;
	}
	
	
	/** 
	 * д����Ϸ����
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private boolean writeConfig() {
		HashMap<Integer, String> keySet = new HashMap<Integer, String>();
		for(int i = 0; i < this.keyText.length; i++){
			int keyCode = this.keyText[i].getKeyCode();
			if(keyCode == 0) {
				this.errorMsg.setText("���󰴼�");
				return false;
			}
			keySet.put(keyCode, this.keyText[i].getMethodName());
		}
		if (keySet.size() != 8) {
			this.errorMsg.setText("�ظ�����");
			return false;
		}
		try {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH));
		oos.writeObject(keySet);
		oos.close();
		} catch(Exception e) {
			e.printStackTrace();
			this.errorMsg.setText(e.getMessage());
			return false;
		}
		this.errorMsg.setText(null);
		return true;
	}
}
