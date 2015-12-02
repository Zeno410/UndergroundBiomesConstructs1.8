package exterminatorJeff.undergroundBiomes.common.item.construct;

import exterminatorJeff.undergroundBiomes.api.StairsEntry;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;

public class UBStairsItem extends OrientedItemBlock {

	public UBStairsItem(UBStone baseStone, StairsEntry entry) {
		super(baseStone, entry);
	}

	@Override
	public String getModelName(int meta) {
		return UndergroundBiomes.MODID + ":" + getVariantName(meta) + "_stairs";
	}

	@Override
	protected Block getBlockToPlace(EntityPlayer player, EnumFacing side) {
		// Stairs do not have up and down facing
		return entry.getBlock(player.getHorizontalFacing());
	}

}
