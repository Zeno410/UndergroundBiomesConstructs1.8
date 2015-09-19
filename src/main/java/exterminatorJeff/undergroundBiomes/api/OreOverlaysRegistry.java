package exterminatorJeff.undergroundBiomes.api;

import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Help registering ore overlays<br/>
 * Mods can use this to register their ore overlays
 * 
 * @author Spooky4672
 *
 */
public interface OreOverlaysRegistry {

	static final String BLOCKS_TX_PATH = UndergroundBiomes.MODID + ":blocks/";
	static final String ITEMS_TX_PATH = UndergroundBiomes.MODID + ":items/";

	/**
	 * Add an ore overlay
	 * 
	 * @param oreName
	 *            : The internal name of the ore (as in {@link OreDictionary})
	 * @param location
	 *            : The location of the texture
	 */
	public void addOreOverlay(String oreName, ResourceLocation location);

	// TODO meta version ?
	@SuppressWarnings("serial")
	public static class OreAlreadyTexturized extends RuntimeException {
		public final String oreName;

		public OreAlreadyTexturized(String oreName) {
			this.oreName = oreName;
		}

		@Override
		public String toString() {
			return "Attempt to texturize Underground Biomes ore " + oreName + " after UB ores have already been texturized !";
		}

	}

}
