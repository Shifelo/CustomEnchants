package sbs.jeflo.customenchants.enchantments.sword;

import sbs.jeflo.customenchants.enchantments.CustomEnchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BleedingEnchantment extends CustomEnchantment {
    public BleedingEnchantment() {
        super("bleeding", 3);
    }
    @Override
    public boolean canEnchantItem(ItemStack item) {
        if (item == null) return false;
        return item.getType().toString().endsWith("_SWORD");
    }
    @Override
    public void applyEffect(org.bukkit.entity.Player player, ItemStack item, int level) {}
    public void applyBleed(EntityDamageByEntityEvent event, int level) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity target = (LivingEntity) event.getEntity();
            int duration = 40 + (level * 20);
            int amplifier = Math.max(0, Math.min(level - 1, 2));
            target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, duration, amplifier));
        }
    }
}
