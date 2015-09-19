package exterminatorJeff.undergroundBiomes.world;

import java.util.Map;

import net.minecraft.world.chunk.Chunk;

public interface UBStoneReplacer {

	final Map<Integer, UBBiome> biomeList = UndergroundBiomeSet.getInstance().biomeList;
	final int NB_BIOMES = UndergroundBiomeSet.getInstance().biomeList.size();

	final int[][] biomeValues = new int[16][16];

	public void replaceStoneInChunk(Chunk chunk);

	/**
	 * @param value
	 * @param n
	 *            : the number of parts
	 * @param min
	 * @param max
	 * @return : Return 0 <-> (n-1)
	 */
	default int getRoundedValueInNParts(final double value, final int n, final double min, final double max) {
		int ret = -1;
		double range = max - min;
		double gap = range / n;
		double[] bounds = new double[n - 1];
		// {b0,b1...b(n-1)}
		for (int i = 1; i <= bounds.length; i++) {
			bounds[i - 1] = min + i * gap;
		}
		//
		if (value >= min && value < bounds[0])
			ret = 0;
		for (int i = 0; i <= n - 3; i++) {
			if (value >= bounds[i] && value < bounds[i + 1])
				ret = i + 1;
		}
		if (value >= bounds[bounds.length - 1] && value <= max)
			ret = n - 1;
		// Error
		if (ret == -1) {
			String msg = String.format("ret == -1 for value : %f | min: %f ; max: %f | %d parts | bounds: {", value, min, max, n);
			for (int i = 0; i < bounds.length - 1; i++) {
				msg += bounds[i] + ", ";
			}
			msg += bounds[bounds.length - 1] + "}";
			throw new RuntimeException(msg);
		}
		return ret;
	}

}
