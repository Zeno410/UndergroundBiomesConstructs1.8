package exterminatorJeff.undergroundBiomes.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class BlockEntry extends UBEntry {

	public BlockEntry(String internalName) {
		super(internalName);
	}

	@Override
	public Item getAssociatedItem() {
		return Item.getItemFromBlock((Block) obj);
	}

	public Block getAssociatedBlock() {
		return (Block) obj;
	}

}
