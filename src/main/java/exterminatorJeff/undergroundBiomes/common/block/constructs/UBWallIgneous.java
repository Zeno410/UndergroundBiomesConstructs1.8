package exterminatorJeff.undergroundBiomes.common.block.constructs;

import exterminatorJeff.undergroundBiomes.api.BlockEntry;
import exterminatorJeff.undergroundBiomes.api.UBEntries;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;

public class UBWallIgneous extends UBWall {

	public UBWallIgneous(BlockEntry entry) {
		super(entry);
	}

	@Override
	protected UBStone baseStone() {
		return (UBStone) UBEntries.IGNEOUS_COBBLE.getAssociatedBlock();
	}

}
