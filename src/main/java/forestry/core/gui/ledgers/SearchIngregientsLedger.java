package forestry.core.gui.ledgers;

import java.util.Locale;

import net.minecraft.util.IIcon;

import forestry.core.render.TextureManager;
import forestry.core.tiles.IIngredientsSearchController;
import forestry.core.tiles.IngredientsStorage;
import forestry.core.utils.StringUtil;

public class SearchIngregientsLedger extends Ledger {

    private final IIngredientsSearchController ingredientsSearch;

    public SearchIngregientsLedger(LedgerManager manager, IIngredientsSearchController ingredientsSearch) {
        super(manager, "search_ingredients");
        this.ingredientsSearch = ingredientsSearch;
        this.maxHeight = 36;
    }

    private boolean isSwitchButton(int mouseX, int mouseY) {
        int shiftX = currentShiftX + 20;
        int shiftY = currentShiftY + 8;
        return mouseX >= shiftX && mouseX <= currentShiftX + currentWidth && mouseY >= shiftY && mouseY <= shiftY + 24;
    }

    @Override
    public void draw(int x, int y) {
        // Draw background
        drawBackground(x, y);

        // Draw icon
        IIcon icon = TextureManager.getInstance().getDefault(
                "misc/search_ingredients."
                        + ingredientsSearch.getIngredientsStorage().toString().toLowerCase(Locale.ENGLISH));
        drawIcon(icon, x + 3, y + 4);

        // Draw description
        if (!isFullyOpened()) {
            return;
        }

        drawHeader(StringUtil.localize("gui.search_ingredients"), x + 22, y + 8);
        drawText(
                StringUtil.localize(
                        "gui.search_ingredients."
                                + ingredientsSearch.getIngredientsStorage().toString().toLowerCase(Locale.ENGLISH)),
                x + 22,
                y + 20);
    }

    @Override
    public String getTooltip() {
        return StringUtil.localize("gui.search_ingredients") + ": "
                + StringUtil.localize(
                        "gui.search_ingredients."
                                + ingredientsSearch.getIngredientsStorage().toString().toLowerCase(Locale.ENGLISH));
    }

    @Override
    public boolean handleMouseClicked(int x, int y, int mouseButton) {
        if (isSwitchButton(x, y)) {
            IngredientsStorage ingredientsStorage = ingredientsSearch.getIngredientsStorage();
            IngredientsStorage newIngredientsStorage = mouseButton == 0 ? ingredientsStorage.next()
                    : ingredientsStorage.previous();
            ingredientsSearch.setIngredientsStorage(newIngredientsStorage);
            return true;
        }

        return false;
    }

    @Override
    public boolean acceptMouseButton(int mouseButton) {
        return mouseButton == 0 || mouseButton == 1;
    }
}
