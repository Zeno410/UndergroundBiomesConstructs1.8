package exterminatorJeff.undergroundBiomes.common.block.constructs;

import exterminatorJeff.undergroundBiomes.api.BlockEntry;
import exterminatorJeff.undergroundBiomes.api.UBEntries;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;

public class UBWallMetamorphicCobble extends UBWall {

	public UBWallMetamorphicCobble(BlockEntry entry) {
		super(entry);
	}

	@Override
	protected UBStone baseStone() {
		return (UBStone) UBEntries.METAMORPHIC_COBBLE.getAssociatedBlock();
	}

}
