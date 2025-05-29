package sbs.jeflo.customenchants.enchantments.tools;

import sbs.jeflo.customenchants.enchantments.CustomEnchantment;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import java.util.HashSet;
import java.util.Set;

public class LumberjackEnchantment extends CustomEnchantment {
    public LumberjackEnchantment() {
        super("lumberjack", 3);
    }
    @Override
    public boolean canEnchantItem(ItemStack item) {
        if (item == null) return false;
        return item.getType().toString().endsWith("_AXE");
    }
    @Override
    public void applyEffect(Player player, ItemStack item, int level) {}
    public void breakTree(BlockBreakEvent event, int level) {
        if (event == null || event.getBlock() == null || event.getPlayer() == null) return;
        Block start = event.getBlock();
        Player player = event.getPlayer();
        Material logType = start.getType();
        Set<Block> visited = new HashSet<>();
        int limit;
        switch (Math.max(1, Math.min(level, 3))) {
            case 1: limit = 30; break;
            case 2: limit = 60; break;
            case 3: limit = 100; break;
            default: limit = 30;
        }
        breakTreeRecursive(start, logType, visited, limit, player);
    }
    private void breakTreeRecursive(Block block, Material logType, Set<Block> visited, int limit, Player player) {
        if (visited.size() > limit) return;
        if (block.getType() != logType || visited.contains(block)) return;
        visited.add(block);
        block.breakNaturally(player.getInventory().getItemInMainHand());
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (x == 0 && y == 0 && z == 0) continue;
                    Block relative = block.getLocation().add(x, y, z).getBlock();
                    breakTreeRecursive(relative, logType, visited, limit, player);
                }
            }
        }
    }
}
