package exterminatorJeff.undergroundBiomes.common;

import exterminatorJeff.undergroundBiomes.api.UBEntries;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class FuelManager implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		if (UBEntries.ligniteCoal.getAssociatedItem().equals(fuel.getItem())) {
			return 200;
		} else {
			return 0;
		}
	}

}
