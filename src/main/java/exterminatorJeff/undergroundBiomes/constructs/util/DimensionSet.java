package exterminatorJeff.undergroundBiomes.constructs.util;

import java.util.HashSet;
import java.util.logging.Logger;

import Zeno410Utils.Zeno410Logger;

/**
 * This class contains the logic for manipulating sets of dimensions in the
 * context of mod config setup
 * 
 * @author Zeno410
 */
public class DimensionSet {
	private final boolean ignore;
	private HashSet<Integer> members = new HashSet<Integer>();
	private static Logger logger = new Zeno410Logger("DimensionSet").logger();

	public static class Include extends DimensionSet {
		public Include(String includeDimensions) {
			super(includeDimensions);
		}

		public boolean isIncluded(Integer dimension, Exclude exclusions) {
			if (ignore())
				return !exclusions.contains(dimension);
			return contains(dimension);
		}
	}

	public static class Exclude extends DimensionSet {
		public Exclude(String includeDimensions) {
			super(includeDimensions);
		}
	}

	public boolean ignore() {
		return ignore;
	}

	private DimensionSet(String includeDimensions) {
		if (includeDimensions.equals("*")) {
			ignore = true;
		} else {
			ignore = false;
			for (String v : includeDimensions.split(",")) {
				members.add(Integer.parseInt(v));
			}
		}
	}

	public boolean contains(Integer dimension) {
		return members.contains(dimension);
	}
}
