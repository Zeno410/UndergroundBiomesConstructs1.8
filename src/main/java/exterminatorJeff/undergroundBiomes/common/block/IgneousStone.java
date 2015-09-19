package exterminatorJeff.undergroundBiomes.common.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import exterminatorJeff.undergroundBiomes.api.UBEntries;
import exterminatorJeff.undergroundBiomes.api.UBEntry;
import exterminatorJeff.undergroundBiomes.common.UBConfig;
import exterminatorJeff.undergroundBiomes.common.UBOresMaker;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;

public class IgneousStone extends UBStone {

	public static final PropertyEnum IGNEOUS_TYPE = PropertyEnum.create("igneous_type", IgneousStoneType.class);
	public static final int nbVariants = IgneousStoneType.values().length;
	public static final IgneousStoneType[] IGNEOUS_STONE_TYPES = IgneousStoneType.values();

	public static enum IgneousStoneType implements IStringSerializable {
		RED_GRANITE(1.7f, 1.42f), BLACK_GRANITE(1.6f, 1.39f), RHYOLITE(1.3f, 1.26f), ANDESITE(1.4f, 1.31f), GABBRO(1.0f, 1.0f), BASALT(1.4f, 1.31f), KOMATIITE(1.5f, 1.35f), DACITE(1.2f, 1.2f);

		private float hardness;
		private float resistance;

		private IgneousStoneType(float hardness, float resistance) {
			this.hardness = hardness;
			this.resistance = resistance;
		}

		public int getMetadata() {
			return ordinal();
		}

		public float getHardness() {
			return hardness;
		}

		public float getResistance() {
			return resistance;
		}

		@Override
		public String toString() {
			return name().toLowerCase();
		}

		@Override
		public String getName() {
			return toString();
		}

	}

	public IgneousStone() {
		this(UBEntries.IGNEOUS_STONE);
	}

	public IgneousStone(UBEntry namer) {
		super(namer);
		setHardness(UBConfig.hardnessModifier()).setResistance(UBConfig.resistanceModifier());
		setDefaultState(blockState.getBaseState().withProperty(IGNEOUS_TYPE, IgneousStoneType.RED_GRANITE));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tabs, List list) {
		for (int i = 0; i < nbVariants; i++) {
			list.add(new ItemStack(itemIn, 1, i));
		}
	}

	@Override
	public BlockState createBlockState() {
		return new BlockState(this, IGNEOUS_TYPE);
	}

	@Override
	public int getNbVariants() {
		return nbVariants;
	}

	@Override
	public IProperty getVariantProperty() {
		return IGNEOUS_TYPE;
	}

	@Override
	public Enum<IgneousStoneType>[] getVariantEnum() {
		return IGNEOUS_STONE_TYPES;
	}

	public UBStoneType getStoneType() {
		return UBStoneType.IGNEOUS_STONE;
	}

	public UBStoneStyle getStoneStyle() {
		return UBStoneStyle.STONE;
	}

	@Override
	public IBlockState getUBifiedOre(Block ore, int metadata) {
		return UBOresMaker.UB_ORES_IGNEOUS.get(ore).getDefaultState().withProperty(getVariantProperty(), IgneousStoneType.values()[metadata]);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(IGNEOUS_TYPE, IgneousStoneType.values()[meta & 7]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((IgneousStoneType) state.getValue(IGNEOUS_TYPE)).getMetadata();
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		List<ItemStack> drops = super.getDrops(world, pos, state, fortune);

		if (hasRareDrops()) {
			Random rand = world instanceof World ? ((World) world).rand : RANDOM;

			// Very rare drops
			if (pos.getY() <= 32 && rand.nextInt(1000) <= fortune) {
				int nbNuggets = UndergroundBiomes.nuggets.size();
				if (nbNuggets > 0) {
					// Add a random nugget
					drops.add(UndergroundBiomes.nuggets.get(rand.nextInt(nbNuggets)));
				}
			}
		}
		return drops;
	}

	@Override
	public boolean hasRareDrops() {
		return true;
	}

	@Override
	public float getBlockHardness(World worldIn, BlockPos pos) {
		return super.getBlockHardness(worldIn, pos) * ((IgneousStoneType) getActualState(getDefaultState(), worldIn, pos).getValue(IGNEOUS_TYPE)).getHardness();
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		return super.getExplosionResistance(world, pos, exploder, explosion) * ((IgneousStoneType) getActualState(getDefaultState(), world, pos).getValue(IGNEOUS_TYPE)).getResistance();
	}

	@Override
	public String getVariantName(int meta) {
		return IgneousStoneType.values()[meta].toString();
	}

	@Override
	public String getModelName(int meta) {
		return UndergroundBiomes.MODID + ":" + getVariantName(meta);
	}

}
