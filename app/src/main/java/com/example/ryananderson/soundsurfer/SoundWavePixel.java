import java.awt.Color;
import java.awt.Graphics;

//the basic rectangle of the wave
public class SoundWavePixel {
	
	private static Color color = Color.BLUE;
	private int y;
	private int x;
	public static final int DEFAULT_HEIGHT = 5;
	private int height = DEFAULT_HEIGHT;
	
	public SoundWavePixel(int _x, int _y)
	{
		x = _x;
		y = _y;
	}
	
	//gettas
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public static Color getColor()
	{
		return color;
	}
	
	//settas
	public static void setColor(Color c)
	{
		color = c;
	}
	
	public void setHeight(int h)
	{
		height = h;
	}
	
	//draws a SoundWavePixel rectangle based on the height
	//the name is self-contradictory, the "pixels" are actually multiple pixels
	public void draw(Graphics g)
	{
		g.setColor(color);
		g.fillRect(x, y, 3, height);
	}
	
	public void update(int newYVal)
	{
		y = newYVal;
	}
	
}
	

