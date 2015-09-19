package exterminatorJeff.undergroundBiomes.common.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import exterminatorJeff.undergroundBiomes.api.Registrable;
import exterminatorJeff.undergroundBiomes.api.UBEntries;
import exterminatorJeff.undergroundBiomes.api.UBEntry;
import exterminatorJeff.undergroundBiomes.common.UBCreativeTab;

public class ItemLigniteCoal extends Item implements Registrable {

	private UBEntry entry;

	public ItemLigniteCoal() {
		this(UBEntries.ligniteCoal);
	}

	public ItemLigniteCoal(UBEntry entry) {
		this.entry = entry;
		setUnlocalizedName(this.entry.internal());
		setCreativeTab(UBCreativeTab.UB_ITEMS_TAB);
	}

	@Override
	public void register() {
		GameRegistry.registerItem(this, entry.internal());
	}

	@Override
	public void registerModel() {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation(entry.external(), "inventory"));
	}
}
