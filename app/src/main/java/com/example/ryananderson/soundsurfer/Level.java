import java.applet.AudioClip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

/*
 * This is the room which manages the level, which includes the wave, all background effects,
 * music, the player, and the time elapsed in the level.
 * It delegates to specialized instances of classes, e.g. waveManager.
 */
public class Level implements Room, MouseListener{
	
	public static long timeElapsedMillis = 0;
	private Main applet;
	private Player player;
	private MusicReader mr;
	private WaveManager waveManager;
	private BackgroundEffectsManager bfxManager;
	private String songFilePath;
	private boolean isRunning = false;
	private SoundWavePixel[] pixels;
	private AudioClip song;
	private LevelButton myBtn;
	private int myPercent;
	private long myScore;
	boolean started = false;
	
	public Level(Main a, LevelButton btn)
	{
		applet = a;
		applet.addMouseListener(this);
		songFilePath = btn.getSongFilePath();
		myBtn = btn;
		waveManager = new WaveManager(songFilePath);
		myPercent = waveManager.getPercent();
		bfxManager = new BackgroundEffectsManager(mr, btn);
		pixels  = waveManager.getPixels();
		player = new Player(Main.APPLET_HEIGHT/3*2, pixels, applet);
		myScore = player.getScore();
		song = applet.getAudioClip(applet.getDocumentBase(), songFilePath);
	}
	
	//gettas
	public static long timeElapsedMillis()
	{
		return timeElapsedMillis;
	}
	
	private void start()
	{
		
		System.out.println("song file path: " + songFilePath);
		song.play();
		applet.removeMouseListener(this);
		isRunning = true;
		started = true;
		
	}
	
	// Draw player, waveManager, and bfxManager.
	public void draw(Graphics g)
	{
		bfxManager.draw(g);
		SoundWavePixel.setColor(myBtn.getColorTimeline().getLineColor(timeElapsedMillis));
		waveManager.draw(g);
		player.setColor(myBtn.getColorTimeline().getPlayerColor(timeElapsedMillis));
		player.draw(g);
	}
	
	// Update player and waveManager.
	public void update()
	{
		if(isRunning)
		{
			//set properties based on special regions
			SpecialRegionTimeline srt = myBtn.getSpecialRegionTimeline();
			player.setRadius(srt.getPlayerRadius(timeElapsedMillis));
			player.setGravity(srt.getGravity(timeElapsedMillis));
			player.setJumpStrength(srt.getJumpStrength(timeElapsedMillis));
			player.setJumpScoreIncrease(srt.getJumpScoreIncrease(timeElapsedMillis));
			player.setMissScoreDecrease(srt.getMissScoreDecrease(timeElapsedMillis));
			waveManager.setPixelSpeed((int)srt.getSpeed(timeElapsedMillis));
			waveManager.setAmplitudeMultiplier(srt.getAmplitudeMultiplier(timeElapsedMillis));
			waveManager.update();
			if(player.update()) 
				destroy(); //Will execute if player is dead.
			else if (started)
				timeElapsedMillis += Main.BETWEEN_FRAME_WAIT_TIME;
			if(waveManager.valueBankIsExhausted()) win();
		//	System.out.println(timeElapsedMillis);
		}
		
	}

	//destroys the level and all the specialized classes if the player dies.
	//loads up the GameOver screen
	private void destroy()
	{
		myPercent = waveManager.getPercent();
		myScore = player.getScore();
		if(myScore<0)
			myScore = 0;
		isRunning = false;
		song.stop();
		player.destroy();
		applet.setCurrentRoom(new GameOver(applet, myBtn, myPercent, myScore));
		timeElapsedMillis = 0;
	}
	
	//destroys the level if the player wins.
	//loads up the Win screen.
	private void win()
	{
		myPercent = waveManager.getPercent();
		myScore = player.getScore();
		isRunning = false;
		song.stop();
		player.destroy();
		applet.setCurrentRoom(new Win(applet, myBtn, myPercent, myScore));
		timeElapsedMillis = 0;
	}
	
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		start();
		
	}

	public void mouseReleased(MouseEvent arg0) {}
}
