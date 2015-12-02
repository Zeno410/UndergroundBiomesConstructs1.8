package exterminatorJeff.undergroundBiomes.api;

import exterminatorJeff.undergroundBiomes.common.block.UBStone;

public class ButtonEntry extends OrientedBlockEntry {

	/**
	 * 
	 * @param reference
	 *            : The {@link UBStone} reference
	 */
	public ButtonEntry(UBEntry reference) {
		super(reference.internal() + "_button");
	}

}
