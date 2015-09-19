package exterminatorJeff.undergroundBiomes.world.noise;

public final class Ints2DNoiseWrapper {

	private final int[][] noise;

	public Ints2DNoiseWrapper(int[][] noise) {
		this.noise = noise;
	}

	public int getNoiseAt(int x, int z) {
		return noise[x][z];
	}

	public void setNoiseAt(int x, int z, int value) {
		noise[x][z] = value;
	}
}
