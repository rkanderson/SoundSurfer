import java.awt.Color;
import java.util.ArrayList;


public class ColorTimeline {
	
	private ArrayList<ColorRegion> colorRegions = new ArrayList<ColorRegion>();
	
	public ColorTimeline()
	{
		
	}
	
	public Color getBackgroundColor(long timeMillis)
	{
		for(ColorRegion cr: colorRegions)
		{
			if(cr.isInMyRange(timeMillis)) return cr.getBackgroundColor();
		}
		return Color.WHITE;
	}
	
	public Color getLineColor(long timeMillis)
	{
		for(ColorRegion cr: colorRegions)
		{
			if(cr.isInMyRange(timeMillis)) return cr.getLineColor();
		}
		return Color.BLACK;
	}
	
	public Color getPlayerColor(long timeMillis)
	{
		for(ColorRegion cr: colorRegions)
		{
			if(cr.isInMyRange(timeMillis)) return cr.getPlayerColor();
		}
		return Color.RED;
	}
	
	public void add(ColorRegion cr)
	{
		colorRegions.add(cr);
	}
	
	
	
	public static class ColorRegion
	{
		protected final long START_TIME_MILLIS, END_TIME_MILLIS;
		protected final Color bgColor, lineColor, playerColor;
		
		public ColorRegion(long start, long end, Color bgc, Color lc, Color pc)
		{
			if(start > end) throw new IllegalArgumentException("The start time must be less than the end time");
			START_TIME_MILLIS = start;
			END_TIME_MILLIS = end;
			bgColor = bgc;
			lineColor = lc;
			playerColor = pc;
		}
		
		public Color getBackgroundColor()
		{
			return bgColor;
		}
		
		public Color getLineColor()
		{
			return lineColor;
		}
		
		public Color getPlayerColor()
		{
			return playerColor;
		}
		
		public boolean isInMyRange(long timeMillis)
		{
			if(timeMillis >= START_TIME_MILLIS && timeMillis <= END_TIME_MILLIS) return true;
			return false;
		}
	}
	
	public static class ColorFadeRegion extends ColorRegion
	{
		private Color bgColor2, lineColor2, playerColor2, 
		currentBgColor, currentLineColor, currentPlayerColor;
		private int diffRedb, diffBlueb, diffGreenb;
		private int diffRedl, diffBluel, diffGreenl;
		private int diffRedp, diffBluep, diffGreenp;
		public ColorFadeRegion(long start, long end, Color bgc1, Color bgc2, Color lc1, Color lc2, Color pc1, Color pc2)
		{
			super(start, end, bgc1, lc1, pc1);
			bgColor2 = bgc2;
			lineColor2 = lc2;
			playerColor2 = pc2;
			currentBgColor = bgc1;
			currentLineColor = lc1;
			currentPlayerColor = pc1;
			diffRedb = bgColor2.getRed() - currentBgColor.getRed();
			diffBlueb = bgColor2.getBlue() - currentBgColor.getBlue();
			diffGreenb = bgColor2.getGreen() - currentBgColor.getGreen();
			diffRedl = lineColor2.getRed() - currentLineColor.getRed();
			diffBluel = lineColor2.getBlue() - currentLineColor.getBlue();
			diffGreenl = lineColor2.getGreen() - currentLineColor.getGreen();
			diffRedp = playerColor2.getRed() - currentPlayerColor.getRed();
			diffBluep = playerColor2.getBlue() - currentPlayerColor.getBlue();
			diffGreenp = playerColor2.getGreen() - currentPlayerColor.getGreen();
		}
		
		@Override
		public Color getBackgroundColor()
		{
			long timeElapsed = Level.timeElapsedMillis();
			//<implementation code>
			// Set current color to a color with the intermediate hex value between color's hex value and color2's hex value.
			// It should be based on timeElapsed. Note that color is a protected variable in the parent ColorRegion class.
			double howFar = ((double)(timeElapsed - START_TIME_MILLIS))/(END_TIME_MILLIS - START_TIME_MILLIS);
			currentBgColor = new Color((int)(bgColor.getRed() + diffRedb * howFar), 
					(int)(bgColor.getBlue() + diffBlueb * howFar),
					(int)(bgColor.getGreen() + diffGreenb * howFar));
			return currentBgColor;
		}
		
		@Override
		public Color getLineColor()
		{
			long timeElapsed = Level.timeElapsedMillis();
			//<implementation code>
			// Set current color to a color with the intermediate hex value between color's hex value and color2's hex value.
			// It should be based on timeElapsed. Note that color is a protected variable in the parent ColorRegion class.
			double howFar = ((double)(timeElapsed - START_TIME_MILLIS))/(END_TIME_MILLIS - START_TIME_MILLIS);
			currentLineColor = new Color((int)(lineColor.getRed() + diffRedl * howFar), 
					(int)(lineColor.getBlue() + diffBluel * howFar),
					(int)(lineColor.getGreen() + diffGreenl * howFar));
			return currentLineColor;
		}
		
		@Override
		public Color getPlayerColor()
		{
			long timeElapsed = Level.timeElapsedMillis();
			//<implementation code>
			// Set current color to a color with the intermediate hex value between color's hex value and color2's hex value.
			// It should be based on timeElapsed. Note that color is a protected variable in the parent ColorRegion class.
			double howFar = ((double)(timeElapsed - START_TIME_MILLIS))/(END_TIME_MILLIS - START_TIME_MILLIS);
			currentPlayerColor = new Color((int)(playerColor.getRed() + diffRedp * howFar), 
					(int)(playerColor.getBlue() + diffBluep * howFar),
					(int)(playerColor.getGreen() + diffGreenp * howFar));
			return currentPlayerColor;
		}
	}
	
}
