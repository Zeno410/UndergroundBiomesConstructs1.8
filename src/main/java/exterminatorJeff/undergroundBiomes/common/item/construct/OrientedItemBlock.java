package exterminatorJeff.undergroundBiomes.common.item.construct;

import java.util.List;

import exterminatorJeff.undergroundBiomes.api.OrientedBlockEntry;
import exterminatorJeff.undergroundBiomes.api.Variable;
import exterminatorJeff.undergroundBiomes.common.UBCreativeTab;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * 
 * @author Spooky4672
 *
 */
public abstract class OrientedItemBlock extends Item implements Variable {

	private final UBStone baseStone;
	private final OrientedBlockEntry entry;

	public OrientedItemBlock(UBStone baseStone, OrientedBlockEntry entry) {
		this.baseStone = baseStone;
		this.entry = entry;
		setMaxDamage(0);
		setHasSubtypes(true);
		setUnlocalizedName(this.entry.internal());
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
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		Block block = worldIn.getBlockState(pos).getBlock();
		Block orientedBlock = entry.getBlock(side);

		stack = new ItemStack(orientedBlock, 1, playerIn.getHeldItem().getMetadata());

		if (!block.isReplaceable(worldIn, pos))
			pos = pos.offset(side);

		if (stack.stackSize == 0) {
			return false;
		} else if (!playerIn.canPlayerEdit(pos, side, stack)) {
			return false;
		} else if (pos.getY() == 255 && orientedBlock.getMaterial().isSolid()) {
			return false;
		} else if (worldIn.canBlockBePlaced(orientedBlock, pos, false, side, (Entity) null, stack)) {
			int meta = getMetadata(stack.getMetadata());
			IBlockState state = orientedBlock.onBlockPlaced(worldIn, pos, side, hitX, hitY, hitZ, meta, playerIn);

			if (placeBlockAt(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ, state)) {
				worldIn.playSoundEffect(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, orientedBlock.stepSound.getPlaceSound(), (orientedBlock.stepSound.getVolume() + 1.0F) / 2.0F,
						orientedBlock.stepSound.getFrequency() * 0.8F);
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
		Block orientedBlock = entry.getBlock(side);

		if (state.getBlock() == orientedBlock) {
			orientedBlock.onBlockPlacedBy(world, pos, state, player, stack);
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

	@Override
	public String getVariantName(int meta) {
		// Metadatas 8-15 have the same name as 0-7
		return baseStone.getVariantName(meta & 7) + "_" + baseStone.getStoneStyle();
	}
}
