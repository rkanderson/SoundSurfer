import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class MainMenu implements Room, MouseMotionListener, MouseListener, KeyListener
{	

	private Main applet;
	private ArrayList<LevelButton> buttons = new ArrayList<LevelButton>();

	// The title image will be displayed until the user either clicks or presses any key.
	// When this happens, the image will move up and out of sight, revealing level buttons
	private BufferedImage titleImage;
	private int titleImageY;
	private boolean titleImageIsMovingUp = false, buttonsCanBeClicked = false;
	private AudioClip song;
	private int myPercent;
	private long myScore;

	public MainMenu(Main _applet)
	{
		applet = _applet;
		applet.addMouseListener(this);
		applet.addMouseMotionListener(this);
		applet.addKeyListener(this);
		song = applet.getAudioClip(applet.getDocumentBase(), Main.RESOURCES_DIR+"/Geometry Wars - Theme.wav");
		try {
			titleImage = ImageIO.read(new File(Main.RESOURCES_DIR+"/title.jpg"));
		} catch (IOException e) {
			System.out.println("title.jpg wasn't found");
			e.printStackTrace();
		}
		song.play();

		//add any Buttons to buttons here
		//buttons.add(new LevelButton("I'm blue", Main.RESOURCES_DIR+"/blue.wav",0, 0, Color.CYAN));

		//Demons
/*		ColorTimeline demonsColorTimeline = new ColorTimeline();
		SpecialRegionTimeline demonsSRT = new SpecialRegionTimeline();
		BackgroundEffectsTimeline demonsBFXT = new BackgroundEffectsTimeline();
		demonsBFXT.add(new BackgroundEffectsTimeline.CityEffect(0, 45370));
		demonsBFXT.add(new BackgroundEffectsTimeline.FlashEffect(45370,45390, Color.MAGENTA));
		demonsBFXT.add(new BackgroundEffectsTimeline.FlashEffect(45410,45430, Color.MAGENTA));
		demonsBFXT.add(new BackgroundEffectsTimeline.FlashEffect(45450,45470, Color.MAGENTA));
		demonsBFXT.add(new BackgroundEffectsTimeline.FlashEffect(45490,45510, Color.MAGENTA));
		demonsBFXT.add(new BackgroundEffectsTimeline.FlashEffect(45530,45550, Color.MAGENTA));
		demonsBFXT.add(new BackgroundEffectsTimeline.FlashEffect(45570,45590, Color.MAGENTA));
		demonsBFXT.add(new BackgroundEffectsTimeline.FlashEffect(45610,45630, Color.MAGENTA));
		demonsBFXT.add(new BackgroundEffectsTimeline.FlashEffect(45650,45670, Color.MAGENTA));
		
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(0, 2780, Color.PINK, Color.BLUE, Color.RED));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(2780, 5380, Color.BLACK, Color.WHITE, Color.CYAN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(5380, 7990, Color.MAGENTA, Color.YELLOW, Color.GREEN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(7990, 10640, Color.BLACK, Color.WHITE, Color.CYAN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(10640, 13370, Color.PINK, Color.BLUE, Color.RED));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(13370, 15980, Color.BLACK, Color.WHITE, Color.CYAN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(15980, 21260, Color.MAGENTA, Color.YELLOW, Color.GREEN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(21260, 24040, Color.BLACK, Color.WHITE, Color.CYAN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(24040, 26620, Color.PINK, Color.BLUE, Color.RED));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(26620, 32430, Color.BLACK, Color.WHITE, Color.CYAN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(32430, 42760, Color.MAGENTA, Color.YELLOW, Color.GREEN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(42760, 45370, Color.BLACK, Color.WHITE, Color.CYAN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(45370, 47850, Color.PINK, Color.BLUE, Color.RED));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(47850, 50460, Color.BLACK, Color.WHITE, Color.CYAN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(50460, 53260, Color.MAGENTA, Color.YELLOW, Color.GREEN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(53260, 55950, Color.BLACK, Color.WHITE, Color.CYAN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(55950, 58630, Color.PINK, Color.BLUE, Color.RED));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(58630, 61390, Color.BLACK, Color.WHITE, Color.CYAN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(61390, 64050, Color.MAGENTA, Color.YELLOW, Color.GREEN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(64050, 70530, Color.BLACK, Color.WHITE, Color.CYAN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(70530, 73130, Color.PINK, Color.BLUE, Color.RED));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(73130, 76010, Color.BLACK, Color.WHITE, Color.CYAN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(76010, 85310, Color.MAGENTA, Color.YELLOW, Color.GREEN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(85310, 90690, Color.BLACK, Color.WHITE, Color.CYAN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(90690, 96050, Color.PINK, Color.BLUE, Color.RED));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(96050, 98710, Color.BLACK, Color.WHITE, Color.CYAN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(98710, 101320, Color.MAGENTA, Color.YELLOW, Color.GREEN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(101320, 104000, Color.BLACK, Color.WHITE, Color.CYAN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(104000, 106560, Color.MAGENTA, Color.YELLOW, Color.GREEN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(106560, 116010, Color.BLACK, Color.WHITE, Color.CYAN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(116010, 126760, Color.MAGENTA, Color.YELLOW, Color.GREEN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(126760, 139060, Color.BLACK, Color.WHITE, Color.CYAN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(139060, 150910, Color.MAGENTA, Color.YELLOW, Color.GREEN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(139060, 150910, Color.MAGENTA, Color.YELLOW, Color.GREEN));
		demonsColorTimeline.add(new ColorTimeline.ColorRegion(150910, 256000, Color.MAGENTA, Color.YELLOW, Color.GREEN));
		//sandstormSRT.add(new SpecialRegion.FeatherRegion(2000, 15350));
	
		demonsSRT.add(new SpecialRegion.FeatherRegion(45370, 64050));
		demonsSRT.add(new SpecialRegion.EnhancedGravityRegion(85310, 106560, 2));		
		demonsSRT.add(new SpecialRegion.EnhancedSpeedRegion(106560, 126760, 2));
		demonsSRT.add(new SpecialRegion.EnhancedSpeedRegion(126760, 150910, 4));
		//sandstormSRT.add(new SpecialRegion.FeatherRegion(500, 30350));
		buttons.add(new LevelButton("Demons", Main.RESOURCES_DIR+"/demons.wav",Main.APPLET_WIDTH/2-LevelButton.WIDTH/2, 200, demonsColorTimeline, demonsSRT, demonsBFXT));
	*/	
		
		//F-Zero
		ColorTimeline fzeroColorTimeline = new ColorTimeline();
		SpecialRegionTimeline fzeroSRT = new SpecialRegionTimeline();
		BackgroundEffectsTimeline fzeroBFXT = new BackgroundEffectsTimeline();
		//fzeroColorTimeline.add(new ColorTimeline.ColorRegion(0, 19000, Color.BLUE, Color.RED, Color.GREEN)); // Build
		// ColorFadeRegion Test 
		fzeroColorTimeline.add(new ColorTimeline.ColorFadeRegion(0, 19000, Color.CYAN, Color.PINK, Color.GRAY, Color.BLUE, Color.RED, Color.GREEN)); // Build
		fzeroBFXT.add(new BackgroundEffectsTimeline.PictureEffect(24000, 38300, "galaxy.jpg"));
		fzeroBFXT.add(new BackgroundEffectsTimeline.RainEffect(1000,19000, 5, Color.WHITE));
		fzeroBFXT.add(new BackgroundEffectsTimeline.CityEffect(1000, 19000));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(18900, 19100, Color.WHITE));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(38300, 38500, Color.WHITE));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(38700, 38900, Color.WHITE));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(39100, 39300, Color.WHITE));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(39500, 39700, Color.WHITE));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(39900, 40100, Color.WHITE));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(40300, 40500, Color.WHITE));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(40700, 40900, Color.WHITE));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(41100, 41300, Color.WHITE));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(41500, 41700, Color.WHITE));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(41900, 42100, Color.WHITE));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(42300, 42500, Color.WHITE));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(42700, 42900, Color.WHITE));
		fzeroSRT.add(new SpecialRegion.EnhancedSpeedRegion(19000, 24000, 2));//Beat drop
		fzeroBFXT.add(new BackgroundEffectsTimeline.SeizureEffect(19000, 24000));
		fzeroColorTimeline.add(new ColorTimeline.ColorRegion(19000, 24000, Color.BLUE, Color.CYAN, Color.WHITE));
		fzeroSRT.add(new SpecialRegion.EnhancedSpeedRegion(24000, 29000, 2.5));//Beat drop 2.0
		fzeroColorTimeline.add(new ColorTimeline.ColorFadeRegion(24000, 33000, Color.ORANGE, Color.YELLOW, Color.WHITE, Color.GREEN, Color.RED, Color.YELLOW));
		fzeroSRT.add(new SpecialRegion.EnhancedSpeedRegion(33000, 38300, 1.5));
		fzeroColorTimeline.add(new ColorTimeline.ColorFadeRegion(33000, 38300, Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE, Color.RED, Color.GREEN));
		fzeroColorTimeline.add(new ColorTimeline.ColorRegion(38300, 52930, Color.RED, Color.PINK, Color.MAGENTA));
		fzeroSRT.add(new SpecialRegion.FeatherRegion(38300, 63300));
		fzeroSRT.add(new SpecialRegion.EnhancedSpeedRegion(63300, 68300, 2));
		fzeroBFXT.add(new BackgroundEffectsTimeline.SeizureEffect(63300, 78000));
		fzeroColorTimeline.add(new ColorTimeline.ColorRegion(63300, 68300, Color.BLACK, Color.CYAN, Color.WHITE));
		fzeroSRT.add(new SpecialRegion.EnhancedSpeedRegion(68300, 78000, 2.5));
		fzeroColorTimeline.add(new ColorTimeline.ColorRegion(68300, 78000, Color.ORANGE, Color.YELLOW, Color.WHITE));
		fzeroColorTimeline.add(new ColorTimeline.ColorRegion(78000, 88000, Color.GRAY, Color.BLACK, Color.WHITE));
		fzeroBFXT.add(new BackgroundEffectsTimeline.SeizureEffect(78000, 88000));
		fzeroBFXT.add(new BackgroundEffectsTimeline.MeteorShowerEffect(78000,  88000, 20, Color.BLACK));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(78000, 78100, Color.BLACK));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(79000, 79100, Color.BLACK));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(80000, 80100, Color.BLACK));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(81000, 81100, Color.BLACK));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(82000, 82100, Color.BLACK));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(83000, 83100, Color.BLACK));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(84000, 84100, Color.BLACK));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(85000, 85100, Color.BLACK));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(86000, 86100, Color.BLACK));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(87000, 87100, Color.BLACK));
		fzeroBFXT.add(new BackgroundEffectsTimeline.FlashEffect(88000, 88100, Color.BLACK));
	
		
		buttons.add(new LevelButton("F-Zero", Main.RESOURCES_DIR+"/f-zero.wav", Main.APPLET_WIDTH/2-LevelButton.WIDTH/2, 300, fzeroColorTimeline, fzeroSRT, fzeroBFXT));
		
		
	/*	//the knife AL remix
		ColorTimeline alColorTimeline = new ColorTimeline();
		SpecialRegionTimeline alSRT = new SpecialRegionTimeline();
		BackgroundEffectsTimeline alBFXT = new BackgroundEffectsTimeline();
		alColorTimeline.add(new ColorTimeline.ColorRegion(0, 12250, Color.BLACK, Color.LIGHT_GRAY, Color.RED));
		//alBFXT.add(new BackgroundEffectsTimeline.SeizureEffect(0, 12250));
		alColorTimeline.add(new ColorTimeline.ColorRegion(12250, 31000, Color.LIGHT_GRAY, Color.RED, Color.BLACK));
		alColorTimeline.add(new ColorTimeline.ColorRegion(31000, 35500, Color.RED, Color.BLACK, Color.LIGHT_GRAY));
		alColorTimeline.add(new ColorTimeline.ColorRegion(35500, 39500, Color.BLACK, Color.LIGHT_GRAY, Color.RED));
		alColorTimeline.add(new ColorTimeline.ColorRegion(39500, 42500, Color.LIGHT_GRAY, Color.RED, Color.BLACK));
		alColorTimeline.add(new ColorTimeline.ColorRegion(42500, 45700, Color.RED, Color.BLACK, Color.LIGHT_GRAY));
		alColorTimeline.add(new ColorTimeline.ColorRegion(45700, 48180, Color.BLACK, Color.LIGHT_GRAY, Color.RED));
		alColorTimeline.add(new ColorTimeline.ColorRegion(48180, 50490, Color.LIGHT_GRAY, Color.RED, Color.BLACK));
		alColorTimeline.add(new ColorTimeline.ColorRegion(50490, 52940, Color.RED, Color.BLACK, Color.LIGHT_GRAY));
		alColorTimeline.add(new ColorTimeline.ColorRegion(52940, 55280, Color.BLACK, Color.LIGHT_GRAY, Color.RED));
		alColorTimeline.add(new ColorTimeline.ColorRegion(55280, 57690, Color.LIGHT_GRAY, Color.RED, Color.BLACK));
		alColorTimeline.add(new ColorTimeline.ColorRegion(57690, 60050, Color.RED, Color.BLACK, Color.LIGHT_GRAY));
		alColorTimeline.add(new ColorTimeline.ColorRegion(60050, 79930, Color.BLACK, Color.LIGHT_GRAY, Color.RED));
		buttons.add(new LevelButton("The Knife", Main.RESOURCES_DIR+"/fly-pig-fly.mp3.wav", Main.APPLET_WIDTH/2-LevelButton.WIDTH/2, 400, alColorTimeline, alSRT, alBFXT));
	*/	
		/*ColorTimeline ufColorTimeline = new ColorTimeline();
		SpecialRegionTimeline ufSRT = new SpecialRegionTimeline();
		BackgroundEffectsTimeline ufBFXT = new BackgroundEffectsTimeline();
		buttons.add(new LevelButton("Uptown Funk", Main.RESOURCES_DIR+"/Uptown Funk.wav", Main.APPLET_WIDTH/2-LevelButton.WIDTH/2, 500, ufColorTimeline, ufSRT, ufBFXT));
	*/
	}



	public void draw(Graphics g) 
	{
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, Main.APPLET_WIDTH, Main.APPLET_HEIGHT);
		g.setColor(Color.MAGENTA);
		Font font = new Font("Arial", Font.BOLD, 48);
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics();
		g.drawString("LEVEL SELECT", Main.APPLET_WIDTH/2 - fm.stringWidth("LEVEL SELECT")/2, Main.APPLET_HEIGHT/4);
		for(LevelButton b: buttons)
		{
			b.draw(g);
		}
		g.drawImage(titleImage, 0, titleImageY, null);

	}

	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}	
	public void mousePressed(MouseEvent e) 
	{
		titleImageIsMovingUp  = true;
		if(buttonsCanBeClicked){
			int mouseX = e.getX(), mouseY = e.getY();
			for(LevelButton b: buttons) if(b.mouseIsInside(mouseX, mouseY))
			{
				b.select();
				System.out.println("MOUSEPRESSED");
				song.stop();
				applet.setCurrentRoom(new Level(applet, b));
				applet.removeMouseListener(this);
				applet.removeMouseMotionListener(this);
				applet.removeKeyListener(this);
				break;
			}
		}
	}

	public void mouseReleased(MouseEvent arg0) {}
	public void mouseDragged(MouseEvent arg0) {}
	public void mouseMoved(MouseEvent e)
	{
		int mouseX = e.getX(), mouseY = e.getY();
		for(LevelButton b: buttons) 
			if(b.mouseIsInside(mouseX, mouseY))
			{
				b.setColor(LevelButton.COLOR_MOUSE_HOVER);
			}
			else b.setColor(LevelButton.COLOR_DEFAULT);
	}



	public void update() {
		//System.out.println("Title");
		if(titleImageIsMovingUp) titleImageY-=15;
		if(titleImageY <= -Main.APPLET_HEIGHT)
		{
			titleImageIsMovingUp = false;
			buttonsCanBeClicked = true;
		}
	}



	public void keyPressed(KeyEvent arg0) {
		titleImageIsMovingUp = true;
	}



	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
