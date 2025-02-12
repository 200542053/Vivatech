package vivatech.controller;

import io.github.cottonmc.cotton.datapack.recipe.CottonRecipes;
import io.github.cottonmc.cotton.gui.CottonScreenController;
import io.github.cottonmc.cotton.gui.widget.WBar;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Identifier;
import vivatech.Vivatech;
import vivatech.VivatechClient;
import vivatech.util.StringHelper;

public class EnergyBankController extends CottonScreenController {

    public EnergyBankController(int syncId, PlayerInventory playerInventory, BlockContext context) {
        super(CottonRecipes.NULL_RECIPE, syncId, playerInventory, getBlockInventory(context), getBlockPropertyDelegate(context));

        WPlainPanel root = new WPlainPanel();
        ((WGridPanel) getRootPanel()).add(root, 0, 0);

        // Bars
        WBar energyBar = new WBar(VivatechClient.ENERGY_BAR_BG, VivatechClient.ENERGY_BAR, 0, 1);
        energyBar.withTooltip(StringHelper.getTranslationKey("info", new Identifier(Vivatech.MODID, "energy_with_max")));
        root.add(energyBar, 1, 2, 14, 64);

        // Slots
        root.add(WItemSlot.of(blockInventory, 0), 36, 27);
        root.add(WItemSlot.of(blockInventory, 1), 108, 27);
        root.add(createPlayerInventoryPanel(), 0, 72);

        root.validate(this);
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return -1;
    }
}
