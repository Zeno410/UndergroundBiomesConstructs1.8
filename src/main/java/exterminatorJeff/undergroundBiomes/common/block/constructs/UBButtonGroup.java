package exterminatorJeff.undergroundBiomes.common.block.constructs;

import java.util.HashMap;
import java.util.Map;

import exterminatorJeff.undergroundBiomes.api.ButtonEntry;
import exterminatorJeff.undergroundBiomes.common.item.construct.UBButtonItem;
import net.minecraft.util.EnumFacing;

/**
 * Regroup the 6 instances of a same button
 * 
 * @author Spooky4672
 *
 */
public class UBButtonGroup extends OrientedBlockGroup {

	private final Map<EnumFacing, UBButton> instances = new HashMap<EnumFacing, UBButton>(6);

	public UBButtonGroup(ButtonEntry entry, UBButton... buttons) {
		super(entry, buttons);
	}

	@Override
	public void register() {
		// Register the blocks
		instances.values().forEach((button) -> button.register());
		// Register one item for all 6 facing
		itemBlock = new UBButtonItem(((UBButton) entry.getAssociatedBlock()).baseStone(), (ButtonEntry) entry);
		itemBlock.register();
	}

}
