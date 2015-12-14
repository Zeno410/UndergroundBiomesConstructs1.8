package exterminatorJeff.undergroundBiomes.world;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import exterminatorJeff.undergroundBiomes.api.UBEntries;
import exterminatorJeff.undergroundBiomes.api.VanillaBlockEntry;
import exterminatorJeff.undergroundBiomes.common.UBConfig;

/**
 * All underground biomes
 * 
 * @author Zeno410
 */
public enum UndergroundBiomeSet {
	INSTANCE;

	private final StrataLayers strataLayers = new StrataLayers();

	public final Map<Integer, UBBiome> biomeList;

	private UndergroundBiomeSet() {
		Builder<Integer, UBBiome> mb = ImmutableMap.builder();

		mb.put(0, new UBBiome("Igneous", UBEntries.IGNEOUS_STONE, 0).addStrataLayers(strataLayers.layers[0]));
		mb.put(1, new UBBiome("Igneous", UBEntries.IGNEOUS_STONE, 1).addStrataLayers(strataLayers.layers[1]));
		mb.put(2, new UBBiome("Igneous", UBEntries.IGNEOUS_STONE, 2).addStrataLayers(strataLayers.layers[2]));
		mb.put(3, new UBBiome("Igneous", UBEntries.IGNEOUS_STONE, 3).addStrataLayers(strataLayers.layers[3]));
		mb.put(4, new UBBiome("Igneous", UBEntries.IGNEOUS_STONE, 4).addStrataLayers(strataLayers.layers[4]));
		mb.put(5, new UBBiome("Igneous", UBEntries.IGNEOUS_STONE, 5).addStrataLayers(strataLayers.layers[5]));
		mb.put(6, new UBBiome("Igneous", UBEntries.IGNEOUS_STONE, 6).addStrataLayers(strataLayers.layers[6]));
		mb.put(7, new UBBiome("Igneous", UBEntries.IGNEOUS_STONE, 7).addStrataLayers(strataLayers.layers[7]));

		mb.put(8, new UBBiome("Igneous", UBEntries.IGNEOUS_STONE, 0).addStrataLayers(strataLayers.layers[8]));
		mb.put(9, new UBBiome("Igneous", UBEntries.IGNEOUS_STONE, 1).addStrataLayers(strataLayers.layers[9]));
		mb.put(10, new UBBiome("Igneous", UBEntries.IGNEOUS_STONE, 2).addStrataLayers(strataLayers.layers[0]));
		mb.put(11, new UBBiome("Igneous", UBEntries.IGNEOUS_STONE, 3).addStrataLayers(strataLayers.layers[1]));
		mb.put(12, new UBBiome("Igneous", UBEntries.IGNEOUS_STONE, 4).addStrataLayers(strataLayers.layers[2]));
		mb.put(13, new UBBiome("Igneous", UBEntries.IGNEOUS_STONE, 5).addStrataLayers(strataLayers.layers[3]));
		mb.put(14, new UBBiome("Igneous", UBEntries.IGNEOUS_STONE, 6).addStrataLayers(strataLayers.layers[4]));
		mb.put(15, new UBBiome("Igneous", UBEntries.IGNEOUS_STONE, 7).addStrataLayers(strataLayers.layers[5]));

		mb.put(16, new UBBiome("Metamorphic", UBEntries.METAMORPHIC_STONE, 0).addStrataLayers(strataLayers.layers[6]));
		mb.put(17, new UBBiome("Metamorphic", UBEntries.METAMORPHIC_STONE, 1).addStrataLayers(strataLayers.layers[7]));
		mb.put(18, new UBBiome("Metamorphic", UBEntries.METAMORPHIC_STONE, 2).addStrataLayers(strataLayers.layers[8]));
		mb.put(19, new UBBiome("Metamorphic", UBEntries.METAMORPHIC_STONE, 3).addStrataLayers(strataLayers.layers[9]));
		mb.put(20, new UBBiome("Metamorphic", UBEntries.METAMORPHIC_STONE, 4).addStrataLayers(strataLayers.layers[0]));
		mb.put(21, new UBBiome("Metamorphic", UBEntries.METAMORPHIC_STONE, 5).addStrataLayers(strataLayers.layers[1]));
		mb.put(22, new UBBiome("Metamorphic", UBEntries.METAMORPHIC_STONE, 6).addStrataLayers(strataLayers.layers[2]));
		mb.put(23, new UBBiome("Metamorphic", UBEntries.METAMORPHIC_STONE, 7).addStrataLayers(strataLayers.layers[3]));

		mb.put(24, new UBBiome("Metamorphic", UBEntries.METAMORPHIC_STONE, 0).addStrataLayers(strataLayers.layers[4]));
		mb.put(25, new UBBiome("Metamorphic", UBEntries.METAMORPHIC_STONE, 1).addStrataLayers(strataLayers.layers[5]));
		mb.put(26, new UBBiome("Metamorphic", UBEntries.METAMORPHIC_STONE, 2).addStrataLayers(strataLayers.layers[6]));
		mb.put(27, new UBBiome("Metamorphic", UBEntries.METAMORPHIC_STONE, 3).addStrataLayers(strataLayers.layers[7]));
		mb.put(28, new UBBiome("Metamorphic", UBEntries.METAMORPHIC_STONE, 4).addStrataLayers(strataLayers.layers[8]));
		mb.put(29, new UBBiome("Metamorphic", UBEntries.METAMORPHIC_STONE, 5).addStrataLayers(strataLayers.layers[9]));
		mb.put(30, new UBBiome("Metamorphic", UBEntries.METAMORPHIC_STONE, 6).addStrataLayers(strataLayers.layers[0]));
		mb.put(31, new UBBiome("Metamorphic", UBEntries.METAMORPHIC_STONE, 7).addStrataLayers(strataLayers.layers[1]));

		if (UBConfig.vanillaStoneBiomes()) {
			mb.put(32, new UBBiome("Metamorphic", VanillaBlockEntry.stone, 0).addStrataLayers(strataLayers.layers[0]));
			mb.put(33, new UBBiome("Metamorphic", VanillaBlockEntry.stone, 0).addStrataLayers(strataLayers.layers[1]));
			mb.put(34, new UBBiome("Metamorphic", VanillaBlockEntry.stone, 0).addStrataLayers(strataLayers.layers[2]));
			mb.put(35, new UBBiome("Metamorphic", VanillaBlockEntry.stone, 0).addStrataLayers(strataLayers.layers[3]));
		}

		biomeList = mb.build();

	}

}
