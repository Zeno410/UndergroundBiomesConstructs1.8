package exterminatorJeff.undergroundBiomes.common.block;

import exterminatorJeff.undergroundBiomes.api.UBEntries;

public class IgneousBrick extends IgneousStone {

	public IgneousBrick() {
		super(UBEntries.IGNEOUS_BRICK);
		replaceableByOre = false;
	}

	@Override
	public UBStoneStyle getStoneStyle() {
		return UBStoneStyle.BRICK;
	}

	@Override
	public boolean hasRareDrops() {
		return false;
	}

	@Override
	public String getModelName(int meta) {
		return super.getModelName(meta) + "_brick";
	}
}
