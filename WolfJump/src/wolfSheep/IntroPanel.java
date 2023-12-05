package wolfSheep;

import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;	
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class IntroPanel extends JFrame{
	ImageIcon intro_bg = new ImageIcon("images/startani_wolf.png");
	 private BackBGM introBGM;
	public IntroPanel() {
		this.setTitle("인트로 화면");
		
		
		ImageIcon start = new ImageIcon("images/start.png");
		//ImageIcon rank = new ImageIcon("images/rank.png");
				
		MyPanel panel = new MyPanel();
		JButton startBtn = new JButton(start);
		
		
		introBGM = new BackBGM("music/opening_menu.wav", true);
		
		panel.setLayout(null);
		
        startBtn.setBorderPainted(false);
        startBtn.setContentAreaFilled(false);
        startBtn.setFocusPainted(false);

        //rankBtn.setBorderPainted(false);
        //rankBtn.setContentAreaFilled(false);
        //rankBtn.setFocusPainted(false);

		panel.add(startBtn);
		//panel.add(rankBtn);
		startBtn.setBounds(360, 320, 357, 74);
		//rankBtn.setBounds(360, 380, 357, 74);
		this.add(panel);
		this.setSize(1096,646);
		
		Dimension frameSize = getSize();
		
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((windowSize.width-frameSize.width)/2,
				(windowSize.height - frameSize.height)/2);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		//start button에 대한 액션
		startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	introBGM.stop();
            	new aniPanel();
                //setVisible(false); // 창 안보이게 하기 
                dispose();
            }
        });
		introBGM.start();
		
	}
	class MyPanel extends JPanel{
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(intro_bg.getImage(), 0,0,getWidth(),getHeight(),this);
		}
	}
	public static void main(String[] args) {
		new IntroPanel();
	}
}