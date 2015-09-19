package exterminatorJeff.undergroundBiomes.world.noise;

public final class Ints3DNoiseWrapper {

	private final int[][][] noise;;

	public Ints3DNoiseWrapper(int[][][] noiseMap) {
		this.noise = noiseMap;
	}

	public int getNoiseAt(int x, int y, int z) {
		return noise[x][y][z];
	}

	public void setNoiseAt(int x, int y, int z, int value) {
		noise[x][y][z] = value;
	}

}
