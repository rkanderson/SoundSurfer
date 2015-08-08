import java.awt.Graphics;

// Room is the interface for anything that wants to be a room Main can draw and update.
public interface Room
{
	public void draw(Graphics g);
	public void update();
}
