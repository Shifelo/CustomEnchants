package sbs.jeflo.customenchants.enchantments.sword;

import sbs.jeflo.customenchants.enchantments.CustomEnchantment;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class DecapitateEnchantment extends CustomEnchantment {
    public DecapitateEnchantment() {
        super("decapitate", 1);
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        if (item == null) return false;
        return item.getType().toString().endsWith("_SWORD");
    }

    @Override
    public void applyEffect(Player player, ItemStack item, int level) {}

    public void tryDropHead(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            meta.setOwningPlayer((Player) entity);
            skull.setItemMeta(meta);
            entity.getWorld().dropItemNaturally(entity.getLocation(), skull);
        }
    }
}
