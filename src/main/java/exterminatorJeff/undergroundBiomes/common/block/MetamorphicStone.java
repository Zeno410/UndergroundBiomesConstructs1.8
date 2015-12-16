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
import exterminatorJeff.undergroundBiomes.api.VanillaItemEntry;
import exterminatorJeff.undergroundBiomes.common.UBConfig;
import exterminatorJeff.undergroundBiomes.common.UBOresMaker;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;

public class MetamorphicStone extends UBStone {

	public static final PropertyEnum METAMORPHIC_TYPE = PropertyEnum.create("metamorphic_type", MetamorphicStoneType.class);
	public static final int nbVariants = MetamorphicStoneType.values().length;
	public static final MetamorphicStoneType[] METAMORPHIC_STONE_TYPES = MetamorphicStoneType.values();

	public static enum MetamorphicStoneType implements IStringSerializable {
		GNEISS(1.1f, 1.11f), ECLOGITE(1.0f, 1.0f), MARBLE(1.1f, 1.11f), QUARTZITE(1.3f, 1.26f), BLUESCHIST(0.7f, 0.54f), GREENSCHIST(0.7f, 0.54f), SOAPSTONE(0.4f,
				0.2f), MIGMATITE(0.9f, 0.86f);

		private float hardness;
		private float resistance;

		private MetamorphicStoneType(float hardness, float resistance) {
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

	public MetamorphicStone() {
		this(UBEntries.METAMORPHIC_STONE);
	}

	public MetamorphicStone(UBEntry entry) {
		super(entry);
		setHardness(UBConfig.hardnessModifier()).setResistance(UBConfig.resistanceModifier());
		setDefaultState(blockState.getBaseState().withProperty(METAMORPHIC_TYPE, MetamorphicStoneType.GNEISS));
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
		return new BlockState(this, METAMORPHIC_TYPE);
	}

	@Override
	public int getNbVariants() {
		return nbVariants;
	}

	@Override
	public IProperty getVariantProperty() {
		return METAMORPHIC_TYPE;
	}

	@Override
	public Enum<MetamorphicStoneType>[] getVariantEnum() {
		return METAMORPHIC_STONE_TYPES;
	}

	public UBStoneType getStoneType() {
		return UBStoneType.METAMORPHIC;
	}

	public UBStoneStyle getStoneStyle() {
		return UBStoneStyle.STONE;
	}

	@Override
	public IBlockState getUBifiedOre(Block ore, int metadata) {
		return UBOresMaker.UB_ORES_METAMORPHIC.get(ore).getDefaultState().withProperty(getVariantProperty(), MetamorphicStoneType.values()[metadata]);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(METAMORPHIC_TYPE, MetamorphicStoneType.values()[meta & 7]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((MetamorphicStoneType) state.getValue(METAMORPHIC_TYPE)).getMetadata();
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		List<ItemStack> drops = super.getDrops(world, pos, state, fortune);

		Random rand = world instanceof World ? ((World) world).rand : RANDOM;

		if (hasRareDrops()) {
			// Very rare drops // TODO I think this is too low for the reward
			if (rand.nextInt(1000) <= fortune) { // 1,2,3 per thousand
				if (pos.getY() <= 16) {
					// Redstone
					drops.add(new ItemStack(VanillaItemEntry.redstone.getAssociatedItem(), 1, 0));
				} else if (pos.getY() <= 32) {
					// Lapis lazuli
					drops.add(new ItemStack(VanillaItemEntry.dyePowder.getAssociatedItem(), 1, 4));
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
		return super.getBlockHardness(worldIn, pos) * ((MetamorphicStoneType) getActualState(getDefaultState(), worldIn, pos).getValue(METAMORPHIC_TYPE)).getHardness();
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		return super.getExplosionResistance(world, pos, exploder, explosion)
				* ((MetamorphicStoneType) getActualState(getDefaultState(), world, pos).getValue(METAMORPHIC_TYPE)).getResistance();
	}

	@Override
	public String getVariantName(int meta) {
		return MetamorphicStoneType.values()[meta].toString();
	}

	@Override
	public String getModelName(int meta) {
		return UndergroundBiomes.MODID + ":" + getVariantName(meta);
	}

}
