package sbs.jeflo.customenchants.enchantments.tools;

import sbs.jeflo.customenchants.enchantments.CustomEnchantment;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class AutoSmeltEnchantment extends CustomEnchantment {
    public AutoSmeltEnchantment() {
        super("autosmelt", 1);
    }
    @Override
    public boolean canEnchantItem(ItemStack item) {
        if (item == null) return false;
        String type = item.getType().toString();
        return type.endsWith("_PICKAXE") || type.endsWith("_SHOVEL");
    }
    @Override
    public void applyEffect(org.bukkit.entity.Player player, ItemStack item, int level) {}
    public void autoSmelt(BlockBreakEvent event) {
        if (event == null || event.getBlock() == null) return;
        Block block = event.getBlock();
        Material drop = getSmelted(block.getType());
        if (drop != null) {
            block.setType(Material.AIR);
            int amount = 1;
            for (int i = 0; i < amount; i++) {
                block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(drop));
            }
        }
    }
    private Material getSmelted(Material type) {
        switch (type) {
            case IRON_ORE: return Material.IRON_INGOT;
            case GOLD_ORE: return Material.GOLD_INGOT;
            case ANCIENT_DEBRIS: return Material.NETHERITE_SCRAP;
            case SAND: return Material.GLASS;
            case COBBLESTONE: return Material.STONE;
            case COPPER_ORE: return Material.COPPER_INGOT;
            case DEEPSLATE_IRON_ORE: return Material.IRON_INGOT;
            case DEEPSLATE_GOLD_ORE: return Material.GOLD_INGOT;
            case DEEPSLATE_COPPER_ORE: return Material.COPPER_INGOT;
            default: return null;
        }
    }
}
