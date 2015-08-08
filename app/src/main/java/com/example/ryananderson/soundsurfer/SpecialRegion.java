//a class filled with special regions that make the game easier or harder, but definitely more fun to play
abstract public class SpecialRegion {

	public final long START_TIME_MILLIS, END_TIME_MILLIS;

	public SpecialRegion(int start, int end)
	{
		START_TIME_MILLIS = start;
		END_TIME_MILLIS = end;
	}

	public boolean isInMyRange(long time)
	{
		if(time >= START_TIME_MILLIS && time <= END_TIME_MILLIS) return true;
		return false;
	}

	public double gravity(){return Player.DEFAULT_GRAVITY;}
	public double jumpStrength(){return Player.DEFAULT_JUMP_STRENGTH;}
	public double speed(){return WaveManager.DEFAULT_X_SPEED;}
	public double amplitudeMultiplier(){return WaveManager.DEFAULT_AMPLITUDE_MULTIPLIER;};
	public int playerRadius(){return Player.DEFAULT_RADIUS;}
	public int jumpScoreIncrease(){return Player.DEFAULT_JUMP_SCORE_INCREASE;}
	public int missScoreDecrease(){return Player.DEFAULT_MISS_SCORE_DECREASE;}


	public static class EnhancedGravityRegion extends SpecialRegion
	{
		double gravity;
		public EnhancedGravityRegion(int start, int end, double gravMultiplier) {
			super(start, end);
			gravity = gravMultiplier;
		}
		public double gravity() {return Player.DEFAULT_GRAVITY * gravity;}
	}


	public static class EnhancedSpeedRegion extends SpecialRegion
	{
		double speed;
		public EnhancedSpeedRegion(int start, int end, double speedMultiplier) {
			super(start, end);
			speed = speedMultiplier;
		}
		public double speed() {return WaveManager.DEFAULT_X_SPEED * speed;}
	}

	
	// 3 times the amplitude multiplier. Half the jump strength and half the gravity. Quickly climb up the wave mountains!
	public static class MountainClimberRegion extends SpecialRegion
	{
		public MountainClimberRegion(int start, int end) {
			super(start, end);
		}
		public double gravity() {return Player.DEFAULT_GRAVITY / 2;}
		public double amplitudeMultiplier() {return WaveManager.DEFAULT_AMPLITUDE_MULTIPLIER * 3;}
		public double jumpStrength() {return Player.DEFAULT_JUMP_STRENGTH/2;}
	}

	// half the xSpeed, half the ball radius, half the gravity, twice the fun!
	public static class FeatherRegion extends SpecialRegion
	{
		public FeatherRegion(int start, int end) {
			super(start, end);
		}
		public int playerRadius() {return Player.DEFAULT_RADIUS/2;}
		public double speed() {return WaveManager.DEFAULT_X_SPEED/2;}
		public double gravity() {return Player.DEFAULT_GRAVITY/2;}
		public int jumpScoreIncrease(){return Player.DEFAULT_JUMP_SCORE_INCREASE*4;}
		public int missScoreDecrease(){return Player.DEFAULT_MISS_SCORE_DECREASE/4;}
	}

	//rickroll
	public static class RickRollRegion extends SpecialRegion
	{
		public RickRollRegion(int start, int end) {
			super(start, end);
		}
		public double amplitudeMultiplier() {return WaveManager.DEFAULT_AMPLITUDE_MULTIPLIER * 3;}
		public double jumpStrength() {return Player.DEFAULT_JUMP_STRENGTH / 2;}
	}
}
