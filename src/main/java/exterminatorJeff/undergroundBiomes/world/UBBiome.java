package exterminatorJeff.undergroundBiomes.world;

import net.minecraft.block.state.IBlockState;
import exterminatorJeff.undergroundBiomes.api.BlockEntry;
import exterminatorJeff.undergroundBiomes.world.noise.PerlinNoiseGenerator;

public class UBBiome {

	public final String name;
	public final IBlockState fillerBlock;
	public final PerlinNoiseGenerator strataNoise;

	public boolean hasStrata = false;
	public StrataLayer[] strata;

	protected UBBiome(String name, BlockEntry filler, int metadata) {
		this.name = name;
		fillerBlock = filler.getAssociatedBlock().getStateFromMeta(metadata);
		strataNoise = new PerlinNoiseGenerator(1);
	}

	protected UBBiome addStrataLayers(StrataLayer[] strata) {
		hasStrata = true;
		this.strata = strata;
		return this;
	}

	public IBlockState getStrataBlockAtLayer(int y) {
		for (int i = 0; i < strata.length; i++) {
			if (strata[i].valueIsInLayer(y)) {
				return strata[i].layerBlockState;
			}
		}
		return fillerBlock;
	}

}
