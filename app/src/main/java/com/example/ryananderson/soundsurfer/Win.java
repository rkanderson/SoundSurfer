import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//go-to class for winning a level
public class Win implements Room, KeyListener{

	private static BufferedImage winImg;

	static
	{
		try {
			winImg = ImageIO.read(new File(Main.RESOURCES_DIR+"/win.jpg"));
		} catch (IOException e) {
			System.out.println("win.jpg wasn't found");
			e.printStackTrace();
		}
	}

	private Main applet;
	private LevelButton myBtn;
	private int myPercent;
	private long myScore;


	public Win(Main a, LevelButton btn, int percent, long score){
		applet = a;
		myBtn = btn;
		myPercent = percent;
		myScore = score;
		applet.addKeyListener(this);
	}

	public void draw(Graphics g) {
		g.drawImage(winImg,0,0,null);
		g.setColor(Color.BLACK);
		String str1 = "Percent Completed: 100 %";
		g.drawString(str1, 125, Main.APPLET_HEIGHT/2-50);
		String str2 = "Score: " + myScore;
		g.drawString(str2, 125, Main.APPLET_HEIGHT/2);
	}

	public void update() {
		// TODO Auto-generated method stub

	}

	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_SPACE) restartLevel();
		else if(e.getKeyCode() == KeyEvent.VK_M) mainMenu();

	}

	private void restartLevel()
	{
		applet.removeKeyListener(this);
		applet.setCurrentRoom(new Level(applet, myBtn));
	}

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