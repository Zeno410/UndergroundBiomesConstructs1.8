package exterminatorJeff.undergroundBiomes.client;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Vector3f;

import com.google.common.primitives.Ints;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.IFlexibleBakedModel;

/**
 * 
 * @author Spooky4672
 *
 */
@SuppressWarnings("deprecation")
public class UBOreModel implements IFlexibleBakedModel {

	private final Map<EnumFacing, List<BakedQuad>> faceQuads = new HashMap<EnumFacing, List<BakedQuad>>();
	private final VertexFormat format;
	private final TextureAtlasSprite texture;

	public UBOreModel(VertexFormat format, TextureAtlasSprite stoneTexture, TextureAtlasSprite oreTexture) {
		this.format = format;
		this.texture = stoneTexture;
		for (int i = 0; i < EnumFacing.VALUES.length; i++) {
			faceQuads.put(EnumFacing.VALUES[i], Arrays.asList(getBakedQuad(stoneTexture, EnumFacing.VALUES[i]), getBakedQuad(oreTexture, EnumFacing.VALUES[i])));
		}
	}

	@Override
	public List<BakedQuad> getFaceQuads(EnumFacing facing) {
		return faceQuads.get(facing);
	}

	@Override
	public List<BakedQuad> getGeneralQuads() {
		return Collections.emptyList();
	}

	@Override
	public boolean isAmbientOcclusion() {
		return true;
	}

	@Override
	public boolean isGui3d() {
		return true;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return false;
	}

	@Override
	public TextureAtlasSprite getTexture() {
		return texture;
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return new ItemCameraTransforms(new ItemTransformVec3f(new Vector3f(10, -45, 170), new Vector3f(0, 0.15F, -0.175F), new Vector3f(0.375F, 0.375F, 0.375F)), ItemTransformVec3f.DEFAULT,
				ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT);
	}

	@Override
	public VertexFormat getFormat() {
		return format;
	}

	private BakedQuad getBakedQuad(TextureAtlasSprite texture, EnumFacing face) {
		final float MIN = -0.001F, MAX = 1.001F;

		float x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4;

		switch (face) {
		case DOWN:
			x1 = x2 = z2 = z3 = MIN;
			z1 = x4 = z4 = x3 = MAX;
			y1 = y2 = y4 = y3 = MIN;
			break;
		case EAST:
			y1 = z1 = z2 = y4 = MAX;
			y2 = z4 = y3 = z3 = MIN;
			x1 = x2 = x4 = x3 = MAX;
			break;
		case NORTH:
			x1 = y1 = x2 = y4 = MAX;
			y2 = x4 = x3 = y3 = MIN;
			z1 = z2 = z4 = z3 = MIN;
			break;
		case SOUTH:
			x1 = x2 = y2 = y3 = MIN;
			y1 = x4 = y4 = x3 = MAX;
			z1 = z2 = z4 = z3 = MAX;
			break;
		case UP:
			x1 = z1 = x2 = z4 = MIN;
			z2 = x4 = x3 = z3 = MAX;
			y1 = y2 = y4 = y3 = MAX;
			break;
		case WEST:
			y1 = y4 = z4 = z3 = MAX;
			z1 = y2 = z2 = y3 = MIN;
			x1 = x2 = x4 = x3 = MIN;
			break;
		default:
			throw new RuntimeException("Unknown face");
		}
		return new BakedQuad(Ints.concat(vertexToInts(x1, y1, z1, Color.WHITE.getRGB(), texture, 0, 0), vertexToInts(x2, y2, z2, Color.WHITE.getRGB(), texture, 0, 16),
				vertexToInts(x3, y3, z3, Color.WHITE.getRGB(), texture, 16, 16), vertexToInts(x4, y4, z4, Color.WHITE.getRGB(), texture, 16, 0)), -1, face);
	}

	private int[] vertexToInts(float x, float y, float z, int color, TextureAtlasSprite texture, float u, float v) {
		return new int[] { Float.floatToRawIntBits(x), Float.floatToRawIntBits(y), Float.floatToRawIntBits(z), color, Float.floatToRawIntBits(texture.getInterpolatedU(u)),
				Float.floatToRawIntBits(texture.getInterpolatedV(v)), 0 };
	}

}
