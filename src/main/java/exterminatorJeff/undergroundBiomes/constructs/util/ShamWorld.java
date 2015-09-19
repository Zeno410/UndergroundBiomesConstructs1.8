package exterminatorJeff.undergroundBiomes.constructs.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

/**
 *
 * @author Zeno410
 */
public class ShamWorld extends World {

	private IBlockState shamState;

	// static ISaveHandler iSaveHandler = null;
	static String name = "Sham World";

	// static WorldProvider WorldProvider = null;
	// static Profiler profiler = null;

	protected ShamWorld(ISaveHandler saveHandlerIn, WorldInfo info, WorldProvider providerIn, Profiler profilerIn, boolean client) {
		super(saveHandlerIn, info, providerIn, profilerIn, client);
		// TODO Auto-generated constructor stub
	}

	// public ShamWorld(WorldSettings settings) {
	// super(null, name, null, settings, null);
	// }

	@Override
	public IBlockState getBlockState(BlockPos pos) {
		return shamState;
	}

	@Override
	protected IChunkProvider createChunkProvider() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Entity getEntityByID(int arg0) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected int getRenderDistanceChunks() {
		// TODO Auto-generated method stub
		return 0;
	}

}