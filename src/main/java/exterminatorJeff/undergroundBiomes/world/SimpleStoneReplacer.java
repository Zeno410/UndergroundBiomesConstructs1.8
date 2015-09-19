package exterminatorJeff.undergroundBiomes.world;

import exterminatorJeff.undergroundBiomes.common.UBConfig;
import exterminatorJeff.undergroundBiomes.common.UBOresMaker;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class SimpleStoneReplacer implements UBStoneReplacer {

	private final UBWorldGenManager manager;

	public SimpleStoneReplacer(UBWorldGenManager manager) {
		this.manager = manager;
	}

	@Override
	public void replaceStoneInChunk(Chunk chunk) {
		/*
		 * Voronoi biomes map for this chunk
		 */
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				double v = manager.noiseGenerator.noise(manager.xPos + x, manager.zPos + z, 2, 0.05D, 10000.0D, true);
				int variation = (int) (v * 8);
				double value = manager.voronoi.getValue(manager.xPos + x + variation, manager.zPos + z + variation, 0);
				biomeValues[x][z] = getRoundedValueInNParts(value, NB_BIOMES, -1.0D, 1.0D);
			}
		}
		// For each storage array
		for (ExtendedBlockStorage storage : chunk.getBlockStorageArray()) {
			if (storage != null && !storage.isEmpty()) {
				int yPos = storage.getYLocation();
				if (yPos >= UBConfig.generateHeight())
					return;
				//
				for (int x = 0; x < 16; ++x) {
					for (int z = 0; z < 16; ++z) {
						// Get the underground biome for the position
						UBBiome currentBiome = biomeList.get(biomeValues[x][z]);
						//
						for (int y = 0; y < 16; ++y) {
							Block currentBlock = storage.get(x, y, z).getBlock();
							/*
							 * Skip air, water and UBStone
							 */
							if (Block.isEqualTo(Blocks.air, currentBlock))
								continue;
							if (Block.isEqualTo(Blocks.water, currentBlock))
								continue;
							// TODO Test without
							if (currentBlock instanceof UBStone)
								continue;
							// Perlin noise for strata layers height variation
							int variation = (int) (manager.noiseGenerator.noise((manager.xPos + x) / 55.533, (manager.zPos + z) / 55.533, 3, 1, 0.5) * 10 - 5);
							/*
							 * Stone
							 */
							if (currentBlock == Blocks.stone) {
								// Replace with UBified version
								storage.set(x, y, z, currentBiome.getStrataBlockAtLayer(yPos + y + variation));
							} else {
								/*
								 * Ore
								 */
								if (UBOresMaker.isUBified(currentBlock)) {
									IBlockState strata = currentBiome.getStrataBlockAtLayer(yPos + y + variation);
									if (strata.getBlock() instanceof UBStone) {
										UBStone stone = ((UBStone) strata.getBlock());
										IBlockState ore = stone.getUBifiedOre(currentBlock, stone.getMetaFromState(strata));
										storage.set(x, y, z, ore);
									}
								}
							}
						}
					}
				}
			}
		}
	}

}
