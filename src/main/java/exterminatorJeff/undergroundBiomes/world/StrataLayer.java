package exterminatorJeff.undergroundBiomes.world;

import exterminatorJeff.undergroundBiomes.api.BlockEntry;
import net.minecraft.block.state.IBlockState;

public class StrataLayer {

	public final BlockEntry layerBlock;
	public final int metadata;
	public final IBlockState layerBlockState;
	public final int minHeight, maxHeight;

	public StrataLayer(BlockEntry layerBlock, int metadata, int minHeight, int maxHeight) {
		this.layerBlock = layerBlock;
		this.metadata = metadata;
		layerBlockState = layerBlock.getAssociatedBlock().getStateFromMeta(metadata);
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
	}

	public boolean valueIsInLayer(int y) {
		return (y >= minHeight && y <= maxHeight);
	}

}
