package exterminatorJeff.undergroundBiomes.api;

import exterminatorJeff.undergroundBiomes.common.block.UBStone;

public class StairsEntry extends OrientedBlockEntry {

	/**
	 * 
	 * @param reference
	 *            : The {@link UBStone} reference
	 */
	public StairsEntry(UBEntry reference) {
		super(reference.internal() + "_stairs");
	}

}
