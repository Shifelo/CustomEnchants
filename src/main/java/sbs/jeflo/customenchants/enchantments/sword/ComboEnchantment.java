package sbs.jeflo.customenchants.enchantments.sword;

import sbs.jeflo.customenchants.enchantments.CustomEnchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ComboEnchantment extends CustomEnchantment {
    public ComboEnchantment() {
        super("combo", 3);
    }
    @Override
    public boolean canEnchantItem(ItemStack item) {
        if (item == null) return false;
        return item.getType().toString().endsWith("_SWORD");
    }
    @Override
    public void applyEffect(org.bukkit.entity.Player player, ItemStack item, int level) {}
    public void applyCombo(EntityDamageByEntityEvent event, int level, int comboHits) {
        if (event.getEntity() instanceof LivingEntity) {
            double extra = Math.min(comboHits, Math.max(1, Math.min(level, 3))) * 0.5;
            event.setDamage(event.getDamage() + extra);
        }
    }
}
