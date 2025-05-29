package sbs.jeflo.customenchants.enchantments.tools;

import sbs.jeflo.customenchants.enchantments.CustomEnchantment;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import java.util.HashSet;
import java.util.Set;

public class VeinMinerEnchantment extends CustomEnchantment {
    public VeinMinerEnchantment() {
        super("veinminer", 3);
    }
    @Override
    public boolean canEnchantItem(ItemStack item) {
        if (item == null) return false;
        return item.getType().toString().endsWith("_PICKAXE");
    }
    @Override
    public void applyEffect(Player player, ItemStack item, int level) {}
    public void breakVein(BlockBreakEvent event) {
        if (event == null || event.getBlock() == null || event.getPlayer() == null) return;
        Block start = event.getBlock();
        Player player = event.getPlayer();
        Material oreType = start.getType();
        Set<Block> visited = new HashSet<>();
        int limit = 20 + 40 * Math.max(1, Math.min(player.getLevel(), 3));
        boolean hasAutoSmelt = false;
        ItemStack tool = player.getInventory().getItemInMainHand();
        if (tool != null && tool.hasItemMeta() && tool.getItemMeta().hasLore()) {
            for (String lore : tool.getItemMeta().getLore()) {
                if (lore.startsWith("customenchants:autosmelt:")) {
                    hasAutoSmelt = true;
                    break;
                }
            }
        }
        breakVeinRecursive(start, oreType, visited, limit, player, hasAutoSmelt, tool);
    }
    private void breakVeinRecursive(Block block, Material oreType, Set<Block> visited, int limit, Player player, boolean hasAutoSmelt, ItemStack tool) {
        if (visited.size() > limit) return;
        if (block.getType() != oreType || visited.contains(block)) return;
        visited.add(block);
        if (hasAutoSmelt) {
            Material smeltedDrop = getSmeltedForVeinMiner(block.getType());
            if (smeltedDrop != null) {
                block.setType(Material.AIR);
                block.getWorld().dropItemNaturally(block.getLocation().add(0.5, 0.5, 0.5), new ItemStack(smeltedDrop));
            } else {
                block.breakNaturally(tool);
            }
        } else {
            block.breakNaturally(tool);
        }
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (x == 0 && y == 0 && z == 0) continue;
                    Block relative = block.getLocation().add(x, y, z).getBlock();
                    breakVeinRecursive(relative, oreType, visited, limit, player, hasAutoSmelt, tool);
                }
            }
        }
    }
    private Material getSmeltedForVeinMiner(Material type) {
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
