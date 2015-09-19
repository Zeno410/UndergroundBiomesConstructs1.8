package exterminatorJeff.undergroundBiomes.world;

import exterminatorJeff.undergroundBiomes.api.UBEntries;
import exterminatorJeff.undergroundBiomes.api.VanillaBlockEntry;
import exterminatorJeff.undergroundBiomes.common.UBConfig;

public class StrataLayers {

	public StrataLayer[][] layers;

	public StrataLayers() {
		layers = new StrataLayer[30][];
		if (UBConfig.harmoniousStrata()) {
			createHarmoniousLayers();
		} else {
			createLayers();
		}
	}

	private void createLayers() {
		layers[0] = new StrataLayer[] { new StrataLayer(UBEntries.SEDIMENTARY_STONE, 0, 30, 32), new StrataLayer(UBEntries.SEDIMENTARY_STONE, 1, 38, 41),
				new StrataLayer(UBEntries.SEDIMENTARY_STONE, 6, 65, 70) };
		layers[1] = new StrataLayer[] { new StrataLayer(UBEntries.SEDIMENTARY_STONE, 2, 33, 36), new StrataLayer(UBEntries.SEDIMENTARY_STONE, 1, 38, 41),
				new StrataLayer(UBEntries.SEDIMENTARY_STONE, 5, 60, 62), new StrataLayer(UBEntries.SEDIMENTARY_STONE, 7, 75, 80) };
		layers[2] = new StrataLayer[] { new StrataLayer(UBEntries.SEDIMENTARY_STONE, 3, 30, 35), new StrataLayer(UBEntries.SEDIMENTARY_STONE, 4, 26, 29),
				new StrataLayer(UBEntries.METAMORPHIC_STONE, 2, 70, 74) };
		layers[3] = new StrataLayer[] { new StrataLayer(VanillaBlockEntry.sandstone, 0, 40, 43), new StrataLayer(VanillaBlockEntry.sand, 0, 44, 47),
				new StrataLayer(UBEntries.SEDIMENTARY_STONE, 1, 55, 57) };
		layers[4] = new StrataLayer[] { new StrataLayer(UBEntries.SEDIMENTARY_STONE, 7, 29, 33), new StrataLayer(UBEntries.SEDIMENTARY_STONE, 5, 42, 44),
				new StrataLayer(UBEntries.SEDIMENTARY_STONE, 4, 90, 105) };
		layers[5] = new StrataLayer[] { new StrataLayer(UBEntries.SEDIMENTARY_STONE, 2, 33, 35), new StrataLayer(UBEntries.SEDIMENTARY_STONE, 3, 38, 41),
				new StrataLayer(UBEntries.SEDIMENTARY_STONE, 6, 72, 79) };
		layers[6] = new StrataLayer[] { new StrataLayer(UBEntries.METAMORPHIC_STONE, 2, 30, 35), new StrataLayer(UBEntries.SEDIMENTARY_STONE, 0, 39, 44),
				new StrataLayer(VanillaBlockEntry.sandstone, 0, 52, 54), new StrataLayer(VanillaBlockEntry.sand, 0, 55, 60) };
		layers[7] = new StrataLayer[] { new StrataLayer(UBEntries.SEDIMENTARY_STONE, 0, 33, 35), new StrataLayer(UBEntries.SEDIMENTARY_STONE, 3, 45, 49),
				new StrataLayer(UBEntries.SEDIMENTARY_STONE, 6, 80, 85) };
		layers[8] = new StrataLayer[] { new StrataLayer(UBEntries.SEDIMENTARY_STONE, 1, 30, 32), new StrataLayer(UBEntries.METAMORPHIC_STONE, 2, 70, 74),
				new StrataLayer(UBEntries.SEDIMENTARY_STONE, 4, 75, 79) };
		layers[9] = new StrataLayer[] { new StrataLayer(UBEntries.SEDIMENTARY_STONE, 2, 31, 35), new StrataLayer(UBEntries.SEDIMENTARY_STONE, 5, 39, 42),
				new StrataLayer(UBEntries.SEDIMENTARY_STONE, 7, 62, 65) };

		// Layers for vanilla stone biomes
		layers[10] = new StrataLayer[] { new StrataLayer(UBEntries.IGNEOUS_STONE, 2, 12, 18), new StrataLayer(UBEntries.IGNEOUS_STONE, 6, 24, 29),
				new StrataLayer(UBEntries.METAMORPHIC_STONE, 2, 80, 85) };
		layers[11] = new StrataLayer[] { new StrataLayer(UBEntries.IGNEOUS_STONE, 5, 13, 22), new StrataLayer(UBEntries.METAMORPHIC_STONE, 6, 29, 36),
				new StrataLayer(UBEntries.METAMORPHIC_STONE, 3, 80, 128) };

	}

	private void createHarmoniousLayers() {
		layers[0] = new StrataLayer[] { new StrataLayer(UBEntries.SEDIMENTARY_STONE, 0, 30, 32), new StrataLayer(UBEntries.SEDIMENTARY_STONE, 1, 38, 41),
				new StrataLayer(UBEntries.SEDIMENTARY_STONE, 6, 65, 70) };
		layers[5] = new StrataLayer[] { new StrataLayer(UBEntries.SEDIMENTARY_STONE, 2, 33, 36), new StrataLayer(UBEntries.SEDIMENTARY_STONE, 1, 38, 41),
				new StrataLayer(UBEntries.SEDIMENTARY_STONE, 5, 60, 62), new StrataLayer(UBEntries.SEDIMENTARY_STONE, 7, 75, 80) };
		layers[2] = new StrataLayer[] { new StrataLayer(UBEntries.SEDIMENTARY_STONE, 3, 30, 35), new StrataLayer(UBEntries.SEDIMENTARY_STONE, 4, 26, 29),
				new StrataLayer(UBEntries.METAMORPHIC_STONE, 2, 70, 74) };
		layers[3] = new StrataLayer[] { new StrataLayer(VanillaBlockEntry.sandstone, 0, 40, 43), new StrataLayer(VanillaBlockEntry.sand, 0, 44, 47),
				new StrataLayer(UBEntries.SEDIMENTARY_STONE, 1, 55, 57) };
		layers[4] = new StrataLayer[] { new StrataLayer(UBEntries.SEDIMENTARY_STONE, 7, 29, 33), new StrataLayer(UBEntries.SEDIMENTARY_STONE, 5, 42, 44),
				new StrataLayer(UBEntries.SEDIMENTARY_STONE, 4, 90, 105) };
		layers[1] = new StrataLayer[] { new StrataLayer(UBEntries.SEDIMENTARY_STONE, 2, 33, 35), new StrataLayer(UBEntries.SEDIMENTARY_STONE, 3, 38, 41),
				new StrataLayer(UBEntries.SEDIMENTARY_STONE, 6, 72, 79) };
		layers[6] = new StrataLayer[] { new StrataLayer(UBEntries.METAMORPHIC_STONE, 2, 30, 35), new StrataLayer(UBEntries.SEDIMENTARY_STONE, 0, 39, 44),
				new StrataLayer(VanillaBlockEntry.sandstone, 0, 52, 54), new StrataLayer(VanillaBlockEntry.sand, 0, 55, 60) };
		layers[7] = new StrataLayer[] { new StrataLayer(UBEntries.SEDIMENTARY_STONE, 0, 33, 35), new StrataLayer(UBEntries.SEDIMENTARY_STONE, 3, 45, 49),
				new StrataLayer(UBEntries.SEDIMENTARY_STONE, 4, 80, 85) };
		layers[8] = new StrataLayer[] { new StrataLayer(UBEntries.SEDIMENTARY_STONE, 1, 30, 32), new StrataLayer(UBEntries.METAMORPHIC_STONE, 2, 70, 74),
				new StrataLayer(UBEntries.SEDIMENTARY_STONE, 6, 75, 79) };
		layers[9] = new StrataLayer[] { new StrataLayer(UBEntries.SEDIMENTARY_STONE, 2, 31, 35), new StrataLayer(UBEntries.SEDIMENTARY_STONE, 5, 39, 42),
				new StrataLayer(UBEntries.SEDIMENTARY_STONE, 7, 62, 65) };

		// Layers for vanilla stone biomes
		layers[10] = new StrataLayer[] { new StrataLayer(UBEntries.IGNEOUS_STONE, 2, 12, 18), new StrataLayer(UBEntries.IGNEOUS_STONE, 6, 24, 29),
				new StrataLayer(UBEntries.METAMORPHIC_STONE, 2, 80, 85) };
		layers[11] = new StrataLayer[] { new StrataLayer(UBEntries.IGNEOUS_STONE, 5, 13, 22), new StrataLayer(UBEntries.METAMORPHIC_STONE, 6, 29, 36),
				new StrataLayer(UBEntries.METAMORPHIC_STONE, 3, 80, 128) };

	}

}
