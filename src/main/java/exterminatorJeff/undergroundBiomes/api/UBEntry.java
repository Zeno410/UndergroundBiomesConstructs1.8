package exterminatorJeff.undergroundBiomes.api;

import net.minecraft.item.Item;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;

public abstract class UBEntry {

	private final String internalName;
	protected Registrable obj;
	protected boolean isRegistered = false;

	public UBEntry(String internalName) {
		this.internalName = internalName;
	}

	/**
	 * ex : metamorphic_cobble, igneous_stone_button
	 * 
	 * @return the internal name
	 */
	public String internal() {
		return internalName;
	}

	/**
	 * ex : undergroundbiomes:metamorphic_cobble
	 * 
	 * @return "MODID:internal"
	 */
	public final String external() {
		return UndergroundBiomes.MODID + ":" + internal();
	}

	public final void register(Registrable obj) {
		if (!isRegistered) {
			isRegistered = true;
			doRegister(obj);
		} else
			throw new RuntimeException("This entry is already registered !");
	}

	protected void doRegister(Registrable obj) {
		this.obj = obj;
		this.obj.register();
	}

	public final void registerModel() {
		if (isRegistered)
			obj.registerModel();
	}

	public abstract Item getAssociatedItem();

}
