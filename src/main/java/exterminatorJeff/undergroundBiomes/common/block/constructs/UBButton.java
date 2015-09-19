package exterminatorJeff.undergroundBiomes.common.block.constructs;

import java.util.List;
import java.util.Random;

import exterminatorJeff.undergroundBiomes.api.ButtonEntry;
import exterminatorJeff.undergroundBiomes.api.Variable;
import exterminatorJeff.undergroundBiomes.common.UBCreativeTab;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
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
public abstract class UBButton extends Block implements SidedBlock, Variable {

	public static final PropertyBool POWERED = PropertyBool.create("powered");

	/*
	 * 
	 */

	private final ButtonEntry entry;

	private final EnumFacing facing;

	@Override
	public EnumFacing getSide() {
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

		setDefaultState(this.blockState.getBaseState().withProperty(POWERED, Boolean.FALSE).withProperty(baseStone().getVariantProperty(), baseStone().getVariantEnum()[0]));
		setTickRandomly(true);

		name = entry.internal() + "_" + facing;

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
		// There is no powered item
		return getMetaFromState(state) & 7;
	}

	/*
	 * 
	 */

	@Override
	public void register() {
		GameRegistry.registerBlock(this, name);
	}

	@Override
	public String getVariantName(int meta) {
		// Metadatas 8-15 have the same name as 0-7
		return baseStone().getVariantName(meta & 7) + "_" + baseStone().getStoneStyle();
	}

	@Override
	public String getModelName(int meta) {
		return UndergroundBiomes.MODID + ":" + getVariantName(meta) + "_button";
	}

	@Override
	public void registerModel() {
		for (int i = 0; i < baseStone().getNbVariants(); i++) {
			ModelBakery.addVariantName(Item.getItemFromBlock(this), getModelName(i));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), i, new ModelResourceLocation(getModelName(i)));
		}
		// StateMapperBase customStateMapper = new StateMapperBase() {
		// @Override
		// protected ModelResourceLocation getModelResourceLocation(IBlockState
		// state) {
		// int meta = getMetaFromState(state);
		// return new ModelResourceLocation(getModelName(meta));
		// }
		// };
		// ModelLoader.setCustomStateMapper(this, customStateMapper);
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
		assert side == facing.getOpposite();
		return worldIn.isSideSolid(pos.offset(side.getOpposite()), side, true);
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
		case DOWN:
			setBlockBounds(0.0F, 0.375F, 0.3125F, f2, 0.625F, 0.6875F);
			break;
		case EAST:
			setBlockBounds(1.0F - f2, 0.375F, 0.3125F, 1.0F, 0.625F, 0.6875F);
			break;
		case NORTH:
			setBlockBounds(0.3125F, 0.375F, 0.0F, 0.6875F, 0.625F, f2);
			break;
		case SOUTH:
			setBlockBounds(0.3125F, 0.375F, 1.0F - f2, 0.6875F, 0.625F, 1.0F);
			break;
		case UP:
			setBlockBounds(0.3125F, 0.0F, 0.375F, 0.6875F, 0.0F + f2, 0.625F);
			break;
		case WEST:
			setBlockBounds(0.3125F, 1.0F - f2, 0.375F, 0.6875F, 1.0F, 0.625F);
			break;
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!((Boolean) state.getValue(POWERED)).booleanValue()) {
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

	/**
	 * 
	 *
	 */
	public static class UBButtonItem extends Item implements Variable {

		private final UBStone baseStone;
		private final ButtonEntry entry;

		public UBButtonItem(UBStone baseStone, ButtonEntry entry) {
			setMaxDamage(0);
			setHasSubtypes(true);
			this.baseStone = baseStone;
			this.entry = entry;

			setUnlocalizedName(entry.internal());
			setCreativeTab(UBCreativeTab.UB_BLOCKS_TAB);
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
			for (int i = 0; i < baseStone.getNbVariants(); i++) {
				subItems.add(new ItemStack(itemIn, 1, i));
			}
		}

		@Override
		public int getMetadata(int meta) {
			return meta;
		}

		@Override
		public String getUnlocalizedName(ItemStack stack) {
			return super.getUnlocalizedName(stack) + "." + baseStone.getVariantName(stack.getMetadata());
		}

		@Override
		public String getItemStackDisplayName(ItemStack stack) {
			String stoneLocalName = StatCollector.translateToLocal(baseStone.getUnlocalizedName() + "." + baseStone.getVariantName(stack.getMetadata() & 7) + ".name").trim();
			// TODO Translate "button"
			return stoneLocalName + " button";
		}

		@Override
		public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
			Block block = worldIn.getBlockState(pos).getBlock();
			Block blockButton = entry.getBlock(side.getOpposite());

			stack = new ItemStack(blockButton, 1, playerIn.getHeldItem().getMetadata());

			if (!block.isReplaceable(worldIn, pos))
				pos = pos.offset(side);

			if (stack.stackSize == 0) {
				return false;
			} else if (!playerIn.canPlayerEdit(pos, side, stack)) {
				return false;
			} else if (pos.getY() == 255 && blockButton.getMaterial().isSolid()) {
				return false;
			} else if (worldIn.canBlockBePlaced(blockButton, pos, false, side, (Entity) null, stack)) {
				int meta = getMetadata(stack.getMetadata());
				IBlockState state = blockButton.onBlockPlaced(worldIn, pos, side, hitX, hitY, hitZ, meta, playerIn);

				if (placeBlockAt(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ, state)) {
					worldIn.playSoundEffect(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, blockButton.stepSound.getPlaceSound(), (blockButton.stepSound.getVolume() + 1.0F) / 2.0F,
							blockButton.stepSound.getFrequency() * 0.8F);
					--stack.stackSize;
				}

				return true;
			} else {
				return false;
			}
		}

		public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
			if (!world.setBlockState(pos, newState, 3))
				return false;

			IBlockState state = world.getBlockState(pos);
			Block blockButton = entry.getBlock(side);

			if (state.getBlock() == blockButton) {
				// ItemBlock.setTileEntityNBT(world, pos, stack, player);
				blockButton.onBlockPlacedBy(world, pos, state, player, stack);
			}

			return true;
		}

		@Override
		public void register() {
			GameRegistry.registerItem(this, entry.internal());
		}

		@Override
		public void registerModel() {
			for (int i = 0; i < baseStone.getNbVariants(); i++) {
				ModelBakery.addVariantName(this, getModelName(i));
				Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, i, new ModelResourceLocation(getModelName(i), "inventory"));
			}
		}

		/**
		 * Example : andesite_cobble
		 */
		@Override
		public String getVariantName(int meta) {
			// Metadatas 8-15 have the same name as 0-7
			return baseStone.getVariantName(meta & 7) + "_" + baseStone.getStoneStyle();
		}

		@Override
		public String getModelName(int meta) {
			return UndergroundBiomes.MODID + ":" + getVariantName(meta) + "_button";
		}

	}

}
