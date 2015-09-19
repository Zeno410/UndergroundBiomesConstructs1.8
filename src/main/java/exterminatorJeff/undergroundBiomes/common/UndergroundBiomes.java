package exterminatorJeff.undergroundBiomes.common;

import static net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent;
import net.minecraftforge.fml.common.event.FMLModIdMappingEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import Zeno410Utils.Acceptor;
import Zeno410Utils.ConfigManager;
import Zeno410Utils.PlayerDetector;
import Zeno410Utils.Zeno410Logger;

import com.google.common.collect.ImmutableList;

import exterminatorJeff.undergroundBiomes.api.UBAPIHook;
import exterminatorJeff.undergroundBiomes.api.UndergroundBiomesSettings;
import exterminatorJeff.undergroundBiomes.api.VanillaItemEntry;
import exterminatorJeff.undergroundBiomes.common.block.IgneousCobble;
import exterminatorJeff.undergroundBiomes.common.block.IgneousStone;
import exterminatorJeff.undergroundBiomes.common.block.IgneousBrick;
import exterminatorJeff.undergroundBiomes.common.block.UBStone;
import exterminatorJeff.undergroundBiomes.common.block.MetamorphicCobble;
import exterminatorJeff.undergroundBiomes.common.block.MetamorphicStone;
import exterminatorJeff.undergroundBiomes.common.block.MetamorphicBrick;
import exterminatorJeff.undergroundBiomes.common.block.SedimentaryStone;
//import exterminatorJeff.undergroundBiomes.common.command.CommandOreDictifyStone;
import exterminatorJeff.undergroundBiomes.common.item.ItemFossilPiece;
import exterminatorJeff.undergroundBiomes.common.item.ItemLigniteCoal;
import exterminatorJeff.undergroundBiomes.common.item.VanillaStoneRecipeManager;
//import exterminatorJeff.undergroundBiomes.constructs.UndergroundBiomesConstructs;
//import exterminatorJeff.undergroundBiomes.constructs.item.UBItemStairs;
//import exterminatorJeff.undergroundBiomes.constructs.util.UBCodeLocations;
import exterminatorJeff.undergroundBiomes.constructs.util.WatchList;

@Mod(modid = UndergroundBiomes.MODID, name = UndergroundBiomes.NAME, version = UndergroundBiomes.VERSION)
public class UndergroundBiomes {

	public static final String MODID = "undergroundbiomes";
	public static final String NAME = "Underground Biomes";
	public static final String VERSION = "0.6h";

	public static Logger logger = new Zeno410Logger(MODID).logger();

	@Instance
	public static UndergroundBiomes instance = new UndergroundBiomes();

	public static boolean isPreInitDone = false;

	public static UBConfig config = new UBConfig();

	@SidedProxy(clientSide = "exterminatorJeff.undergroundBiomes.client.ClientProxy", serverSide = "exterminatorJeff.undergroundBiomes.common.CommonProxy")
	public static CommonProxy proxy;

	public static World world;

	//

	private WatchList defaultIDSetter;
	private boolean runningConfigIDs = false;

	public static long worldSeed;
	private boolean gotWorldSeed;

	public boolean gotWorldSeed() {
		return gotWorldSeed;
	}

	public static List<ItemStack> nuggets;

	private static String[] nuggetStrings = { "nuggetIron", "nuggetCopper", "nuggetTin", "nuggetSilver", "nuggetLead", "nuggetAluminium", "nuggetNaturalAluminium", "nuggetNickel", "nuggetPlatinum",
			"nuggetElectrum", "nuggetZinc", };

	// private final UBCodeLocations serverCodeLocations = new
	// UBCodeLocations();
	// private final UBCodeLocations clientCodeLocations = new
	// UBCodeLocations();
	//
	// public final UBCodeLocations ubCodeLocations(World world) {
	// if (world.isRemote) {
	// return clientCodeLocations;
	// } else {
	// return serverCodeLocations;
	// }
	// }

	// public UndergroundBiomesConstructs constructs;
	
	public UndergroundBiomes() {
		
	}

	public static void throwIfTesting(RuntimeException toThrow) {
		if (UBConfig.crashOnProblems())
			throw toThrow;
	}

	public static void throwIfTesting(String toThrow) {
		if (UBConfig.crashOnProblems())
			throw new RuntimeException(toThrow);
	}

	public static void throwIfTesting(RuntimeException toThrow, String logMessage) {
		logger.info(logMessage);
		if (UBConfig.crashOnProblems())
			throw toThrow;
	}

	public static String blockCategory = "block";
	public static String itemCategory = "item";

	// OreUBifier oreUBifier() {
	// return oreUBifier;
	// }
	//
	// public int ubOreRenderID() {
	// return oreUBifier.getRenderID();
	// }

	// private OreUBifier oreUBifier;

	// private DimensionManager dimensionManager;
	//
	// private OreUBifyRequester oreRequester = new OreUBifyRequester();

	// private PacketPipeline pipeline;
	private PlayerDetector playerDetector;

	// private UndergroundBiomesNetworking networking;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		proxy.preInit(event);

		// if (UBIDs.version != 3)
		// throw new RuntimeException("" + "Another mod has included an obsolete
		// version of the Underground Biomes API. Underground Biomes Constructs
		// cannot run.");

		// config

		nuggets = new ArrayList<ItemStack>();

		nuggets.add(new ItemStack(VanillaItemEntry.goldNugget.getAssociatedItem(), 1, 0));
		assert(nuggets.get(0) != null);

		// register village change events

		// constructs = new UndergroundBiomesConstructs();
		// constructs.preInit(config);
		//
		// config.save();
		// set up ores;

		// test delayed requests

		// oreUBifier.setupUBOre(Blocks.iron_ore,UBOreTexturizer.iron_overlay,
		// event);
		// UBAPIHook.ubAPIHook.ubOreTexturizer.requestUBOreSetup(Blocks.iron_ore,
		// UBOreTexturizer.iron_overlay);

		// test on-the-spot requests

		// oreUBifier.setupUBOre(Blocks.redstone_ore,UBOreTexturizer.redstone_overlay,
		// event);
		// UBAPIHook.ubAPIHook.ubOreTexturizer.setupUBOre(Blocks.redstone_ore,
		// UBOreTexturizer.redstone_overlay, event);

		// oreUBifier = new OreUBifier(config.getUbOres());
		// dimensionManager = new DimensionManager(oreUBifier);

		// oreUBifier.setupUBOre(Blocks.coal_ore, UBOreTexturizer.coal_overlay,
		// event);
		// oreUBifier.setupUBOre(Blocks.diamond_ore,
		// UBOreTexturizer.diamond_overlay, event);
		// oreUBifier.setupUBOre(Blocks.lapis_ore,
		// UBOreTexturizer.lapis_overlay, event);
		// oreUBifier.setupUBOre(Blocks.emerald_ore,
		// UBOreTexturizer.emerald_overlay, event);
		// oreUBifier.setupUBOre(Blocks.gold_ore, UBOreTexturizer.gold_overlay,
		// event);

		// oreRequester.fulfillRequests(event);

		// defaultIDSetter = this.defaultIDs();
		// FMLCommonHandler.instance().bus().register(this);
		// FMLCommonHandler.instance().bus().register(new EventWatcher());

		// pipeline = new PacketPipeline();

		isPreInitDone = true;
	}

	// @EventHandler
	// public void load(FMLInitializationEvent event) {
	// pipeline.initialise();
	// playerDetector = new PlayerDetector();
	// networking = new UndergroundBiomesNetworking(pipeline, settings);
	// // when a player logs in, send the settings
	// playerDetector.addLoginAction(new SettingsSender());
	//
	// addOreDicts();
	// addRecipes();
	// constructs.load(event);
	//
	// }

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) throws Exception {
		proxy.postInit(event);

		// pipeline.postInitialise();
		//
		// if (addOreDictRecipes()) {
		// oreDictifyStone();
		// }
		//
		// // Pull nuggets from ore dictionary
		// List<ItemStack> stacks;
		// for (String s : nuggetStrings) {
		// stacks = OreDictionary.getOres(s);
		// if (stacks.size() > 0) {
		// nuggets.add(stacks.get(0));
		// }
		// }
		//
		// if (Loader.isModLoaded("Thaumcraft")) {
		// try {
		// // ThaumcraftApi.registerObjectTag(id, meta, (new
		// // ObjectTags()).add(EnumTag.VALUABLE, 58).add(EnumTag.LIGHT,
		// // 15));
		// } catch (Exception e) {
		// System.out.println("[UndergroundBiomes] Error while integrating with
		// Thaumcraft");
		// e.printStackTrace(System.err);
		// }
		// }
		//
		// constructs.postInit(event);
		// // logger.info(Block.blockRegistry.getNameForObject(igneousStone));
		// //
		// logger.info(Block.blockRegistry.getNameForObject(metamorphicStone));
		// //
		// logger.info(Block.blockRegistry.getNameForObject(sedimentaryStone));
		//
		// MinecraftForge.EVENT_BUS.register(this);
		// MinecraftForge.TERRAIN_GEN_BUS.register(this);
		// MinecraftForge.ORE_GEN_BUS.register(this);
		// // if (replaceCobblestone()) {
		// MinecraftForge.EVENT_BUS.register(dimensionManager);
		// MinecraftForge.TERRAIN_GEN_BUS.register(dimensionManager);
		// }
	}

	// @EventHandler
	// public void serverLoad(FMLServerAboutToStartEvent event) {
	// // logger.info("server about to start");
	// }
	//
	// @EventHandler
	// public void serverLoad(FMLServerStartingEvent event) {
	// // logger.info("server starting");
	// event.registerServerCommand(new CommandOreDictifyStone());
	// }

	// @EventHandler
	// public void serverLoaded(FMLServerStartedEvent event) {
	// // logger.info("server starting");
	// if (forceRemap) {
	// this.forceConfigIDs();
	// GameData.freezeData();
	// logger.info("forcing on remapping");
	// this.runningConfigIDs = true;
	// }
	// }

	// @EventHandler
	// public void serverUnload(FMLServerStoppingEvent event) {
	// // logger.info("server stopping");
	// for (Object key : Block.blockRegistry.getKeys()) {
	// String name = (String) key;
	// Block named = Block.getBlockFromName(name);
	// int id = Block.getIdFromBlock(named);
	// // logger.info(name + " "+id);
	// }
	// for (Object key : Item.itemRegistry.getKeys()) {
	// String name = (String) key;
	// Item named = (Item) (Item.itemRegistry.getObject(name));
	// int id = Item.getIdFromItem(named);
	// // logger.info(name + " "+id);
	// }
	// if (runningConfigIDs) {
	// // defaultIDSetter.redoAsNeeded();
	// runningConfigIDs = false;
	// }
	// BiomeUndergroundDecorator.noMoreRedos();
	// }

	private boolean forceRemap;

	// @EventHandler
	// public void onMissingMapping(FMLMissingMappingsEvent event) {
	// logger.info("missing mappings");
	// forceRemap = false;
	// for (FMLMissingMappingsEvent.MissingMapping missing : event.get()) {
	// logger.info(missing.name + " " + missing.type.toString());
	// if
	// (missing.name.equalsIgnoreCase("UndergroundBiomes:sedimentaryStoneItem"))
	// forceRemap = true;
	// }
	// }
	//
	// @EventHandler
	// public void adjustMappings(FMLModIdMappingEvent event) {
	//
	// boolean oldIDs = false;
	// logger.info("remapping");
	// ImmutableList<FMLModIdMappingEvent.ModRemapping> remappings =
	// event.remappedIds;
	//
	// Iterator<FMLModIdMappingEvent.ModRemapping> list = remappings.iterator();
	// while (list.hasNext()) {
	// FMLModIdMappingEvent.ModRemapping remapping = list.next();
	// logger.info(remapping.tag + " from " + remapping.oldId + " to " +
	// remapping.newId);
	//
	// // currently tags drop the fist letter
	// if (remapping.tag.equals("inecraft:bed")) {
	// if (remapping.oldId < 256)
	// oldIDs = true;
	// }
	// // and presumably Forge will fix that
	// if (remapping.tag.equals("Minecraft:bed")) {
	// if (remapping.oldId < 256)
	// oldIDs = true;
	// } // currently tags drop the fist letter
	// if (remapping.tag.equals("inecraft:wheat")) {
	// if (remapping.oldId < 256)
	// oldIDs = true;
	// }
	// // and presumably Forge will fix that
	// if (remapping.tag.equals("Minecraft:wheat")) {
	// if (remapping.oldId < 256)
	// oldIDs = true;
	// }
	// if (remapping.tag.equals("ndergroundBiomes:stairs")) {
	// if (remapping.newId == constructs.stoneStairID())
	// oldIDs = true;
	// }
	// if (remapping.tag.equals("UndergroundBiomes:stairs")) {
	// if (remapping.newId == constructs.stoneStairID())
	// oldIDs = true;
	// }
	// if (remapping.tag.equals("UndergroundBiomes:igneousStone")) {
	// if (remapping.newId > 2000)
	// oldIDs = true;
	// }
	// if (remapping.tag.equals("ndergroundBiomes:igneousStone")) {
	// if (remapping.newId > 2000)
	// oldIDs = true;
	// }
	// if (remapping.tag.equals("UndergroundBiomes:metamorphicStone")) {
	// if (remapping.newId > 2000)
	// oldIDs = true;
	// }
	// if (remapping.tag.equals("ndergroundBiomes:metamorphicStone")) {
	// if (remapping.newId > 2000)
	// oldIDs = true;
	// }
	// }
	// // logger.info("old IDs "+oldIDs);
	// if (oldIDs) {
	// this.forceConfigIDs();
	//
	// logger.info("forcing");
	// this.runningConfigIDs = true;
	// }
	// if (forceRemap) {
	// this.forceConfigIDs();
	//
	// logger.info("forcing");
	// this.runningConfigIDs = true;
	// forceRemap = false;
	// }
	// }

	// public void addOreDicts() {
	// OreDictionary.registerOre("stone", new ItemStack(igneousStone, 1,
	// WILDCARD_VALUE));
	// OreDictionary.registerOre("stone", new ItemStack(metamorphicStone, 1,
	// WILDCARD_VALUE));
	// OreDictionary.registerOre("stone", new ItemStack(sedimentaryStone, 1,
	// WILDCARD_VALUE));
	// OreDictionary.registerOre("cobblestone", new
	// ItemStack(igneousCobblestone, 1, WILDCARD_VALUE));
	// OreDictionary.registerOre("cobblestone", new
	// ItemStack(metamorphicCobblestone, 1, WILDCARD_VALUE));
	// OreDictionary.registerOre("stoneBricks", new ItemStack(igneousStoneBrick,
	// 1, WILDCARD_VALUE));
	// OreDictionary.registerOre("stoneBricks", new
	// ItemStack(metamorphicStoneBrick, 1, WILDCARD_VALUE));
	// // OreDictionary.registerOre(textures, ore);
	// this.oreUBifier.registerOres();
	// }

	// public static int oreDictifyStone() throws Exception {
	// int numReplaced = 0;
	// Map<ItemStack, String> replacements = new HashMap<ItemStack, String>();
	// replacements.put(new
	// ItemStack(NamedVanillaBlock.stone.getAssociatedBlock(), 1,
	// WILDCARD_VALUE), "stone");
	// replacements.put(new
	// ItemStack(NamedVanillaBlock.cobblestone.getAssociatedBlock(), 1,
	// WILDCARD_VALUE), oreCobblestoneName());
	// replacements.put(new
	// ItemStack(NamedVanillaBlock.stoneBrick.getAssociatedBlock(), 1,
	// WILDCARD_VALUE), "stoneBricks");
	// ItemStack[] replaceStacks = replacements.keySet().toArray(new
	// ItemStack[replacements.keySet().size()]);
	//
	// // Ignore recipes for the following items
	// ItemStack[] exclusions = new ItemStack[] { new
	// ItemStack(NamedVanillaBlock.stairsStoneBrick.getAssociatedBlock()), new
	// ItemStack(NamedVanillaBlock.stoneBrick.getAssociatedBlock()),
	// new ItemStack(NamedVanillaBlock.stoneSingleSlab.getAssociatedBlock(), 1,
	// 5), };
	// List recipes = CraftingManager.getInstance().getRecipeList();
	// Constructor shapedConstr =
	// ShapedOreRecipe.class.getDeclaredConstructor(ShapedRecipes.class,
	// Map.class);
	// Constructor shapelessConstr =
	// ShapelessOreRecipe.class.getDeclaredConstructor(ShapelessRecipes.class,
	// Map.class);
	// shapedConstr.setAccessible(true);
	// shapelessConstr.setAccessible(true);
	//
	// // zap stone button recipe if needed
	// // this is being altered somewhere else so we need to take it out
	// if (buttonsOn()) {
	// Iterator<IRecipe> iterator =
	// CraftingManager.getInstance().getRecipeList().iterator();
	//
	// while (iterator.hasNext()) {
	// IRecipe recipe = iterator.next();
	// if (recipe == null)
	// continue;
	// ItemStack output = recipe.getRecipeOutput();
	// if (output != null &&
	// NamedVanillaBlock.stoneButton.matches(output.getItem()))
	// iterator.remove();
	// }
	// }
	//
	// // Add ore dictionary entries for replaced blocks
	// for (ItemStack stack : replacements.keySet()) {
	// OreDictionary.registerOre(replacements.get(stack), stack);
	// }
	//
	// // Search stone recipes for recipes to replace
	// for (int i = 0; i < recipes.size(); i++) {
	// Object obj = recipes.get(i);
	// ItemStack output = ((IRecipe) obj).getRecipeOutput();
	// if (output != null && containsMatch(false, exclusions, output)) {
	// continue;
	// }
	// // supress alterations overriding construct recipes
	// if (UndergroundBiomesConstructs.overridesRecipe((IRecipe) obj))
	// continue;
	//
	// if (obj instanceof ShapedRecipes) {
	// ShapedRecipes recipe = (ShapedRecipes) obj;
	// if (containsMatch(true, recipe.recipeItems, replaceStacks)) {
	// recipes.set(i, shapedConstr.newInstance(recipe, replacements));
	// numReplaced++;
	// System.out.println("Changed shaped recipe for " +
	// output.getDisplayName());
	// }
	// } else if (obj instanceof ShapelessRecipes) {
	// ShapelessRecipes recipe = (ShapelessRecipes) obj;
	// if (containsMatch(true, (ItemStack[]) recipe.recipeItems.toArray(new
	// ItemStack[recipe.recipeItems.size()]), replaceStacks)) {
	// recipes.set(i, shapelessConstr.newInstance(recipe, replacements));
	// numReplaced++;
	// System.out.println("Changed shapeless recipe for " +
	// output.getDisplayName());
	// }
	// } else if (obj instanceof ShapedOreRecipe) {
	// ShapedOreRecipe recipe = (ShapedOreRecipe) obj;
	// if (containsMatchReplaceInplace(true, recipe.getInput(), replaceStacks,
	// replacements)) {
	// numReplaced++;
	// System.out.println("Changed shaped ore recipe for " +
	// output.getDisplayName());
	// }
	// } else if (obj instanceof ShapelessOreRecipe) {
	// ShapelessOreRecipe recipe = (ShapelessOreRecipe) obj;
	// if (containsMatchReplaceInplace(true, recipe.getInput(), replaceStacks,
	// replacements)) {
	// numReplaced++;
	// System.out.println("Changed shapeless ore recipe for " +
	// output.getDisplayName());
	// }
	// }
	// }
	// // CraftingManager.getInstance().addShapelessRecipe(new
	// // ItemStack(NamedVanillaBlock.stoneButton.block()), new Object[]
	// // {NamedVanillaBlock.stone.block()});
	//
	// return numReplaced;
	// }

	private static boolean containsMatch(boolean strict, ItemStack[] inputs, ItemStack... targets) {
		try {
			for (ItemStack input : inputs) {
				for (ItemStack target : targets) {
					if (OreDictionary.itemMatches(target, input, strict)) {
						return true;
					}
				}
			}
		} catch (NullPointerException e) {
			return false;
		}
		return false;
	}

	// Doing what Forge tells not to do
	private static boolean containsMatchReplaceInplace(boolean strict, Object inputArrayOrList, ItemStack[] targets, Map<ItemStack, String> replacements) {
		boolean replaced = false;
		if (inputArrayOrList instanceof ArrayList) {
			ArrayList inputList = (ArrayList) inputArrayOrList;
			for (int i = 0; i < inputList.size(); i++) {
				Object input = inputList.get(i);
				if (input instanceof ItemStack) {
					for (ItemStack target : targets) {
						if (OreDictionary.itemMatches(target, (ItemStack) input, strict)) {
							inputList.set(i, OreDictionary.getOres(replacements.get(target)));
							replaced = true;
						}
					}
				}
			}
		} else {
			// Expect array
			Object[] inputArray = (Object[]) inputArrayOrList;
			for (int i = 0; i < inputArray.length; i++) {
				Object input = inputArray[i];
				if (input instanceof ItemStack) {
					for (ItemStack target : targets) {
						if (OreDictionary.itemMatches(target, (ItemStack) input, strict)) {
							inputArray[i] = OreDictionary.getOres(replacements.get(target));
							replaced = true;
						}
					}
				}
			}
		}
		return replaced;
	}

	public static long getWorldSeed() {
		return worldSeed;
	}

	public static World getWorld() {
		return world;
	}

	// public boolean inChunkGenerationAllowed(int id) {
	// return dimensionManager.inChunkGenerationAllowed(id);
	// }
	//
	// @SubscribeEvent
	// public void onWorldLoad(WorldEvent.Load event) {
	// if (world instanceof WorldServer) {
	// WorldServer server = (WorldServer) world;
	// File worldLocation = server.getChunkSaveLocation().getParentFile();
	// // UndergroundBiomes.logger.info(world.toString() + " "
	// // +worldLocation.getAbsolutePath());
	// configManager.setWorldFile(worldLocation);
	// this.dimensionManager.setupGenerators(event);
	// }
	// if (!gotWorldSeed) {
	// world = event.world;
	// worldSeed = world.getSeed();
	// gotWorldSeed = true;
	// }
	// // tabModBlocks.item =
	// // UBItemBlock.itemFrom(UBIDs.igneousStoneBrickName);
	// }
	//
	// @SubscribeEvent
	// public void onWorldUnload(WorldEvent.Unload event) {
	// gotWorldSeed = false;
	// this.serverCodeLocations.clear();
	// this.clientCodeLocations.clear();
	// this.dimensionManager.unload();
	// }
	//
	// @SubscribeEvent(priority = EventPriority.NORMAL)
	// public void onBiomeDecorate(DecorateBiomeEvent.Post event) {
	// dimensionManager.onBiomeDecorate(event);
	// }
	//
	// @SubscribeEvent(priority = EventPriority.LOWEST)
	// public void onOreGenerate(OreGenEvent.Post event) {
	// if (this.oreUBifier.replacementActive()) {
	// dimensionManager.redoOres(event.pos.getX(), event.pos.getZ(),
	// event.world);
	// }
	// }

	@SubscribeEvent
	public void registerOre(OreDictionary.OreRegisterEvent event) {
		if (nuggets.contains(event.Name)) {
			nuggets.add(event.Ore);
		}
	}

	// public void redoOres(int x, int z, World world) {
	// dimensionManager.redoOres(x, z, world);
	// }

	public static int forgeVersion() {
		return ForgeVersion.getBuildVersion();
	}

	// private WatchList configList() {
	// WatchList forcing = new WatchList();
	// forcing.addChangeWithItem(igneousStoneID(), igneousStone);
	// forcing.addChangeWithItem(metamorphicStoneID(), metamorphicStone);
	// forcing.addChangeWithItem(sedimentaryStoneID(), sedimentaryStone);
	// forcing.addChangeWithItem(igneousCobblestoneID(), igneousCobblestone);
	// forcing.addChangeWithItem(metamorphicCobblestoneID(),
	// metamorphicCobblestone);
	// forcing.addChangeWithItem(igneousStoneBrickID(), igneousStoneBrick);
	// forcing.addChangeWithItem(metamorphicStoneBrickID(),
	// metamorphicStoneBrick);
	//
	// forcing.addChangeWithItem(igneousBrickSlabHalfID(),
	// igneousBrickSlab.half);
	// forcing.addChangeWithItem(igneousBrickSlabFullID(),
	// igneousBrickSlab.full);
	//
	// forcing.addChangeWithItem(metamorphicBrickSlabHalfID(),
	// metamorphicBrickSlab.half);
	// forcing.addChangeWithItem(metamorphicBrickSlabFullID(),
	// metamorphicBrickSlab.full);
	//
	// forcing.addChangeWithItem(igneousStoneSlabHalfID(),
	// igneousStoneSlab.half);
	// forcing.addChangeWithItem(igneousStoneSlabFullID(),
	// igneousStoneSlab.full);
	//
	// forcing.addChangeWithItem(metamorphicStoneSlabHalfID(),
	// metamorphicStoneSlab.half);
	// forcing.addChangeWithItem(metamorphicStoneSlabFullID(),
	// metamorphicStoneSlab.full);
	//
	// forcing.addChangeWithItem(igneousCobblestoneSlabHalfID(),
	// igneousCobblestoneSlab.half);
	// forcing.addChangeWithItem(igneousCobblestoneSlabFullID(),
	// igneousCobblestoneSlab.full);
	//
	// forcing.addChangeWithItem(metamorphicCobblestoneSlabHalfID(),
	// metamorphicCobblestoneSlab.half);
	// forcing.addChangeWithItem(metamorphicCobblestoneSlabFullID(),
	// metamorphicCobblestoneSlab.full);
	//
	// forcing.addChangeWithItem(sedimentaryStoneSlabHalfID(),
	// sedimentaryStoneSlab.half);
	// forcing.addChangeWithItem(sedimentaryStoneSlabFullID(),
	// sedimentaryStoneSlab.full);
	//
	// forcing.addChange(constructs.stoneButtonID(),
	// constructs.stoneButton().construct);
	// forcing.addChange(constructs.stoneStairID(),
	// constructs.stoneStair().construct);
	// forcing.addChange(constructs.stoneWallID(),
	// constructs.stoneWall().construct);
	// return forcing;
	// }
	//
	// private WatchList defaultIDs() {
	// WatchList forcing = new WatchList();
	// forcing.addWithItem(igneousStone);
	// forcing.addWithItem(metamorphicStone);
	// forcing.addWithItem(sedimentaryStone);
	// forcing.addWithItem(igneousCobblestone);
	// forcing.addWithItem(metamorphicCobblestone);
	// forcing.addWithItem(igneousStoneBrick);
	// forcing.addWithItem(metamorphicStoneBrick);
	//
	// forcing.addWithItem(igneousBrickSlab.half);
	// forcing.addWithItem(igneousBrickSlab.full);
	//
	// forcing.addWithItem(metamorphicBrickSlab.half);
	// forcing.addWithItem(metamorphicBrickSlab.full);
	//
	// forcing.addWithItem(igneousStoneSlab.half);
	// forcing.addWithItem(igneousStoneSlab.full);
	//
	// forcing.addWithItem(metamorphicStoneSlab.half);
	// forcing.addWithItem(metamorphicStoneSlab.full);
	//
	// forcing.addWithItem(igneousCobblestoneSlab.half);
	// forcing.addWithItem(igneousCobblestoneSlab.full);
	//
	// forcing.addWithItem(metamorphicCobblestoneSlab.half);
	// forcing.addWithItem(metamorphicCobblestoneSlab.full);
	//
	// forcing.addWithItem(sedimentaryStoneSlab.half);
	// forcing.addWithItem(sedimentaryStoneSlab.full);
	//
	// forcing.add(constructs.stoneButton().construct);
	// forcing.add(constructs.stoneStair().construct);
	// forcing.add(constructs.stoneWall().construct);
	// return forcing;
	// }

	// private void forceConfigIDs() {
	// WatchList forcing = configList();
	// try {
	// for (String warning : forcing.problems()) {
	// logger.info(warning);
	// }
	// logger.info("forcing config IDs ");
	// forcing.redoAsNeeded();
	// for (String warning : forcing.problems()) {
	// logger.info(warning);
	// }
	// } catch (Exception e) {
	// logger.info("redoerror " + e.toString());
	// }
	//
	// WatchList check = new WatchList();
	// check.add(constructs.stoneButton().construct);
	// check.add(constructs.stoneStair().construct);
	// check.add(constructs.stoneWall().construct);
	// for (Item item : UBItemStairs.instances) {
	// check.add(item);
	// }
	// for (String warning : check.problems()) {
	// // logger.info(warning);
	// }
	// }
	//
	// public class EventWatcher {
	// public void processEvent(FMLEvent event) {
	// // logger.info(event.getEventType());
	// }
	// }

	// private class SettingsSender extends Acceptor<EntityPlayerMP> {
	//
	// @Override
	// public void accept(EntityPlayerMP accepted) {
	// networking.settings.sendTo(settings, accepted);
	// }
	//
	// }
}
