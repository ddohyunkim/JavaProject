package wolfSheep;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/*
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

/*
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
*/

// 점프킹앱 클래스입니다.
// 플레이어를 생성하고 조작하는역할을합니다.
// 픽셀체크와, 공주를 생성합니다.
// 공주와 플레이어와 충돌시 패널을 출력합니다.

public class WolfSheepApp extends JFrame implements Initable {

	// 컨텍스트 저장
	private WolfSheepApp wolfSheepApp = this; // 버블 컨텍스트 남기기
	// 태그
	private static final String TAG = "JumpKing : ";
	
	private Clip clip;
	public Player player; // 플레이어
	public Meat meat; // 플레이어
	public Mike mike;
	public Flour flour;
	public Boss boss;
	private Sheep1 sheep1;
	private Sheep2 sheep2;
	private Sheep3 sheep3;
	private Sheep4 sheep4;
	private Sheep5 sheep5;
	private Sheep6 sheep6;
	private ImageIcon sheepIcon;
	
	private Thread thPixel; // 픽셀검사
	private BgJumpKing bgPanel; // 백그라운드
	private JButton musicToggleButton;
	//private BackgroundMusicApp backgroundMusicApp;
	//private Clip clip;
	private boolean musicisPlaying = true;
	private boolean bossmusicPlaying = false;
	private boolean houseCheck = false;
	private BackBGM back;
	private BackBGM house;
	private BackBGM bossbgm;
	
	
	
	private boolean wolfis = false;
	
	private ImageIcon[] icon = { null, new ImageIcon("images/1stagemap.png"), new ImageIcon("images/2stagemap.png"),
			new ImageIcon("images/3stagemap.png"), new ImageIcon("images/4stagemap.png"), new ImageIcon("images/5stagemap.png")
			, new ImageIcon("images/6stagemap.png"), new ImageIcon("images/7stagemap.png"), new ImageIcon("images/8stagemap.png")
			, new ImageIcon("images/9stagemap.png"), new ImageIcon("images/10stagemap.png"), new ImageIcon("images/11stagemap.png")
			, new ImageIcon("images/12stagemap.png"), new ImageIcon("images/bossmap.png")};
	private int imgCount = 1;
	private Image img = icon[imgCount].getImage(); // 이미지 객체
	private ImageIcon meatIc, mikeIc, flourIc;
	private ImageIcon musicon, musicoff;
	
	private Thread laThread;
	private Thread bossMove;
	
	//프레임 관련
	private DefalutPanel mikePN, meatPN, flourPN, bossPN, gameoverPN, gameClear;
	private DefalutPanel noMhouse1, noMhouse2, Mhouse1, Mhouse2, noFhouse1, noFhouse2,Fhouse1, Fhouse2;
	//private JLabel laPrincess;
	private JLabel la1Stage, la2Stage, la3Stage, la4Stage, la5Stage, la6Stage, la7Stage, la8Stage, la9Stage, la10Stage, la11Stage, la12Stage, la13Stage;
	//private boolean bossadd = false;
	private int clear = 0;
	private int sheepcount = 0;
	private boolean clearCheck = true;
	
	// 캐릭터 숨김 관련
	//private boolean ch_visible = true;
	//private Timer bossTimer;
	
	
	private int bossStage = 0;
	
	public WolfSheepApp() {
		meatIc = new ImageIcon("images/meat.png");
		mikeIc = new ImageIcon("images/mike.png");
		flourIc = new ImageIcon("images/flour.png");
		musicon = new ImageIcon("images/on.png");
		musicoff = new ImageIcon("images/off.png");
		sheepIcon = new ImageIcon("images/sheep_Lf.png"); 
		init(); // 생성 객체모음
		
		setting(); // 셋팅 모음
		batch(); // 배치 모음
		listener(); // 리스너 모음
		
		setVisible(true);
		
		musicToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(getMusicPlaying() == false && houseCheck == false) {
            		back.start(); //음악 재생
                	musicToggleButton.setIcon(musicon);
                	setMusicPlaying(true);
                	wolfSheepApp.requestFocus();
                	
            	}else if (getMusicPlaying() == true && houseCheck == false){
            		back.stop(); //음악 재생
                	musicToggleButton.setIcon(musicoff);
                	setMusicPlaying(false);
                	wolfSheepApp.requestFocus();
            	}
            	if(houseCheck == true && getMusicPlaying() == false) {
            		house.start(); //음악 재생
                	musicToggleButton.setIcon(musicon);
                	setMusicPlaying(true);
                	wolfSheepApp.requestFocus();
            	}else if(houseCheck == true && getMusicPlaying() == true) {
            		house.stop(); //음악 재생
                	musicToggleButton.setIcon(musicoff);
                	setMusicPlaying(false);
                	wolfSheepApp.requestFocus();
            	}
            	if(boss.check == true && bossmusicPlaying == false) {
            		bossbgm.start(); //음악 재생
                	musicToggleButton.setIcon(musicon);
                	bossmusicPlaying = true;
                	wolfSheepApp.requestFocus();
            	}else if(boss.check == true && bossmusicPlaying == true) {
            		bossbgm.stop(); //음악 재생
                	musicToggleButton.setIcon(musicoff);
                	bossmusicPlaying = false;
                	wolfSheepApp.requestFocus();
            	}
            }
        });

	}

	class BgJumpKing extends JLabel {

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, this);

		}

		public BgJumpKing() {
			new Thread(new Runnable() {

				@Override
				public void run() {
					while (true) {
						img = icon[imgCount].getImage();
						repaint();
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();
		}

	}

	@Override
	public void init() {
		player = new Player();
		meat = new Meat();
		mike = new Mike();
		flour = new Flour();
		boss = new Boss();
		bgPanel = new BgJumpKing();
		mikePN = new DefalutPanel(wolfSheepApp);
		flourPN = new DefalutPanel(wolfSheepApp);
		meatPN = new DefalutPanel(wolfSheepApp);
		bossPN = new DefalutPanel(wolfSheepApp);
		sheep1 = new Sheep1();
		sheep2 = new Sheep2();
		sheep3 = new Sheep3();
		sheep4 = new Sheep4();
		sheep5 = new Sheep5();
		sheep6 = new Sheep6();
		
		gameoverPN = new DefalutPanel(wolfSheepApp);
		gameClear = new DefalutPanel(wolfSheepApp);
		back = new BackBGM("music/game_bgm.wav",true);
		//jumpBgm = new BackBGM();
		
		noMhouse1 = new DefalutPanel(wolfSheepApp);
		noMhouse2 = new DefalutPanel(wolfSheepApp);
		Mhouse1 = new DefalutPanel(wolfSheepApp);
		Mhouse2 = new DefalutPanel(wolfSheepApp);
		
		noFhouse1 = new DefalutPanel(wolfSheepApp);
		noFhouse2 = new DefalutPanel(wolfSheepApp);
		Fhouse1 = new DefalutPanel(wolfSheepApp);
		Fhouse2 = new DefalutPanel(wolfSheepApp);
		
		musicToggleButton = new JButton();
	    musicToggleButton.setIcon(musicon);
	    musicToggleButton.setBounds(1000, 20, 40, 40);
	    //backgroundMusicApp = new BackgroundMusicApp(musicToggleButton);
	    
		
		//laPrincess = new JLabel("공주");
		la1Stage = new JLabel("----------1Stage----------");
		la2Stage = new JLabel("----------2Stage----------");
		la3Stage = new JLabel("----------3Stage----------");
		la4Stage = new JLabel("----------4Stage----------");
		la5Stage = new JLabel("----------5Stage----------");
		la6Stage = new JLabel("----------6Stage----------");
		la7Stage = new JLabel("----------7Stage----------");
		la8Stage = new JLabel("----------8Stage----------");
		la9Stage = new JLabel("----------9Stage----------");
		la10Stage = new JLabel("----------10tage----------");
		la11Stage = new JLabel("----------11tage----------");
		la12Stage = new JLabel("----------12tage----------");
		la13Stage = new JLabel("----------BOSS----------");
		thPixel = new Thread(new PixelCheck(player, wolfSheepApp)); // 색깔 연산 스레드
		thPixel.start();
		laThread = new Thread(new StageChange());
		laThread.start();
		bossMove = new Thread(new bossMove());

	}
	
	@Override
	public void setting() {
		setTitle("점프킹");
		//setSize(1080, 607);
		setSize(1096, 646);
		
		mikePN.setBounds(190, 300, 700, 200);
		mikePN.getLaName().setText("마이크");
		mikePN.getJtaContent().setText("목소리가 우아하게 변했다.");
		
		flourPN.setBounds(190, 300, 700, 200);
		flourPN.getLaName().setText("밀가루");
		flourPN.getJtaContent().setText("털이 하얗게 변했다.");
		
		meatPN.setBounds(190, 300, 700, 200);
		meatPN.getLaName().setText("정체를 알 수 없는 고기");
		meatPN.getJtaContent().setText("맛있어보인다...");
		
		bossPN.setBounds(190, 300, 700, 200);
		bossPN.getLaName().setText("???");
		bossPN.getJtaContent().setText("5초 뒤 출현 예정");
		
		gameoverPN.setBounds(190, 300, 700, 200);
		gameoverPN.getLaName().setText("엄마양에게 치여 죽으셨습니다.");
		gameoverPN.getJtaContent().setText("GAME OVER");
		
		noMhouse1.setBounds(190, 300, 700, 200);
		noMhouse1.getLaName().setText("늑대");
		noMhouse1.getJtaContent().setText("얘들아 엄마야 장보고 왔어 문좀 열어줘");
		
		noMhouse2.setBounds(190, 300, 700, 200);
		noMhouse2.getLaName().setText("양");
		noMhouse2.getJtaContent().setText("이건 엄마 목소리가 아니야..! 열어주면 안돼");
		
		Mhouse1.setBounds(190, 300, 700, 200);
		Mhouse1.getLaName().setText("엄?마양");
		Mhouse1.getJtaContent().setText("ㅎㅎ 얘들아 진짜 엄마야 장보고 왔단다 문 좀 열어주렴~");
		
		Mhouse2.setBounds(190, 300, 700, 200);
		Mhouse2.getLaName().setText("양");
		Mhouse2.getJtaContent().setText("앗 엄마 목소리다! 근데 까매서 아닌 것 같아");
		
		noFhouse1.setBounds(190, 300, 700, 200);
		noFhouse1.getLaName().setText("늑대");
		noFhouse1.getJtaContent().setText("호호 얘들아 엄마야 장보고 왔어 문좀 열어줘");
	
		noFhouse2.setBounds(190, 300, 700, 200);
		noFhouse2.getLaName().setText("양");
		noFhouse2.getJtaContent().setText("아까랑 똑같이 까매..! 절대 열어주지 마");
		
		Fhouse1.setBounds(190, 300, 700, 200);
		Fhouse1.getLaName().setText("늑대");
		Fhouse1.getJtaContent().setText("호호 얘들아 엄마야 장보고 왔어 문좀 열어줘");
		
		Fhouse2.setBounds(190, 300, 700, 200);
		Fhouse2.getLaName().setText("양");
		Fhouse2.getJtaContent().setText("목소리도 맞고 하얗다 엄마 맞나봐! 열어주자");
		
		gameClear.setBounds(190, 300, 700, 200);
		gameClear.getLaName().setText("늑대");
		gameClear.getJtaContent().setText("죽을뻔 했네 아~ 맛잇었다 (클리어 성공~~)");
		
		la1Stage.setBounds(450, 80, 300, 400);
		la1Stage.setForeground(Color.black);
		la1Stage.setFont(new Font("Serif", Font.BOLD, 22));
		la2Stage.setBounds(450, 80, 300, 400);
		la2Stage.setForeground(Color.black);
		la2Stage.setFont(new Font("Serif", Font.BOLD, 22));
		la3Stage.setBounds(450, 80, 300, 400);
		la3Stage.setForeground(Color.black);
		la3Stage.setFont(new Font("Serif", Font.BOLD, 22));
		
		la4Stage.setBounds(450, 80, 300, 400);
		la4Stage.setForeground(Color.black);
		la4Stage.setFont(new Font("Serif", Font.BOLD, 22));
		la5Stage.setBounds(450, 80, 300, 400);
		la5Stage.setForeground(Color.black);
		la5Stage.setFont(new Font("Serif", Font.BOLD, 22));
		la6Stage.setBounds(450, 80, 300, 400);
		la6Stage.setForeground(Color.black);
		la6Stage.setFont(new Font("Serif", Font.BOLD, 22));
		la7Stage.setBounds(450, 80, 300, 400);
		la7Stage.setForeground(Color.black);
		la7Stage.setFont(new Font("Serif", Font.BOLD, 22));
		la8Stage.setBounds(450, 80, 300, 400);
		la8Stage.setForeground(Color.black);
		la8Stage.setFont(new Font("Serif", Font.BOLD, 22));
		la9Stage.setBounds(450, 80, 300, 400);
		la9Stage.setForeground(Color.black);
		la9Stage.setFont(new Font("Serif", Font.BOLD, 22));
		la10Stage.setBounds(450, 80, 300, 400);
		la10Stage.setForeground(Color.black);
		la10Stage.setFont(new Font("Serif", Font.BOLD, 22));
		la11Stage.setBounds(450, 80, 300, 400);
		la11Stage.setForeground(Color.black);
		la11Stage.setFont(new Font("Serif", Font.BOLD, 22));
		la12Stage.setBounds(450, 80, 300, 400);
		la12Stage.setForeground(Color.black);
		la12Stage.setFont(new Font("Serif", Font.BOLD, 22));
		la13Stage.setBounds(450, 80, 300, 400);
		la13Stage.setForeground(Color.black);
		la13Stage.setFont(new Font("Serif", Font.BOLD, 22));
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setContentPane(bgPanel); // 기본 컨텐트페인 = 라벨 백그라운드
	}

	@Override
	public void batch() {
		add(player);
		add(mikePN);
		mikePN.setVisible(false);
		add(flourPN);
		flourPN.setVisible(false);
		add(meatPN);
		meatPN.setVisible(false);
		add(bossPN);
		bossPN.setVisible(false);
		add(gameoverPN);
		gameoverPN.setVisible(false);
		
		// private DefalutPanel noMhouse1, noMhouse2, Mhouse1, Mhouse2, noFhouse1, noFhouse2,Fhouse1, Fhouse2;
		
		add(noMhouse1);
		add(noMhouse2);
		add(Mhouse1);
		add(Mhouse2);
		add(noFhouse1);
		add(noFhouse2);
		add(Fhouse1);
		add(Fhouse2);
		add(gameClear);
		
		noMhouse1.setVisible(false);
		noMhouse2.setVisible(false);
		Mhouse1.setVisible(false);
		Mhouse2.setVisible(false);
		noFhouse1.setVisible(false);
		noFhouse2.setVisible(false);
		Fhouse1.setVisible(false);
		Fhouse2.setVisible(false);
		
		gameClear.setVisible(false);
		
		add(musicToggleButton);
		back.start();
		setFocusable(true);
	}

	@Override
	public void listener() {   // 리스너..? 뭘?
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (player.isMoveLock() == true) { // Move락이 걸려있으면 메서드실행안됨
					return;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) { // 오른쪽 이동
					player.setJumpUpDirectionStay(true); // 제자리 점프시 우측 방향 설정 (우측 이미지 때문에 사용)
					player.moveRight();
//					System.out.println(player.getPlayerX()                      +" a "+ player.getPlayerY());
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) { // 왼쪽이동
					player.setJumpUpDirectionStay(false); // 제자리 점프시 좌측 방향 설정 (좌측 이미지 때문에 사용)
					player.moveLeft();
				}
				else if (e.getKeyCode() == KeyEvent.VK_SPACE && player.isLeft() == true) { // 좌측 위 점프  
					player.setJumpUpDirection(-1);// 좌측 방향값 설정
					player.jumpUp();
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE && player.isRight() == true) { // 우측 위 점프 && player.isRight() == true
					player.setJumpUpDirection(1); // 우측 방향값 설정
					player.jumpUp();
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {// 제자리 점프
					player.jumpUp();
				}
			} 
			@Override
			public void keyReleased(KeyEvent e) { // 버튼땔때 멈추는 함수
				if (e.getKeyCode() == KeyEvent.VK_SPACE) // UP버튼 연속입력 방지
					player.setUp(false);
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) // 좌,우이동 매끄러운 변환 조건문
					player.setRight(false);
				else if (e.getKeyCode() == KeyEvent.VK_LEFT)
					player.setLeft(false);
			}	
		});

}

	
	class StageChange implements Runnable {
	 //공주 한번만 실행

		@Override
		public void run() {	
			boolean lacount = true;
			boolean n = true;
			while (true) {
				try {
					if(imgCount == 7 && houseCheck==true && n==true) {back.stop(); house = new BackBGM("music/opening_mom.wav",true);house.start();n=false;}
					if (imgCount == 1 && lacount == true) {
						System.out.println("스테이지1");
						lacount = false;
						add(la1Stage);
						Thread.sleep(2000);
					} else if (imgCount == 2 && lacount == false) {
						System.out.println("스테이지22");
						lacount = true;
						add(la2Stage);
						Thread.sleep(2000);
					} else if (imgCount == 3 && lacount == true) {
						System.out.println("스테이지3");
						lacount = false;
						add(la3Stage);
						Thread.sleep(3000);
					}
					else if (imgCount == 4 && lacount == false) {
						System.out.println("스테이지4");
						lacount = true;
						add(la4Stage);
						Thread.sleep(2000);
					} else if (imgCount == 5 && lacount == true) {
						System.out.println("스테이지5");
						lacount = false;
						add(la5Stage);
						Thread.sleep(3000);
					}
					else if (imgCount == 6 && lacount == false) {
						System.out.println("스테이지6");
						lacount = true;
						add(la5Stage);
						Thread.sleep(2000);
					} else if (imgCount == 7 && lacount == true) {
						System.out.println("스테이지7");
						lacount = false;
						add(la7Stage);
						Thread.sleep(3000);
					}
					else if (imgCount == 8 && lacount == false) {
						System.out.println("스테이지88");
						lacount = true;
						add(la8Stage);
						Thread.sleep(2000);
					} else if (imgCount == 9 && lacount == true) {
						System.out.println("스테이지9");
						lacount = false;
						add(la9Stage);
						Thread.sleep(3000);
					}
					else if (imgCount == 10 && lacount == false) {
						System.out.println("스테이지10");
						lacount = true;
						add(la10Stage);
						Thread.sleep(2000);
					} else if (imgCount == 11 && lacount == true) {
						System.out.println("스테이지11");
						lacount = false;
						add(la11Stage);
						Thread.sleep(3000);
					}else if (imgCount == 12 && lacount == false) {
						System.out.println("스테이지12");
						lacount = true;
						add(la12Stage);
						Thread.sleep(2000);
					}else if (imgCount == 13 && lacount == false) {
						System.out.println("BOSS");
						lacount = true;
						add(la13Stage);
						Thread.sleep(2000);
					}
				} catch (Exception e) {
				}
				//if(clear == 10) {clear = 0;}
				if ((player.getX() < 80+25) && (80+25 < (player.getX() + 55)) && 
					    (player.getY()-20 < 20+50) && (20+50 < player.getY() + 70) &&
					    (clear == 0 && imgCount == 2)) {
					mike.setVisible(false);
					System.out.println("마이크 겟");
					mikePN.setVisible(true);  // 마이크를 얻었다.
					clear = 1;
					mike.check = false;
				}else if ((player.getX() < 920+25) && (920+25 < (player.getX() + 55)) && 
					    (player.getY()-20 < 60+50) && (60+50 < player.getY() + 70) &&
					    (clear == 2 && imgCount == 5)) {  // 2일 때 발동
					flour.setVisible(false);
					System.out.println("밀가루 겟");
					flourPN.setVisible(true);
					clear = 3;    // 밀가루 먹으면 3으로 변한다..
					flour.check = false; 
				}else if ((player.getX() < 300+25) && (300+25 < (player.getX() + 55)) && 
					    (player.getY()-20 < 350+50) && (350+50 < player.getY() + 70) &&
					    (clear == 4 && imgCount == 9)) {
					meat.setVisible(false);  // 4일때 발동
					System.out.println("고기 냠");
					meatPN.setVisible(true);
					clear = 5;
					meat.check = false;
				}else if ((player.getX() < 900) && (900 < (player.getX() + 55)) && 
				         (player.getY() - 20 < 450) && (450 < player.getY() + 70) &&
				         ((clear==4 || clear==5) && imgCount == 13)) {
					player.setMoveLock(true);
					System.out.println("보스 출현");
					bossPN.setVisible(true);
					boss.check = true;
					createBoss();
					clear = 9;
				}
				else if((player.getX() < 80+25) && (80+25 < (player.getX() + 55)) && 
					    (player.getY()-20 < 160+50) && (160+50 < player.getY() + 70)) {
					if (clear == 1 && imgCount == 3) {
						System.out.println("엄마같은 늑대 출현 ");
						Mhouse1.setVisible(true);  // 마이크를 얻었다.
						player.playSound("music/wolf_voice_mom.wav", false);
						Mhouse2.setVisible(true);
						clear = 2;   // 집 통과!
						wolfis = false;
					}else if (clear == 0 && imgCount == 3 && wolfis == false) {
						System.out.println("늑대 출현 ");
						try{
							noMhouse1.setVisible(true);  // 마이크를 얻었다.
							Thread.sleep(50);
							player.playSound("music/wolf_voice.wav", false);
							noMhouse2.setVisible(true);
							wolfis = true;
						}catch (Exception e) {
						}
				}
			}
				else if((player.getX() < 80+25) && (80+25 < (player.getX() + 55)) && 
					    (player.getY()-20 < 140+50) && (140+50 < player.getY() + 70)) {
					if (clear == 3 && imgCount == 6) {
						System.out.println("하얀 늑대 출현 ");
						Fhouse1.setVisible(true);
						Fhouse2.setVisible(true);
						clear = 4;   // 집 통과!
						wolfis = false;
						houseCheck = true;
					}else if (clear == 2 && imgCount == 6 && wolfis == false) {
						System.out.println("늑대 출현 ");
						try{
							noFhouse1.setVisible(true);
							noFhouse2.setVisible(true);
							//Thread.sleep(1000);
						}catch (Exception e) {
						}
						wolfis = true;
						//clear = 10;
					}
				}

				if ((player.getX() < 200+25) && (200+25 < (player.getX() + 55)) && 
					    (player.getY()-20 < 300+50) && (300+50 < player.getY() + 70) && (imgCount == 7 && sheepcount == 0)){
					if(imgCount == 7 && sheepcount == 0) {
						sheep1.check = false;
						sheep1.setVisible(false);
						sheepcount = 1;
					}
				}else if ((player.getX() < 350+25) && (350+25 < (player.getX() + 55)) && 
					    (player.getY()-20 < 400+50) && (400+50 < player.getY() + 70) && (imgCount == 8 && sheepcount == 1)) {
					sheep2.check = false;
					sheep2.setVisible(false);
					sheepcount = 2;
				}else if ((player.getX() < 200+25) && (200+25 < (player.getX() + 55)) && 
					    (player.getY()-20 < 350+50) && (350+50 < player.getY() + 70) && (imgCount == 9 && sheepcount == 2)) {
					sheep3.check = false;
					sheep3.setVisible(false);
					sheepcount = 3;
				}else if ((player.getX() < 400+25) && (400+25 < (player.getX() + 55)) && 
					    (player.getY()-20 < 350+50) && (350+50 < player.getY() + 70) && (imgCount == 10 && sheepcount == 3)) {
					sheep4.check = false;
					sheep4.setVisible(false);
					sheepcount = 4;
				}else if ((player.getX() < 350+25) && (350+25 < (player.getX() + 55)) && 
					    (player.getY()-20 < 300+50) && (300+50 < player.getY() + 70) && (imgCount == 11 && sheepcount == 4)) {
					sheep5.check = false;
					sheep5.setVisible(false);
					sheepcount = 5;
				}else if ((player.getX() < 250+25) && (250+25 < (player.getX() + 55)) && 
					    (player.getY()-20 < 300+50) && (300+50 < player.getY() + 70) && (imgCount == 12 && sheepcount == 5)) {
					sheep6.check = false;
					sheep6.setVisible(false);
					sheepcount = 6;
				}
				
				remove(la1Stage);
				remove(la2Stage);
				remove(la3Stage);
				
				remove(la4Stage);
				remove(la5Stage);
				remove(la6Stage);
				remove(la7Stage);
				remove(la8Stage);
				remove(la9Stage);
				remove(la10Stage); 
				remove(la11Stage);
				remove(la12Stage);
				remove(la13Stage);
			}
		}
	}
	
	
	//양1
		class Sheep1 extends JLabel{
			boolean check = true;
			public Sheep1() {
				setIcon(sheepIcon);
				setSize(80, 80); // 버블버블 크기
				setLocation(200, 300);
			
				//Thread momTh = new Thread(new momMove());
				//momTh.start();
			}
		}
		
		public void creatsheep1() { // 고기
			if(sheep1.check == true) {
				add(sheep1);
				sheep1.setVisible(true);
			}
		}

		public void removesheep1() { // 고기
			sheep1.setVisible(false);
			remove(sheep1);
		}
		//양2
		class Sheep2 extends JLabel {
		    boolean check = true;

		    public Sheep2() {
		        setIcon(sheepIcon); // 이미지 아이콘을 설정해야 합니다.
		        setSize(80, 80);
		        setLocation(350, 400); // 적절한 초기 위치를 설정해야 합니다.
		    }
		}
		

		public void createSheep2() {
		    if (sheep2.check == true) {
		        add(sheep2);
		        sheep2.setVisible(true);
		    }
		}

		public void removeSheep2() {
		    sheep2.setVisible(false);
		    remove(sheep2);
		}
		//양3
		class Sheep3 extends JLabel {
		    boolean check = true;

		    public Sheep3() {
		        setIcon(sheepIcon); // 이미지 아이콘을 설정해야 합니다.
		        setSize(80, 80);
		        setLocation(200, 350); // 적절한 초기 위치를 설정해야 합니다.
		    }
		}

		public void createSheep3() {
		    if (sheep3.check == true) {
		        add(sheep3);
		        sheep3.setVisible(true);
		    }
		}

		public void removeSheep3() {
		    sheep3.setVisible(false);
		    remove(sheep3);
		}
		//양4
		class Sheep4 extends JLabel {
		    boolean check = true;

		    public Sheep4() {
		        setIcon(sheepIcon); // 이미지 아이콘을 설정해야 합니다.
		        setSize(80, 80);
		        setLocation(400, 450); // 적절한 초기 위치를 설정해야 합니다.
		    }
		}

		public void createSheep4() {
		    if (sheep4.check == true) {
		        add(sheep4);
		        sheep4.setVisible(true);
		    }
		}

		public void removeSheep4() {
		    sheep4.setVisible(false);
		    remove(sheep4);
		}
		
		//양5
		
		class Sheep5 extends JLabel {
		    boolean check = true;

		    public Sheep5() {
		        setIcon(sheepIcon); // 이미지 아이콘을 설정해야 합니다.
		        setSize(80, 80);
		        setLocation(350, 300); // 적절한 초기 위치를 설정해야 합니다.
		    }
		}

		public void createSheep5() {
		    if (sheep5.check == true) {
		        add(sheep5);
		        sheep5.setVisible(true);
		    }
		}

		public void removeSheep5() {
		    sheep5.setVisible(false);
		    remove(sheep5);
		}
		//양6
		class Sheep6 extends JLabel {
		    boolean check = true;

		    public Sheep6() {
		        setIcon(sheepIcon); // 이미지 아이콘을 설정해야 합니다.
		        setSize(80, 80);
		        setLocation(250, 300); // 적절한 초기 위치를 설정해야 합니다.
		    }
		}

		public void createSheep6() {
		    if (sheep6.check == true) {
		        add(sheep6);
		        sheep6.setVisible(true);
		    }
		}

		public void removeSheep6() {
		    sheep6.setVisible(false);
		    remove(sheep6);
		}
		//양끝
	class Meat extends JLabel{
		boolean check = true;
		public Meat() {
			setIcon(meatIc);
			setSize(80, 40); // 버블버블 크기
			setLocation(300, 350);
		
			//Thread momTh = new Thread(new momMove());
			//momTh.start();
		}
	}
	
	public void creatMeat() { // 고기
		if(meat.check == true) {
			add(meat);
			meat.setVisible(true);
		}
	}

	public void removeMeat() { // 고기
		meat.setVisible(false);
		remove(meat);
	}
	public boolean getCheck() {return meat.check;}
	class Mike extends JLabel{
		boolean check = true;
		public Mike() {
			setIcon(mikeIc);
			setSize(80, 80); // 버블버블 크기
			setLocation(80, 20);
		
			//Thread momTh = new Thread(new momMove());
			//momTh.start();
		}
	}
	
	public void creatMike() { // 고기
		if(mike.check == true) {
		add(mike);
		mike.setVisible(true);
		}
	}

	public void removeMike() { // 고기
		remove(mike);
	}
	class Flour extends JLabel{
		boolean check = true;
		public Flour() {
			setIcon(flourIc);
			setSize(80, 80); // 버블버블 크기
			setLocation(920, 60);
		
			//Thread momTh = new Thread(new momMove());
			//momTh.start();
		}
	}
	public boolean getFlourCheck() {return flour.check;}
	public void creatFlour() { // 고기
		if(flour.check == true) {
		add(flour);
		flour.setVisible(true);
		}
	}

	public void removeFlour() { // 고기
		flour.setVisible(false);
		remove(flour);
	}
	public int getImgCount() {
		return imgCount;
	}

	public void setImgCount(int imgCount) {
		this.imgCount = imgCount;
	}
	
	public class Boss extends JLabel{
		private ImageIcon icboss_lf, icboss_rt;
		private boolean check = false;
		private int bossX = 900;
		private int bossY = 100;
		
		public Boss() {
			init();
			setIcon(icboss_lf);
			setSize(500, 480);
			setLocation(bossX, bossY);
		}
		
		void init() {
			icboss_lf = new ImageIcon("images/Bigmom_Lf.png");
			icboss_rt = new ImageIcon("images/Bigmom_Rt.png");
		}
	}
	public void createBoss() {
		if(boss.check == true) {
			bossMove.start();
			house.stop();
			houseCheck=false;
			bossbgm = new BackBGM("music/opening_wolf.wav",true);
			bossbgm.start();
			add(boss);
			boss.setVisible(true);
			//bossTimer.start();
			//bossadd = true;
		}
	}
	public void removeBoss() {
		remove(boss);
	}
	class bossMove extends JLabel implements Runnable{
		@Override
		public void run() {
			if(getCheck() == false) {
				player.setMoveLock(false);
				wolfSheepApp.requestFocus();
			}else {
				player.setMoveLock(true);
			}
			while(boss.check==true ) {   // 보스가 있을 때 까지만..!	 
				try {
					Thread.sleep(5000);
					while(boss.bossX >600) {   // x가 600보다 클때까지만
						try {
							boss.setLocation(boss.bossX, boss.bossY);   // boss의 위치를 옮긴다.
							boss.bossX -= 10;
							Thread.sleep(30);
							if(player.getX() > boss.bossX && player.getY()>160) {
								//boss.setVisible(false);
								clearCheck = false;
								boss.check = false;
								//bossMove.interrupt();
								}
							}
						catch(Exception e) {
						}
					}
				}catch(InterruptedException e) {
				}
			while(boss.bossX >0) {
				try {
					boss.setLocation(boss.bossX, boss.bossY);
					boss.bossX -= 10;
					Thread.sleep(5);
					if(player.getX() > boss.bossX && player.getY()>160) {
						//boss.setVisible(false);
						clearCheck = false;
						boss.check = false;
						}
					}
				catch(Exception e) {
				}
			}
			if(clearCheck == true) {
				gameClear.setVisible(true);
			}else {
				player.setVisible(false);
				gameoverPN.setVisible(true);
			}
			try {
				Thread.sleep(10000);
				System.exit(0);
			}catch(Exception e) {
			}
			
		}
	}
}
	
	
public void setBossStage(int n) {
		bossStage = n;
	}
public int getBossStage() {return bossStage;}
public int getClear(){
		return clear;
}

public boolean getMusicPlaying() {
	return this.musicisPlaying;
}
public void setMusicPlaying(boolean s) {
	this.musicisPlaying = s;
}


}