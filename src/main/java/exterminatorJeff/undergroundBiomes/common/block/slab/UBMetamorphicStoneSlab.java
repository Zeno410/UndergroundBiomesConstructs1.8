package exterminatorJeff.undergroundBiomes.common.block.slab;

import exterminatorJeff.undergroundBiomes.api.SlabEntry;
import exterminatorJeff.undergroundBiomes.api.UBEntries;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;

public abstract class UBMetamorphicStoneSlab extends UBStoneSlab {

	public UBMetamorphicStoneSlab(SlabEntry namer) {
		super(namer);
	}

	@Override
	protected UBStone baseStone() {
		return (UBStone) UBEntries.METAMORPHIC_STONE.getAssociatedBlock();
	}

}
