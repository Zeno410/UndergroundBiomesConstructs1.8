package exterminatorJeff.undergroundBiomes.api;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import Zeno410Utils.Mutable;
import Zeno410Utils.Settings;
import Zeno410Utils.Streamer;

/**
 * TODO Translate the config
 * @author Zeno410
 */
public final class UndergroundBiomesSettings extends Settings {

	public static Streamer<UndergroundBiomesSettings> streamer(final UndergroundBiomesSettings setting) {
		return new Streamer<UndergroundBiomesSettings>() {

			@Override
			public UndergroundBiomesSettings readFrom(DataInput input) throws IOException {
				setting.readFrom(input);
				return setting;
			}

			@Override
			public void writeTo(UndergroundBiomesSettings written, DataOutput output) throws IOException {
				written.writeTo(output);
			}

		};
	}

	// private final Category blockCategory = category("block");
	// private final Category itemCategory = category("item");

	public final Mutable<Boolean> addOreDictRecipes = general().booleanSetting("oreDictifyStone", true, "Modify all recipes to include Underground Biomes blocks");

	public final Mutable<Boolean> vanillaStoneBiomes = general().booleanSetting("vanillaStoneBiomes", false,
			"Will cause sharp biome transitions if changed while playing the same world");

	/*
	 * Buttons
	 */

	public final Mutable<Boolean> buttonsOn = general().booleanSetting("UndergroundBiomesButtons", true, "Provide buttons for Underground Biomes blocks");
	public final Mutable<Integer> buttonsTypes = general().intSetting("UndergroundBiomesButtonsTypes", 7, "What types of stone for buttons : +1 for igneous, +2 for metamorphic and +4 for sedimentary\nDefault : 7 -> all");
	public final Mutable<Integer> buttonsStyles = general().intSetting("UndergroundBiomesButtonsStyles", 3, "What styles of stone for buttons : +1 for stone, +2 for cobble and +4 for brick\nDefault : 3 -> stone and cobble");

	/*
	 * Stairs
	 */

	public final Mutable<Boolean> stairsOn = general().booleanSetting("UndergroundBiomesStairs", true, "Provide Stairs for Underground Biomes blocks");
	public final Mutable<Integer> stairsTypes = general().intSetting("UndergroundBiomesStairsTypes", 7, "What types of stone for stairs : +1 for igneous, +2 for metamorphic and +4 for sedimentary\nDefault : 7 -> all");
	public final Mutable<Integer> stairsStyles = general().intSetting("UndergroundBiomesStairsStyles", 7, "What styles of stone for stairs : +1 for stone, +2 for cobble and +4 for brick\nDefault : 7 -> all");

	/*
	 * Walls
	 */
	
	public final Mutable<Boolean> wallsOn = general().booleanSetting("UndergroundBiomesWalls", true, "Provide Walls for Underground Biomes blocks");
	public final Mutable<Integer> wallsTypes = general().intSetting("UndergroundBiomesWallsTypes", 7, "What types of stone for walls : +1 for igneous, +2 for metamorphic and +4 for sedimentary\nDefault : 7 -> all");
	public final Mutable<Integer> wallsStyles = general().intSetting("UndergroundBiomesWallsStyles", 7, "What styles of stone for walls : +1 for stone, +2 for cobble and +4 for brick\nDefault : 7 -> all");

	public final Mutable<Boolean> harmoniousStrata = general().booleanSetting("HarmoniousStrata", false, "Avoid jarring strata transitions");

	public final Mutable<Boolean> replaceCobblestone = general().booleanSetting("replaceCobblestone", true,
			"Swap vanilla cobble and slabs with Underground Biomes where appropriate, plus some village changes");

	public final Mutable<Boolean> imposeUBStone = general().booleanSetting("ImposeUBStone", "Impose UB stone on other mods specially programmed for (currently Highlands)", true);

	public final Mutable<Boolean> replaceVillageGravel = general().booleanSetting("ReplaceVillageGravel", false, "Replace village gravel with brick");

	public final Mutable<Boolean> crashOnProblems = general().booleanSetting("CrashOnProblems", false, "Crash rather than try to get by when encountering problems");

	public final Mutable<Boolean> clearVarsForRecursiveGeneration = general().booleanSetting("clearVarsForRecursiveGeneration", false,
			"Clear the world var in BiomeGenBase for recursive generation");

	public final Mutable<Boolean> ubOres = general().booleanSetting("UBifyOres", true, "Convert ores to have Underground Biomes stone backgrounds");

	public final Mutable<Integer> biomeSize = general().intSetting("biomeSize", 4, "Interval : [1-16]; Warning: exponential");

	public final Mutable<String> excludeDimensions = general().stringSetting("excludeDimensionIDs", "-1,1",
			"Comma-separated list of dimension IDs, used only if include list is *");

	public final Mutable<String> includeDimensions = general().stringSetting("includeDimensionIDs", "*", "Comma-separated list of dimension IDs, put * to use exclude list");

	public final Mutable<Integer> generateHeight = general().intSetting("generateHeight", 256, "Highest block to generated UB stone for");

	public final Mutable<Integer> vanillaStoneCrafting = general().intSetting("vanillaStoneCrafting", 4,
			"0 = none; 1 = one rock; 2 = with redstone; 3 = 2x2 stone, lose 3; 4 = 2x2 stone");

	public final Mutable<Double> hardnessModifier = general().doubleSetting("hardnessModifier", 1.5, "Increase to make stone longer to mine. Normal is 1.5");

	public final Mutable<Double> resistanceModifier = general().doubleSetting("resistanceModifier", 6.0, "Increase to make stone more resistant to explosions. Normal is 6.0");

	public final Mutable<Boolean> ubActive = general().booleanSetting("undergroundBiomesActive", "True if Underground Biomes is supposed to replace stones", true);

	public final Mutable<Boolean> dimensionSpecificSeeds = general().booleanSetting("DimensionSpecificSeeds", false, "Use different seeds in different dimensions");

	public final Mutable<Boolean> inChunkGeneration = general().booleanSetting("InChunkGeneration", false, "Change stones during chunk generation");

	public final Mutable<String> inChunkGenerationExclude = general().stringSetting("inChunkDimensionExclusions", "-1,1",
			"Comma-separated list of dimension to only use old decoration-phase generation, used only if inclusion list is *");

	public final Mutable<String> inChunkGenerationInclude = general().stringSetting("inChunkDimensionInclusions", "0",
			"Comma-separated list of dimension IDs to allow new chunk-phase decoration, put * to use exclusion list");

}
