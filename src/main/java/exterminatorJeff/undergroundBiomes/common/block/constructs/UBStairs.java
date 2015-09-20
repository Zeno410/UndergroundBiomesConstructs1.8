package exterminatorJeff.undergroundBiomes.common.block.constructs;

import java.util.Random;

import exterminatorJeff.undergroundBiomes.api.UBEntry;
import exterminatorJeff.undergroundBiomes.api.Variable;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStairs.EnumHalf;
import net.minecraft.block.BlockStairs.EnumShape;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
public abstract class UBStairs extends Block implements SidedBlock, Variable {

	private final UBEntry entry;
	private final EnumFacing facing;

	@Override
	public EnumFacing getSide() {
		return facing;
	}

	protected abstract UBStone baseStone();

	/**
	 * Example : "igneous_cobble_stairs_north"
	 */
	private final String name;

	public UBStairs(UBEntry entry, EnumFacing facing) {
		super(Material.rock);
		this.entry = entry;
		this.facing = facing;

		setDefaultState(blockState.getBaseState().withProperty(BlockStairs.HALF, EnumHalf.BOTTOM).withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT).withProperty(baseStone().getVariantProperty(),
				baseStone().getVariantEnum()[0]));

		name = entry.internal() + "_" + facing;
		setUnlocalizedName(name);
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { BlockStairs.HALF, BlockStairs.SHAPE, baseStone().getVariantProperty() });
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = getDefaultState();
		if (meta >= 8)
			state = state.withProperty(BlockStairs.HALF, EnumHalf.TOP);
		return state.withProperty(baseStone().getVariantProperty(), baseStone().getVariantEnum()[meta & 7]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = baseStone().getMetaFromState(state);
		if (state.getValue(BlockStairs.HALF) == EnumHalf.TOP)
			i |= 8;
		return i;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return entry.getAssociatedItem();
	}

	@Override
	public int damageDropped(IBlockState state) {
		// There is no reverse stairs item
		return getMetaFromState(state) & 7;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		return new ItemStack(entry.getAssociatedItem(), 1, state.getBlock().getMetaFromState(state));
	}

	/*
	 * 
	 */

	@Override
	public void register() {
		GameRegistry.registerBlock(this, name);
	}

	@Override
	public void registerModel() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getVariantName(int meta) {
		return baseStone().getVariantName(meta & 7) + "_" + baseStone().getStoneStyle();
	}

	@Override
	public String getModelName(int meta) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Copied from BlockStairs class
	 */
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

}
