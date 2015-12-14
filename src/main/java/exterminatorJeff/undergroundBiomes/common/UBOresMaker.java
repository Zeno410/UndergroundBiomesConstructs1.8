package exterminatorJeff.undergroundBiomes.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.block.Block;
import exterminatorJeff.undergroundBiomes.api.OreMaker;
import exterminatorJeff.undergroundBiomes.common.block.UBOre;
import exterminatorJeff.undergroundBiomes.common.block.UBOreIgneous;
import exterminatorJeff.undergroundBiomes.common.block.UBOreMetamorphic;
import exterminatorJeff.undergroundBiomes.common.block.UBOreSedimentary;

public final class UBOresMaker implements OreMaker {

	public static final Set<UBOre> UB_ORES = new HashSet<UBOre>();

	public static final Map<Block, UBOre> UB_ORES_IGNEOUS = new HashMap<Block, UBOre>();
	public static final Map<Block, UBOre> UB_ORES_METAMORPHIC = new HashMap<Block, UBOre>();
	public static final Map<Block, UBOre> UB_ORES_SEDIMENTARY = new HashMap<Block, UBOre>();

	public static boolean isUBified(Block ore) {
		return UB_ORES_IGNEOUS.containsKey(ore) || UB_ORES_METAMORPHIC.containsKey(ore) || UB_ORES_SEDIMENTARY.containsKey(ore);
	}

	/*
	 * 
	 */

	private boolean alreadySetup = false;

	/**
	 * 
	 */
	private final HashSet<UBifyRequest> requests = new HashSet<>();

	@Override
	public void setupOre(Block baseOre) {
		if (UndergroundBiomes.isPreInitDone)
			setupWithoutMeta(baseOre, baseOre.getUnlocalizedName().substring(5));
		else
			throw new RuntimeException("UB preInit is not done yet !");
	}

	@Override
	public void setupOre(Block baseOre, String baseOreName) {
		if (UndergroundBiomes.isPreInitDone)
			setupWithoutMeta(baseOre, baseOreName);
		else
			throw new RuntimeException("UB preInit is not done yet !");
	}

	@Override
	public void setupOre(Block baseOre, int meta, String baseOreName) {
		if (UndergroundBiomes.isPreInitDone)
			setupWithMeta(baseOre, meta, baseOreName);
		else
			throw new RuntimeException("UB preInit is not done yet !");
	}

	@Override
	public void requestOreSetup(Block baseOre) throws OreAlreadyUBified {
		if (UndergroundBiomes.isPreInitDone || alreadySetup) {
			throw new OreAlreadyUBified(baseOre);
		} else {
			requests.add(new UBifyRequest(baseOre, baseOre.getUnlocalizedName().substring(5)));
		}
	}

	@Override
	public void requestOreSetup(Block baseOre, String baseOreName) throws OreAlreadyUBified {
		if (UndergroundBiomes.isPreInitDone || alreadySetup) {
			throw new OreAlreadyUBified(baseOre);
		} else {
			requests.add(new UBifyRequest(baseOre, baseOreName));
		}
	}

	@Override
	public void requestOreSetup(Block baseOre, int meta, String baseOreName) throws OreAlreadyUBified {
		if (UndergroundBiomes.isPreInitDone || alreadySetup) {
			throw new OreAlreadyUBified(baseOre);
		} else {
			requests.add(new UBifyRequestWithMetadata(baseOre, meta, baseOreName));
		}
	}

	private void setupWithoutMeta(Block baseOre, String baseOreName) {
		UBOre o1 = new UBOreIgneous(baseOre, baseOreName);
		UBOre o2 = new UBOreMetamorphic(baseOre, baseOreName);
		UBOre o3 = new UBOreSedimentary(baseOre, baseOreName);
		o1.register();
		o2.register();
		o3.register();
		UB_ORES_IGNEOUS.put(baseOre, o1);
		UB_ORES_METAMORPHIC.put(baseOre, o2);
		UB_ORES_SEDIMENTARY.put(baseOre, o3);
	}

	private void setupWithMeta(Block baseOre, int meta, String baseOreName) {
		UBOre o1 = new UBOreIgneous(baseOre, true, meta, baseOreName);
		UBOre o2 = new UBOreMetamorphic(baseOre, true, meta, baseOreName);
		UBOre o3 = new UBOreSedimentary(baseOre, true, meta, baseOreName);
		o1.register();
		o2.register();
		o3.register();
		UB_ORES_IGNEOUS.put(baseOre, o1);
		UB_ORES_METAMORPHIC.put(baseOre, o2);
		UB_ORES_SEDIMENTARY.put(baseOre, o3);
	}

	/**
	 * This should not be called by anything other than Underground Biomes
	 */
	public void fulfillRequests() {
		if (!alreadySetup) {
			alreadySetup = true;
			for (UBifyRequest request : requests) {
				request.fulfill();
			}
			requests.clear();
		}
		UB_ORES.addAll(UB_ORES_IGNEOUS.values());
		UB_ORES.addAll(UB_ORES_METAMORPHIC.values());
		UB_ORES.addAll(UB_ORES_SEDIMENTARY.values());
	}

	/*
	 * 
	 */

	private class UBifyRequest {
		final Block baseOre;
		final String baseOreName;

		UBifyRequest(Block baseOre, String baseOreName) {
			this.baseOre = baseOre;
			this.baseOreName = baseOreName;
		}

		void fulfill() {
			setupWithoutMeta(baseOre, baseOreName);
		}
	}

	private class UBifyRequestWithMetadata extends UBifyRequest {
		final int metadata;

		UBifyRequestWithMetadata(Block baseOre, int metadata, String baseOreName) {
			super(baseOre, baseOreName);
			this.metadata = metadata;
		}

		@Override
		void fulfill() {
			setupWithMeta(baseOre, metadata, baseOreName);
		}
	}

}
