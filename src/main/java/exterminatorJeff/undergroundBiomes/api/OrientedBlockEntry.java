package exterminatorJeff.undergroundBiomes.api;

import exterminatorJeff.undergroundBiomes.common.block.constructs.OrientedBlockGroup;
import exterminatorJeff.undergroundBiomes.common.block.constructs.OrientedBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;

public abstract class OrientedBlockEntry extends BlockEntry {

	public OrientedBlockEntry(String internalName) {
		super(internalName);
	}

	/**
	 * Example : igneous_stone_button_north, metamorphic_cobble_stairs_south
	 * 
	 * @param facing
	 * @return
	 */
	public String internal(EnumFacing facing) {
		return internal() + "_" + facing;
	}

	@Override
	public Item getAssociatedItem() {
		return ((OrientedBlockGroup) obj).getItem();
	}

	@Override
	public Block getAssociatedBlock() {
		return ((OrientedBlockGroup) obj).getBlock(OrientedBlock.INVENTORY_FACING);
	}

	public Block getBlock(EnumFacing facing) {
		return ((OrientedBlockGroup) obj).getBlock(facing);
	}

}
