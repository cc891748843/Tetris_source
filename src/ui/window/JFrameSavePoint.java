package ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.FrameUtil;
import control.GameControl;

@SuppressWarnings("serial")
public class JFrameSavePoint extends JFrame{
	
	private JLabel lbPoint = null;
	
	private JTextField txName = null;
	
	private JButton btnOk = null;

	private JLabel errMsg = null;
	
	private GameControl gameControl = null;
	
	public JFrameSavePoint(GameControl gameControl){
		this.gameControl = gameControl;
		this.setTitle("�����¼");
		this.setSize(256, 128);
		FrameUtil.setFrameCenter(this);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.createCom();
		this.createAction();
	}
	
	/**
	 * ��ʾ����
	 */
	public void show(int point) {
		this.lbPoint.setText("���ĵ÷���:" + point);
		this.setVisible(true);
	}
	
	/**
	 * �����¼�����
	 */
	private void createAction() {
		this.btnOk.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO�����Լ�������������֤
				String name = txName.getText();
				if(name.length() > 16 || name == null || "".equals(name)){
					errMsg.setText("������������");
				} else{
					setVisible(false);
					gameControl.savePoint(name);
				}
			}	
		});	
	}

	/**
	 * ��ʼ���ؼ�
	 */
	private void createCom(){
		//��ӱ�����壨��ʽ���֣�
		JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
		//������������
		this.lbPoint = new JLabel();
		north.add(lbPoint);
		//����������Ϣ�ռ�
		this.errMsg = new JLabel();
		this.errMsg.setForeground(Color.RED);
		//��Ӵ�����Ϣ���������
		north.add(this.errMsg);
		//���������ӵ������
		this.add(north, BorderLayout.NORTH);
		JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.txName = new JTextField(10);
		//TODO������󳤶�
		center.add(new JLabel("��������:"));
		center.add(this.txName);
		this.add(center, BorderLayout.CENTER);
		//����ȷ����ť
		this.btnOk = new JButton("ȷ��");
		//�����ϲ���壨��ʽ���֣�
		JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
		//��ť��ӵ��ϲ����
		south.add(btnOk);
		//�ϲ������ӵ������
		this.add(south, BorderLayout.SOUTH);
	}
}
