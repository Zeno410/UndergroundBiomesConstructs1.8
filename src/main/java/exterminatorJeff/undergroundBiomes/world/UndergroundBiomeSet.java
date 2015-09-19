package exterminatorJeff.undergroundBiomes.world;

import java.util.HashMap;

import exterminatorJeff.undergroundBiomes.api.UBEntries;
import exterminatorJeff.undergroundBiomes.api.VanillaBlockEntry;

/**
 * All underground biomes
 * 
 * @author Zeno410
 */
public final class UndergroundBiomeSet {

	// Singleton instance
	private static UndergroundBiomeSet INSTANCE;

	public static final UndergroundBiomeSet getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new UndergroundBiomeSet();
		else
			return INSTANCE;
	}

	private UndergroundBiomeSet() {
	}

	private final StrataLayers strataLayers = new StrataLayers();

	public final HashMap<Integer, UBBiome> biomeList = new HashMap<Integer, UBBiome>();

	public final UBBiome igneous1 = (new UBBiome(0, UBEntries.IGNEOUS_STONE, 0, biomeList)).setName("Igneous").AddStrataLayers(strataLayers.layers[0]);
	public final UBBiome igneous2 = (new UBBiome(1, UBEntries.IGNEOUS_STONE, 1, biomeList)).setName("Igneous").AddStrataLayers(strataLayers.layers[1]);
	public final UBBiome igneous3 = (new UBBiome(2, UBEntries.IGNEOUS_STONE, 2, biomeList)).setName("Igneous").AddStrataLayers(strataLayers.layers[2]);
	public final UBBiome igneous4 = (new UBBiome(3, UBEntries.IGNEOUS_STONE, 3, biomeList)).setName("Igneous").AddStrataLayers(strataLayers.layers[3]);
	public final UBBiome igneous5 = (new UBBiome(4, UBEntries.IGNEOUS_STONE, 4, biomeList)).setName("Igneous").AddStrataLayers(strataLayers.layers[4]);
	public final UBBiome igneous6 = (new UBBiome(5, UBEntries.IGNEOUS_STONE, 5, biomeList)).setName("Igneous").AddStrataLayers(strataLayers.layers[5]);
	public final UBBiome igneous7 = (new UBBiome(6, UBEntries.IGNEOUS_STONE, 6, biomeList)).setName("Igneous").AddStrataLayers(strataLayers.layers[6]);
	public final UBBiome igneous8 = (new UBBiome(7, UBEntries.IGNEOUS_STONE, 7, biomeList)).setName("Igneous").AddStrataLayers(strataLayers.layers[7]);

	public final UBBiome igneous9 = (new UBBiome(8, UBEntries.IGNEOUS_STONE, 0, biomeList)).setName("Igneous").AddStrataLayers(strataLayers.layers[8]);
	public final UBBiome igneous10 = (new UBBiome(9, UBEntries.IGNEOUS_STONE, 1, biomeList)).setName("Igneous").AddStrataLayers(strataLayers.layers[9]);
	public final UBBiome igneous11 = (new UBBiome(10, UBEntries.IGNEOUS_STONE, 2, biomeList)).setName("Igneous").AddStrataLayers(strataLayers.layers[0]);
	public final UBBiome igneous12 = (new UBBiome(11, UBEntries.IGNEOUS_STONE, 3, biomeList)).setName("Igneous").AddStrataLayers(strataLayers.layers[1]);
	public final UBBiome igneous13 = (new UBBiome(12, UBEntries.IGNEOUS_STONE, 4, biomeList)).setName("Igneous").AddStrataLayers(strataLayers.layers[2]);
	public final UBBiome igneous14 = (new UBBiome(13, UBEntries.IGNEOUS_STONE, 5, biomeList)).setName("Igneous").AddStrataLayers(strataLayers.layers[3]);
	public final UBBiome igneous15 = (new UBBiome(14, UBEntries.IGNEOUS_STONE, 6, biomeList)).setName("Igneous").AddStrataLayers(strataLayers.layers[4]);
	public final UBBiome igneous16 = (new UBBiome(15, UBEntries.IGNEOUS_STONE, 7, biomeList)).setName("Igneous").AddStrataLayers(strataLayers.layers[5]);

	public final UBBiome metamorphic1 = (new UBBiome(16, UBEntries.METAMORPHIC_STONE, 0, biomeList)).setName("Metamorphic").AddStrataLayers(strataLayers.layers[6]);
	public final UBBiome metamorphic2 = (new UBBiome(17, UBEntries.METAMORPHIC_STONE, 1, biomeList)).setName("Metamorphic").AddStrataLayers(strataLayers.layers[7]);
	public final UBBiome metamorphic3 = (new UBBiome(18, UBEntries.METAMORPHIC_STONE, 1, biomeList)).setName("Metamorphic").AddStrataLayers(strataLayers.layers[8]);
	public final UBBiome metamorphic4 = (new UBBiome(19, UBEntries.METAMORPHIC_STONE, 3, biomeList)).setName("Metamorphic").AddStrataLayers(strataLayers.layers[9]);
	public final UBBiome metamorphic5 = (new UBBiome(20, UBEntries.METAMORPHIC_STONE, 4, biomeList)).setName("Metamorphic").AddStrataLayers(strataLayers.layers[0]);
	public final UBBiome metamorphic6 = (new UBBiome(21, UBEntries.METAMORPHIC_STONE, 5, biomeList)).setName("Metamorphic").AddStrataLayers(strataLayers.layers[1]);
	public final UBBiome metamorphic7 = (new UBBiome(22, UBEntries.METAMORPHIC_STONE, 6, biomeList)).setName("Metamorphic").AddStrataLayers(strataLayers.layers[2]);
	public final UBBiome metamorphic8 = (new UBBiome(23, UBEntries.METAMORPHIC_STONE, 7, biomeList)).setName("Metamorphic").AddStrataLayers(strataLayers.layers[3]);

	public final UBBiome metamorphic9 = (new UBBiome(24, UBEntries.METAMORPHIC_STONE, 0, biomeList)).setName("Metamorphic").AddStrataLayers(strataLayers.layers[4]);
	public final UBBiome metamorphic10 = (new UBBiome(25, UBEntries.METAMORPHIC_STONE, 1, biomeList)).setName("Metamorphic").AddStrataLayers(strataLayers.layers[5]);
	public final UBBiome metamorphic11 = (new UBBiome(26, UBEntries.METAMORPHIC_STONE, 1, biomeList)).setName("Metamorphic").AddStrataLayers(strataLayers.layers[6]);
	public final UBBiome metamorphic12 = (new UBBiome(27, UBEntries.METAMORPHIC_STONE, 3, biomeList)).setName("Metamorphic").AddStrataLayers(strataLayers.layers[7]);
	public final UBBiome metamorphic13 = (new UBBiome(28, UBEntries.METAMORPHIC_STONE, 4, biomeList)).setName("Metamorphic").AddStrataLayers(strataLayers.layers[8]);
	public final UBBiome metamorphic14 = (new UBBiome(29, UBEntries.METAMORPHIC_STONE, 5, biomeList)).setName("Metamorphic").AddStrataLayers(strataLayers.layers[9]);
	public final UBBiome metamorphic15 = (new UBBiome(30, UBEntries.METAMORPHIC_STONE, 6, biomeList)).setName("Metamorphic").AddStrataLayers(strataLayers.layers[0]);
	public final UBBiome metamorphic16 = (new UBBiome(31, UBEntries.METAMORPHIC_STONE, 7, biomeList)).setName("Metamorphic").AddStrataLayers(strataLayers.layers[1]);

	public final UBBiome vanillaStone1 = (new UBBiome(32, VanillaBlockEntry.stone, 0, biomeList)).setName("Metamorphic").AddStrataLayers(strataLayers.layers[0]);
	public final UBBiome vanillaStone2 = (new UBBiome(33, VanillaBlockEntry.stone, 0, biomeList)).setName("Metamorphic").AddStrataLayers(strataLayers.layers[1]);
	public final UBBiome vanillaStone3 = (new UBBiome(34, VanillaBlockEntry.stone, 0, biomeList)).setName("Metamorphic").AddStrataLayers(strataLayers.layers[2]);
	public final UBBiome vanillaStone4 = (new UBBiome(35, VanillaBlockEntry.stone, 0, biomeList)).setName("Metamorphic").AddStrataLayers(strataLayers.layers[3]);

}
