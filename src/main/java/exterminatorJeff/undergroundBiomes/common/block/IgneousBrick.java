package exterminatorJeff.undergroundBiomes.common.block;

import exterminatorJeff.undergroundBiomes.api.UBEntries;

public class IgneousBrick extends IgneousStone {

	public IgneousBrick() {
		super(UBEntries.IGNEOUS_BRICK);
		replaceableByOre = false;
	}

	public UBStoneType getStoneType() {
		return UBStoneType.IGNEOUS_BRICK;
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
