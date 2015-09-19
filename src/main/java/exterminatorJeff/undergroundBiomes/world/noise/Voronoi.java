package exterminatorJeff.undergroundBiomes.world.noise;

/**
 * 
 *
 */
public class Voronoi {

	private static final int X_NOISE_GEN = 1619;
	private static final int Y_NOISE_GEN = 31337;
	private static final int Z_NOISE_GEN = 6971;
	private static final int SEED_NOISE_GEN = 1013;

	private final static double DEFAULT_VORONOI_DISPLACEMENT = 1.0;
	private final static double DEFAULT_VORONOI_FREQUENCY = 1.0;
	private final static int DEFAULT_VORONOI_SEED = 0;

	private static final double SQRT_3 = 1.7320508075688772935;

	/**
	 * Scale of the random displacement to apply to each Voronoi cell.
	 */
	private double displacement;

	/**
	 * Determines if the distance from the nearest seed point is applied to the
	 * output value.
	 */
	private boolean enableDistance;

	/**
	 * Frequency of the seed points.
	 */
	private double frequency;

	/**
	 * Seed value used by the coherent-noise function to determine the positions
	 * of the seed points.
	 */
	private int seed;

	public Voronoi() {
		displacement = DEFAULT_VORONOI_DISPLACEMENT;
		enableDistance = false;
		frequency = DEFAULT_VORONOI_FREQUENCY;
		seed = DEFAULT_VORONOI_SEED;
	}

	public double getValue(double x, double y, double z) {
		// This method could be more efficient by caching the seed values. Fix
		// later.

		x *= frequency;
		y *= frequency;
		z *= frequency;

		int xInt = (x > 0.0 ? (int) x : (int) x - 1);
		int yInt = (y > 0.0 ? (int) y : (int) y - 1);
		int zInt = (z > 0.0 ? (int) z : (int) z - 1);

		double minDist = 2147483647.0;
		double xCandidate = 0;
		double yCandidate = 0;
		double zCandidate = 0;

		// Inside each unit cube, there is a seed point at a random position. Go
		// through each of the nearby cubes until we find a cube with a seed
		// point
		// that is closest to the specified position.
		for (int zCur = zInt - 2; zCur <= zInt + 2; zCur++) {
			for (int yCur = yInt - 2; yCur <= yInt + 2; yCur++) {
				for (int xCur = xInt - 2; xCur <= xInt + 2; xCur++) {
					// Calculate the position and distance to the seed point
					// inside of
					// this unit cube.
					double xPos = xCur + valueNoise3D(xCur, yCur, zCur, seed);
					double yPos = yCur + valueNoise3D(xCur, yCur, zCur, seed + 1);
					double zPos = zCur + valueNoise3D(xCur, yCur, zCur, seed + 2);
					double xDist = xPos - x;
					double yDist = yPos - y;
					double zDist = zPos - z;
					double dist = xDist * xDist + yDist * yDist + zDist * zDist;

					if (dist < minDist) {
						// This seed point is closer to any others found so far,
						// so record
						// this seed point.
						minDist = dist;
						xCandidate = xPos;
						yCandidate = yPos;
						zCandidate = zPos;
					}
				}
			}
		}

		double value;
		if (enableDistance) {
			// Determine the distance to the nearest seed point.
			double xDist = xCandidate - x;
			double yDist = yCandidate - y;
			double zDist = zCandidate - z;
			value = (Math.sqrt(xDist * xDist + yDist * yDist + zDist * zDist)) * SQRT_3 - 1.0;
		} else {
			value = 0.0;
		}

		// Return the calculated distance with the displacement value applied.
		return value + (displacement * (double) valueNoise3D((int) (Math.floor(xCandidate)), (int) (Math.floor(yCandidate)), (int) (Math.floor(zCandidate)), seed));
	}

	private double valueNoise3D(int x, int y, int z, int seed) {
		return 1.0 - ((double) IntValueNoise3D(x, y, z, seed) / 1073741824.0);
	}

	private int IntValueNoise3D(int x, int y, int z, int seed) {
		// All constants are primes and must remain prime in order for this
		// noise
		// function to work correctly.
		int n = (X_NOISE_GEN * x + Y_NOISE_GEN * y + Z_NOISE_GEN * z + SEED_NOISE_GEN * seed) & 0x7fffffff;

		n = (n >> 13) ^ n;

		return (n * (n * n * 60493 + 19990303) + 1376312589) & 0x7fffffff;
	}

	public void enableDistance(boolean enable) {
		enableDistance = enable;
	}

	public double getDisplacement() {
		return displacement;
	}

	public double getFrequency() {
		return frequency;
	}

	public int getSeed() {
		return seed;
	}

	public boolean isDistanceEnabled() {
		return enableDistance;
	}

	public void setDisplacement(double displacement) {
		this.displacement = displacement;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}

}
