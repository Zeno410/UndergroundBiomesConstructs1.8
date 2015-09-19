package exterminatorJeff.undergroundBiomes.api;

import exterminatorJeff.undergroundBiomes.common.block.UBStone;
import exterminatorJeff.undergroundBiomes.common.block.constructs.SidedBlock;
import exterminatorJeff.undergroundBiomes.common.block.constructs.UBButtonGroup;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;

public class ButtonEntry extends BlockEntry {

	/**
	 * 
	 * @param reference
	 *            : The {@link UBStone} reference
	 */
	public ButtonEntry(UBEntry reference) {
		super(reference.internal() + "_button");
	}

	/**
	 * Example : igneous_stone_button_north
	 * 
	 * @param facing
	 * @return
	 */
	public String internal(EnumFacing facing) {
		return internal() + "_" + facing;
	}

	@Override
	public Item getAssociatedItem() {
		return ((UBButtonGroup) obj).getButtonItem();
	}

	@Override
	public Block getAssociatedBlock() {
		return ((UBButtonGroup) obj).get(SidedBlock.INVENTORY_FACING);
	}

	public Block getBlock(EnumFacing facing) {
		return ((UBButtonGroup) obj).get(facing);
	}

}
