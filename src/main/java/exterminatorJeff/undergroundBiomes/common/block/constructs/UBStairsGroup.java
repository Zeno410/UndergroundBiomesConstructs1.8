package exterminatorJeff.undergroundBiomes.common.block.constructs;

import exterminatorJeff.undergroundBiomes.api.StairsEntry;
import exterminatorJeff.undergroundBiomes.common.item.construct.UBStairsItem;

/**
 * Regroup the 4 instances of the same stairs
 * 
 * @author Spooky4672
 *
 */
public class UBStairsGroup extends OrientedBlockGroup {

	public UBStairsGroup(StairsEntry entry, UBStairs... stairs) {
		super(entry, stairs);
	}

	@Override
	public void register() {
		// Register the blocks
		instances.values().forEach((stairs) -> ((UBStairs) stairs).register());
		// Register one item for all 4 facing
		itemBlock = new UBStairsItem(((UBStairs) entry.getAssociatedBlock()).baseStone(), (StairsEntry) entry);
		itemBlock.register();
	}

}
