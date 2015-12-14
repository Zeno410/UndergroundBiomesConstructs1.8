package exterminatorJeff.undergroundBiomes.common;

import static net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE;
import exterminatorJeff.undergroundBiomes.common.block.IgneousCobble;
import exterminatorJeff.undergroundBiomes.common.block.IgneousStone;
import exterminatorJeff.undergroundBiomes.api.ButtonEntry;
import exterminatorJeff.undergroundBiomes.api.StairsEntry;
import exterminatorJeff.undergroundBiomes.api.UBAPIHook;
import exterminatorJeff.undergroundBiomes.api.UBEntries;
import exterminatorJeff.undergroundBiomes.api.VanillaBlockEntry;
import exterminatorJeff.undergroundBiomes.api.VanillaItemEntry;
import exterminatorJeff.undergroundBiomes.client.UBOreOverlaysRegistry;
import exterminatorJeff.undergroundBiomes.common.block.IgneousBrick;
import exterminatorJeff.undergroundBiomes.common.block.MetamorphicCobble;
import exterminatorJeff.undergroundBiomes.common.block.MetamorphicStone;
import exterminatorJeff.undergroundBiomes.common.block.MetamorphicBrick;
import exterminatorJeff.undergroundBiomes.common.block.SedimentaryStone;
import exterminatorJeff.undergroundBiomes.common.block.UBSlabPair;
import exterminatorJeff.undergroundBiomes.common.block.constructs.UBButtonGroup;
import exterminatorJeff.undergroundBiomes.common.block.constructs.UBButtonIgneous;
import exterminatorJeff.undergroundBiomes.common.block.constructs.UBButtonIgneousCobble;
import exterminatorJeff.undergroundBiomes.common.block.constructs.UBButtonMetamorphic;
import exterminatorJeff.undergroundBiomes.common.block.constructs.UBButtonMetamorphicCobble;
import exterminatorJeff.undergroundBiomes.common.block.constructs.UBButtonSedimentary;
import exterminatorJeff.undergroundBiomes.common.block.constructs.UBStairsGroup;
import exterminatorJeff.undergroundBiomes.common.block.constructs.UBStairsIgneous;
import exterminatorJeff.undergroundBiomes.common.block.constructs.UBStairsIgneousBrick;
import exterminatorJeff.undergroundBiomes.common.block.constructs.UBStairsIgneousCobble;
import exterminatorJeff.undergroundBiomes.common.block.constructs.UBStairsMetamorphic;
import exterminatorJeff.undergroundBiomes.common.block.constructs.UBStairsMetamorphicBrick;
import exterminatorJeff.undergroundBiomes.common.block.constructs.UBStairsMetamorphicCobble;
import exterminatorJeff.undergroundBiomes.common.block.constructs.UBStairsSedimentary;
import exterminatorJeff.undergroundBiomes.common.block.constructs.UBWallIgneous;
import exterminatorJeff.undergroundBiomes.common.block.constructs.UBWallMetamorphic;
import exterminatorJeff.undergroundBiomes.common.block.slab.UBIgneousBrickSlabDouble;
import exterminatorJeff.undergroundBiomes.common.block.slab.UBIgneousBrickSlabHalf;
import exterminatorJeff.undergroundBiomes.common.block.slab.UBIgneousCobbleSlabDouble;
import exterminatorJeff.undergroundBiomes.common.block.slab.UBIgneousCobbleSlabHalf;
import exterminatorJeff.undergroundBiomes.common.block.slab.UBIgneousStoneSlabDouble;
import exterminatorJeff.undergroundBiomes.common.block.slab.UBIgneousStoneSlabHalf;
import exterminatorJeff.undergroundBiomes.common.block.slab.UBMetamorphicBrickSlabDouble;
import exterminatorJeff.undergroundBiomes.common.block.slab.UBMetamorphicBrickSlabHalf;
import exterminatorJeff.undergroundBiomes.common.block.slab.UBMetamorphicCobbleSlabDouble;
import exterminatorJeff.undergroundBiomes.common.block.slab.UBMetamorphicCobbleSlabHalf;
import exterminatorJeff.undergroundBiomes.common.block.slab.UBMetamorphicStoneSlabDouble;
import exterminatorJeff.undergroundBiomes.common.block.slab.UBMetamorphicStoneSlabHalf;
import exterminatorJeff.undergroundBiomes.common.block.slab.UBSedimentaryStoneSlabDouble;
import exterminatorJeff.undergroundBiomes.common.block.slab.UBSedimentaryStoneSlabHalf;
import exterminatorJeff.undergroundBiomes.common.item.ItemFossilPiece;
import exterminatorJeff.undergroundBiomes.common.item.ItemLigniteCoal;
import exterminatorJeff.undergroundBiomes.common.item.VanillaStoneRecipeManager;
import exterminatorJeff.undergroundBiomes.world.UBWorldGenManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {
		UBAPIHook.UB_ORES_MAKER = new UBOresMaker();
		UBAPIHook.UB_ORES_OVERLAYS_REGISTRY = new UBOreOverlaysRegistry();

		UndergroundBiomes.config.initConfig(e);

		createBlocks();
		createItems();
		createOres();

		UBAPIHook.UB_ORES_MAKER.fulfillRequests();
	}

	public void init(FMLInitializationEvent e) {
		createRecipes();
	}

	public void postInit(FMLPostInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new UBWorldGenManager());
	}

	/**
	 * 
	 */

	private final void createBlocks() {
		/*
		 * Blocks
		 */
		UBEntries.IGNEOUS_STONE.register(new IgneousStone());
		UBEntries.IGNEOUS_COBBLE.register(new IgneousCobble());
		UBEntries.IGNEOUS_BRICK.register(new IgneousBrick());
		UBEntries.METAMORPHIC_STONE.register(new MetamorphicStone());
		UBEntries.METAMORPHIC_COBBLE.register(new MetamorphicCobble());
		UBEntries.METAMORPHIC_BRICK.register(new MetamorphicBrick());
		UBEntries.SEDIMENTARY_STONE.register(new SedimentaryStone());
		/*
		 * Slabs
		 */
		UBEntries.IGNEOUS_BRICK_SLAB.register(new UBSlabPair(new UBIgneousBrickSlabHalf(UBEntries.IGNEOUS_BRICK_SLAB), new UBIgneousBrickSlabDouble(UBEntries.IGNEOUS_BRICK_SLAB)));
		UBEntries.METAMORPHIC_BRICK_SLAB
				.register(new UBSlabPair(new UBMetamorphicBrickSlabHalf(UBEntries.METAMORPHIC_BRICK_SLAB), new UBMetamorphicBrickSlabDouble(UBEntries.METAMORPHIC_BRICK_SLAB)));
		UBEntries.IGNEOUS_STONE_SLAB.register(new UBSlabPair(new UBIgneousStoneSlabHalf(UBEntries.IGNEOUS_STONE_SLAB), new UBIgneousStoneSlabDouble(UBEntries.IGNEOUS_STONE_SLAB)));
		UBEntries.METAMORPHIC_STONE_SLAB
				.register(new UBSlabPair(new UBMetamorphicStoneSlabHalf(UBEntries.METAMORPHIC_STONE_SLAB), new UBMetamorphicStoneSlabDouble(UBEntries.METAMORPHIC_STONE_SLAB)));
		UBEntries.IGNEOUS_COBBLE_SLAB
				.register(new UBSlabPair(new UBIgneousCobbleSlabHalf(UBEntries.IGNEOUS_COBBLE_SLAB), new UBIgneousCobbleSlabDouble(UBEntries.IGNEOUS_COBBLE_SLAB)));
		UBEntries.METAMORPHIC_COBBLE_SLAB
				.register(new UBSlabPair(new UBMetamorphicCobbleSlabHalf(UBEntries.METAMORPHIC_COBBLE_SLAB), new UBMetamorphicCobbleSlabDouble(UBEntries.METAMORPHIC_COBBLE_SLAB)));
		UBEntries.SEDIMENTARY_STONE_SLAB
				.register(new UBSlabPair(new UBSedimentaryStoneSlabHalf(UBEntries.SEDIMENTARY_STONE_SLAB), new UBSedimentaryStoneSlabDouble(UBEntries.SEDIMENTARY_STONE_SLAB)));
		/*
		 * Buttons
		 */
		ButtonEntry e1;
		(e1 = UBEntries.IGNEOUS_STONE_BUTTON)
				.register(new UBButtonGroup(e1, 
						new UBButtonIgneous(e1, EnumFacing.DOWN), new UBButtonIgneous(e1, EnumFacing.EAST), new UBButtonIgneous(e1, EnumFacing.NORTH),
						new UBButtonIgneous(e1, EnumFacing.SOUTH), new UBButtonIgneous(e1, EnumFacing.UP), new UBButtonIgneous(e1, EnumFacing.WEST)));
		(e1 = UBEntries.METAMORPHIC_STONE_BUTTON).register(
				new UBButtonGroup(e1, 
						new UBButtonMetamorphic(e1, EnumFacing.DOWN), new UBButtonMetamorphic(e1, EnumFacing.EAST), new UBButtonMetamorphic(e1, EnumFacing.NORTH),
						new UBButtonMetamorphic(e1, EnumFacing.SOUTH), new UBButtonMetamorphic(e1, EnumFacing.UP), new UBButtonMetamorphic(e1, EnumFacing.WEST)));
		if (UBConfig.cobbleButtonsOn()) {
			(e1 = UBEntries.IGNEOUS_COBBLE_BUTTON).register(new UBButtonGroup(e1, 
					new UBButtonIgneousCobble(e1, EnumFacing.DOWN), new UBButtonIgneousCobble(e1, EnumFacing.EAST), new UBButtonIgneousCobble(e1, EnumFacing.NORTH),
					new UBButtonIgneousCobble(e1, EnumFacing.SOUTH), new UBButtonIgneousCobble(e1, EnumFacing.UP), new UBButtonIgneousCobble(e1, EnumFacing.WEST)));
			(e1 = UBEntries.METAMORPHIC_COBBLE_BUTTON).register(new UBButtonGroup(e1, 
					new UBButtonMetamorphicCobble(e1, EnumFacing.DOWN), new UBButtonMetamorphicCobble(e1, EnumFacing.EAST), new UBButtonMetamorphicCobble(e1, EnumFacing.NORTH),
					new UBButtonMetamorphicCobble(e1, EnumFacing.SOUTH),	new UBButtonMetamorphicCobble(e1, EnumFacing.UP), new UBButtonMetamorphicCobble(e1, EnumFacing.WEST)));
		}
		// TODO brick buttons
		UBEntries.SEDIMENTARY_STONE_BUTTON.register(new UBButtonGroup(UBEntries.SEDIMENTARY_STONE_BUTTON,
				new UBButtonSedimentary(UBEntries.SEDIMENTARY_STONE_BUTTON, EnumFacing.DOWN), new UBButtonSedimentary(UBEntries.SEDIMENTARY_STONE_BUTTON, EnumFacing.EAST),
				new UBButtonSedimentary(UBEntries.SEDIMENTARY_STONE_BUTTON, EnumFacing.NORTH), new UBButtonSedimentary(UBEntries.SEDIMENTARY_STONE_BUTTON, EnumFacing.SOUTH),
				new UBButtonSedimentary(UBEntries.SEDIMENTARY_STONE_BUTTON, EnumFacing.UP), new UBButtonSedimentary(UBEntries.SEDIMENTARY_STONE_BUTTON, EnumFacing.WEST)));
		/*
		 * Walls
		 */
		UBEntries.IGNEOUS_WALL.register(new UBWallIgneous(UBEntries.IGNEOUS_WALL));
		UBEntries.METAMORPHIC_WALL.register(new UBWallMetamorphic(UBEntries.METAMORPHIC_WALL));
		// TODO Why not sedimentary walls ?
		/*
		 * Stairs
		 */
		StairsEntry e2;
		(e2 = UBEntries.IGNEOUS_STONE_STAIRS).register(new UBStairsGroup(e2, 
				new UBStairsIgneous(e2, EnumFacing.EAST), new UBStairsIgneous(e2, EnumFacing.NORTH),
				new UBStairsIgneous(e2, EnumFacing.SOUTH), new UBStairsIgneous(e2, EnumFacing.WEST)));
		(e2 = UBEntries.METAMORPHIC_STONE_STAIRS).register(new UBStairsGroup(e2, 
				new UBStairsMetamorphic(e2, EnumFacing.EAST), new UBStairsMetamorphic(e2, EnumFacing.NORTH),
				new UBStairsMetamorphic(e2, EnumFacing.SOUTH), new UBStairsMetamorphic(e2, EnumFacing.WEST)));
		if (UBConfig.cobbleStairsOn()) {
			(e2 = UBEntries.IGNEOUS_COBBLE_STAIRS).register(new UBStairsGroup(e2, 
					new UBStairsIgneousCobble(e2, EnumFacing.EAST), new UBStairsIgneousCobble(e2, EnumFacing.NORTH),
					new UBStairsIgneousCobble(e2, EnumFacing.SOUTH), new UBStairsIgneousCobble(e2, EnumFacing.WEST)));
			(e2 = UBEntries.METAMORPHIC_COBBLE_STAIRS).register(new UBStairsGroup(e2, 
					new UBStairsMetamorphicCobble(e2, EnumFacing.EAST), new UBStairsMetamorphicCobble(e2, EnumFacing.NORTH),
					new UBStairsMetamorphicCobble(e2, EnumFacing.SOUTH), new UBStairsMetamorphicCobble(e2, EnumFacing.WEST)));
		}
		if (UBConfig.brickStairsOn()) {
			(e2 = UBEntries.IGNEOUS_BRICK_STAIRS).register(new UBStairsGroup(e2,
					new UBStairsIgneousBrick(e2, EnumFacing.EAST), new UBStairsIgneousBrick(e2, EnumFacing.NORTH),
					new UBStairsIgneousBrick(e2, EnumFacing.SOUTH), new UBStairsIgneousBrick(e2, EnumFacing.WEST)));
			(e2 = UBEntries.METAMORPHIC_BRICK_STAIRS).register(new UBStairsGroup(e2,
					new UBStairsMetamorphicBrick(e2, EnumFacing.EAST), new UBStairsMetamorphicBrick(e2, EnumFacing.NORTH),
					new UBStairsMetamorphicBrick(e2, EnumFacing.SOUTH), new UBStairsMetamorphicBrick(e2, EnumFacing.WEST)));
		}
		(e2 = UBEntries.SEDIMENTARY_STONE_STAIRS).register(new UBStairsGroup(e2,
				new UBStairsSedimentary(e2, EnumFacing.EAST), new UBStairsSedimentary(e2, EnumFacing.NORTH),
				new UBStairsSedimentary(e2, EnumFacing.SOUTH), new UBStairsSedimentary(e2, EnumFacing.WEST)));
	}

	private final void createItems() {
		UBEntries.ligniteCoal.register(new ItemLigniteCoal());
		UBEntries.fossilPiece.register(new ItemFossilPiece());
	}

	private final void createOres() {
		UBAPIHook.UB_ORES_MAKER.requestOreSetup(Blocks.diamond_ore, "diamond_ore");
		UBAPIHook.UB_ORES_MAKER.requestOreSetup(Blocks.gold_ore, "gold_ore");
		UBAPIHook.UB_ORES_MAKER.requestOreSetup(Blocks.iron_ore, "iron_ore");
		UBAPIHook.UB_ORES_MAKER.requestOreSetup(Blocks.coal_ore, "coal_ore");
		UBAPIHook.UB_ORES_MAKER.requestOreSetup(Blocks.emerald_ore, "emerald_ore");
		UBAPIHook.UB_ORES_MAKER.requestOreSetup(Blocks.redstone_ore, "redstone_ore");
		UBAPIHook.UB_ORES_MAKER.requestOreSetup(Blocks.lapis_ore, 4, "lapis_ore");
	}

	private static VanillaStoneRecipeManager vanillaStoneRecipeManager = new VanillaStoneRecipeManager("cobblestone");

	private final void createRecipes() {
		if (!UBConfig.addOreDictRecipes()) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(VanillaBlockEntry.furnace.getAssociatedBlock(), 1), "XXX", "X X", "XXX", 'X',
					VanillaBlockEntry.cobblestone.getAssociatedBlock()));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(VanillaBlockEntry.lever.getAssociatedBlock(), 1), "I", "X", 'X',
					VanillaBlockEntry.cobblestone.getAssociatedBlock(), 'I', VanillaItemEntry.stick.getAssociatedItem()));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(VanillaBlockEntry.piston.getAssociatedBlock(), 1), "WWW", "CIC", "CRC", 'W',
					VanillaBlockEntry.planks.getAssociatedBlock(), 'C', VanillaBlockEntry.cobblestone.getAssociatedBlock(), 'I', VanillaItemEntry.ingotIron.getAssociatedItem(),
					'R', VanillaItemEntry.redstone.getAssociatedItem()));
			if (!UBConfig.stairsOn()) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(VanillaBlockEntry.stairsCobblestone.getAssociatedBlock(), 4), "X  ", "XX ", "XXX", 'X',
						VanillaBlockEntry.cobblestone.getAssociatedBlock()));
			}
			if (!UBConfig.wallsOn()) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(VanillaBlockEntry.cobblestone_wall.getAssociatedBlock(), 1), "XXX", "XXX", 'X',
						VanillaBlockEntry.cobblestone.getAssociatedBlock()));
			}
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(VanillaItemEntry.axeStone.getAssociatedItem(), 1), "XX ", "XW ", " W ", 'X',
					VanillaBlockEntry.cobblestone.getAssociatedBlock(), 'W', VanillaItemEntry.stick.getAssociatedItem()));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(VanillaItemEntry.pickaxeStone.getAssociatedItem(), 1), "XXX", " W ", " W ", 'X',
					VanillaBlockEntry.cobblestone.getAssociatedBlock(), 'W', VanillaItemEntry.stick.getAssociatedItem()));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(VanillaItemEntry.hoeStone.getAssociatedItem(), 1), "XX ", " W ", " W ", 'X',
					VanillaBlockEntry.cobblestone.getAssociatedBlock(), 'W', VanillaItemEntry.stick.getAssociatedItem()));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(VanillaItemEntry.shovelStone.getAssociatedItem(), 1), " X ", " W ", " W ", 'X',
					VanillaBlockEntry.cobblestone.getAssociatedBlock(), 'W', VanillaItemEntry.stick.getAssociatedItem()));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(VanillaItemEntry.swordStone.getAssociatedItem(), 1), "X", "X", "W", 'X',
					VanillaBlockEntry.cobblestone.getAssociatedBlock(), 'W', VanillaItemEntry.stick.getAssociatedItem()));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(VanillaItemEntry.brewingStand.getAssociatedItem(), 1), " B ", "XXX", 'X',
					VanillaBlockEntry.cobblestone.getAssociatedBlock(), 'B', VanillaItemEntry.blazeRod.getAssociatedItem()));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(VanillaBlockEntry.dispenser.getAssociatedBlock(), 1), "XXX", "XBX", "XRX", 'X',
					VanillaBlockEntry.cobblestone.getAssociatedBlock(), 'B', VanillaItemEntry.bow.getAssociatedItem(), 'R', VanillaItemEntry.redstone.getAssociatedItem()));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(VanillaBlockEntry.stone_pressure_plate.getAssociatedBlock(), 1), "XX", 'X', "stone"));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.stone_slab, 6, 3), "XXX", 'X', VanillaBlockEntry.cobblestone.getAssociatedBlock()));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.stone_slab, 6, 0), "XXX", 'X', "stoneSmooth"));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(VanillaItemEntry.redstoneRepeater.getAssociatedItem(), 1), "TRT", "XXX", 'X', "stone", 'T',
					VanillaBlockEntry.torchRedstoneActive.getAssociatedBlock(), 'R', VanillaItemEntry.redstone.getAssociatedItem()));
			if (!UBConfig.stairsOn()) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(VanillaBlockEntry.stairsStoneBrick.getAssociatedBlock(), 4), "X  ", "XX ", "XXX", 'X', "stoneBricks"));
			}
			if (!UBConfig.buttonsOn()) {
				GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(VanillaBlockEntry.stoneButton.getAssociatedBlock(), 1), "stone"));

			}
		}

		GameRegistry.addRecipe(new ItemStack(VanillaItemEntry.coal.getAssociatedItem(), 1), "XXX", "XXX", "XXX", 'X', UBEntries.ligniteCoal.getAssociatedItem());
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 15), new ItemStack(UBEntries.fossilPiece.getAssociatedItem(), 1, WILDCARD_VALUE));

		// Vanilla cobblestone recipe
		vanillaStoneRecipeManager.accept(UBConfig.vanillaStoneCrafting());
		UBConfig.settings.vanillaStoneCrafting.informOnChange(vanillaStoneRecipeManager);

		// Igneous stones
		for (int i = 0; i < IgneousStone.nbVariants; i++) {
			GameRegistry.addRecipe(new ItemStack(UBEntries.IGNEOUS_BRICK_SLAB.getSlabPair().half, 6, i), "XXX", 'X',
					new ItemStack(UBEntries.IGNEOUS_BRICK.getAssociatedBlock(), 1, i));
			GameRegistry.addRecipe(new ItemStack(UBEntries.IGNEOUS_STONE_SLAB.getSlabPair().half, 6, i), "XXX", 'X',
					new ItemStack(UBEntries.IGNEOUS_STONE.getAssociatedBlock(), 1, i));
			GameRegistry.addRecipe(new ItemStack(UBEntries.IGNEOUS_COBBLE_SLAB.getSlabPair().half, 6, i), "XXX", 'X',
					new ItemStack(UBEntries.IGNEOUS_COBBLE.getAssociatedBlock(), 1, i));
			GameRegistry.addRecipe(new ItemStack(UBEntries.IGNEOUS_BRICK.getAssociatedBlock(), 4, i), "xx", "xx", 'x',
					new ItemStack(UBEntries.IGNEOUS_STONE.getAssociatedBlock(), 1, i));
			GameRegistry.addSmelting(new ItemStack(UBEntries.IGNEOUS_COBBLE.getAssociatedBlock(), 1, i), new ItemStack(UBEntries.IGNEOUS_STONE.getAssociatedBlock(), 1, i), 0.1f);
		}
		// Metamorphic stones
		for (int i = 0; i < MetamorphicStone.nbVariants; i++) {
			GameRegistry.addRecipe(new ItemStack(UBEntries.METAMORPHIC_BRICK_SLAB.getSlabPair().half, 6, i), "XXX", 'X',
					new ItemStack(UBEntries.METAMORPHIC_BRICK.getAssociatedBlock(), 1, i));
			GameRegistry.addRecipe(new ItemStack(UBEntries.METAMORPHIC_STONE_SLAB.getSlabPair().half, 6, i), "XXX", 'X',
					new ItemStack(UBEntries.METAMORPHIC_STONE.getAssociatedBlock(), 1, i));
			GameRegistry.addRecipe(new ItemStack(UBEntries.METAMORPHIC_COBBLE_SLAB.getSlabPair().half, 6, i), "XXX", 'X',
					new ItemStack(UBEntries.METAMORPHIC_COBBLE.getAssociatedBlock(), 1, i));
			GameRegistry.addRecipe(new ItemStack(UBEntries.METAMORPHIC_BRICK.getAssociatedBlock(), 4, i), "xx", "xx", 'x',
					new ItemStack(UBEntries.METAMORPHIC_STONE.getAssociatedBlock(), 1, i));
			GameRegistry.addSmelting(new ItemStack(UBEntries.METAMORPHIC_COBBLE.getAssociatedBlock(), 1, i), new ItemStack(UBEntries.METAMORPHIC_STONE.getAssociatedBlock(), 1, i),
					0.1f);
		}
		// Sedimentary stones
		for (int i = 0; i < SedimentaryStone.nbVariants; i++) {
			GameRegistry.addRecipe(new ItemStack(UBEntries.SEDIMENTARY_STONE_SLAB.getSlabPair().half, 6, i), "XXX", 'X',
					new ItemStack(UBEntries.SEDIMENTARY_STONE.getAssociatedBlock(), 1, i));
		}

		// Fuel manager (Lignite coal)
		GameRegistry.registerFuelHandler(new FuelManager());

		// Slabs to vanilla slabs
		for (int i = 0; i < IgneousStone.nbVariants; i++) {
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.stone_slab, 1, 0), new ItemStack(UBEntries.IGNEOUS_STONE_SLAB.getSlabPair().half, 1, i)));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.stone_slab, 1, 3), new ItemStack(UBEntries.IGNEOUS_COBBLE_SLAB.getSlabPair().half, 1, i)));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.stone_slab, 1, 5), new ItemStack(UBEntries.IGNEOUS_BRICK_SLAB.getSlabPair().half, 1, i)));
		}
		for (int i = 0; i < MetamorphicStone.nbVariants; i++) {
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.stone_slab, 1, 0), new ItemStack(UBEntries.METAMORPHIC_STONE_SLAB.getSlabPair().half, 1, i)));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.stone_slab, 1, 3), new ItemStack(UBEntries.METAMORPHIC_COBBLE_SLAB.getSlabPair().half, 1, i)));
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.stone_slab, 1, 5), new ItemStack(UBEntries.METAMORPHIC_BRICK_SLAB.getSlabPair().half, 1, i)));
		}
		for (int i = 0; i < SedimentaryStone.nbVariants; i++) {
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.stone_slab, 1, 1), new ItemStack(UBEntries.SEDIMENTARY_STONE_SLAB.getSlabPair().half, 1)));
		}
	}

}
