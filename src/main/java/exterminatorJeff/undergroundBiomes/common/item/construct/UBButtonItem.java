package exterminatorJeff.undergroundBiomes.common.item.construct;

import exterminatorJeff.undergroundBiomes.api.ButtonEntry;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;

public class UBButtonItem extends OrientedItemBlock {

	public UBButtonItem(UBStone baseStone, ButtonEntry entry) {
		super(baseStone, entry);
	}

	@Override
	public String getModelName(int meta) {
		return UndergroundBiomes.MODID + ":" + getVariantName(meta) + "_button";
	}

}
