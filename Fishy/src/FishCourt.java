import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import sun.audio.*;
import sun.audio.AudioPlayer;

public class FishCourt extends JPanel {	
	public static FileInputStream sound = null;
	public static AudioStream as = null;


	public static final AudioPlayer player = AudioPlayer.player;
	public List<EnemyFish> enemList = new ArrayList<EnemyFish>();
	public UserFish me;
	
	public boolean playing = false;
	private JLabel status;
	
	public static final int COURT_WIDTH = 600;
	public static final int COURT_HEIGHT = 400;
	public static final int MY_VELOCITY = 6;
	
	final static String img_file = "water.jpg";
	private BufferedImage water = null;
	{
	try {
		if (water == null) {
			water = ImageIO.read(new File(img_file));
		}
	} catch (IOException e) {
		System.out.println("Internal Error:" + e.getMessage());
	}
	}
	
	public FishCourt(JLabel status) {
		
	setBorder(BorderFactory.createLineBorder(Color.BLACK));
	//setBackground(Color.cyan);
	
	Timer t = new Timer(1300, new ActionListener() {
		public void actionPerformed (ActionEvent e) {
			tick();
		}
	});
	
	Timer t1 =new Timer(35, new ActionListener() {
		public void actionPerformed (ActionEvent e) {
			tock();
		}
	});
	
	t.start();
	t1.start();
	
	setFocusable(true);
	
	addKeyListener(new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT)
				me.v_x = -MY_VELOCITY;
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				me.v_x = MY_VELOCITY;
			else if (e.getKeyCode() == KeyEvent.VK_DOWN)
				me.v_y = MY_VELOCITY;
			else if (e.getKeyCode() == KeyEvent.VK_UP)
				me.v_y = -MY_VELOCITY;
		}

		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
				me.v_x = 0;
			}
			else {
				me.v_y = 0; 
			}
			
			//me.v_x = 0;
			//me.v_y = 0;
		}
	});
	
	this.status = status;
	}
	
	
	/**
	public Set<EnemyCircles> getSet() {
		return enemList;
	}**/
	
	
	public void reset() {
		me = new UserFish(COURT_WIDTH, COURT_HEIGHT);
		enemList.clear();
		
		playing = true;
		status.setText("Swimming...");
		
		requestFocusInWindow();
		player.stop(sound);
		background();
		//player.stop(sound);
	
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		// draw background
		g.drawImage(water,0,0,600,400,null); 
		
		for (EnemyFish a : enemList) {
			g2.setColor(Color.green);
			g2.fill(a.getArea());	
			}
			
			
		g2.setColor(Color.blue);
		g2.fill(me.getArea());
		
		AffineTransform flip = new AffineTransform();
		flip.scale(-1, 1);
		
		AffineTransform translate = new AffineTransform();
		translate.setToTranslation(me.pos_x + me.size, me.pos_y);
		
		Area eye = new Area(new Ellipse2D.Double(21.0/30*me.getSize(), 7.0/30*me.getSize(),
				2.0/30*me.getSize(), 2.0/30*me.getSize()));
		eye.transform(flip);
		eye.transform(translate);
		
		g2.setColor(Color.black);
		g2.fill(eye);
		
		//Draw Score
		g2.setColor(Color.red);
		g2.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		g2.drawString("Score " + (me.getSize() - 25), 500, 30);
		//g2.drawString("Number of Enemy Fish: " + (enemList.size()), 400, 60);

	}
	
	void tick() {
		if (playing) {
		enemList.add(new EnemyFish(COURT_WIDTH, COURT_HEIGHT));
		EnemyFish rightspawn = new RightSpawnFish(COURT_WIDTH, COURT_HEIGHT);
		enemList.add(rightspawn);
		
		repaint();
		}
	}
	
	void tock() {
		if (playing) {
			me.move();
			for (EnemyFish a : enemList) {
			a.move();
			}
			
			//Check for intersection
			Set<EnemyFish> delete = new TreeSet<EnemyFish>();
			
			for (EnemyFish a: enemList) {
				if (me.intersects(a)) {
					//Case 1: intersect smaller fish.  It disappears and you grow.
					if (me.getSize() > a.getR()) {
						delete.add(a);
						//enemList.remove(a);
						//me.grow();
						burp();
					}
					else {
						playing = false;
						status.setText("You lose");
						player.stop(as);
						
					}
				}
			}
			enemList.removeAll(delete);
			for (EnemyFish b: delete) {
				me.grow(b);
				}
			
			//Check for win.
			if (me.getSize() > 550) {
				playing = false;
				status.setText("You win!");
				player.stop(as);
			}
			
			//Removes circle from set upon exiting screen
			Set<EnemyFish> delete2 = new TreeSet<EnemyFish>();
			for (EnemyFish a : enemList) {
				if (a.getX() > 600 + a.length || a.getX() + a.getR() < 0) /**(a.getX() > 300)**/ {
					delete2.add(a);
				}
			}
			
			enemList.removeAll(delete2);
			
		repaint();
		}
	}
	
	
	@SuppressWarnings("restriction")
	public static void burp() {
		AudioPlayer player2 = AudioPlayer.player;
		ContinuousAudioDataStream loop = null;
		FileInputStream sound1 = null;
		
		try {
			@SuppressWarnings("resource")
			AudioStream stream = new AudioStream(new FileInputStream("burp.wav"));
			AudioData data = stream.getData();
		    sound1 = new FileInputStream("burp.wav");
		    loop = new ContinuousAudioDataStream(data);
		} catch(IOException error) {}
		
		player2.start(sound1);
	}
	
	//Background music
		@SuppressWarnings("restriction")
		public static void background() {
			//AudioPlayer player = AudioPlayer.player;
			ContinuousAudioDataStream loop = null;

			
			try {
				@SuppressWarnings("resource")
				AudioStream stream = new AudioStream(new FileInputStream("background2.wav"));
				//AudioData data = stream.getData();
				sound = new FileInputStream("background2.wav");
				as = new AudioStream(sound);
				
				//loop = new ContinuousAudioDataStream(as);
			} catch(IOException error) {}
			player.start(as);
		}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}
