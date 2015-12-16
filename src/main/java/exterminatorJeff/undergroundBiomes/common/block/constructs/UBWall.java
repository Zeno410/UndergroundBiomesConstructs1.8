package exterminatorJeff.undergroundBiomes.common.block.constructs;

import java.util.List;

import exterminatorJeff.undergroundBiomes.api.BlockEntry;
import exterminatorJeff.undergroundBiomes.api.Variable;
import exterminatorJeff.undergroundBiomes.common.UBCreativeTab;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * @author Spooky4672
 *
 */
public abstract class UBWall extends Block implements Variable {

	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");

	/*
	 * 
	 */

	private final BlockEntry entry;
	private final String name;

	protected abstract UBStone baseStone();

	public UBWall(BlockEntry entry) {
		super(Material.rock);
		this.entry = entry;

		setCreativeTab(UBCreativeTab.UB_BLOCKS_TAB);
		setDefaultState(blockState.getBaseState().withProperty(baseStone().getVariantProperty(), baseStone().getVariantEnum()[0]).withProperty(EAST, Boolean.FALSE)
				.withProperty(NORTH, Boolean.FALSE).withProperty(SOUTH, Boolean.FALSE).withProperty(UP, Boolean.FALSE).withProperty(WEST, Boolean.FALSE));

		name = this.entry.internal();
		setUnlocalizedName(name);
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { baseStone().getVariantProperty(), EAST, NORTH, SOUTH, UP, WEST });
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = getDefaultState();
		return state.withProperty(baseStone().getVariantProperty(), baseStone().getVariantEnum()[meta & 7]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return baseStone().getMetaFromState(state);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		for (int i = 0; i < baseStone().getNbVariants(); i++) {
			list.add(new ItemStack(itemIn, 1, i));
		}
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	/*
	 * 
	 */

	@Override
	public void register() {
		GameRegistry.registerBlock(this, UBItemWall.class, name);
	}

	@Override
	public void registerModel() {
		for (int i = 0; i < baseStone().getNbVariants(); i++) {
			ModelBakery.addVariantName(entry.getAssociatedItem(), getModelName(i));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(entry.getAssociatedItem(), i, new ModelResourceLocation(getModelName(i), "inventory"));
		}
	}

	/**
	 * 
	 */
	@Override
	public String getVariantName(int meta) {
		return baseStone().getVariantName(meta);
	}

	@Override
	public String getModelName(int meta) {
		return UndergroundBiomes.MODID + ":" + getVariantName(meta) + "_" + baseStone().getStoneStyle() + "_wall";
	}

	/*
	 * Copied from BlockWall class
	 */

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		boolean flag = canConnectTo(worldIn, pos.north());
		boolean flag1 = canConnectTo(worldIn, pos.south());
		boolean flag2 = canConnectTo(worldIn, pos.west());
		boolean flag3 = canConnectTo(worldIn, pos.east());
		float f = 0.25F;
		float f1 = 0.75F;
		float f2 = 0.25F;
		float f3 = 0.75F;
		float f4 = 1.0F;

		if (flag)
			f2 = 0.0F;
		if (flag1)
			f3 = 1.0F;
		if (flag2)
			f = 0.0F;
		if (flag3)
			f1 = 1.0F;
		if (flag && flag1 && !flag2 && !flag3) {
			f4 = 0.8125F;
			f = 0.3125F;
			f1 = 0.6875F;
		} else if (!flag && !flag1 && flag2 && flag3) {
			f4 = 0.8125F;
			f2 = 0.3125F;
			f3 = 0.6875F;
		}

		setBlockBounds(f, 0.0F, f2, f1, f4, f3);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
		setBlockBoundsBasedOnState(worldIn, pos);
		maxY = 1.5D;
		return super.getCollisionBoundingBox(worldIn, pos, state);
	}

	public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos).getBlock();
		if (block instanceof UBWall)
			return true;
		return block == Blocks.barrier ? false
				: (block != this && !(block instanceof BlockFenceGate) ? (block.getMaterial().isOpaque() && block.isFullCube() ? block.getMaterial() != Material.gourd : false)
						: true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
		return side == EnumFacing.DOWN ? super.shouldSideBeRendered(worldIn, pos, side) : true;
	}

	/**
	 * Get the actual Block state of this Block at the given position. This
	 * applies properties not visible in the metadata, such as fence
	 * connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(UP, Boolean.valueOf(!worldIn.isAirBlock(pos.up()))).withProperty(NORTH, Boolean.valueOf(canConnectTo(worldIn, pos.north())))
				.withProperty(EAST, Boolean.valueOf(canConnectTo(worldIn, pos.east()))).withProperty(SOUTH, Boolean.valueOf(canConnectTo(worldIn, pos.south())))
				.withProperty(WEST, Boolean.valueOf(canConnectTo(worldIn, pos.west())));
	}

	/*
	 * 
	 */

	public static class UBItemWall extends ItemBlock {

		public UBItemWall(Block block) {
			super(block);
			if (!(block instanceof UBWall))
				throw new RuntimeException(String.format("The given Block %s is not an instance of UBWall !", block.getUnlocalizedName()));
			setMaxDamage(0);
			setHasSubtypes(true);
		}

		@Override
		public int getMetadata(int meta) {
			return meta;
		}

		@Override
		public String getUnlocalizedName(ItemStack stack) {
			return super.getUnlocalizedName(stack) + "." + ((UBWall) block).getVariantName(stack.getMetadata());
		}

	}

}
