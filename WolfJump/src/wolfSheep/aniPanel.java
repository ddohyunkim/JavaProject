package wolfSheep;

import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;	
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.WindowConstants;


public class aniPanel extends JFrame implements Initable {
	private aniPanel anipanel = this;
	private BgAni bgAni;
	private ImageIcon pMom, pWolf;
	private ImageIcon ani_bg = new ImageIcon("images/ani_wolf.png");
	private ImageIcon skip= new ImageIcon("images/skip.png");
	private DefalutPanel mom;
	private DefalutPanel wolf;
	private DefalutPanel sheep;
	private JButton skipBtn;
	private AniChange aniMom;
	private Thread momMove;
	private int mx = 650;
	private int my = 450;
	private static boolean ismove;
	private static int scene;
	private BackBGM aniback;
	
	public aniPanel() {;
		scene = 0;
		ismove = false;
		bgAni = new BgAni();
		pMom = new ImageIcon("images/mom_rt.png");
		init();
		setting();
		batch();
		listener();
		this.setVisible(true);
		skipBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	aniback.stop();
            	new WolfSheepApp();
            	dispose();
                //setVisible(false); // 창 안보이게 하기 
            }
        });
		aniback.start();
	}

	public void init() {
		mom = new DefalutPanel(this);
		wolf = new DefalutPanel(this);
		sheep = new DefalutPanel(this);
		skipBtn = new JButton(skip);
		aniMom = new AniChange();
		aniback = new BackBGM("music/plus.wav",true);
		momMove = new Thread(new momMove());
		momMove.start();
	}
		
	@Override
	public void setting() {
		this.setTitle("애니 화면");
		this.setSize(1096, 646);
		
		skipBtn.setBounds(990,10,80,30);
		skipBtn.setBorderPainted(false);
        skipBtn.setContentAreaFilled(false);
        skipBtn.setFocusPainted(false);
		
		mom.setBounds(190, 300, 700, 200);
		mom.getLaName().setText("엄마 양");
		mom.getJtaContent().setText("얘들아~ 엄마 장보고 올게");
		mom.getJButton().setText("다음");
		
		sheep.setBounds(190, 300, 700, 200);
		sheep.getLaName().setText("아기 양들");
		sheep.getJtaContent().setText("네~ (*´∪`) ");
		sheep.getJButton().setText("다음");
		
		wolf.setBounds(190, 300, 700, 200);
		wolf.getLaName().setText("늑대");
		wolf.getJtaContent().setText("음~ 맛있는 냄새..!!!!");
		wolf.getJButton().setText("다음");
	
		
		Dimension frameSize = getSize();
		
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((windowSize.width-frameSize.width)/2,
				(windowSize.height - frameSize.height)/2);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		
		this.setLayout(null);
		setContentPane(bgAni);
		
	}
	@Override
	public void batch() {
		add(skipBtn);
		add(mom);
		mom.setVisible(false);
		add(sheep);
		sheep.setVisible(false);
		add(wolf);
		wolf.setVisible(false);
		add(aniMom);
	}
	@Override
	public void listener() {
		
	}
	class BgAni extends JLabel{
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(ani_bg.getImage(), 0,0,getWidth(),getHeight(),this);
		}
	}
	class AniChange extends JLabel{
		public AniChange() {
			setIcon(pMom);
			setSize(80, 80); // 버블버블 크기
			setLocation(mx, my);

		}
	}
	
	
	class momMove extends JLabel implements Runnable {
		@Override
		public void run() {
			
				if(getScene() == 0) {
					
					try {
					aniMom.setVisible(true);
					Thread.sleep(1000);
					}catch (Exception e) {
						
					}

					setScene(1);
					setisMove(true);
				}
				while(getisMove()==true) {
				if (getScene() == 1) {
					while(mx > 600) {
					try {
						aniMom.setLocation(mx, my);
						mx -= 5;
						Thread.sleep(30);
					}catch (Exception e) {
						//
					}
					}
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
					}
					mom.setVisible(true);
				} else if (getScene() == 2) {
					mom.setVisible(false);
					try {
						Thread.sleep(1000);
					}catch (Exception e) {
					}
					sheep.setVisible(true);
				} else if (getScene() == 3) {
					sheep.setVisible(false);
					while(mx < 1100) {
						try {
							aniMom.setLocation(mx, my);
							mx += 5;
							Thread.sleep(10);
						}catch (Exception e) {
						}
						setScene(4); 
						}
				} else if (getScene() == 4) {
					try {
						Thread.sleep(1000);
						//setScene(3);
					}catch (Exception e) {
						//
					}
					wolf.setVisible(true);
				} else if (getScene() == 5) {
					wolf.setVisible(false);
					aniback.stop();
					new WolfSheepApp();
	                setisMove(false);
	                dispose();
	                //momMove.interrupt();
	                //anipanel.setVisible(false);
				}
			}
		}
	}
	public void setisMove(boolean s) {ismove = s;}
	public boolean getisMove() {return ismove;}
	
	public void setScene(int s) {scene = s;}
	public int getScene() {return scene;}
}