package exterminatorJeff.undergroundBiomes.common.block.slab;

import java.util.List;
import java.util.Random;

import exterminatorJeff.undergroundBiomes.api.SlabEntry;
import exterminatorJeff.undergroundBiomes.api.Variable;
import exterminatorJeff.undergroundBiomes.common.UBCreativeTab;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class UBStoneSlab extends BlockSlab implements Variable {

	protected final SlabEntry entry;

	public UBStoneSlab(SlabEntry entry) {
		super(Material.rock);
		this.entry = entry;
		setHarvestLevel(baseStone().getHarvestTool(baseStone().getDefaultState()), baseStone().getHarvestLevel(baseStone().getDefaultState()));
		setUnlocalizedName(this.entry.internal(isDouble()));
		if (!isDouble()) {
			setDefaultState(getDefaultState().withProperty(HALF, EnumBlockHalf.BOTTOM));
			setCreativeTab(UBCreativeTab.UB_BLOCKS_TAB);
		}
		useNeighborBrightness = !isDouble();
	}

	@Override
	public abstract boolean isDouble();

	protected abstract UBStone baseStone();

	@Override
	public IProperty getVariantProperty() {
		return baseStone().getVariantProperty();
	}

	@Override
	public String getVariantName(int meta) {
		return baseStone().getVariantName(meta & 7);
	}

	@Override
	public String getModelName(int meta) {
		return baseStone().getModelName(meta & 7);
	}

	@Override
	public Object getVariant(ItemStack stack) {
		return getStateFromMeta(stack.getMetadata()).getValue(getVariantProperty());
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = getDefaultState().withProperty(getVariantProperty(), baseStone().getVariantEnum()[meta & 7]);
		if (!isDouble()) {
			return state.withProperty(HALF, (meta < 8 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP));
		}
		return state;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = baseStone().getMetaFromState(state);

		if (!isDouble() && state.getValue(HALF) == EnumBlockHalf.TOP)
			meta |= 8;

		return meta;
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state) & 7;
	}

	@Override
	protected BlockState createBlockState() {
		if (isDouble())
			return new BlockState(this, getVariantProperty());
		else
			return new BlockState(this, getVariantProperty(), HALF);
	}

	@Override
	public void register() {
		if (isDouble())
			GameRegistry.registerBlock(this, UBItemSlab.class, entry.internal(true), entry.getSlabPair().half, this);
		else {
			GameRegistry.registerBlock(this, UBItemSlab.class, entry.internal(false), this, entry.getSlabPair().full);
		}
	}

	@Override
	public void registerModel() {
		if (!isDouble())
			for (int i = 0; i < baseStone().getNbVariants(); i++) {
				ModelBakery.addVariantName(entry.getHalf(), getModelName(i) + "_top");
				Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(entry.getHalf(), i, new ModelResourceLocation(getModelName(i) + "_top", "inventory"));
				ModelBakery.addVariantName(entry.getHalf(), getModelName(i) + "_bottom");
				Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(entry.getHalf(), i, new ModelResourceLocation(getModelName(i) + "_bottom", "inventory"));
			}
		else
			for (int i = 0; i < baseStone().getNbVariants(); i++) {
				ModelBakery.addVariantName(entry.getFull(), getModelName(i));
				Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(entry.getFull(), i, new ModelResourceLocation(getModelName(i), "inventory"));
			}
	}

	@Override
	public String getUnlocalizedName(int meta) {
		return super.getUnlocalizedName() + "." + getVariantName(meta);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		for (int i = 0; i < baseStone().getNbVariants(); i++) {
			list.add(new ItemStack(itemIn, 1, i));
		}
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos) {
		return new ItemStack(entry.getSlabPair().half, 1, getMetaFromState(world.getBlockState(pos)));
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return entry.getAssociatedItem();
	}

	@Override
	public float getBlockHardness(World worldIn, BlockPos pos) {
		return baseStone().getBlockHardness(worldIn, pos);
	}

	@Override
	public float getExplosionResistance(Entity exploder) {
		return baseStone().getExplosionResistance(exploder);
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		return baseStone().getExplosionResistance(world, pos, exploder, explosion);
	}

	/*
	 * Static
	 */

	public static final class UBItemSlab extends ItemSlab {

		public UBItemSlab(Block block, UBIgneousStoneSlabHalf half, UBIgneousStoneSlabDouble full) {
			super(block, half, full);
		}

		public UBItemSlab(Block block, UBIgneousCobbleSlabHalf half, UBIgneousCobbleSlabDouble full) {
			super(block, half, full);
		}

		public UBItemSlab(Block block, UBIgneousBrickSlabHalf half, UBIgneousBrickSlabDouble full) {
			super(block, half, full);
		}

		public UBItemSlab(Block block, UBMetamorphicStoneSlabHalf half, UBMetamorphicStoneSlabDouble full) {
			super(block, half, full);
		}

		public UBItemSlab(Block block, UBMetamorphicCobbleSlabHalf half, UBMetamorphicCobbleSlabDouble full) {
			super(block, half, full);
		}

		public UBItemSlab(Block block, UBMetamorphicBrickSlabHalf half, UBMetamorphicBrickSlabDouble full) {
			super(block, half, full);
		}

		public UBItemSlab(Block block, UBSedimentaryStoneSlabHalf half, UBSedimentaryStoneSlabDouble full) {
			super(block, half, full);
		}

	}

}
