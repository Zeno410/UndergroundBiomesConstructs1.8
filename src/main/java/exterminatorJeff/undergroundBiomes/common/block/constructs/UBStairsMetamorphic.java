package exterminatorJeff.undergroundBiomes.common.block.constructs;

import exterminatorJeff.undergroundBiomes.api.StairsEntry;
import exterminatorJeff.undergroundBiomes.api.UBEntries;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;
import net.minecraft.util.EnumFacing;

public class UBStairsMetamorphic extends UBStairs {

	public UBStairsMetamorphic(StairsEntry entry, EnumFacing facing) {
		super(entry, facing);
	}

	@Override
	protected UBStone baseStone() {
		return (UBStone) UBEntries.METAMORPHIC_STONE.getAssociatedBlock();
	}

}
