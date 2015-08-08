//generates wave
public class WaveGenerator {
	
	//uses Lagrange's Interpolation to generate polynomial curve
	private static int[] getCurve1(double x1, double y1, double x2, double y2)
	{
		double x3 = 2*x1-x2;
		double y3 = y2;
		int len = (int)(x2-x1);
		int[] curve = new int[len];
		for(int x=(int)x1; x<len+(int)x1; x++)
		{
			//lagrange formula
			curve[x-(int)x1] = (int)((x-x2)*(x-x3)/(x1-x2)/(x1-x3)*y1 + 
					   (x-x1)*(x-x3)/(x2-x1)/(x2-x3)*y2 + 
					   (x-x1)*(x-x2)/(x3-x1)/(x3-x2)*y3);
		//	System.out.println(x1 + " " + y1 + " " + x2 + " " + y2 + " " + curve[x-(int)x1]);
		}
		return curve;
	}
	
	private static int[] getCurve2(double x1, double y1, double x2, double y2)
	{
		double x3 = 2*x2-x1;
		double y3 = y1;
		int len = (int)(x2-x1+1);
		int[] curve = new int[len];
		for(int x=(int)x1; x<len+(int)x1; x++)
		{
			//lagrange formula
			curve[x-(int)x1] = (int)((x-x2)*(x-x3)/(x1-x2)/(x1-x3)*y1 + 
					   (x-x1)*(x-x3)/(x2-x1)/(x2-x3)*y2 + 
					   (x-x1)*(x-x2)/(x3-x1)/(x3-x2)*y3);
		//	System.out.println(x1 + " " + y1 + " " + x2 + " " + y2 + " " + curve[x-(int)x1]);
		}
		return curve;
	}
	
	public static int[] getCurve(double x1, double y1, double x2, double y2)
	{
		int[] curve = new int[(int)(x2-x1)+1];
		double x3 = (x1+x2)/2;
		double y3 = (y1+y2)/2;
		int[] curve1 = WaveGenerator.getCurve1(x1,y1,x3,y3);
		int[] curve2 = WaveGenerator.getCurve2(x3,y3,x2,y2);
		int c=0;
		for(int i: curve1)
		{
			curve[c] = i;
			c++;
		}
		for(int i: curve2)
		{
			curve[c] = i;
			c++;
		}
		return curve;
	}
	
	public static void main(String[] args)
	{
		double start = 100;
		double end = 500;
		double y1 = 300;
		double y2 = 0;
		int[] curve = WaveGenerator.getCurve(start, y1, end, y2);
		for(int i: curve)
			System.out.println(i);
	}

}
