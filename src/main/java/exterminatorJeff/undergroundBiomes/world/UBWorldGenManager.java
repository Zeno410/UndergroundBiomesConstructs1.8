package exterminatorJeff.undergroundBiomes.world;

import exterminatorJeff.undergroundBiomes.common.UBConfig;
import exterminatorJeff.undergroundBiomes.world.noise.PerlinNoiseGenerator;
import exterminatorJeff.undergroundBiomes.world.noise.Voronoi;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class UBWorldGenManager {

	private final UBStoneReplacer replacer;

	/**
	 * Coordinates of the current chunk
	 */
	int xPos, zPos;
	PerlinNoiseGenerator noiseGenerator;
	Voronoi voronoi = new Voronoi();

	private int seed;
	private boolean worldLoaded = false;

	public UBWorldGenManager() {
		// TODO Choose the replacer from config
		replacer = new SimpleStoneReplacer(this);
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		World world = event.world;
		WorldProvider provider = event.world.provider;

		if (provider.getDimensionId() == 0 && !worldLoaded) {
			worldLoaded = true;

			seed = (int) world.getSeed();
			noiseGenerator = new PerlinNoiseGenerator(seed);
			voronoi.setSeed(seed);
			voronoi.setFrequency(0.05D / UBConfig.biomeSize());
		}
	}

	@SubscribeEvent
	public void onPopulateChunkPost(PopulateChunkEvent.Post event) {
		// TODO Check dimensions list in config
		if (event.world.provider.getDimensionId() == 0 && worldLoaded) {
			Chunk chunk = event.world.getChunkFromChunkCoords(event.chunkX, event.chunkZ);

			xPos = chunk.xPosition * 16;
			zPos = chunk.zPosition * 16;

			replacer.replaceStoneInChunk(chunk);
		}
	}

}
