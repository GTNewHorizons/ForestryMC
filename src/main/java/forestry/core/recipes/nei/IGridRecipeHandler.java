package forestry.core.recipes.nei;

import java.util.List;

import codechicken.nei.PositionedStack;

public interface IGridRecipeHandler {

    int getGridStartX();

    int getGridStartY();

    int getGridSpacing();

    List<PositionedStack> getGridStacks(int recipeIndex);

}
