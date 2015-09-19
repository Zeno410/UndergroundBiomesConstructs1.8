package exterminatorJeff.undergroundBiomes.common.block.constructs;

import net.minecraft.util.EnumFacing;
import exterminatorJeff.undergroundBiomes.api.ButtonEntry;
import exterminatorJeff.undergroundBiomes.api.UBEntries;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;

public class UBButtonIgneous extends UBButton {

	public UBButtonIgneous(ButtonEntry entry, EnumFacing facing) {
		super(entry, facing);
	}

	@Override
	protected UBStone baseStone() {
		return (UBStone) UBEntries.IGNEOUS_STONE.getAssociatedBlock();
	}

}
