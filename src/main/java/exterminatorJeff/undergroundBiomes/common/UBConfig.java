package exterminatorJeff.undergroundBiomes.common;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import Zeno410Utils.ConfigManager;
import exterminatorJeff.undergroundBiomes.api.UndergroundBiomesSettings;

public final class UBConfig {

	public static final boolean addOreDictRecipes() {
		return settings.addOreDictRecipes.value();
	}

	public static final boolean vanillaStoneBiomes() {
		return settings.vanillaStoneBiomes.value();
	}

	public static final boolean buttonsOn() {
		return settings.buttonsOn.value();
	}

	public static final boolean stairsOn() {
		return settings.stairsOn.value();
	}

	public static final boolean wallsOn() {
		return settings.wallsOn.value();
	}

	public static final boolean harmoniousStrata() {
		return settings.harmoniousStrata.value();
	}

	public static final boolean replaceCobblestone() {
		return settings.replaceCobblestone.value();
	}

	public static final boolean imposeUBStone() {
		return settings.imposeUBStone.value();
	}

	public static final boolean replaceVillageGravel() {
		return settings.replaceVillageGravel.value();
	}

	public static final boolean crashOnProblems() {
		return settings.crashOnProblems.value();
	}

	public static final boolean clearVarsForRecursiveGeneration() {
		return settings.clearVarsForRecursiveGeneration.value();
	}

	public static final boolean forceConfigIds() {
		return settings.forceConfigIds.value();
	}

	public static final boolean ubOres() {
		return settings.ubOres.value();
	}

	public static final int biomeSize() {
		return settings.biomeSize.value();
	}

	public static final String excludeDimensions() {
		return settings.excludeDimensions.value();
	}

	public static final String includeDimensions() {
		return settings.includeDimensions.value();
	}

	public static final int generateHeight() {
		return settings.generateHeight.value();
	}

	public static final int vanillaStoneCrafting() {
		return settings.vanillaStoneCrafting.value();
	}

	public static final float hardnessModifier() {
		return settings.hardnessModifier.value().floatValue();
	}

	public static final float resistanceModifier() {
		return settings.resistanceModifier.value().floatValue() / 3.0F;
	}

	public static final boolean isUBActive() {
		return settings.ubActive.value();
	}

	public static final boolean dimensionSpecificSeeds() {
		return settings.dimensionSpecificSeeds.value();
	}

	public static final boolean inChunkGeneration() {
		return settings.inChunkGeneration.value();
	}

	public static final String inChunkGenerationExclude() {
		return settings.inChunkGenerationExclude.value();
	}

	public static final String inChunkGenerationInclude() {
		return settings.inChunkGenerationInclude.value();
	}

	public static final UndergroundBiomesSettings settings = new UndergroundBiomesSettings();

	private Configuration config;
	@SuppressWarnings("unused")
	private ConfigManager<UndergroundBiomesSettings> configManager;

	private List<Integer> includeDimensionIDs;
	private List<Integer> excludeDimensionIDs;

	public void initConfig(FMLPreInitializationEvent e) {
		config = new Configuration(e.getSuggestedConfigurationFile());
		config.load();

		settings.readFrom(config);
		configManager = new ConfigManager<UndergroundBiomesSettings>(config, settings, e.getSuggestedConfigurationFile());

		if (includeDimensions().equals("*")) {
			excludeDimensionIDs = new ArrayList<Integer>();
			for (String v : excludeDimensions().split(",")) {
				excludeDimensionIDs.add(Integer.parseInt(v));
			}
		} else {
			includeDimensionIDs = new ArrayList<Integer>();
			for (String v : includeDimensions().split(",")) {
				includeDimensionIDs.add(Integer.parseInt(v));
			}
		}

		config.save();
	}

}
