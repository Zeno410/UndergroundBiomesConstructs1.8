package exterminatorJeff.undergroundBiomes.api;

import net.minecraft.item.Item;

public class ItemEntry extends UBEntry {

	public ItemEntry(String internalName) {
		super(internalName);
	}

	@Override
	public Item getAssociatedItem() {
		return (Item) obj;
	}

}
