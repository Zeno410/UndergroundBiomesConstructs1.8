package exterminatorJeff.undergroundBiomes.common.item;

import java.util.Collection;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import Zeno410Utils.Acceptor;
import Zeno410Utils.Mutable;

/**
 *
 * @author Zeno410
 */
public class SwitchableRecipeGroup extends Acceptor<Boolean> {

	private Collection<IRecipe> recipes;

	public SwitchableRecipeGroup(Collection<IRecipe> recipes, Mutable<Boolean> flag) {
		this.recipes = recipes;
		if (flag.value())
			addRecipes();
		flag.informOnChange(this);
	}

	@SuppressWarnings("unchecked")
	private void addRecipes() {
		for (IRecipe recipe : recipes) {
			CraftingManager.getInstance().getRecipeList().add(recipe);
		}
	}

	private void removeRecipes() {
		for (IRecipe recipe : recipes) {
			CraftingManager.getInstance().getRecipeList().remove(recipe);
		}
	}

	@Override
	public void accept(Boolean accepted) {
		if (accepted) {
			addRecipes();
		} else {
			removeRecipes();
		}
	}

}
