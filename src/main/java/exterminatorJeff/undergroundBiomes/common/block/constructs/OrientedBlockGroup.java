package exterminatorJeff.undergroundBiomes.common.block.constructs;

import java.util.HashMap;
import java.util.Map;

import exterminatorJeff.undergroundBiomes.api.OrientedBlockEntry;
import exterminatorJeff.undergroundBiomes.api.Registrable;
import exterminatorJeff.undergroundBiomes.common.item.construct.OrientedItemBlock;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;

public abstract class OrientedBlockGroup implements Registrable {

	protected OrientedItemBlock itemBlock;

	public OrientedItemBlock getItem() {
		return itemBlock;
	}

	protected final Map<EnumFacing, OrientedBlock> instances = new HashMap<EnumFacing, OrientedBlock>(6);
	protected final OrientedBlockEntry entry;

	public OrientedBlockGroup(OrientedBlockEntry entry, OrientedBlock... blocks) {
		this.entry = entry;
		for (OrientedBlock block : blocks) {
			instances.put(block.getFacing(), block);
		}
	}

	@Override
	public void registerModel() {
		itemBlock.registerModel();
	}

	public Block getBlock(EnumFacing facing) {
		return (Block) instances.get(facing);
	}
}
