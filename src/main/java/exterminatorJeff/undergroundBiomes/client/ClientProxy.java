package exterminatorJeff.undergroundBiomes.client;

import java.util.function.Consumer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import exterminatorJeff.undergroundBiomes.api.UBAPIHook;
import exterminatorJeff.undergroundBiomes.api.UBEntries;
import exterminatorJeff.undergroundBiomes.common.CommonProxy;
import exterminatorJeff.undergroundBiomes.common.UBCreativeTab;
import exterminatorJeff.undergroundBiomes.common.UBOresMaker;
import exterminatorJeff.undergroundBiomes.common.block.UBOre;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);

		((UBOreOverlaysRegistry) UBAPIHook.UB_ORES_OVERLAYS_REGISTRY).addVanillaOverlays();
		MinecraftForge.EVENT_BUS.register(new TexturesRegistrationHandler());

		StateMapperBase customStateMapper = new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				UBOre ubOre = (UBOre) state.getBlock();
				return new ModelResourceLocation(ubOre.getModelName(ubOre.getMetaFromState(state)), "inventory");
			}
		};
		UBOresMaker.UB_ORES.forEach(new Consumer<UBOre>() {
			@Override
			public void accept(UBOre ubOre) {
				ModelLoader.setCustomStateMapper(ubOre, customStateMapper);
			}
		});
		ModelLoaderRegistry.registerLoader(new UBOreModelLoader());
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);

		registerBlocksModels();
		registerItemsModels();
		registerOresModels();

		UBCreativeTab.UB_BLOCKS_TAB.setTabIconItem(UBEntries.IGNEOUS_BRICK.getAssociatedItem());
		UBCreativeTab.UB_ITEMS_TAB.setTabIconItem(UBEntries.ligniteCoal.getAssociatedItem());
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);

	}

	private final void registerBlocksModels() {
		// Stones
		UBEntries.IGNEOUS_STONE.registerModel();
		UBEntries.IGNEOUS_COBBLE.registerModel();
		UBEntries.IGNEOUS_BRICK.registerModel();
		UBEntries.METAMORPHIC_STONE.registerModel();
		UBEntries.METAMORPHIC_COBBLE.registerModel();
		UBEntries.METAMORPHIC_BRICK.registerModel();
		UBEntries.SEDIMENTARY_STONE.registerModel();
		// Slabs
		UBEntries.IGNEOUS_BRICK_SLAB.registerModel();
		UBEntries.METAMORPHIC_BRICK_SLAB.registerModel();
		UBEntries.IGNEOUS_STONE_SLAB.registerModel();
		UBEntries.METAMORPHIC_STONE_SLAB.registerModel();
		UBEntries.IGNEOUS_COBBLE_SLAB.registerModel();
		UBEntries.METAMORPHIC_COBBLE_SLAB.registerModel();
		UBEntries.SEDIMENTARY_STONE_SLAB.registerModel();
		// Buttons
		UBEntries.IGNEOUS_STONE_BUTTON.registerModel();
		UBEntries.IGNEOUS_COBBLE_BUTTON.registerModel();
		UBEntries.METAMORPHIC_STONE_BUTTON.registerModel();
		UBEntries.METAMORPHIC_COBBLE_BUTTON.registerModel();
		UBEntries.SEDIMENTARY_STONE_BUTTON.registerModel();
		// Walls
		UBEntries.IGNEOUS_STONE_WALL.registerModel();
		UBEntries.IGNEOUS_COBBLE_WALL.registerModel();
		UBEntries.IGNEOUS_BRICK_WALL.registerModel();
		UBEntries.METAMORPHIC_STONE_WALL.registerModel();
		UBEntries.METAMORPHIC_COBBLE_WALL.registerModel();
		UBEntries.METAMORPHIC_BRICK_WALL.registerModel();
		UBEntries.SEDIMENTARY_STONE_WALL.registerModel();
		// Stairs
		UBEntries.IGNEOUS_STONE_STAIRS.registerModel();
		UBEntries.IGNEOUS_COBBLE_STAIRS.registerModel();
		UBEntries.IGNEOUS_BRICK_STAIRS.registerModel();
		UBEntries.METAMORPHIC_STONE_STAIRS.registerModel();
		UBEntries.METAMORPHIC_COBBLE_STAIRS.registerModel();
		UBEntries.METAMORPHIC_BRICK_STAIRS.registerModel();
		UBEntries.SEDIMENTARY_STONE_STAIRS.registerModel();
	}

	private final void registerItemsModels() {
		UBEntries.ligniteCoal.registerModel();
		UBEntries.fossilPiece.registerModel();
	}

	private final void registerOresModels() {
		UBOresMaker.UB_ORES.forEach(new Consumer<UBOre>() {
			@Override
			public void accept(UBOre ubOre) {
				ubOre.registerModel();
			}
		});
	}

}
