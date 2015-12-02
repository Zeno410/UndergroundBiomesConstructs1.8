package exterminatorJeff.undergroundBiomes.client;

import java.util.Collection;
import java.util.Collections;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Function;

import exterminatorJeff.undergroundBiomes.api.OreOverlaysRegistry;

/**
 * One instance per UBOre instance
 * 
 * @author Spooky4672
 *
 */
@SideOnly(Side.CLIENT)
public class UBOreModelBase implements IModel {

	@SuppressWarnings("unused")
	private final String stoneType;
	private final String stoneVariant;
	private final String oreResourcesDomain;
	private final String oreType;

	public UBOreModelBase(ResourceLocation modelLocation) {
		String str = StringUtils.removeStart(modelLocation.getResourcePath(), UBOreModelLoader.UBORE_MODEL);
		/*
		 * Ore type
		 */
		oreType = StringUtils.substringAfterLast(str, ":");
		str = StringUtils.remove(str, ":" + oreType);
		oreResourcesDomain = StringUtils.substringAfterLast(str, ".");
		str = StringUtils.remove(str, "." + oreResourcesDomain);
		/*
		 * Stone variant
		 */
		stoneVariant = StringUtils.substringAfterLast(str, ".");
		str = StringUtils.remove(str, "." + stoneVariant);
		stoneType = StringUtils.substringAfterLast(str, ".");
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
		/*
		 * Prepare the textures
		 */
		TextureMap textureMap = Minecraft.getMinecraft().getTextureMapBlocks();
		TextureAtlasSprite stoneTexture = textureMap.getAtlasSprite(OreOverlaysRegistry.BLOCKS_TX_PATH + stoneVariant);
		TextureAtlasSprite oreTexture = textureMap.getAtlasSprite(OreOverlaysRegistry.BLOCKS_TX_PATH + UBOreOverlaysRegistry.oresToOverlays.get(oreType));
		/*
		 *
		 */
		return new UBOreModel(format, stoneTexture, oreTexture);
	}

	@Override
	public IModelState getDefaultState() {
		return null;
	}

}
