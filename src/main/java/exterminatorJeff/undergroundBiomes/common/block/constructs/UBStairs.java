package exterminatorJeff.undergroundBiomes.common.block.constructs;

import java.util.List;

import exterminatorJeff.undergroundBiomes.api.Variable;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;

/**
 * 1 instance -> 8 variants + half -> 16 metadatas<br/>
 * 4 facings<br/>
 * 3 stone types<br/>
 * 3 stone style except for sedimentary<br/>
 * Total : 28 instances (7 subclasses)
 * 
 * @author Spooky4672
 *
 */
public abstract class UBStairs extends BlockStairs implements SidedBlock, Variable {

	private final EnumFacing facing;

	@Override
	public EnumFacing getSide() {
		return facing;
	}

	protected abstract UBStone baseStone();

	public UBStairs(IBlockState modelState, EnumFacing facing) {
		super(modelState);
		this.facing = facing;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		// TODO Auto-generated method stub
		super.getSubBlocks(itemIn, tab, list);
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { FACING, HALF, SHAPE, baseStone().getVariantProperty() });
	}

	@Override
	public void register() {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerModel() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getVariantName(int meta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getModelName(int meta) {
		// TODO Auto-generated method stub
		return null;
	}

}
