package exterminatorJeff.undergroundBiomes.common.block.constructs;

import net.minecraft.util.EnumFacing;

/**
 * A block that implements {@link OrientedBlock} has one instance per facing<br/>
 * 
 * @author Spooky4672
 *
 */
public interface OrientedBlock {

	/**
	 * Facing for the inventory item
	 */
	public static final EnumFacing INVENTORY_FACING = EnumFacing.NORTH;

	/**
	 * 
	 * @return
	 */
	public EnumFacing getFacing();

}
