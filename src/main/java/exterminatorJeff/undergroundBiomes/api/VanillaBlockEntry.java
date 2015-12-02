package exterminatorJeff.undergroundBiomes.api;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public final class VanillaBlockEntry extends BlockEntry {

	private final Block block;

	public VanillaBlockEntry(Block block) {
		super(block.getUnlocalizedName());
		isRegistered = true;
		this.block = block;
	}

	@Override
	public Item getAssociatedItem() {
		return Item.getItemFromBlock(block);
	}

	@Override
	public Block getAssociatedBlock() {
		return block;
	}

	/*
	 * 
	 */

	public final static VanillaBlockEntry cobblestone = new VanillaBlockEntry(Blocks.cobblestone);
	public final static VanillaBlockEntry cobblestone_wall = new VanillaBlockEntry(Blocks.cobblestone_wall);
	public final static VanillaBlockEntry dispenser = new VanillaBlockEntry(Blocks.dispenser);
	public final static VanillaBlockEntry furnace = new VanillaBlockEntry(Blocks.furnace);

	public final static VanillaBlockEntry lever = new VanillaBlockEntry(Blocks.lever);
	public final static VanillaBlockEntry piston = new VanillaBlockEntry(Blocks.piston);
	public final static VanillaBlockEntry planks = new VanillaBlockEntry(Blocks.planks);
	public final static VanillaBlockEntry stone_pressure_plate = new VanillaBlockEntry(Blocks.stone_pressure_plate);

	public final static VanillaBlockEntry sand = new VanillaBlockEntry(Blocks.sand);
	public final static VanillaBlockEntry sandstone = new VanillaBlockEntry(Blocks.sandstone);
	public final static VanillaBlockEntry smoothSandstone = new VanillaBlockEntry(Blocks.sandstone);
	public final static VanillaBlockEntry stairsCobblestone = new VanillaBlockEntry(Blocks.stone_stairs);
	public final static VanillaBlockEntry stairsStoneBrick = new VanillaBlockEntry(Blocks.stone_brick_stairs);
	public final static VanillaBlockEntry stone = new VanillaBlockEntry(Blocks.stone);
	public final static VanillaBlockEntry stoneBrick = new VanillaBlockEntry(Blocks.stonebrick);
	public final static VanillaBlockEntry stoneButton = new VanillaBlockEntry(Blocks.stone_button);
	public final static VanillaBlockEntry stoneSingleSlab = new VanillaBlockEntry(Blocks.stone_slab);
	public final static VanillaBlockEntry torchRedstoneActive = new VanillaBlockEntry(Blocks.lit_redstone_lamp);

}
