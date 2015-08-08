import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;


// LevelButton represents a button one can click in the main menu to start a level. It also acts as a useful
// Container for level data and its reference is numTimesuently passed around as a seed.
public class LevelButton 
{
	public static final Color COLOR_DEFAULT = Color.BLACK, 
			COLOR_MOUSE_HOVER = Color.RED,
			COLOR_SELECT = Color.BLUE,
			COLOR_TEXT = Color.WHITE;
	private Color currentColor = COLOR_DEFAULT;
	public static final int WIDTH = 100,
			HEIGHT = 50;
	public final String buttonText, songFilePath;
	public final int x, y; // x and y are in upper right corner
	private boolean selected = false;
	private ColorTimeline colorTimeline;
	private SpecialRegionTimeline specialRegionTimeline;
	private BackgroundEffectsTimeline backgroundEffectsTimeline;
	
	public LevelButton(String _buttonText, String _songFilePath, int _x, int _y, ColorTimeline ct, SpecialRegionTimeline srt, BackgroundEffectsTimeline bet)
	{
		buttonText = _buttonText;
		songFilePath = _songFilePath;
		x = _x;
		y = _y;
		colorTimeline = ct;
		specialRegionTimeline = srt;
		backgroundEffectsTimeline = bet;
	}
	
	//gettas
	public String getSongFilePath()
	{
		return songFilePath;
	}
	
	public ColorTimeline getColorTimeline()
	{
		return colorTimeline;
	}
	
	public SpecialRegionTimeline getSpecialRegionTimeline()
	{
		return specialRegionTimeline;
	}
	
	public BackgroundEffectsTimeline getBFXTimeline()
	{
		return backgroundEffectsTimeline;
	}
	
	//settas
	public void setColor(Color c)
	{
		if(!selected)currentColor = c;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(currentColor);
		g.fillRoundRect(x, y, WIDTH, HEIGHT, WIDTH, HEIGHT/4);
		//g.fill3DRect(x, y, WIDTH, HEIGHT, true);
		g.setFont(new Font("Arial", Font.ITALIC, 12));
		g.setColor(COLOR_TEXT);
		FontMetrics fm = g.getFontMetrics();
		g.drawString(buttonText, x + WIDTH/2 - fm.stringWidth(buttonText)/2, y+HEIGHT/2);
	}
	
	public boolean mouseIsInside(int mouseX, int mouseY)
	{
		if(mouseX >= x && mouseX <= x + WIDTH && mouseY >= y && mouseY <= y + HEIGHT) return true;
		return false;
	}
	
	public void select()
	{
		selected = true;
		setColor(COLOR_SELECT);
		Main.pause(250);
	}
	
}
