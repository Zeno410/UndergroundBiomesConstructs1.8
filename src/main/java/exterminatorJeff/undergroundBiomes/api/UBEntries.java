package exterminatorJeff.undergroundBiomes.api;

/**
 * 
 * @author Spooky4672
 *
 */
public final class UBEntries {

	/*
	 * ------ Stones ------
	 */

	public static final BlockEntry IGNEOUS_STONE = new BlockEntry("igneous_stone");
	public static final BlockEntry IGNEOUS_COBBLE = new BlockEntry("igneous_cobble");
	public static final BlockEntry IGNEOUS_BRICK = new BlockEntry("igneous_brick");
	public static final BlockEntry METAMORPHIC_STONE = new BlockEntry("metamorphic_stone");
	public static final BlockEntry METAMORPHIC_COBBLE = new BlockEntry("metamorphic_cobble");
	public static final BlockEntry METAMORPHIC_BRICK = new BlockEntry("metamorphic_brick");
	public static final BlockEntry SEDIMENTARY_STONE = new BlockEntry("sedimentary_stone");

	/*
	 * ----- Items -----
	 */

	public static final UBEntry ligniteCoal = new ItemEntry("ligniteCoal");
	public static final UBEntry fossilPiece = new ItemEntry("fossil_piece");

	/*
	 * ----- Slabs -----
	 */

	public static final SlabEntry IGNEOUS_STONE_SLAB = new SlabEntry(IGNEOUS_STONE);
	public static final SlabEntry IGNEOUS_COBBLE_SLAB = new SlabEntry(IGNEOUS_COBBLE);
	public static final SlabEntry IGNEOUS_BRICK_SLAB = new SlabEntry(IGNEOUS_BRICK);
	public static final SlabEntry METAMORPHIC_STONE_SLAB = new SlabEntry(METAMORPHIC_STONE);
	public static final SlabEntry METAMORPHIC_COBBLE_SLAB = new SlabEntry(METAMORPHIC_COBBLE);
	public static final SlabEntry METAMORPHIC_BRICK_SLAB = new SlabEntry(METAMORPHIC_BRICK);
	public static final SlabEntry SEDIMENTARY_STONE_SLAB = new SlabEntry(SEDIMENTARY_STONE);

	/*
	 * ------- Buttons -------
	 */

	public static final ButtonEntry IGNEOUS_STONE_BUTTON = new ButtonEntry(IGNEOUS_STONE);
	public static final ButtonEntry IGNEOUS_COBBLE_BUTTON = new ButtonEntry(IGNEOUS_COBBLE);
	public static final ButtonEntry METAMORPHIC_STONE_BUTTON = new ButtonEntry(METAMORPHIC_STONE);
	public static final ButtonEntry METAMORPHIC_COBBLE_BUTTON = new ButtonEntry(METAMORPHIC_COBBLE);
	public static final ButtonEntry SEDIMENTARY_STONE_BUTTON = new ButtonEntry(SEDIMENTARY_STONE);

	/*
	 * ----- Walls -----
	 */

	public static final BlockEntry IGNEOUS_WALL = new BlockEntry("igneous_wall");
	public static final BlockEntry METAMORPHIC_WALL = new BlockEntry("metamorphic_wall");

	/*
	 * ------ Stairs ------
	 */

	public static final StairsEntry IGNEOUS_STONE_STAIRS = new StairsEntry(IGNEOUS_STONE);
	public static final StairsEntry IGNEOUS_COBBLE_STAIRS = new StairsEntry(IGNEOUS_COBBLE);
	public static final StairsEntry IGNEOUS_BRICK_STAIRS = new StairsEntry(IGNEOUS_BRICK);
	public static final StairsEntry METAMORPHIC_STONE_STAIRS = new StairsEntry(METAMORPHIC_STONE);
	public static final StairsEntry METAMORPHIC_COBBLE_STAIRS = new StairsEntry(METAMORPHIC_COBBLE);
	public static final StairsEntry METAMORPHIC_BRICK_STAIRS = new StairsEntry(METAMORPHIC_BRICK);
	public static final StairsEntry SEDIMENTARY_STONE_STAIRS = new StairsEntry(SEDIMENTARY_STONE);

	/*
	 * 
	 */

	private UBEntries() {
	}

}
