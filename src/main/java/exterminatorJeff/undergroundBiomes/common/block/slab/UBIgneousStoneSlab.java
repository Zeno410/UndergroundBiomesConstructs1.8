package exterminatorJeff.undergroundBiomes.common.block.slab;

import exterminatorJeff.undergroundBiomes.api.SlabEntry;
import exterminatorJeff.undergroundBiomes.api.UBEntries;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;

public abstract class UBIgneousStoneSlab extends UBStoneSlab {

	public UBIgneousStoneSlab(SlabEntry namer) {
		super(namer);
	}

	@Override
	protected UBStone baseStone() {
		return (UBStone) UBEntries.IGNEOUS_STONE.getAssociatedBlock();
	}

}
