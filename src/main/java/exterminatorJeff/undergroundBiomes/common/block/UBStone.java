package exterminatorJeff.undergroundBiomes.common.block;

import java.util.HashMap;
import java.util.Random;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import Zeno410Utils.Acceptor;
import exterminatorJeff.undergroundBiomes.api.UBEntry;
import exterminatorJeff.undergroundBiomes.api.Variable;
import exterminatorJeff.undergroundBiomes.client.UBOreOverlaysRegistry;
import exterminatorJeff.undergroundBiomes.common.UBConfig;
import exterminatorJeff.undergroundBiomes.common.UBCreativeTab;
import exterminatorJeff.undergroundBiomes.common.block.SedimentaryStone.SedimentaryStoneType;

public abstract class UBStone extends Block implements Variable {

	public static enum UBStoneType {
		IGNEOUS_STONE, IGNEOUS_COBBLE, IGNEOUS_BRICK, METAMORPHIC_STONE, METAMORPHIC_COBBLE, METAMORPHIC_BRICK, SEDIMENTARY_STONE;

		@Override
		public String toString() {
			return name().toLowerCase();
		}

	}

	public static enum UBStoneStyle {
		STONE, COBBLE, BRICK;

		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}

	/**
	 * All ub stones models locations<br/>
	 * Variant name -> Model location
	 */
	public static final HashMap<String, ResourceLocation> stoneModels = new HashMap<>();

	/**
	 * Stones textures<br/>
	 * Variant name -> Texture location
	 */
	public static final HashMap<String, ResourceLocation> stoneTextures = new HashMap<>();

	/*
	 * 
	 */

	protected boolean replaceableByOre = true;
	public final UBEntry entry;

	private final float baseHardness = 1.5F;
	private final float baseResistance = 1.66F;
	protected final float cobbleHardnessModifier = 1.333F;

	// protected float ubExplosionResistance;

	private final Acceptor<Double> hardnessUpdater = new Acceptor<Double>() {
		@Override
		public void accept(Double newHardness) {
			// we use the standard rather than the passed since we're having to
			// cut back resistance
			setHardness(UBConfig.hardnessModifier());
		}
	};

	private final Acceptor<Double> resistanceUpdater = new Acceptor<Double>() {
		@Override
		public void accept(Double newResistance) {
			// we use the standard rather than the passed since we're having to
			// cut back resistance
			setResistance(UBConfig.resistanceModifier());
			// ubExplosionResistance = blockResistance;
		}
	};

	public UBStone(UBEntry entry) {
		super(Material.rock);
		this.entry = entry;
		setUnlocalizedName(this.entry.internal());
		setCreativeTab(UBCreativeTab.UB_BLOCKS_TAB);
		UBConfig.settings.hardnessModifier.informOnChange(this.hardnessUpdater);
		UBConfig.settings.resistanceModifier.informOnChange(this.resistanceUpdater);

	}

	@Override
	public abstract BlockState createBlockState();

	public abstract int getNbVariants();

	public abstract IProperty getVariantProperty();

	@SuppressWarnings("rawtypes")
	public abstract Enum[] getVariantEnum();

	public abstract UBStoneType getStoneType();

	public abstract UBStoneStyle getStoneStyle();

	/**
	 * 
	 * @param ore
	 * @return The UBified version of the ore
	 */
	public abstract IBlockState getUBifiedOre(Block ore, int metadata);

	@Override
	public abstract IBlockState getStateFromMeta(int meta);

	@Override
	public abstract int getMetaFromState(IBlockState state);

	@Override
	public Block setHardness(float hardness) {
		return super.setHardness(hardness * baseHardness);
	}

	@Override
	public Block setResistance(float resistance) {
		return super.setResistance(resistance * baseResistance);
	}

	public float getHardness() {
		return blockHardness;
	}

	public float getResistance() {
		return blockResistance;
	}

	@Override
	public boolean isReplaceableOreGen(World world, BlockPos pos, Predicate<IBlockState> target) {
		// this obnoxious call is needed because something is redoing ore
		// placement without calling my routines
		// BiomeUndergroundDecorator.needsRedo(pos.getX(), pos.getZ(), world);
		return super.isReplaceableOreGen(world, pos, target);
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		int quantity = 1;
		if ((fortune != 0) && (((UBStone) state.getBlock()).isFortuneAffected(state))) {
			// Fortune III gives up to 4 items
			int j = random.nextInt(fortune + 2);
			quantity = (j < 1) ? 1 : j;
		}
		return quantity;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		// The item corresponding to the block but without the metadata !
		// Should return the corresponding item
		return Item.getItemFromBlock(state.getBlock());
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos) {
		return new ItemStack(this, 1, getMetaFromState(world.getBlockState(pos)));
	}

	public abstract boolean hasRareDrops();

	public boolean isFortuneAffected(IBlockState state) {
		if (state.getBlock() instanceof SedimentaryStone) {
			return ((SedimentaryStone.SedimentaryStoneType) state.getValue(SedimentaryStone.SEDIMENTARY_TYPE)) == SedimentaryStoneType.LIGNITE;
		}
		return false;
	}

	@Override
	public void register() {
		GameRegistry.registerBlock(this, UBItemBlockStone.class, entry.internal());
	}

	@Override
	public void registerModel() {
		for (int i = 0; i < getNbVariants(); i++) {
			ModelBakery.addVariantName(entry.getAssociatedItem(), getModelName(i));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(entry.getAssociatedItem(), i, new ModelResourceLocation(getModelName(i), "inventory"));
			stoneModels.put(getVariantName(i), new ModelResourceLocation(getModelName(i), "inventory"));
			stoneTextures.put(getVariantName(i), new ResourceLocation(UBOreOverlaysRegistry.BLOCKS_TX_PATH + getVariantName(i)));
		}
	}

	/*
	 * 
	 */

	public static class UBItemBlockStone extends ItemBlock {

		public UBItemBlockStone(Block block) {
			super(block);
			if (!(block instanceof UBStone))
				throw new IllegalArgumentException(String.format("The given Block %s is not an instance of UBBlockStone !", block.getUnlocalizedName()));
			setMaxDamage(0);
			setHasSubtypes(true);
		}

		@Override
		public int getMetadata(int meta) {
			return meta;
		}

		@Override
		public String getUnlocalizedName(ItemStack stack) {
			return super.getUnlocalizedName() + "." + ((UBStone) block).getVariantName(stack.getMetadata());
		}

	}

}
