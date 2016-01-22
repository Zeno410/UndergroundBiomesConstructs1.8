package exterminatorJeff.undergroundBiomes.common.block.constructs;

import exterminatorJeff.undergroundBiomes.api.StairsEntry;
import exterminatorJeff.undergroundBiomes.api.UBEntries;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;
import net.minecraft.util.EnumFacing;

public class UBStairsIgneousBrick extends UBStairs {

	public UBStairsIgneousBrick(StairsEntry entry, EnumFacing facing) {
		super(entry, facing);
	}

	@Override
	protected UBStone baseStone() {
		return (UBStone) UBEntries.IGNEOUS_BRICK.getAssociatedBlock();
	}

}
