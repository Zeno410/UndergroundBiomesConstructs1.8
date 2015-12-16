package exterminatorJeff.undergroundBiomes.common.block.constructs;

import exterminatorJeff.undergroundBiomes.api.BlockEntry;
import exterminatorJeff.undergroundBiomes.api.UBEntries;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;

public class UBWallMetamorphic extends UBWall {

	public UBWallMetamorphic(BlockEntry entry) {
		super(entry);
	}

	@Override
	protected UBStone baseStone() {
		return (UBStone) UBEntries.METAMORPHIC_STONE.getAssociatedBlock();
	}

}
