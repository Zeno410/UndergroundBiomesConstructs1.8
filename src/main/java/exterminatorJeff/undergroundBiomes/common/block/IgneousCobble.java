package exterminatorJeff.undergroundBiomes.common.block;

import exterminatorJeff.undergroundBiomes.api.UBEntries;
import net.minecraft.block.Block;

public class IgneousCobble extends IgneousStone {

	public IgneousCobble() {
		super(UBEntries.IGNEOUS_COBBLE);
		replaceableByOre = false;
	}

	@Override
	public UBStoneStyle getStoneStyle() {
		return UBStoneStyle.COBBLE;
	}

	@Override
	public boolean hasRareDrops() {
		return false;
	}

	@Override
	public Block setHardness(float hardness) {
		return super.setHardness(hardness * cobbleHardnessModifier);
	}

	@Override
	public String getModelName(int meta) {
		return super.getModelName(meta) + "_cobble";
	}

}
