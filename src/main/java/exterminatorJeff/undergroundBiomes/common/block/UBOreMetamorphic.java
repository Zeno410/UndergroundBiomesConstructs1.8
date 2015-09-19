package exterminatorJeff.undergroundBiomes.common.block;

import exterminatorJeff.undergroundBiomes.api.UBEntries;
import exterminatorJeff.undergroundBiomes.common.block.MetamorphicStone.MetamorphicStoneType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class UBOreMetamorphic extends UBOre {

	public UBOreMetamorphic(Block baseOre, String baseOreName) {
		super(baseOre, baseOreName, "minecraft");
	}

	public UBOreMetamorphic(Block baseOre, String baseOreName, String oreModDomain) {
		super(baseOre, baseOreName, oreModDomain);
	}

	public UBOreMetamorphic(Block baseOre, boolean oreHasMetadata, int oreMeta, String baseOreName) {
		super(baseOre, oreHasMetadata, oreMeta, baseOreName, "minecraft");
	}

	public UBOreMetamorphic(Block baseOre, boolean oreHasMetadata, int oreMeta, String baseOreName, String oreModDomain) {
		super(baseOre, oreHasMetadata, oreMeta, baseOreName, oreModDomain);
	}

	@Override
	public UBStone baseStone() {
		return (UBStone) UBEntries.METAMORPHIC_STONE.getAssociatedBlock();
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(MetamorphicStone.METAMORPHIC_TYPE, MetamorphicStoneType.values()[placer.getHeldItem().getMetadata()]);
	}

}
