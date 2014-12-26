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
		this.setTitle("保存记录");
		this.setSize(256, 128);
		FrameUtil.setFrameCenter(this);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.createCom();
		this.createAction();
	}
	
	/**
	 * 显示窗口
	 */
	public void show(int point) {
		this.lbPoint.setText("您的得分是:" + point);
		this.setVisible(true);
	}
	
	/**
	 * 创建事件监听
	 */
	private void createAction() {
		this.btnOk.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO还可以加入其它文字验证
				String name = txName.getText();
				if(name.length() > 16 || name == null || "".equals(name)){
					errMsg.setText("名字输入有误");
				} else{
					setVisible(false);
					gameControl.savePoint(name);
				}
			}	
		});	
	}

	/**
	 * 初始化控件
	 */
	private void createCom(){
		//添加北部面板（流式布局）
		JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
		//创建分数文字
		this.lbPoint = new JLabel();
		north.add(lbPoint);
		//创建错误信息空间
		this.errMsg = new JLabel();
		this.errMsg.setForeground(Color.RED);
		//添加错误信息到北部面板
		north.add(this.errMsg);
		//北部面板添加到主面板
		this.add(north, BorderLayout.NORTH);
		JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.txName = new JTextField(10);
		//TODO设置最大长度
		center.add(new JLabel("您的名字:"));
		center.add(this.txName);
		this.add(center, BorderLayout.CENTER);
		//创建确定按钮
		this.btnOk = new JButton("确定");
		//创建南部面板（流式布局）
		JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
		//按钮添加到南部面板
		south.add(btnOk);
		//南部面板添加到主面板
		this.add(south, BorderLayout.SOUTH);
	}
}
