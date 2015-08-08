import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


//BackgroundEffectsTimeline will serve as a container for BackgroundEffects Regions
public class BackgroundEffectsTimeline {

	private ArrayList<BackgroundEffectsRegion> regions = new ArrayList<BackgroundEffectsRegion>();
	
	// Adds a new B.E.R.
	public void add(BackgroundEffectsRegion r)
	{
		regions.add(r);
	}
	
	// Draws a B.E.R. at a certain time
	public void draw(Graphics g, long time)
	{
		for(BackgroundEffectsRegion r: regions) 
			if(r.isInMe(time)) r.draw(g);
	}
	
	public boolean shouldHaveFilter(long time)
	{
		for(BackgroundEffectsRegion r: regions)
			if(r.isInMe(time) && r.shouldHaveFilter()) return true;
		return false;
	}
	
	// A BackgroundEffectsRegion represents an area in which there is background effects. Different areas
	// with different types of effects will extend this class.
	abstract public static class BackgroundEffectsRegion
	{
		protected final long START_TIME, END_TIME;
		public BackgroundEffectsRegion(long st, long et)
		{
			START_TIME = st;
			END_TIME = et;
		}
		abstract public boolean shouldHaveFilter();
		public boolean isInMe(long time)
		{
			if(time >= START_TIME && time <= END_TIME) return true;
			return false;
		}
		abstract void draw(Graphics g);
		abstract void destroy();
	}
	
	// This will be a simple effect which involves a circle growing and shrinking based on playing field.
	public static class CircleEffect extends BackgroundEffectsRegion
	{
		public CircleEffect(long st, long et) {
			super(st, et);

		}
		
		public boolean shouldHaveFilter(){return false;}		@Override
		void draw(Graphics g) {
			// The val the circle will be based on is the most recently in playing field. Val will be radius
			int val = WaveManager.playingField()[WaveManager.playingField().length-1]+500;
			val = -val + WaveManager.DEFAULT_PIXEL_Y; //Renders
			g.setColor(Color.WHITE);
			g.fillOval(Main.APPLET_WIDTH/2-val, Main.APPLET_HEIGHT/2-val, val*2, val*2);
		}
		
		@Override
		void destroy()
		{
			return;
		}
		
	}
	
	//This will be a simple effect. It draws an image in the upper left corner.
	public static class PictureEffect extends BackgroundEffectsRegion
	{
		private Image image;

		public PictureEffect(long st, long et, String picFile) {
			super(st, et);
			try {
				image = ImageIO.read(new File(Main.RESOURCES_DIR+"/"+picFile));
			} catch (IOException e) {
				System.out.println(picFile+" wasn't found");
				e.printStackTrace();
			}
		}

		@Override
		public boolean shouldHaveFilter() {return true;}

		@Override
		void draw(Graphics g) {
			g.drawImage(image, 0 ,0, null);
		}

		@Override
		void destroy() {
			return;
		}
		
	}
	
	// Flashes by turning the whole background a certain color
	public static class FlashEffect extends BackgroundEffectsRegion
	{
		Color col;
		public FlashEffect(long st, long et, Color color)
		{
			super(st, et);
			col = color;
		}
		
		public boolean shouldHaveFilter(){return false;}
		
		@Override
		void draw(Graphics g)
		{
			g.setColor(col);
			g.fillRect(0,0,Main.APPLET_WIDTH, Main.APPLET_HEIGHT);
		}
		
		@Override
		void destroy()
		{
			return;
		}
	}
	
	// Shows a city-scape in the background which slowly moves to the left.
	public static class CityEffect extends BackgroundEffectsRegion
	{	
		// A container of arrays representing building properties
		// array[0] is the x-coordinate (left edge) and array[1] is the height
		private ArrayList<int[]> buildings = new ArrayList<int[]>();
		private int space = 21, buildingWidth = 50, buildingSpeed = 2;
		public CityEffect(long st, long et)
		{
			super(st,et);
			int x=Main.APPLET_WIDTH;
			while(x>=0)
			{
				buildings.add(new int[]{x, (int) (Math.random()*500)+200});
				x-=(buildingWidth + space);
			}
		}
		
		public boolean shouldHaveFilter(){return true;}
		
		@Override
		void draw(Graphics g)
		{
			
			space = (int)(Math.random()*70);
			//updating
			//remove out of bounds buildings
			for(int i = buildings.size()-1; i >= 0; i--)
				if(buildings.get(i)[0] + buildingWidth < 0) buildings.remove(i);
			// add new one if necessary
			if(Main.APPLET_WIDTH >= buildings.get(buildings.size()-1)[0] + buildingWidth + space) {
				buildings.add(new int[]{Main.APPLET_WIDTH, (int) (Math.random()*500)+200});
			}
			//update position
			for(int[] building: buildings) building[0] -= buildingSpeed;
			
			//drawing
			g.setColor(Color.BLACK);
			for(int[] props: buildings){
				int x = props[0];
				int height = props[1];
				g.fillRect(x, Main.APPLET_HEIGHT-height, buildingWidth, height);
				
			if(Level.timeElapsedMillis() == END_TIME-1)
				destroy();
			}
		}
		
		@Override
		void destroy()
		{
			for(int i=buildings.size()-1; i>=0; i--)
				buildings.remove(i);
		}
	}
	
	public static class MeteorShowerEffect extends BackgroundEffectsRegion
	{
		private int xSpeed = -2, ySpeed = 10, meteorRadius = 3;
		protected Color meteorColor;
		private ArrayList<int[]> meteors = new ArrayList<int[]>(); //array[0] = x, array[1] = y
		
		public MeteorShowerEffect(long st, long et, int radius, Color color)
		{
			super(st,et);
			meteorRadius = radius;
			meteorColor = color;
		}
		
		public boolean shouldHaveFilter(){return false;}
		
		void draw(Graphics g)
		{
			//updating
			if(Math.random()>0.25) meteors.add(new int[]{(int) (Math.random()*Main.APPLET_WIDTH), 0});
			
			//drawing
			g.setColor(meteorColor);
			for(int[] meteor: meteors)
			{
				meteor[0] += xSpeed;
				meteor[1] += ySpeed;
				g.fillOval(meteor[0]-meteorRadius, meteor[1]-meteorRadius, meteorRadius*2, meteorRadius*2);
			}
		}
		void destroy()
		{
			for(int i=meteors.size()-1; i>=0; i--)
				meteors.remove(i);
		}
	}
	
	public static class RainEffect extends MeteorShowerEffect
	{
		double pos = Main.APPLET_HEIGHT;
		public static final double rainSpeed = 0.05;
		private long et;
		public RainEffect(long st, long et, int radius, Color col)
		{
			super(st, et, radius, col);
		}
		
		public boolean shouldHaveFilter(){return false;}
		void draw(Graphics g)
		{
			super.draw(g);
			g.fillRect(0,(int)pos, Main.APPLET_WIDTH, Main.APPLET_HEIGHT-(int)pos);
			pos-=rainSpeed;
			if(isInMe(Level.timeElapsedMillis())){destroy();}
			return;
		}
		void destroy()
		{
			return;
		}
	}
	
	//This effect may or may not result in serious health and eye problems.
	//Please contact your doctor before playing this game.
	public static class SeizureEffect extends BackgroundEffectsRegion
	{
		private static Color[] colors = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW};
		public SeizureEffect(long st, long et) {
			super(st, et);
			// TODO Auto-generated constructor stub
		}
		void draw(Graphics g)
		{
			g.setColor(colors[(int)(Math.random()*colors.length)]);
			g.fillRect(0, 0, Main.APPLET_WIDTH, Main.APPLET_HEIGHT);//Background
			
			//Circles within
			for(int xPos = 0; xPos <= Main.APPLET_WIDTH-50; xPos += 50)
			{
				g.setColor(colors[(int)(Math.random()*colors.length)]);
				//more colors to potentially harm you and your family ;)
				for(int yPos = 0; yPos <= Main.APPLET_HEIGHT - 50; yPos += 50)
				{
					g.fillOval(xPos, yPos, 50, 50);
				}
			}
			
			return;
		}
		void destroy()
		{
			return;
		}
		@Override
		public boolean shouldHaveFilter() {
			// TODO Auto-generated method stub
			return true;
		}
	}
}

