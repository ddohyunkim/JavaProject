package wolfSheep;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;



class DefalutPanel extends JPanel{

private DefalutPanel defalutPanel = this;
private JPanel mainPanel;
private JPanel panel;
private JLabel laName;
private JTextArea jtaContent;
private JButton btnOk;
private WolfSheepApp wolfSheepApp;
private aniPanel aniPanel;

	public DefalutPanel(WolfSheepApp wolfSheepApp) {		
		this.wolfSheepApp = wolfSheepApp;
		init(); 
		setting();
		batch();	
		listener();
		
	}
	public DefalutPanel(aniPanel aniPanel) {
		this.aniPanel = aniPanel;
		if (aniPanel == null) {System.out.println("생성자... 초기화안됨");}
		init();
		setting();
		batch();
		listen_ani();
	}
	public void init() {
		this.btnOk = new JButton("확인");
		this.mainPanel = new JPanel(); 
		this.panel = new JPanel();
		this.laName = new JLabel("이름");
		this.jtaContent = new JTextArea();
		 
	}
	
	void setting() {
		setLayout(null);
		this.mainPanel.setLayout(null);
		this.mainPanel.setBounds(0, 0, 700, 300);
		this.mainPanel.setBackground(Color.black);
		this.panel.setBackground(Color.GRAY);
		this.panel.setBounds(10, 15, 680, 175);
		this.panel.setLayout(null);
		this.laName.setOpaque(true);
		this.laName.setBackground(Color.WHITE);
		this.laName.setBounds(12, 10, 660, 29);
		this.btnOk.setBounds(600, 120, 60, 40);
		this.btnOk.setForeground(Color.white);
		this.btnOk.setBackground(new Color(255,56,92));
		
		this.jtaContent.setBackground(Color.WHITE);
		this.jtaContent.setFont(new Font("Serif",Font.BOLD,20));
		this.jtaContent.setBounds(12, 49, 660, 116);
		
	}
	
	void batch() {
	add(this.mainPanel);
	this.mainPanel.add(panel);
	panel.add(this.btnOk);
	panel.add(this.jtaContent);
	panel.add(this.laName);
	}
	
	void listener() {
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				wolfSheepApp.setFocusable(true);
				setVisible(false);				
			}
		});
	}
	void listen_ani() {
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int n = aniPanel.getScene();
				aniPanel.setScene(n+1);
				aniPanel.setFocusable(true);
				//System.out.println(aniPanel.getScene());
			}
		});
	}

	
	public JLabel getLaName() {
		return this.laName;
	}
	public JTextArea getJtaContent() {
		return this.jtaContent;
	}
	public JButton getJButton() {
		return this.btnOk;
	}
	}

class thani implements Runnable{
	@Override
	public void run() {
		
	}
}