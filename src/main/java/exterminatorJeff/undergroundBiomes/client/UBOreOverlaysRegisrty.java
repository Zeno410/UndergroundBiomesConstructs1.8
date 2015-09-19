package exterminatorJeff.undergroundBiomes.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.util.ResourceLocation;
import exterminatorJeff.undergroundBiomes.api.OreOverlaysRegistry;
import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;

public class UBOreOverlaysRegisrty implements OreOverlaysRegistry {

	/**
	 * Ore overlays textures locations<br/>
	 * Overlay name -> Overlay location<br/>
	 * Mods must add their overlays to this
	 */
	static final Map<String, ResourceLocation> overlaysLocations = new HashMap<String, ResourceLocation>();
	/**
	 * Ore name (internal name) -> Overlay name
	 */
	static final Map<String, String> oresToOverlays = new HashMap<String, String>();

	@SuppressWarnings("unused")
	private static final String[] providedOverlays = new String[] { "amber_overlay", "cinnabar_overlay", "copper_overlay", "lead_overlay", "manganese_overlay", "olivine-peridot_overlay",
			"ruby_overlay", "sapphire_overlay", "silver_overlay", "tin_overlay", "uranium_overlay" };

	/*
	 * 
	 */

	@Override
	public void addOreOverlay(String oreName, ResourceLocation location) {
		String overlayName = StringUtils.substringAfterLast(location.getResourcePath(), "/");
		UndergroundBiomes.logger.info(oreName + " -> " + overlayName);
		if (!oresToOverlays.containsKey(oreName)) {
			oresToOverlays.put(oreName, overlayName);
			overlaysLocations.put(overlayName, location);
		} else {
			throw new OreAlreadyTexturized(oreName);
		}
	}

	void addVanillaOverlays() {
		addOreOverlay("coal_ore", new ResourceLocation(BLOCKS_TX_PATH + "coal_overlay"));
		addOreOverlay("diamond_ore", new ResourceLocation(BLOCKS_TX_PATH + "diamond_overlay"));
		addOreOverlay("emerald_ore", new ResourceLocation(BLOCKS_TX_PATH + "emerald_overlay"));
		addOreOverlay("gold_ore", new ResourceLocation(BLOCKS_TX_PATH + "gold_overlay"));
		addOreOverlay("iron_ore", new ResourceLocation(BLOCKS_TX_PATH + "iron_overlay"));
		addOreOverlay("lapis_ore", new ResourceLocation(BLOCKS_TX_PATH + "lapis_overlay"));
		addOreOverlay("redstone_ore", new ResourceLocation(BLOCKS_TX_PATH + "redstone_overlay"));
	}

}
