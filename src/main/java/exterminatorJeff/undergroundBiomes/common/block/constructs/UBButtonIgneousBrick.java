package exterminatorJeff.undergroundBiomes.common.block.constructs;

import exterminatorJeff.undergroundBiomes.api.ButtonEntry;
import exterminatorJeff.undergroundBiomes.api.UBEntries;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;
import net.minecraft.util.EnumFacing;

public class UBButtonIgneousBrick extends UBButtonIgneous {

	public UBButtonIgneousBrick(ButtonEntry entry, EnumFacing facing) {
		super(entry, facing);
	}

	@Override
	protected UBStone baseStone() {
		return (UBStone) UBEntries.IGNEOUS_BRICK.getAssociatedBlock();
	}

}
