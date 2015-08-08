import java.awt.Color;
import java.awt.Graphics;


public class BackgroundEffectsManager
{

	private MusicReader mr;
	private LevelButton btn;
	private int[] vals;
	private Color c;
	private BackgroundEffectsTimeline timeline;
	
	public BackgroundEffectsManager(MusicReader _mr, LevelButton b)
	{
		mr = _mr;
		vals = MusicReader.getWaveModifiedValues(b.getSongFilePath());
		btn = b;
		c = btn.getColorTimeline().getBackgroundColor(0);
		timeline = btn.getBFXTimeline();
	}

	public Color getColor()
	{
		return c;
	}
	
	public void draw(Graphics g)
	{
		c = btn.getColorTimeline().getBackgroundColor(Level.timeElapsedMillis());
		g.setColor(c);
		g.fillRect(0, 0, Main.APPLET_WIDTH, Main.APPLET_HEIGHT);
		if(timeline.shouldHaveFilter(Level.timeElapsedMillis))timeline.draw(g, Level.timeElapsedMillis());
		c = btn.getColorTimeline().getBackgroundColor(Level.timeElapsedMillis());
		c = new Color(c.getRed(), c.getGreen(), c.getBlue(), 125);
		g.setColor(c);
		g.fillRect(0, 0, Main.APPLET_WIDTH, Main.APPLET_HEIGHT);
		if(!timeline.shouldHaveFilter(Level.timeElapsedMillis))timeline.draw(g, Level.timeElapsedMillis());
	}
	
}
