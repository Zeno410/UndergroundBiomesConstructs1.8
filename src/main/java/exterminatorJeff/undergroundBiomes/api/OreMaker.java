package exterminatorJeff.undergroundBiomes.api;

import net.minecraft.block.Block;

/**
 * 
 * The setupOre methods must be called after UB pre-init<br/>
 * The requestOreSetup methods must be called before UB pre-init<br/>
 * 
 * @author Zeno410
 *
 */
public interface OreMaker {

	public void setupOre(Block baseOre);

	public void setupOre(Block baseOre, String baseOreName);

	public void setupOre(Block baseOre, int meta, String baseOreName);

	public void requestOreSetup(Block baseOre) throws OreAlreadyUBified;

	public void requestOreSetup(Block baseOre, String baseOreName) throws OreAlreadyUBified;

	public void requestOreSetup(Block baseOre, int meta, String baseOreName) throws OreAlreadyUBified;

	public void fulfillRequests();

	/**
	 * This is thrown if UB has already run its pre-init step and can no longer
	 * register blocks
	 */
	@SuppressWarnings("serial")
	public class OreAlreadyUBified extends RuntimeException {
		public final Block oreBlock;

		public OreAlreadyUBified(Block oreBlock) {
			this.oreBlock = oreBlock;
		}

		@Override
		public String toString() {
			return "Attempt to create Underground Biomes ores for " + oreBlock.getUnlocalizedName() + " after blocks have already been defined";
		}

	}

}
