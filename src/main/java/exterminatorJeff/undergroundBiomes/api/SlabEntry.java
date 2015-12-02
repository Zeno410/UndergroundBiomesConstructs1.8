package exterminatorJeff.undergroundBiomes.api;

import exterminatorJeff.undergroundBiomes.common.block.UBSlabPair;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class SlabEntry extends BlockEntry {

	public SlabEntry(UBEntry reference) {
		super(reference.internal());
	}

	public String internal(boolean isDouble) {
		return isDouble ? super.internal() + "_fullslab" : super.internal() + "_halfslab";
	}

	public UBSlabPair getSlabPair() {
		return (UBSlabPair) obj;
	}

	public Item getHalf() {
		return Item.getItemFromBlock((Block) ((UBSlabPair) obj).half);
	}

	public Item getFull() {
		return Item.getItemFromBlock((Block) ((UBSlabPair) obj).full);
	}

	@Override
	public Item getAssociatedItem() {
		return getHalf();
	}

	@Override
	public Block getAssociatedBlock() {
		return (Block) ((UBSlabPair) obj).half;
	}

}
