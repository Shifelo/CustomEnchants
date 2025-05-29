package sbs.jeflo.customenchants.enchantments.sword;

import sbs.jeflo.customenchants.enchantments.CustomEnchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.attribute.Attribute;

public class VampirismEnchantment extends CustomEnchantment {
    public VampirismEnchantment() {
        super("vampirism", 3);
    }
    @Override
    public boolean canEnchantItem(ItemStack item) {
        if (item == null) return false;
        return item.getType().toString().endsWith("_SWORD");
    }
    @Override
    public void applyEffect(org.bukkit.entity.Player player, ItemStack item, int level) {}
    public void applyVampirism(EntityDamageByEntityEvent event, int level) {
        if (event.getDamager() instanceof org.bukkit.entity.Player && event.getEntity() instanceof LivingEntity) {
            org.bukkit.entity.Player attacker = (org.bukkit.entity.Player) event.getDamager();
            double heal = event.getDamage() * (0.1 * Math.max(1, Math.min(level, 3)));
            org.bukkit.attribute.AttributeInstance attr = null;
            for (org.bukkit.attribute.Attribute attribute : org.bukkit.attribute.Attribute.values()) {
                if (attribute.name().toLowerCase().contains("max_health")) {
                    attr = attacker.getAttribute(attribute);
                    break;
                }
            }
            double maxHealth = (attr != null) ? attr.getValue() : 20.0;
            double newHealth = Math.min(attacker.getHealth() + heal, maxHealth);
            attacker.setHealth(newHealth);
        }
    }
}
