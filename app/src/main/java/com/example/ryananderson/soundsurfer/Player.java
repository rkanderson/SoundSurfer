import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

//This class represents the player object and will receive input from the mouse or touch-screen interface.
//It will make the ball jump accordingly to the user's actions.
//The ball also moves using the laws of physics (constant accel. due to gravity) 
public class Player implements MouseListener, KeyListener 
{

	public static final int x = 100; 
	public static final int DEFAULT_RADIUS = 50;
	private int radius;
	public Color color = Color.RED;
	public static final double DEFAULT_GRAVITY = 1, DEFAULT_JUMP_STRENGTH = 16; 
	private double gravity, jumpStrength;
	private int y;
	private double ySpeed;
	private Main applet;
	private boolean isDead = false;
	private SoundWavePixel[] pixels;
	public static final int DEFAULT_JUMP_SCORE_INCREASE = 5, DEFAULT_MISS_SCORE_DECREASE = 10;
	private int jumpScoreIncrease = DEFAULT_JUMP_SCORE_INCREASE, missScoreDecrease = DEFAULT_MISS_SCORE_DECREASE; //These 2 attributes represent the amount of score is added or deducted depending on if a player jumps on the line or jumps off
	private long score = 0;
	private ArrayList<ScoreText> scoreTexts = new ArrayList<ScoreText>();
	private boolean autoJump = false;


	public Player(int _y, SoundWavePixel[] _pixels, Main _applet)
	{
		y = _y;
		ySpeed = 0;
		applet = _applet;
		applet.addMouseListener(this);
		applet.addKeyListener(this);
		gravity = DEFAULT_GRAVITY;
		jumpStrength = DEFAULT_JUMP_STRENGTH;
		radius = DEFAULT_RADIUS;
		pixels = _pixels;
	}

	//gettas
	public Color getColor()
	{
		return color;
	}
	
	//settas
	public void setGravity(double val)
	{
		gravity = val;
	}

	public void setJumpStrength(double val)
	{
		jumpStrength = val;
	}
	
	public void setJumpScoreIncrease(int v)
	{
		jumpScoreIncrease = v;
	}
	
	public void setMissScoreDecrease(int v)
	{
		missScoreDecrease = v;
	}
	
	//updatas
	public void miss()
	{
		score-=missScoreDecrease;
		scoreTexts.add(new ScoreText(-missScoreDecrease, x, y, this));
	}

	public void jump()
	{
		ySpeed = -jumpStrength;
		score+=jumpScoreIncrease;
		scoreTexts.add(new ScoreText(jumpScoreIncrease, x, y, this));
	}

	public void draw(Graphics g)
	{
		g.setColor(color); 
		g.fillOval(x-radius, y-radius, radius*2, radius*2); //draws the new update
		g.setColor(Color.BLACK);
		g.drawOval(x-radius, y-radius, radius*2, radius*2);
		for(ScoreText t: scoreTexts) t.draw(g);
		g.setColor(SoundWavePixel.getColor());
		g.setFont(new Font("Arial", Font.BOLD, 21));
		g.drawString("SCORE: "+score, Main.APPLET_WIDTH-200, Main.APPLET_HEIGHT-30);
	}

	// Update position of Player object. Should feel gravity. If at bottom, kill player.
	// @Return true if player is dead
	public boolean update()
	{
		if(!isDead)
		{			
			ySpeed += gravity; //updates the position of the ball
			y += ySpeed;
			if(autoJump) for(SoundWavePixel p: pixels)
			{
				//checks if is in range to jump
				if(Math.sqrt((x-p.getX()) * (x-p.getX()) + (y-p.getY()) * (y-p.getY())) <= radius)
				{
					jump();//auto-jump
				}
			}
		}
		if(Main.APPLET_HEIGHT<=y-radius) //ball now disappears from the screen
		{
			isDead = true; //kills player
			System.out.println("THANK YOU FOR BETA TESTING");
		}
		else if(score<0) isDead = true;
		for(int i=scoreTexts.size()-1; i>=0; i--)
		{
			scoreTexts.get(i).update();
			if(scoreTexts.get(i).isOutOfBounds()) scoreTexts.remove(i);
		}
		//System.out.println("scoreTextsSize: "+scoreTexts.size());
		
		return isDead;
	}

	//destroyas
	public void destroy()
	{
		applet.removeMouseListener(this);
	}
	
	//inputas
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}

	// If mousePressed() is executed, check to see if this player is in contact with any SoundWavePixel.
	// If so, jump. Otherwise do not jump.
	// @param arg0 is if not used
	public void mousePressed(MouseEvent arg0) 
	{
		try
		{
			for(SoundWavePixel p: pixels)
			{
				//checks if is in range to jump
				int pixelX = p.getX();
				for(int pixelY = p.getY(); pixelY <= p.getY()+p.getHeight(); pixelY++){
					if(Math.sqrt((x-pixelX) * (x-pixelX) + (y-pixelY) * (y-pixelY)) <= radius)
					{
						jump();
						return;
					}
				}

			}
			miss();
		}
		//debug
		catch(Exception e)
		{
			System.out.println("player mousePressed problem");
			System.out.println(e.getMessage());
		}
	}

	public void mouseReleased(MouseEvent arg0) {}

	public void setColor(Color c) {
		// TODO Auto-generated method stub
		color = c;
	}

	public void setRadius(double playerRadius) {
		// TODO Auto-generated method stub
		radius = (int) playerRadius;
	}
	
	public long getScore() {
		return score;
	}

	// The ScoreText class represents the numbers (e.g. +100, -200, +50, etc) that fly around when the player gains or loses points.
	private static class ScoreText
	{
		private static final int verticalSpeed  = 20;
		int myX, myY, myValue;
		byte alpha = 125;
		Player player;
	
		public ScoreText(int v, int x, int y, Player p)
		{
			myValue = v;
			myX = x;
			myY = y;
			player = p;
		}
		
		public boolean isOutOfBounds() {
			if(myY<0 || myY>Main.APPLET_HEIGHT) return true;
			return false;
		}

		public void draw(Graphics g)
		{
			if(myValue>0) g.setColor(SoundWavePixel.getColor());
			else g.setColor(player.getColor());
			String str = "";
			if(myValue>0)str+="+";
			Font font = new Font("Arial", Font.BOLD, 21);
			g.setFont(font);
			g.drawString(str+myValue, myX, myY);
		}
		
		public void update()
		{
			if(myValue>0) myY-=verticalSpeed;
			else myY+=verticalSpeed;
			myX-=WaveManager.xSpeed()/3;
		}
		
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_A)
		{
			if(autoJump) autoJump = false;
			else autoJump = true;
		}
		/*if(e.getKeyCode()==KeyEvent.VK_SPACE)
		{
			try
			{
				for(SoundWavePixel p: pixels)
				{
					//checks if is in range to jump
					int pixelX = p.getX();
					for(int pixelY = p.getY(); pixelY <= p.getY()+p.getHeight(); pixelY++){
						if(Math.sqrt((x-pixelX) * (x-pixelX) + (y-pixelY) * (y-pixelY)) <= radius)
						{
							jump();
							return;
						}
					}

				}
				miss();
			}
			//debug
			catch(Exception f)
			{
				System.out.println("player mousePressed problem");
				System.out.println(f.getMessage());
			}
		}*/ //use space to jump
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
