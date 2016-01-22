package exterminatorJeff.undergroundBiomes.api;

import exterminatorJeff.undergroundBiomes.common.block.UBStone;

public class WallEntry extends BlockEntry {

	/**
	 * 
	 * @param reference
	 *            : The {@link UBStone} reference
	 */
	public WallEntry(UBEntry reference) {
		super(reference.internal() + "_wall");
	}

}
