package exterminatorJeff.undergroundBiomes.common.block.constructs;

import net.minecraft.util.EnumFacing;
import exterminatorJeff.undergroundBiomes.api.ButtonEntry;
import exterminatorJeff.undergroundBiomes.api.UBEntries;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;

public class UBButtonMetamorphicCobble extends UBButtonMetamorphic {

	public UBButtonMetamorphicCobble(ButtonEntry entry, EnumFacing facing) {
		super(entry, facing);
	}

	@Override
	protected UBStone baseStone() {
		return (UBStone) UBEntries.METAMORPHIC_COBBLE.getAssociatedBlock();
	}

}
