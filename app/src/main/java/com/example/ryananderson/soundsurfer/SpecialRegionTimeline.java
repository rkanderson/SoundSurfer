import java.util.ArrayList;

//timeline for all SpecialRegions
public class SpecialRegionTimeline {
	private ArrayList<SpecialRegion> regions = new ArrayList<SpecialRegion>();

	public SpecialRegionTimeline()
	{

	}

	public void add(SpecialRegion r)
	{
		regions.add(r);
	}

	public double getGravity(long timeMillis)
	{
		for(SpecialRegion r: regions)
		{
			if(r.isInMyRange(timeMillis)) return r.gravity(); 
		}
		return Player.DEFAULT_GRAVITY;
	}

	public double getJumpStrength(long timeMillis)
	{
		for(SpecialRegion r: regions)
		{
			if(r.isInMyRange(timeMillis)) return r.jumpStrength(); 
		}
		return Player.DEFAULT_JUMP_STRENGTH;
	}

	public int getJumpScoreIncrease(long timeMillis)
	{
		for(SpecialRegion r: regions)
		{
			if(r.isInMyRange(timeMillis)) return r.jumpScoreIncrease(); 
		}
		return Player.DEFAULT_JUMP_SCORE_INCREASE;
	}

	public int getMissScoreDecrease(long timeMillis)
	{
		for(SpecialRegion r: regions)
		{
			if(r.isInMyRange(timeMillis)) return r.missScoreDecrease(); 
		}
		return Player.DEFAULT_MISS_SCORE_DECREASE;
	}

	public double getSpeed(long timeMillis)
	{
		for(SpecialRegion r: regions)
		{
			if(r.isInMyRange(timeMillis)) return r.speed(); 
		}
		return WaveManager.DEFAULT_X_SPEED;
	}

	public double getAmplitudeMultiplier(long timeMillis)
	{
		for(SpecialRegion r: regions)
		{
			if(r.isInMyRange(timeMillis)) return r.amplitudeMultiplier(); 
		}
		return WaveManager.DEFAULT_AMPLITUDE_MULTIPLIER;
	}

	public double getPlayerRadius(long timeMillis)
	{
		for(SpecialRegion r: regions)
		{
			if(r.isInMyRange(timeMillis)) return r.playerRadius(); 
		}
		return Player.DEFAULT_RADIUS;
	}



}
