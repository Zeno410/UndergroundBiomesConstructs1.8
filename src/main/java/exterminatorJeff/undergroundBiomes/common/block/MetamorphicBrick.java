package exterminatorJeff.undergroundBiomes.common.block;

import exterminatorJeff.undergroundBiomes.api.UBEntries;

public class MetamorphicBrick extends MetamorphicStone {

	public MetamorphicBrick() {
		super(UBEntries.METAMORPHIC_BRICK);
		this.replaceableByOre = false;
	}

	@Override
	public UBStoneType getStoneType() {
		return UBStoneType.METAMORPHIC_BRICK;
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
