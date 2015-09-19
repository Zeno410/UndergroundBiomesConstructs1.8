package exterminatorJeff.undergroundBiomes.common.block.constructs;

import java.util.HashMap;
import java.util.Map;

import exterminatorJeff.undergroundBiomes.api.ButtonEntry;
import exterminatorJeff.undergroundBiomes.api.Registrable;
import exterminatorJeff.undergroundBiomes.common.block.constructs.UBButton.UBButtonItem;
import net.minecraft.util.EnumFacing;

/**
 * Regroup the 6 instances of a same button
 * 
 * @author Spooky4672
 *
 */
public class UBButtonGroup implements Registrable {

	private UBButtonItem buttonItem;

	public UBButtonItem getButtonItem() {
		return buttonItem;
	}

	private final Map<EnumFacing, UBButton> instances = new HashMap<EnumFacing, UBButton>(EnumFacing.values().length);

	private final ButtonEntry entry;

	public UBButtonGroup(ButtonEntry entry, UBButton... buttons) {
		this.entry = entry;
		for (UBButton button : buttons) {
			instances.put(button.getSide(), button);
		}
	}

	@Override
	public void register() {
		// Register the blocks
		instances.values().forEach((button) -> button.register());
		// Register one item for all 6 facing
		buttonItem = new UBButtonItem(((UBButton) entry.getAssociatedBlock()).baseStone(), entry);
		buttonItem.register();
	}

	@Override
	public void registerModel() {
		instances.values().forEach((button) -> button.registerModel());
		buttonItem.registerModel();
	}

	public UBButton get(EnumFacing facing) {
		return instances.get(facing);
	}

}
