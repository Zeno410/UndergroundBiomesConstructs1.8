package exterminatorJeff.undergroundBiomes.client;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IModelState;
import net.minecraftforge.client.model.IRetexturableModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;

import exterminatorJeff.undergroundBiomes.api.OreOverlaysRegistry;

/**
 * One instance per UBOre instance
 * 
 * @author Spooky4672
 *
 */
@SideOnly(Side.CLIENT)
public class UBOreModel implements IModel {

	private final String stoneTexture;
	private final String oreTexture;

	public UBOreModel(ResourceLocation modelLocation) {
		String str = StringUtils.removeStart(modelLocation.getResourcePath(), UBOreModelLoader.UBORE_MODEL);
		String oreType = StringUtils.substringAfterLast(str, ":");
		str = StringUtils.remove(str, ":" + oreType);
		String oreResourcesDomain = StringUtils.substringAfterLast(str, ".");
		str = StringUtils.remove(str, "." + oreResourcesDomain);
		String stoneVariant = StringUtils.substringAfterLast(str, ".");
		stoneTexture = OreOverlaysRegistry.BLOCKS_TX_PATH + stoneVariant;
		oreTexture = OreOverlaysRegistry.BLOCKS_TX_PATH + UBOreOverlaysRegistry.oresToOverlays.get(oreType);
	}

	@Override
	public Collection<ResourceLocation> getDependencies() {
		return Collections.emptySet();
	}

	@Override
	public Collection<ResourceLocation> getTextures() {
		return Collections.emptySet();
	}

	@Override
	public IFlexibleBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		IModel baseModel = null;
		try {
			baseModel = ModelLoaderRegistry.getModel(new ResourceLocation("undergroundbiomes:block/custom_ore"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		IRetexturableModel retexturableModel = (IRetexturableModel) baseModel;
		IModel finalModel = retexturableModel.retexture(ImmutableMap.of("stone", stoneTexture, "ore", oreTexture));

		return finalModel.bake(state, format, bakedTextureGetter);
	}

	@Override
	public IModelState getDefaultState() {
		return ModelRotation.X0_Y0;
	}

}
