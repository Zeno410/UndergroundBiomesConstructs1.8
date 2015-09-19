package exterminatorJeff.undergroundBiomes.common.block.constructs;

import net.minecraft.util.EnumFacing;

/**
 * A block that implements {@link SidedBlock} has one instance per side<br/>
 * 
 * @author Spooky4672
 *
 */
public interface SidedBlock {

	/**
	 * Facing for the inventory item
	 */
	public static final EnumFacing INVENTORY_FACING = EnumFacing.NORTH;

	/**
	 * 
	 * @return
	 */
	public EnumFacing getSide();

}
