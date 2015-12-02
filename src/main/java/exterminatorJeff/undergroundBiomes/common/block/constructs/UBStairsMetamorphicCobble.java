package exterminatorJeff.undergroundBiomes.common.block.constructs;

import exterminatorJeff.undergroundBiomes.api.StairsEntry;
import exterminatorJeff.undergroundBiomes.api.UBEntries;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;
import net.minecraft.util.EnumFacing;

public class UBStairsMetamorphicCobble extends UBStairs {

	public UBStairsMetamorphicCobble(StairsEntry entry, EnumFacing facing) {
		super(entry, facing);
	}

	@Override
	protected UBStone baseStone() {
		return (UBStone) UBEntries.METAMORPHIC_COBBLE.getAssociatedBlock();
	}

}
