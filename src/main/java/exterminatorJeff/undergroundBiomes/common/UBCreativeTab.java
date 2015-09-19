package exterminatorJeff.undergroundBiomes.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UBCreativeTab extends CreativeTabs {

	public static final UBCreativeTab UB_BLOCKS_TAB = new UBCreativeTab(CreativeTabs.getNextID(), UndergroundBiomes.MODID + "blocks");
	public static final UBCreativeTab UB_ITEMS_TAB = new UBCreativeTab(CreativeTabs.getNextID(), UndergroundBiomes.MODID + "items");

	private Item icon;

	public UBCreativeTab(int id, String name) {
		super(id, name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return icon;
	}

	@SideOnly(Side.CLIENT)
	public void setTabIconItem(Item icon) {
		this.icon = icon;
	}

}
