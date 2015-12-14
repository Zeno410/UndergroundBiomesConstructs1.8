package exterminatorJeff.undergroundBiomes.common.item;

import java.util.logging.Logger;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import Zeno410Utils.Zeno410Logger;

/**
 *
 * @author Zeno410
 */
public class MutableRecipe {
	public static Logger logger = new Zeno410Logger("MutableRecipe").logger();
	private IRecipe current;

	@SuppressWarnings("unchecked")
	public void set(IRecipe newRecipe) {
		if (current != null) {
			// remove current recipe
			CraftingManager.getInstance().getRecipeList().remove(current);
		}
		current = newRecipe;
		if (current == null) {
			logger.info("null recipe");
		} else {
			logger.info(current.getRecipeOutput().getDisplayName());
		}
		if (current != null) {
			CraftingManager.getInstance().getRecipeList().add(current);
		}
	}

}
