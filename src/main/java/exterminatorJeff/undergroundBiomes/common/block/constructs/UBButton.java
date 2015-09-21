package exterminatorJeff.undergroundBiomes.common.block.constructs;

import java.util.Random;

import exterminatorJeff.undergroundBiomes.api.ButtonEntry;
import exterminatorJeff.undergroundBiomes.api.Registrable;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

// TODO Add the possibility to deactivate cobble and/or sedimentary button

/**
 * 1 instance -> 8 variants + powered -> 16 metadatas<br/>
 * 6 facings<br/>
 * 3 stone types<br/>
 * 2 stone styles (no brick button) except for sedimentary<br/>
 * Total : 30 instances (5 subclasses)
 * 
 * @author Spooky4672
 *
 */
public abstract class UBButton extends Block implements OrientedBlock, Registrable {

	public static final PropertyBool POWERED = PropertyBool.create("powered");

	/*
	 * 
	 */

	private final ButtonEntry entry;
	private final EnumFacing facing;

	@Override
	public EnumFacing getFacing() {
		return facing;
	}

	protected abstract UBStone baseStone();

	/**
	 * Example : "igneous_cobble_button_north"
	 */
	private final String name;

	public UBButton(ButtonEntry entry, EnumFacing facing) {
		super(Material.rock);
		this.entry = entry;
		this.facing = facing;

		setDefaultState(blockState.getBaseState().withProperty(POWERED, Boolean.FALSE).withProperty(baseStone().getVariantProperty(), baseStone().getVariantEnum()[0]));
		setTickRandomly(true);

		name = entry.internal(facing);
		setUnlocalizedName(name);
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { POWERED, baseStone().getVariantProperty() });
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = getDefaultState();
		if (meta >= 8)
			state = state.withProperty(POWERED, Boolean.TRUE);
		return state.withProperty(baseStone().getVariantProperty(), baseStone().getVariantEnum()[meta & 7]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = baseStone().getMetaFromState(state);
		if (((Boolean) state.getValue(POWERED)).booleanValue())
			i |= 8;
		return i;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return entry.getAssociatedItem();
	}

	@Override
	public int damageDropped(IBlockState state) {
		// There is no powered button item
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
		// No need
	}

	/*
	 * Copied from BlockButton class
	 */

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
		return null;
	}

	@Override
	public int tickRate(World worldIn) {
		return 20;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
		// The item choose the correct orientation
		assert side == facing;
		return true;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		UBButton button = (UBButton) worldIn.getBlockState(pos).getBlock();
		return worldIn.isSideSolid(pos.offset(button.getFacing().getOpposite()), button.getFacing(), true);
	}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		if (checkForDrop(worldIn, pos, state)) {
			if (!worldIn.isSideSolid(pos.offset(facing.getOpposite()), facing, true)) {
				this.dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
			}
		}
	}

	private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
		if (!this.canPlaceBlockAt(worldIn, pos)) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		this.updateBlockBounds(worldIn.getBlockState(pos));
	}

	@SuppressWarnings("unused")
	private void updateBlockBounds(IBlockState state) {
		boolean flag = ((Boolean) state.getValue(POWERED)).booleanValue();
		float f = 0.25F;
		float f1 = 0.375F;
		float f2 = (flag ? 1 : 2) / 16.0F;
		float f3 = 0.125F;
		float f4 = 0.1875F;

		switch (facing) {
		case EAST:
			setBlockBounds(0.0F, 0.375F, 0.3125F, f2, 0.625F, 0.6875F);
			break;
		case WEST:
			setBlockBounds(1.0F - f2, 0.375F, 0.3125F, 1.0F, 0.625F, 0.6875F);
			break;
		case SOUTH:
			setBlockBounds(0.3125F, 0.375F, 0.0F, 0.6875F, 0.625F, f2);
			break;
		case NORTH:
			setBlockBounds(0.3125F, 0.375F, 1.0F - f2, 0.6875F, 0.625F, 1.0F);
			break;
		case UP:
			setBlockBounds(0.3125F, 0.0F, 0.375F, 0.6875F, 0.0F + f2, 0.625F);
			break;
		case DOWN:
			setBlockBounds(0.3125F, 1.0F - f2, 0.375F, 0.6875F, 1.0F, 0.625F);
			break;
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		boolean alreadyActivated = ((Boolean) state.getValue(POWERED)).booleanValue();
		if (!alreadyActivated) {
			worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.TRUE), 3);
			worldIn.markBlockRangeForRenderUpdate(pos, pos);
			worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
			notifyNeighbors(worldIn, pos, facing);
			worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
		}
		return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (((Boolean) state.getValue(POWERED)).booleanValue()) {
			notifyNeighbors(worldIn, pos, facing);
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
		return ((Boolean) state.getValue(POWERED)).booleanValue() ? 15 : 0;
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
		return !((Boolean) state.getValue(POWERED)).booleanValue() ? 0 : (facing == side ? 15 : 0);
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
		// Do nothing
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			if (((Boolean) state.getValue(POWERED)).booleanValue()) {
				worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.FALSE));
				notifyNeighbors(worldIn, pos, facing);
				worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
				worldIn.markBlockRangeForRenderUpdate(pos, pos);
			}
		}
	}

	@Override
	public void setBlockBoundsForItemRender() {
		float f = 0.1875F;
		float f1 = 0.125F;
		float f2 = 0.125F;
		setBlockBounds(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
	}

	private void notifyNeighbors(World worldIn, BlockPos pos, EnumFacing facing) {
		worldIn.notifyNeighborsOfStateChange(pos, this);
		worldIn.notifyNeighborsOfStateChange(pos.offset(facing.getOpposite()), this);
	}

}
