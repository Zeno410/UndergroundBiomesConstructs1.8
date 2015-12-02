package exterminatorJeff.undergroundBiomes.api;

/**
 * A block or item which have metadata variants
 * 
 * @author Spooky4672
 *
 */
public interface Variable extends Registrable {

	public String getVariantName(int meta);

	public String getModelName(int meta);

}
