package exterminatorJeff.undergroundBiomes.client;

import java.util.function.BiConsumer;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Help registering textures not associated with blocks like ore overlays
 * 
 * @author Spooky4672
 *
 */
@SideOnly(Side.CLIENT)
final class TexturesRegistrationHandler {

	@SubscribeEvent
	public void registerOverlays(TextureStitchEvent.Pre e) {
		assert !UBOreOverlaysRegistry.overlaysLocations.isEmpty() : "overlaysLocations is empty !";
		UBOreOverlaysRegistry.overlaysLocations.forEach(new BiConsumer<String, ResourceLocation>() {
			@Override
			public void accept(String srt, ResourceLocation location) {
				e.map.registerSprite(location);
			}
		});
	}

}
