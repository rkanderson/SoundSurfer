
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

// Main's room will be set to a GameOver when the player loses.
public class GameOver implements Room, KeyListener{

	private static BufferedImage gameOverImg;
	
	static
	{
		try {
			gameOverImg = ImageIO.read(new File(Main.RESOURCES_DIR+"/gameOver.jpg"));
		} catch (IOException e) {
			System.out.println("gameOver.jpg wasn't found");
			e.printStackTrace();
		}
	}
	
	private Main applet;
	private LevelButton myBtn;
	private int myPercent;
	private long myScore;

	
	public GameOver(Main a, LevelButton btn, int percent, long score){
		applet = a;
		myBtn = btn;
		myPercent = percent;
		myScore = score;
		applet.addKeyListener(this);
	}
	
	// Draws the game over image as well as the percent and score the player got
	public void draw(Graphics g) {
		//g.drawString("You died. Press <SPACE> to retry. Press <m> for main menu.", Main.APPLET_WIDTH/2, Main.APPLET_HEIGHT/2);
		g.drawImage(gameOverImg,0,0,null);
		g.setColor(Color.BLACK);
		String str1 = "Percent Completed: " + myPercent + "%";
		Font font = new Font(Font.SERIF, Font.BOLD, 30);
		g.drawString(str1, 125, Main.APPLET_HEIGHT/2-50);
		String str2 = "Score: " + myScore;
		g.drawString(str2, 125, Main.APPLET_HEIGHT/2);
	}

	public void update() {
		// TODO Auto-generated method stub

	}

	// If space is pressed, restart the level. If m is pressed, go to main menu
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) restartLevel();
		else if(e.getKeyCode() == KeyEvent.VK_M) mainMenu();
		
	}
	
	// Restarts the level by removing itself as a key listener and setting the current room
	// to a new level, passing the button as a seed. 
	private void restartLevel()
	{
		applet.removeKeyListener(this);
		applet.setCurrentRoom(new Level(applet, myBtn));
	}
	
	// Goes to main menu by removing itself as a key listener and setting the room to a MainMenu.
	private void mainMenu()
	{
		applet.removeKeyListener(this);
		applet.setCurrentRoom(new MainMenu(applet));
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}