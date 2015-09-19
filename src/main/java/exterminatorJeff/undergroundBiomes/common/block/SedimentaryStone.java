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
import exterminatorJeff.undergroundBiomes.common.item.ItemFossilPiece;

public class SedimentaryStone extends UBStone {

	public static final PropertyEnum SEDIMENTARY_TYPE = PropertyEnum.create("sedimentary_type", SedimentaryStoneType.class);
	public static final int nbVariants = SedimentaryStoneType.values().length;
	public static final SedimentaryStoneType[] SEDIMENTARY_STONE_TYPES = SedimentaryStoneType.values();

	public static enum SedimentaryStoneType implements IStringSerializable {
		LIMESTONE(0.5f, 0.29f), CHALK(0.5f, 0.29f), SHALE(0.5f, 0.29f), SILTSTONE(0.6f, 0.4f), LIGNITE(0.5f, 0.29f), DOLOMITE(0.5f, 0.29f), GREYWACKE(1.0f, 1.0f), CHERT(0.9f, 0.86f);

		private float hardness;
		private float resistance;

		private SedimentaryStoneType(float hardness, float resistance) {
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

	public SedimentaryStone() {
		this(UBEntries.SEDIMENTARY_STONE);
	}

	public SedimentaryStone(UBEntry entry) {
		super(entry);
		setHardness(1.5F * UBConfig.hardnessModifier()).setResistance(1.66F * UBConfig.resistanceModifier());
		setDefaultState(blockState.getBaseState().withProperty(SEDIMENTARY_TYPE, SedimentaryStoneType.LIMESTONE));
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
		return new BlockState(this, SEDIMENTARY_TYPE);
	}

	@Override
	public int getNbVariants() {
		return nbVariants;
	}

	@Override
	public IProperty getVariantProperty() {
		return SEDIMENTARY_TYPE;
	}

	@Override
	public Enum<SedimentaryStoneType>[] getVariantEnum() {
		return SEDIMENTARY_STONE_TYPES;
	}

	public UBStoneType getStoneType() {
		return UBStoneType.SEDIMENTARY_STONE;
	}

	public UBStoneStyle getStoneStyle() {
		return UBStoneStyle.STONE;
	}

	@Override
	public IBlockState getUBifiedOre(Block ore, int metadata) {
		return UBOresMaker.UB_ORES_SEDIMENTARY.get(ore).getDefaultState().withProperty(getVariantProperty(), SedimentaryStoneType.values()[metadata]);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(SEDIMENTARY_TYPE, SedimentaryStoneType.values()[meta & 7]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((SedimentaryStoneType) state.getValue(SEDIMENTARY_TYPE)).getMetadata();
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		List<ItemStack> drops = super.getDrops(world, pos, state, fortune);

		Random rand = world instanceof World ? ((World) world).rand : RANDOM;

		if (state.getValue(SEDIMENTARY_TYPE) == SedimentaryStoneType.LIGNITE) {
			// Lignite do not drop stone
			drops.clear();
			drops.add(new ItemStack(UBEntries.ligniteCoal.getAssociatedItem(), 1, 0));
		}

		// Rare drops
		if (rand.nextInt(100) <= fortune) { // 1,2,3 per hundred
			switch ((SedimentaryStoneType) state.getValue(SEDIMENTARY_TYPE)) {
			case CHALK:
			case DOLOMITE:
			case LIGNITE:
			case LIMESTONE:
			case SILTSTONE:
				drops.add(new ItemStack(UBEntries.fossilPiece.getAssociatedItem(), 1, rand.nextInt(ItemFossilPiece.nbValues)));
				break;
			case SHALE:
				drops.add(new ItemStack(VanillaItemEntry.clay.getAssociatedItem(), 1, 0));
				break;
			case CHERT:
				drops.add(new ItemStack(VanillaItemEntry.flint.getAssociatedItem(), 1, 0));
				break;
			default:
				break;
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
		return super.getBlockHardness(worldIn, pos) * ((SedimentaryStoneType) getActualState(getDefaultState(), worldIn, pos).getValue(SEDIMENTARY_TYPE)).getHardness();
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		return super.getExplosionResistance(world, pos, exploder, explosion) * ((SedimentaryStoneType) getActualState(getDefaultState(), world, pos).getValue(SEDIMENTARY_TYPE)).getResistance();
	}

	@Override
	public String getVariantName(int meta) {
		return SedimentaryStoneType.values()[meta].toString();
	}

	@Override
	public String getModelName(int meta) {
		return UndergroundBiomes.MODID + ":" + getVariantName(meta);
	}

}
