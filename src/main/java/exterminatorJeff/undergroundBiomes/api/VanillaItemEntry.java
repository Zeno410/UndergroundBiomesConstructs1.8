package exterminatorJeff.undergroundBiomes.api;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class VanillaItemEntry extends ItemEntry {

	private final Item item;

	public VanillaItemEntry(Item item) {
		super(item.getUnlocalizedName());
		isRegistered = true;
		this.item = item;
	}

	@Override
	public Item getAssociatedItem() {
		return item;
	}

	/*
	 * 
	 */

	public final static VanillaItemEntry axeStone = new VanillaItemEntry(Items.stone_axe);
	public final static VanillaItemEntry blazeRod = new VanillaItemEntry(Items.blaze_rod);
	public final static VanillaItemEntry bow = new VanillaItemEntry(Items.bow);
	public final static VanillaItemEntry brewingStand = new VanillaItemEntry(Items.brewing_stand);
	public final static VanillaItemEntry clay = new VanillaItemEntry(Items.clay_ball);
	public final static VanillaItemEntry coal = new VanillaItemEntry(Items.coal);
	public final static VanillaItemEntry dyePowder = new VanillaItemEntry(Items.dye);
	public final static VanillaItemEntry flint = new VanillaItemEntry(Items.flint);
	public final static VanillaItemEntry goldNugget = new VanillaItemEntry(Items.gold_nugget);
	public final static VanillaItemEntry hoeStone = new VanillaItemEntry(Items.stone_hoe);
	public final static VanillaItemEntry ingotIron = new VanillaItemEntry(Items.iron_ingot);
	public final static VanillaItemEntry pickaxeStone = new VanillaItemEntry(Items.stone_pickaxe);
	public final static VanillaItemEntry redstone = new VanillaItemEntry(Items.redstone);
	public final static VanillaItemEntry redstoneRepeater = new VanillaItemEntry(Items.repeater);
	public final static VanillaItemEntry shovelStone = new VanillaItemEntry(Items.stone_shovel);
	public final static VanillaItemEntry stick = new VanillaItemEntry(Items.stick);
	public final static VanillaItemEntry swordStone = new VanillaItemEntry(Items.stone_sword);

}
