package exterminatorJeff.undergroundBiomes.common.item.construct;

import exterminatorJeff.undergroundBiomes.api.StairsEntry;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;

public class UBStairsItem extends OrientedItemBlock {

	public UBStairsItem(UBStone baseStone, StairsEntry entry) {
		super(baseStone, entry);
	}

	@Override
	public String getModelName(int meta) {
		return UndergroundBiomes.MODID + ":" + getVariantName(meta) + "_stairs";
	}

}
