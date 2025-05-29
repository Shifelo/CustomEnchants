package sbs.jeflo.customenchants.enchantments.sword;

import sbs.jeflo.customenchants.enchantments.CustomEnchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class DisarmEnchantment extends CustomEnchantment {
    public DisarmEnchantment() {
        super("disarm", 3);
    }
    @Override
    public boolean canEnchantItem(ItemStack item) {
        if (item == null) return false;
        return item.getType().toString().endsWith("_SWORD");
    }
    @Override
    public void applyEffect(Player player, ItemStack item, int level) {}
    public void tryDisarm(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player target = (Player) event.getEntity();
            PlayerInventory inv = target.getInventory();
            ItemStack hand = inv.getItemInMainHand();
            if (hand != null && hand.getType() != org.bukkit.Material.AIR) {
                double chance = 0.15 + 0.1 * Math.max(1, Math.min(event.getDamager() instanceof Player ? ((Player)event.getDamager()).getLevel() : 1, 3));
                if (new java.util.Random().nextDouble() < chance) {
                    inv.setItemInMainHand(null);
                    target.getWorld().dropItemNaturally(target.getLocation(), hand);
                }
            }
        }
    }
}
