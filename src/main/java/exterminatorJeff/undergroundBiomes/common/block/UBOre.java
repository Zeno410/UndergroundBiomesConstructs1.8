package exterminatorJeff.undergroundBiomes.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import exterminatorJeff.undergroundBiomes.api.Variable;
import exterminatorJeff.undergroundBiomes.client.UBOreModelLoader;
import exterminatorJeff.undergroundBiomes.common.UBCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * One instance per ore. Each instance has metadata used for UB stone variants
 * <br/>
 * If the baseOre is metadata based then it's one instance per baseOre:meta
 * couples<br/>
 * The mod should create an instance for each registered ores, if an overlay is
 * supplied
 * 
 * @author Spooky4672
 *
 */
public abstract class UBOre extends Block implements Variable {

	public abstract UBStone baseStone();

	public final Block baseOre;

	private final boolean oreHasMetadata;
	private final int oreMeta;

	public final boolean oreHasMetadata() {
		return oreHasMetadata;
	}

	public final int oreMeta() {
		return oreMeta;
	}

	/**
	 * The ore name (internal name)<br/>
	 * Example : "gold_ore", "iron_ore"
	 */
	public final String baseOreName;
	/**
	 * The stone name<br/>
	 * Example : "igneous_stone", "metamorphic_stone", "sedimentary_stone"
	 */
	public final String baseStoneName;
	public final String oreModDomain;
	/**
	 * Examples : "igneous_stone_coal", "metamorphic_stone_diamond"
	 */
	private final String name;

	public UBOre(Block baseOre, String baseOreName, String oreModDomain) {
		this(baseOre, false, 0, baseOreName, oreModDomain);
	}

	public UBOre(Block baseOre, boolean oreHasMetadata, int oreMeta, String baseOreName, String oreModDomain) {
		super(Material.rock);
		setHarvestLevel(baseOre.getHarvestTool(baseOre.getDefaultState()), baseOre.getHarvestLevel(baseOre.getDefaultState()));
		setCreativeTab(UBCreativeTab.UB_BLOCKS_TAB);

		this.baseOre = baseOre;
		this.oreHasMetadata = oreHasMetadata;
		this.oreMeta = oreHasMetadata ? oreMeta : 0;
		this.baseOreName = baseOreName;
		this.oreModDomain = oreModDomain;

		baseStoneName = baseStone().entry.internal();
		name = baseStoneName + "_" + StringUtils.remove(baseOreName, "_ore");

		setUnlocalizedName(name);
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT_MIPPED;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		for (int i = 0; i < baseStone().getNbVariants(); i++) {
			list.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(baseStone().getVariantProperty(), baseStone().getVariantEnum()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return baseStone().getMetaFromState(state);
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, baseStone().getVariantProperty());
	}

	@Override
	public String getVariantName(int meta) {
		return baseStone().getVariantName(meta);
	}

	/**
	 * This one is special, I use it to pass arguments to the corresponding
	 * IModel<br/>
	 * Examples :<br/>
	 * "undergroundbiomes:ubores.igneous_stone.red_granite.minecraft:iron_ore",
	 * <br/>
	 * "undergroundbiomes:ubores.metamorphic_stone.gneiss.simpleores:tin_ore"
	 */
	@Override
	public String getModelName(int meta) {
		return UBOreModelLoader.UBORE_MODEL + "." + baseStoneName + "." + getVariantName(meta) + "." + oreModDomain + ":" + baseOreName;
	}

	@Override
	public void register() {
		GameRegistry.registerBlock(this, UBItemOre.class, name, baseStone(), baseOre.getUnlocalizedName());
		// Check if the baseOre has smelting recipes
		ItemStack result = FurnaceRecipes.instance().getSmeltingResult(new ItemStack(baseOre));
		if (result != null) {
			// Add the recipe for each variant
			for (int i = 0; i < baseStone().getNbVariants(); i++) {
				GameRegistry.addSmelting(new ItemStack(this, 1, i), result, FurnaceRecipes.instance().getSmeltingExperience(result));
			}
		}
	}

	@Override
	public void registerModel() {
		for (int i = 0; i < baseStone().getNbVariants(); i++) {
			ModelBakery.addVariantName(Item.getItemFromBlock(this), getModelName(i));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), i, new ModelResourceLocation(getModelName(i), "inventory"));
		}
	}

	/*
	 * 
	 */

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return baseOre.quantityDropped(state, fortune, random);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		Item item = Item.getItemFromBlock(baseOre);
		Item drop = baseOre.getItemDropped(state, rand, fortune);
		if (drop != item)
			return drop;
		else
			return Item.getItemFromBlock(this);
	}

	@Override
	public int damageDropped(IBlockState state) {
		if (oreHasMetadata)
			return oreMeta;
		else
			return 0;
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		List<ItemStack> drops = new ArrayList<ItemStack>();

		Random rand = world instanceof World ? ((World) world).rand : RANDOM;

		Item itemBlock = Item.getItemFromBlock(this);

		int count = quantityDropped(state, fortune, rand);
		for (int i = 0; i < count; i++) {
			Item itemDropped = getItemDropped(state, rand, fortune);
			if (itemDropped != null) {
				// If the ore block do not drop the block item (like diamond)
				if (itemDropped != itemBlock)
					drops.add(new ItemStack(itemDropped, 1, damageDropped(state)));
				else
					drops.add(new ItemStack(itemDropped, 1, getMetaFromState(state)));
			}
		}
		return drops;
	}

	/*
	 * 
	 */

	@Override
	public void dropXpOnBlockBreak(World worldIn, BlockPos pos, int amount) {
		baseOre.dropXpOnBlockBreak(worldIn, pos, amount);
	}

	@Override
	public float getBlockHardness(World worldIn, BlockPos pos) {
		return baseOre.getBlockHardness(worldIn, pos);
	}

	@Override
	public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune) {
		return baseOre.getExpDrop(world, pos, fortune);
	}

	@Override
	public float getExplosionResistance(Entity exploder) {
		return baseOre.getExplosionResistance(exploder);
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		return baseOre.getExplosionResistance(world, pos, exploder, explosion);
	}

	@Override
	public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
		return true;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		baseOre.updateTick(worldIn, pos, state, rand);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos) {
		return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
	}

	@Override
	public abstract IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer);

	/**
	 * Example : "Black Granite Coal Ore"
	 */
	@Override
	public String getLocalizedName() {
		String stoneLocalName = StatCollector.translateToLocal(baseStone().getUnlocalizedName() + ".name");
		String oreLocalName = StatCollector.translateToLocal(baseOre.getUnlocalizedName() + ".name");
		return stoneLocalName + " " + oreLocalName;
	}

	/*
	 * 
	 */

	public static class UBItemOre extends ItemBlock {

		private final UBStone baseStone;
		private final String baseOreUnlocalizedName;

		public UBItemOre(Block block, IgneousStone baseStone, String baseOreUnlocalizedName) {
			this(block, (UBStone) baseStone, baseOreUnlocalizedName);
		}

		public UBItemOre(Block block, MetamorphicStone baseStone, String baseOreUnlocalizedName) {
			this(block, (UBStone) baseStone, baseOreUnlocalizedName);
		}

		public UBItemOre(Block block, SedimentaryStone baseStone, String baseOreUnlocalizedName) {
			this(block, (UBStone) baseStone, baseOreUnlocalizedName);
		}

		public UBItemOre(Block block, UBStone baseStone, String baseOreUnlocalizedName) {
			super(block);
			if (!(block instanceof UBOre))
				throw new IllegalArgumentException(String.format("The given Block %s is not an instance of UBOre !", block.getUnlocalizedName()));
			setMaxDamage(0);
			setHasSubtypes(true);
			this.baseStone = baseStone;
			this.baseOreUnlocalizedName = baseOreUnlocalizedName;
		}

		@Override
		public int getMetadata(int meta) {
			return meta;
		}

		/**
		 * Example : "Black Granite Coal Ore"
		 */
		@Override
		public String getItemStackDisplayName(ItemStack stack) {
			String stoneLocalName = StatCollector.translateToLocal(baseStone.getUnlocalizedName() + "." + baseStone.getVariantName(stack.getMetadata()) + ".name").trim();
			String oreLocalName = StatCollector.translateToLocal(baseOreUnlocalizedName + ".name").trim();
			return stoneLocalName + " " + oreLocalName;
		}

		@Override
		public String getUnlocalizedName(ItemStack stack) {
			return super.getUnlocalizedName(stack) + "." + ((UBOre) block).getVariantName(stack.getMetadata());
		}

	}

}
