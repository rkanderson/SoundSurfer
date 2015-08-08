import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URISyntaxException;

// This is the main class which extends Applet. A thread will continuously draw its current room.
// When it creates a new room, it passes its reference so that the room can set Main's room to something else.
// e.g. main menu sets current room to a level when a LevelButton is clicked.
public class Main extends Applet 
{

	private static final long serialVersionUID = 1L;
	public static final int BETWEEN_FRAME_WAIT_TIME = 25;//25
	public static final int APPLET_WIDTH = 500, APPLET_HEIGHT = 700;
	public static String RESOURCES_DIR;
	public static Dimension dim;
	private Graphics bufferGraphics;
	private Image offscreen;                      
	private Room currentRoom;
	private boolean isRunning = true;
	
	
	static
	{
		try {
			RESOURCES_DIR = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()+"Resources";
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	// Indicates that the applet should initialize. The dimensions, current room, as well
	// as some other things are set up.
	public void init()
	{
		
		setSize(APPLET_WIDTH, APPLET_HEIGHT);
		dim = getSize();
		setFocusable(true);
		requestFocusInWindow();
		offscreen = createImage(dim.width, dim.height); 
		bufferGraphics = offscreen.getGraphics();
		currentRoom = new MainMenu(this);
		
	}

	@Override
	// Indicates that the applet should start. A new thread is spawned which will continuously
	// draw the current room.
	public void start()
	{
		isRunning = true;
		new Thread(new Runnable()
		{
			public void run() 
			{
				while(isRunning){
					repaint();
					pause(BETWEEN_FRAME_WAIT_TIME);
				}

			}
		}).start();
	}
	
	@Override
	public void destroy()
	{
		isRunning = false;
	}

	@Override
	public void paint(Graphics g)
	{
		bufferGraphics.clearRect(0, 0, dim.width, dim.height); 
		currentRoom.draw(bufferGraphics);
		g.drawImage(offscreen, 0, 0, this);
	}

	@Override
	public void update(Graphics g)
	{
		currentRoom.update();
		paint(g);
	}
	
	public void setCurrentRoom(Room r){
		currentRoom = r;
	}

	// A method to easily cause thread to pause for a specified time. 
	// Can be used by any object since it is public static.
	public static void pause(int timeMillis)
	{
		try {
			Thread.sleep(timeMillis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
