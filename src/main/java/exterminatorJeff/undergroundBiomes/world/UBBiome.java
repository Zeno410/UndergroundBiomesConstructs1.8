package exterminatorJeff.undergroundBiomes.world;

import java.util.HashMap;

import net.minecraft.block.state.IBlockState;
import exterminatorJeff.undergroundBiomes.api.BlockEntry;
import exterminatorJeff.undergroundBiomes.world.noise.PerlinNoiseGenerator;

public class UBBiome {

	public String biomeName;
	public final int biomeID;
	public boolean hasStrata = false;
	public StrataLayer[] strata;
	public final PerlinNoiseGenerator strataNoise;
	public final IBlockState fillerBlock;

	protected UBBiome(int ID, BlockEntry filler, int metadata, HashMap<Integer, UBBiome> biomeList) {
		this.biomeID = ID;
		this.fillerBlock = filler.getAssociatedBlock().getStateFromMeta(metadata);
		strataNoise = new PerlinNoiseGenerator(1);
		biomeList.put(ID, this);
	}

	protected UBBiome AddStrataLayers(StrataLayer[] strata) {
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

	protected UBBiome setName(String name) {
		this.biomeName = name;
		return this;
	}

}
