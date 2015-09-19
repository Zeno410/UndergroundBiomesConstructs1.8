package exterminatorJeff.undergroundBiomes.common.block;

import exterminatorJeff.undergroundBiomes.api.Registrable;
import exterminatorJeff.undergroundBiomes.common.block.slab.UBStoneSlab;

/**
 *
 * @author Zeno410, Spooky4672
 */
public class UBSlabPair implements Registrable {

	public final UBStoneSlab half;
	public final UBStoneSlab full;

	public UBSlabPair(UBStoneSlab half, UBStoneSlab full) {
		this.half = half;
		this.full = full;
		if (half.isDouble())
			throw new RuntimeException();
		if (!full.isDouble())
			throw new RuntimeException();
	}

	public UBStoneSlab getOther(UBStoneSlab slab) {
		return slab.isDouble() ? half : full;
	}

	@Override
	public void register() {
		half.register();
		full.register();
	}

	@Override
	public void registerModel() {
		half.registerModel();
		full.registerModel();
	}

}
