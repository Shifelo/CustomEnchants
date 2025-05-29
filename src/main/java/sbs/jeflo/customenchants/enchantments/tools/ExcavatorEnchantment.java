package sbs.jeflo.customenchants.enchantments.tools;

import sbs.jeflo.customenchants.enchantments.CustomEnchantment;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Location;

public class ExcavatorEnchantment extends CustomEnchantment {
    public ExcavatorEnchantment() {
        super("excavator", 3);
    }
    @Override
    public boolean canEnchantItem(ItemStack item) {
        if (item == null) return false;
        String type = item.getType().toString();
        return type.endsWith("_PICKAXE") || type.endsWith("_SHOVEL") || type.endsWith("_AXE") || type.endsWith("_HOE");
    }
    @Override
    public void applyEffect(Player player, ItemStack item, int level) {}
    public void breakArea(BlockBreakEvent event, int level) {
        if (event == null || event.getBlock() == null || event.getPlayer() == null) return;
        Block center = event.getBlock();
        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();
        int radius = Math.max(1, Math.min(level, 3));
        Location loc = center.getLocation();
        boolean hasAutoSmelt = false;
        if (tool != null && tool.hasItemMeta() && tool.getItemMeta().hasLore()) {
            for (String lore : tool.getItemMeta().getLore()) {
                if (lore.startsWith("customenchants:autosmelt:")) {
                    hasAutoSmelt = true;
                    break;
                }
            }
        }
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block b = loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
                    if (b.getType() != Material.AIR && b.getType() != Material.BEDROCK) {
                        if (hasAutoSmelt) {
                            Material smeltedDrop = getSmeltedForExcavator(b.getType());
                            if (smeltedDrop != null) {
                                b.setType(Material.AIR);
                                b.getWorld().dropItemNaturally(b.getLocation().add(0.5, 0.5, 0.5), new ItemStack(smeltedDrop));
                            } else {
                                b.breakNaturally(tool);
                            }
                        } else {
                            b.breakNaturally(tool);
                        }
                    }
                }
            }
        }
    }
    private Material getSmeltedForExcavator(Material type) {
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
