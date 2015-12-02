package exterminatorJeff.undergroundBiomes.common.block.constructs;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import exterminatorJeff.undergroundBiomes.api.Registrable;
import exterminatorJeff.undergroundBiomes.api.StairsEntry;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStairs.EnumHalf;
import net.minecraft.block.BlockStairs.EnumShape;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 1 instance -> 8 variants + half -> 16 metadatas<br/>
 * 4 facings<br/>
 * 3 stone types<br/>
 * 3 stone style except for sedimentary<br/>
 * Total : 28 instances (7 subclasses)
 * 
 * @author Spooky4672
 *
 */
public abstract class UBStairs extends Block implements OrientedBlock, Registrable {

	private final StairsEntry entry;
	private final EnumFacing facing;

	@Override
	public EnumFacing getFacing() {
		return facing;
	}

	protected abstract UBStone baseStone();

	/**
	 * Example : "igneous_cobble_stairs_north"
	 */
	private final String name;

	public UBStairs(StairsEntry entry, EnumFacing facing) {
		super(Material.rock);
		this.entry = entry;
		this.facing = facing;

		setDefaultState(blockState.getBaseState().withProperty(BlockStairs.HALF, EnumHalf.BOTTOM).withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT)
				.withProperty(baseStone().getVariantProperty(), baseStone().getVariantEnum()[0]));
		setHardness(baseStone().getHardness());
		setResistance(baseStone().getResistance() / 3.0F);

		name = entry.internal(facing);
		setUnlocalizedName(name);
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { BlockStairs.HALF, BlockStairs.SHAPE, baseStone().getVariantProperty() });
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = getDefaultState();
		if (meta >= 8)
			state = state.withProperty(BlockStairs.HALF, EnumHalf.TOP);
		return state.withProperty(baseStone().getVariantProperty(), baseStone().getVariantEnum()[meta & 7]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = baseStone().getMetaFromState(state);
		if (state.getValue(BlockStairs.HALF) == EnumHalf.TOP)
			i |= 8;
		return i;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return entry.getAssociatedItem();
	}

	@Override
	public int damageDropped(IBlockState state) {
		// There is no reverse stairs item
		return getMetaFromState(state) & 7;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		return new ItemStack(entry.getAssociatedItem(), 1, state.getBlock().getMetaFromState(state));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
	}

	/*
	 * 
	 */

	@Override
	public void register() {
		GameRegistry.registerBlock(this, name);
	}

	@Override
	public void registerModel() {
		// No need
	}

	/*
	 * Copied from BlockStairs class
	 */

	private static final int[][] field_150150_a = new int[][] { { 4, 5 }, { 5, 7 }, { 6, 7 }, { 4, 6 }, { 0, 1 }, { 1, 3 }, { 2, 3 }, { 0, 2 } };
	private boolean hasRaytraced;
	private int rayTracePass;

	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		if (this.hasRaytraced) {
			this.setBlockBounds(0.5F * (float) (this.rayTracePass % 2), 0.5F * (float) (this.rayTracePass / 4 % 2), 0.5F * (float) (this.rayTracePass / 2 % 2),
					0.5F + 0.5F * (float) (this.rayTracePass % 2), 0.5F + 0.5F * (float) (this.rayTracePass / 4 % 2), 0.5F + 0.5F * (float) (this.rayTracePass / 2 % 2));
		} else {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	/**
	 * Set the block bounds as the collision bounds for the stairs at the given
	 * position
	 */
	public void setBaseCollisionBounds(IBlockAccess worldIn, BlockPos pos) {
		if (worldIn.getBlockState(pos).getValue(BlockStairs.HALF) == EnumHalf.TOP) {
			setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
		} else {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		}
	}

	/**
	 * Checks if a block is stairs
	 */
	public static boolean isBlockStairs(Block block) {
		return block instanceof UBStairs;
	}

	/**
	 * Check whether there is a stair block at the given position and it has the
	 * same properties as the given BlockState
	 */
	public static boolean isSameStair(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
		IBlockState stairsState = worldIn.getBlockState(pos);
		Block block = stairsState.getBlock();
		return isBlockStairs(block) && stairsState.getValue(BlockStairs.HALF) == state.getValue(BlockStairs.HALF);
	}

	public int func_176307_f(IBlockAccess access, BlockPos pos) {
		IBlockState iblockstate = access.getBlockState(pos);
		EnumFacing enumfacing = ((UBStairs) iblockstate.getBlock()).getFacing();
		BlockStairs.EnumHalf enumhalf = (BlockStairs.EnumHalf) iblockstate.getValue(BlockStairs.HALF);
		boolean flag = enumhalf == BlockStairs.EnumHalf.TOP;
		IBlockState iblockstate1;
		Block block;
		EnumFacing enumfacing1;

		if (enumfacing == EnumFacing.EAST) {
			iblockstate1 = access.getBlockState(pos.east());
			block = iblockstate1.getBlock();

			if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(BlockStairs.HALF)) {
				enumfacing1 = ((UBStairs) block).getFacing();

				if (enumfacing1 == EnumFacing.NORTH && !isSameStair(access, pos.south(), iblockstate)) {
					return flag ? 1 : 2;
				}

				if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(access, pos.north(), iblockstate)) {
					return flag ? 2 : 1;
				}
			}
		} else if (enumfacing == EnumFacing.WEST) {
			iblockstate1 = access.getBlockState(pos.west());
			block = iblockstate1.getBlock();

			if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(BlockStairs.HALF)) {
				enumfacing1 = ((UBStairs) block).getFacing();

				if (enumfacing1 == EnumFacing.NORTH && !isSameStair(access, pos.south(), iblockstate)) {
					return flag ? 2 : 1;
				}

				if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(access, pos.north(), iblockstate)) {
					return flag ? 1 : 2;
				}
			}
		} else if (enumfacing == EnumFacing.SOUTH) {
			iblockstate1 = access.getBlockState(pos.south());
			block = iblockstate1.getBlock();

			if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(BlockStairs.HALF)) {
				enumfacing1 = ((UBStairs) block).getFacing();

				if (enumfacing1 == EnumFacing.WEST && !isSameStair(access, pos.east(), iblockstate)) {
					return flag ? 2 : 1;
				}

				if (enumfacing1 == EnumFacing.EAST && !isSameStair(access, pos.west(), iblockstate)) {
					return flag ? 1 : 2;
				}
			}
		} else if (enumfacing == EnumFacing.NORTH) {
			iblockstate1 = access.getBlockState(pos.north());
			block = iblockstate1.getBlock();

			if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(BlockStairs.HALF)) {
				enumfacing1 = ((UBStairs) block).getFacing();

				if (enumfacing1 == EnumFacing.WEST && !isSameStair(access, pos.east(), iblockstate)) {
					return flag ? 1 : 2;
				}

				if (enumfacing1 == EnumFacing.EAST && !isSameStair(access, pos.west(), iblockstate)) {
					return flag ? 2 : 1;
				}
			}
		}

		return 0;
	}

	public int func_176305_g(IBlockAccess access, BlockPos pos) {
		IBlockState iblockstate = access.getBlockState(pos);
		EnumFacing enumfacing = ((UBStairs) iblockstate.getBlock()).getFacing();
		BlockStairs.EnumHalf enumhalf = (BlockStairs.EnumHalf) iblockstate.getValue(BlockStairs.HALF);
		boolean flag = enumhalf == BlockStairs.EnumHalf.TOP;
		IBlockState iblockstate1;
		Block block;
		EnumFacing enumfacing1;

		if (enumfacing == EnumFacing.EAST) {
			iblockstate1 = access.getBlockState(pos.west());
			block = iblockstate1.getBlock();

			if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(BlockStairs.HALF)) {
				enumfacing1 = ((UBStairs) block).getFacing();

				if (enumfacing1 == EnumFacing.NORTH && !isSameStair(access, pos.north(), iblockstate)) {
					return flag ? 1 : 2;
				}

				if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(access, pos.south(), iblockstate)) {
					return flag ? 2 : 1;
				}
			}
		} else if (enumfacing == EnumFacing.WEST) {
			iblockstate1 = access.getBlockState(pos.east());
			block = iblockstate1.getBlock();

			if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(BlockStairs.HALF)) {
				enumfacing1 = ((UBStairs) block).getFacing();

				if (enumfacing1 == EnumFacing.NORTH && !isSameStair(access, pos.north(), iblockstate)) {
					return flag ? 2 : 1;
				}

				if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(access, pos.south(), iblockstate)) {
					return flag ? 1 : 2;
				}
			}
		} else if (enumfacing == EnumFacing.SOUTH) {
			iblockstate1 = access.getBlockState(pos.north());
			block = iblockstate1.getBlock();

			if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(BlockStairs.HALF)) {
				enumfacing1 = ((UBStairs) block).getFacing();

				if (enumfacing1 == EnumFacing.WEST && !isSameStair(access, pos.west(), iblockstate)) {
					return flag ? 2 : 1;
				}

				if (enumfacing1 == EnumFacing.EAST && !isSameStair(access, pos.east(), iblockstate)) {
					return flag ? 1 : 2;
				}
			}
		} else if (enumfacing == EnumFacing.NORTH) {
			iblockstate1 = access.getBlockState(pos.south());
			block = iblockstate1.getBlock();

			if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(BlockStairs.HALF)) {
				enumfacing1 = ((UBStairs) block).getFacing();

				if (enumfacing1 == EnumFacing.WEST && !isSameStair(access, pos.west(), iblockstate)) {
					return flag ? 1 : 2;
				}

				if (enumfacing1 == EnumFacing.EAST && !isSameStair(access, pos.east(), iblockstate)) {
					return flag ? 2 : 1;
				}
			}
		}

		return 0;
	}

	public boolean func_176306_h(IBlockAccess p_176306_1_, BlockPos p_176306_2_) {
		IBlockState iblockstate = p_176306_1_.getBlockState(p_176306_2_);
		EnumFacing enumfacing = ((UBStairs) iblockstate.getBlock()).getFacing();
		BlockStairs.EnumHalf enumhalf = (BlockStairs.EnumHalf) iblockstate.getValue(BlockStairs.HALF);
		boolean flag = enumhalf == BlockStairs.EnumHalf.TOP;
		float f = 0.5F;
		float f1 = 1.0F;

		if (flag) {
			f = 0.0F;
			f1 = 0.5F;
		}

		float f2 = 0.0F;
		float f3 = 1.0F;
		float f4 = 0.0F;
		float f5 = 0.5F;
		boolean flag1 = true;
		IBlockState iblockstate1;
		Block block;
		EnumFacing enumfacing1;

		if (enumfacing == EnumFacing.EAST) {
			f2 = 0.5F;
			f5 = 1.0F;
			iblockstate1 = p_176306_1_.getBlockState(p_176306_2_.east());
			block = iblockstate1.getBlock();

			if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(BlockStairs.HALF)) {
				enumfacing1 = ((UBStairs) block).getFacing();

				if (enumfacing1 == EnumFacing.NORTH && !isSameStair(p_176306_1_, p_176306_2_.south(), iblockstate)) {
					f5 = 0.5F;
					flag1 = false;
				} else if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(p_176306_1_, p_176306_2_.north(), iblockstate)) {
					f4 = 0.5F;
					flag1 = false;
				}
			}
		} else if (enumfacing == EnumFacing.WEST) {
			f3 = 0.5F;
			f5 = 1.0F;
			iblockstate1 = p_176306_1_.getBlockState(p_176306_2_.west());
			block = iblockstate1.getBlock();

			if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(BlockStairs.HALF)) {
				enumfacing1 = ((UBStairs) block).getFacing();

				if (enumfacing1 == EnumFacing.NORTH && !isSameStair(p_176306_1_, p_176306_2_.south(), iblockstate)) {
					f5 = 0.5F;
					flag1 = false;
				} else if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(p_176306_1_, p_176306_2_.north(), iblockstate)) {
					f4 = 0.5F;
					flag1 = false;
				}
			}
		} else if (enumfacing == EnumFacing.SOUTH) {
			f4 = 0.5F;
			f5 = 1.0F;
			iblockstate1 = p_176306_1_.getBlockState(p_176306_2_.south());
			block = iblockstate1.getBlock();

			if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(BlockStairs.HALF)) {
				enumfacing1 = ((UBStairs) block).getFacing();

				if (enumfacing1 == EnumFacing.WEST && !isSameStair(p_176306_1_, p_176306_2_.east(), iblockstate)) {
					f3 = 0.5F;
					flag1 = false;
				} else if (enumfacing1 == EnumFacing.EAST && !isSameStair(p_176306_1_, p_176306_2_.west(), iblockstate)) {
					f2 = 0.5F;
					flag1 = false;
				}
			}
		} else if (enumfacing == EnumFacing.NORTH) {
			iblockstate1 = p_176306_1_.getBlockState(p_176306_2_.north());
			block = iblockstate1.getBlock();

			if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(BlockStairs.HALF)) {
				enumfacing1 = ((UBStairs) block).getFacing();

				if (enumfacing1 == EnumFacing.WEST && !isSameStair(p_176306_1_, p_176306_2_.east(), iblockstate)) {
					f3 = 0.5F;
					flag1 = false;
				} else if (enumfacing1 == EnumFacing.EAST && !isSameStair(p_176306_1_, p_176306_2_.west(), iblockstate)) {
					f2 = 0.5F;
					flag1 = false;
				}
			}
		}

		this.setBlockBounds(f2, f, f4, f3, f1, f5);
		return flag1;
	}

	public boolean func_176304_i(IBlockAccess access, BlockPos pos) {
		IBlockState iblockstate = access.getBlockState(pos);
		EnumFacing enumfacing = ((UBStairs) iblockstate.getBlock()).getFacing();
		BlockStairs.EnumHalf enumhalf = (BlockStairs.EnumHalf) iblockstate.getValue(BlockStairs.HALF);
		boolean flag = enumhalf == BlockStairs.EnumHalf.TOP;
		float f = 0.5F;
		float f1 = 1.0F;

		if (flag) {
			f = 0.0F;
			f1 = 0.5F;
		}

		float f2 = 0.0F;
		float f3 = 0.5F;
		float f4 = 0.5F;
		float f5 = 1.0F;
		boolean flag1 = false;
		IBlockState iblockstate1;
		Block block;
		EnumFacing enumfacing1;

		if (enumfacing == EnumFacing.EAST) {
			iblockstate1 = access.getBlockState(pos.west());
			block = iblockstate1.getBlock();

			if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(BlockStairs.HALF)) {
				enumfacing1 = ((UBStairs) block).getFacing();

				if (enumfacing1 == EnumFacing.NORTH && !isSameStair(access, pos.north(), iblockstate)) {
					f4 = 0.0F;
					f5 = 0.5F;
					flag1 = true;
				} else if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(access, pos.south(), iblockstate)) {
					f4 = 0.5F;
					f5 = 1.0F;
					flag1 = true;
				}
			}
		} else if (enumfacing == EnumFacing.WEST) {
			iblockstate1 = access.getBlockState(pos.east());
			block = iblockstate1.getBlock();

			if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(BlockStairs.HALF)) {
				f2 = 0.5F;
				f3 = 1.0F;
				enumfacing1 = ((UBStairs) block).getFacing();

				if (enumfacing1 == EnumFacing.NORTH && !isSameStair(access, pos.north(), iblockstate)) {
					f4 = 0.0F;
					f5 = 0.5F;
					flag1 = true;
				} else if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(access, pos.south(), iblockstate)) {
					f4 = 0.5F;
					f5 = 1.0F;
					flag1 = true;
				}
			}
		} else if (enumfacing == EnumFacing.SOUTH) {
			iblockstate1 = access.getBlockState(pos.north());
			block = iblockstate1.getBlock();

			if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(BlockStairs.HALF)) {
				f4 = 0.0F;
				f5 = 0.5F;
				enumfacing1 = ((UBStairs) block).getFacing();

				if (enumfacing1 == EnumFacing.WEST && !isSameStair(access, pos.west(), iblockstate)) {
					flag1 = true;
				} else if (enumfacing1 == EnumFacing.EAST && !isSameStair(access, pos.east(), iblockstate)) {
					f2 = 0.5F;
					f3 = 1.0F;
					flag1 = true;
				}
			}
		} else if (enumfacing == EnumFacing.NORTH) {
			iblockstate1 = access.getBlockState(pos.south());
			block = iblockstate1.getBlock();

			if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(BlockStairs.HALF)) {
				enumfacing1 = ((UBStairs) block).getFacing();

				if (enumfacing1 == EnumFacing.WEST && !isSameStair(access, pos.west(), iblockstate)) {
					flag1 = true;
				} else if (enumfacing1 == EnumFacing.EAST && !isSameStair(access, pos.east(), iblockstate)) {
					f2 = 0.5F;
					f3 = 1.0F;
					flag1 = true;
				}
			}
		}

		if (flag1) {
			this.setBlockBounds(f2, f, f4, f3, f1, f5);
		}

		return flag1;
	}

	/**
	 * Add all collision boxes of this Block to the list that intersect with the
	 * given mask.
	 * 
	 * @param collidingEntity
	 *            the Entity colliding with this Block
	 */
	@SuppressWarnings("rawtypes")
	public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity) {
		setBaseCollisionBounds(worldIn, pos);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		boolean flag = func_176306_h(worldIn, pos);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);

		if (flag && func_176304_i(worldIn, pos)) {
			super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		}

		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		baseStone().onBlockClicked(worldIn, pos, playerIn);
	}

	@Override
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		baseStone().randomDisplayTick(worldIn, pos, state, rand);
	}

	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
		baseStone().onBlockDestroyedByPlayer(worldIn, pos, state);
	}

	@Override
	public int getMixedBrightnessForBlock(IBlockAccess worldIn, BlockPos pos) {
		return baseStone().getMixedBrightnessForBlock(worldIn, pos);
	}

	@Override
	public float getExplosionResistance(Entity exploder) {
		return baseStone().getExplosionResistance(exploder);
	}

	@Override
	public int tickRate(World worldIn) {
		return baseStone().tickRate(worldIn);
	}

	@Override
	public Vec3 modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3 motion) {
		return baseStone().modifyAcceleration(worldIn, pos, entityIn, motion);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return baseStone().getBlockLayer();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
		return baseStone().getSelectedBoundingBox(worldIn, pos);
	}

	@Override
	public boolean isCollidable() {
		return baseStone().isCollidable();
	}

	@Override
	public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
		return baseStone().canCollideCheck(state, hitIfLiquid);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return baseStone().canPlaceBlockAt(worldIn, pos);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		onNeighborBlockChange(worldIn, pos, baseStone().getDefaultState(), Blocks.air);
		baseStone().onBlockAdded(worldIn, pos, baseStone().getDefaultState());
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		baseStone().breakBlock(worldIn, pos, baseStone().getDefaultState());
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn) {
		baseStone().onEntityCollidedWithBlock(worldIn, pos, entityIn);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		baseStone().updateTick(worldIn, pos, state, rand);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		return baseStone().onBlockActivated(worldIn, pos, baseStone().getDefaultState(), playerIn, EnumFacing.DOWN, 0.0F, 0.0F, 0.0F);
	}

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
		baseStone().onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
	}

	@Override
	public MapColor getMapColor(IBlockState state) {
		return baseStone().getMapColor(baseStone().getDefaultState());
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		IBlockState state = super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
		state = state.withProperty(BlockStairs.SHAPE, BlockStairs.EnumShape.STRAIGHT);
		return facing != EnumFacing.DOWN && (facing == EnumFacing.UP || hitY <= 0.5D) ? state.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM)
				: state.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP);
	}

	/**
	 * Ray traces through the blocks collision from start vector to end vector
	 * returning a ray trace hit.
	 * 
	 * @param start
	 *            The start vector
	 * @param end
	 *            The end vector
	 */
	public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
		MovingObjectPosition[] amovingobjectposition = new MovingObjectPosition[8];
		IBlockState iblockstate = worldIn.getBlockState(pos);
		int i = facing.getHorizontalIndex();
		boolean flag = iblockstate.getValue(BlockStairs.HALF) == BlockStairs.EnumHalf.TOP;
		int[] aint = field_150150_a[i + (flag ? 4 : 0)];
		this.hasRaytraced = true;

		for (int j = 0; j < 8; ++j) {
			this.rayTracePass = j;

			if (Arrays.binarySearch(aint, j) < 0) {
				amovingobjectposition[j] = super.collisionRayTrace(worldIn, pos, start, end);
			}
		}

		int[] aint1 = aint;
		int k = aint.length;

		for (int l = 0; l < k; ++l) {
			int i1 = aint1[l];
			amovingobjectposition[i1] = null;
		}

		MovingObjectPosition movingobjectposition1 = null;
		double d1 = 0.0D;
		MovingObjectPosition[] amovingobjectposition1 = amovingobjectposition;
		int j1 = amovingobjectposition.length;

		for (int k1 = 0; k1 < j1; ++k1) {
			MovingObjectPosition movingobjectposition = amovingobjectposition1[k1];

			if (movingobjectposition != null) {
				double d0 = movingobjectposition.hitVec.squareDistanceTo(end);

				if (d0 > d1) {
					movingobjectposition1 = movingobjectposition;
					d1 = d0;
				}
			}
		}

		return movingobjectposition1;
	}

	/**
	 * Get the actual Block state of this Block at the given position. This
	 * applies properties not visible in the metadata, such as fence
	 * connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		if (this.func_176306_h(worldIn, pos)) {
			switch (this.func_176305_g(worldIn, pos)) {
			case 0:
				state = state.withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT);
				break;
			case 1:
				state = state.withProperty(BlockStairs.SHAPE, EnumShape.INNER_RIGHT);
				break;
			case 2:
				state = state.withProperty(BlockStairs.SHAPE, EnumShape.INNER_LEFT);
			}
		} else {
			switch (this.func_176307_f(worldIn, pos)) {
			case 0:
				state = state.withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT);
				break;
			case 1:
				state = state.withProperty(BlockStairs.SHAPE, EnumShape.OUTER_RIGHT);
				break;
			case 2:
				state = state.withProperty(BlockStairs.SHAPE, EnumShape.OUTER_LEFT);
			}
		}

		return state;
	}

}
