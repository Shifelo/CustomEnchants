package sbs.jeflo.customenchants.enchantments.tools;

import sbs.jeflo.customenchants.enchantments.CustomEnchantment;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class ReplenishEnchantment extends CustomEnchantment {
    public ReplenishEnchantment() {
        super("replenish", 1);
    }
    @Override
    public boolean canEnchantItem(ItemStack item) {
        if (item == null) return false;
        return item.getType().toString().endsWith("_HOE");
    }
    @Override
    public void applyEffect(org.bukkit.entity.Player player, ItemStack item, int level) {}
    public void autoReplant(BlockBreakEvent event) {
        if (event == null || event.getBlock() == null) return;
        Block block = event.getBlock();
        Material crop = block.getType();
        if (isCrop(crop)) {
            block.setType(crop);
        }
    }
    private boolean isCrop(Material mat) {
        switch (mat) {
            case WHEAT:
            case CARROTS:
            case POTATOES:
            case BEETROOTS:
            case NETHER_WART:
            case COCOA:
            case MELON_STEM:
            case PUMPKIN_STEM:
                return true;
            default:
                return false;
        }
    }
}
