package exterminatorJeff.undergroundBiomes.common.block.constructs;

import exterminatorJeff.undergroundBiomes.api.BlockEntry;
import exterminatorJeff.undergroundBiomes.api.UBEntries;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;

public class UBWallSedimentary extends UBWall {

	public UBWallSedimentary(BlockEntry entry) {
		super(entry);
	}

	@Override
	protected UBStone baseStone() {
		return (UBStone) UBEntries.SEDIMENTARY_STONE.getAssociatedBlock();
	}

}
