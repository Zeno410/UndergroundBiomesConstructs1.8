package exterminatorJeff.undergroundBiomes.common.item;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import exterminatorJeff.undergroundBiomes.api.UBEntries;
import exterminatorJeff.undergroundBiomes.api.UBEntry;
import exterminatorJeff.undergroundBiomes.api.Variable;
import exterminatorJeff.undergroundBiomes.common.UBCreativeTab;

public class ItemFossilPiece extends Item implements Variable {

	public static final int nbValues = FossilType.values().length;

	private static enum FossilType {
		AMMONITE, SHELL, RIB, BONE, SKULL, BONE2, SHELL2, BONESHARD;

		@Override
		public String toString() {
			return this.name().toLowerCase();
		}

		@SuppressWarnings("unused")
		public int getMetadata() {
			return ordinal();
		}
	}

	private final UBEntry entry;

	public ItemFossilPiece() {
		this(UBEntries.fossilPiece);
	}

	public ItemFossilPiece(UBEntry entry) {
		this.entry = entry;
		setMaxDamage(0);
		setHasSubtypes(true);
		setUnlocalizedName(this.entry.internal());
		setCreativeTab(UBCreativeTab.UB_ITEMS_TAB);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tabs, List list) {
		for (int i = 0; i < nbValues; i++) {
			list.add(new ItemStack(itemIn, 1, i));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "." + FossilType.values()[stack.getMetadata()].toString().replace("2", "");
	}

	@Override
	public String getVariantName(int meta) {
		return FossilType.values()[meta].toString();
	}

	@Override
	public String getModelName(int meta) {
		return entry.external() + "_" + getVariantName(meta);
	}

	@Override
	public void register() {
		GameRegistry.registerItem(this, entry.internal());
	}

	@Override
	public void registerModel() {
		for (int i = 0; i < nbValues; i++) {
			ModelBakery.addVariantName(this, getModelName(i));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, i, new ModelResourceLocation(getModelName(i), "inventory"));
		}
	}

}
