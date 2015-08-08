import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

//the CEO of all the wave logic
public class WaveManager {

	public static final int x = Main.APPLET_WIDTH;
	public static final int DEFAULT_PIXEL_Y = Main.APPLET_HEIGHT/3*2;
	public static final int HEIGHT_VALUE_ARRAY_SIZE = 300000;
	public static final double DEFAULT_AMPLITUDE_MULTIPLIER = 1;
	private double amplitudeMultiplier  = DEFAULT_AMPLITUDE_MULTIPLIER;

	public static final double DEFAULT_X_SPEED = 10; //24
	private static int xSpeed = (int)DEFAULT_X_SPEED;

	private SoundWavePixel[] pixels = new SoundWavePixel[Main.APPLET_WIDTH];
	private static int[] playingField = new int[Main.APPLET_WIDTH];
	private int[] valueBank;
	private int valueBankIndex = 0;

	public WaveManager(String songPath)
	{
		System.out.println("Called waveManager constructor");
		int[] temp = MusicReader.getWaveModifiedValues(songPath);
		for(int i=0; i<temp.length; i++) 
		{
		//	System.out.println("before = " + temp[i]);
			temp[i] = DEFAULT_PIXEL_Y - temp[i]; //render the all-positive values of valueBank to properly fit the coordinate system
		//	if (i>0 && temp[i]-temp[i-1]>25)
		//	{
			//	System.out.println("after = " + temp[i-1] +" " + temp[i]);	
				//temp[i] = DEFAULT_PIXEL_Y - (temp[i]-temp[i-1])/3;
	//			System.out.println("after = " + temp[i-1] +" " + temp[i]);
		//	}
		}
		valueBank = temp;
		System.out.println("length of W.M. value bank is "+valueBank.length);
		for(int i=0; i<playingField.length; i++) playingField[i] = DEFAULT_PIXEL_Y; //same for playing field
		for(int i=0; i<pixels.length; i++) pixels[i] = new SoundWavePixel(i, DEFAULT_PIXEL_Y); //initialize pixels array to have all pixels at default y
	}

	public static int[] playingField()
	{
		return playingField;
	}
	
	//gettas
	public SoundWavePixel[] getPixels()
	{
		return pixels;
	}

	//settas
	public void setPixelSpeed(int val)
	{
		xSpeed = val;	
	}


	// Update all SoundWavePixels
	// Create new pixels as necessary all at pixelY so that everything is in a single line (add too pixels)
	// Also, remove any pixels outside of frame
	public void update()
	{
		//update all pixels to their corresponding values in playingField. Also, set heights as appropriate.
		for(int i=0; i<pixels.length; i++)
		{
			SoundWavePixel each = pixels[i];
			each.update(playingField[i]);
			//System.out.println("W.M. update executed. Current i = "+i);
			//System.out.println("getY = " + each.getY());
			//set height to appropriate value. Something is wrong with this. //fixed 5/10/15 Anthony
			if(i==0) {
				each.setHeight(Math.abs(pixels[i+1].getY()-each.getY())+SoundWavePixel.DEFAULT_HEIGHT);
			} else if(i == pixels.length-1) {
				each.setHeight(Math.abs(pixels[i-1].getY()-each.getY())+SoundWavePixel.DEFAULT_HEIGHT);
			} else {
				int diffBetweenLeftAndMe = Math.abs(pixels[i-1].getY() - each.getY());
				int diffBetweenRightAndMe = Math.abs(pixels[i+1].getY() - each.getY());
				each.setHeight(Math.max(diffBetweenLeftAndMe, diffBetweenRightAndMe) + SoundWavePixel.DEFAULT_HEIGHT);
			}			

		}

		// Update playingField as necessary with valueBank 
		// First, shift over all the vals already there (based on xSpeed)
		for(int i = 0; i < playingField.length-xSpeed; i+=xSpeed)
		{
			for(int j = i; j < i + xSpeed; j++)
			{
				playingField[j] = playingField[i+xSpeed];
			}
		}

		//Then, insert the new val (again, based on xSpeed)
		//System.out.println("Val bank index: "+valueBankIndex);
		int pixelCreationVal = valueBank[valueBankIndex];
		int standardYCoordinate = -pixelCreationVal + DEFAULT_PIXEL_Y;
		standardYCoordinate*=amplitudeMultiplier;
		pixelCreationVal = DEFAULT_PIXEL_Y - standardYCoordinate;
		valueBankIndex++;
		for(int i = playingField.length-1; i > playingField.length - 1 - xSpeed; i--) 
			playingField[i] = pixelCreationVal;
		
	}

	// Draw all SoundWavePixels, the enlarged pixels on either end, and the progress bar 
	public void draw(Graphics g)
	{
		// Draw S.W. Pixels
		for(SoundWavePixel p: pixels) p.draw(g);

		// Draw enlarged rects on either end
		g.setColor(SoundWavePixel.getColor());
		g.fillRect(0, pixels[0].getY()-20, 10, 40);
		g.fillRect(Main.APPLET_WIDTH-10, pixels[pixels.length-1].getY()-20, 10, 40);

		// Draw progress bar
		g.setColor(Color.DARK_GRAY);
		g.fillRoundRect(0+20, 0+10, Main.APPLET_WIDTH-40, 40, 10, 10); //Outer bar
		g.setColor(Color.CYAN);
		int fullSpan = Main.APPLET_WIDTH-50;
		g.fillRoundRect(0+25, 0+20, (int)(fullSpan*((double)valueBankIndex/valueBank.length)), 20, 10, 10); //Inner bar
		//%
		g.setColor(Color.white);
		g.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		g.drawString((int)(((double)valueBankIndex/valueBank.length*100))+"%", Main.APPLET_WIDTH/2, 35);
		
	}

	public boolean valueBankIsExhausted() {
		if(valueBankIndex==valueBank.length-1) return true;
		return false;
	}

	public void setAmplitudeMultiplier(double am) {
		// TODO Auto-generated method stub
		amplitudeMultiplier = am;
	}

	public static int xSpeed() {
		// TODO Auto-generated method stub
		return xSpeed;
	}
	
	public int getPercent() {
		return (int)(((double)valueBankIndex/valueBank.length*100));
	}

}
