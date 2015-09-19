package exterminatorJeff.undergroundBiomes.client;

import exterminatorJeff.undergroundBiomes.common.UndergroundBiomes;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UBOreModelLoader implements ICustomModelLoader {

	private static final String UBORE_MODEL_NAME = "ubores";
	public static final String UBORE_MODEL = UndergroundBiomes.MODID + ":" + UBORE_MODEL_NAME;

	@SuppressWarnings("unused")
	private IResourceManager resourceManager;

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}

	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		return modelLocation.getResourceDomain().equals(UndergroundBiomes.MODID) && modelLocation.getResourcePath().contains(UBORE_MODEL_NAME);
	}

	@Override
	public IModel loadModel(ResourceLocation modelLocation) {
		String modelName = modelLocation.getResourcePath();
		if (modelName.contains(UBORE_MODEL_NAME)) {
			return new UBOreModelBase(modelLocation);
		}
		throw new RuntimeException("A builtin model '" + modelName + "' is not defined.");
	}

}
