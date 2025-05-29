package sbs.jeflo.customenchants.enchantments;

public abstract class CustomEnchantment {
    private final String name;
    private final int maxLevel;
    public CustomEnchantment(String name, int maxLevel) {
        this.name = name.replace(" ", "").toLowerCase();
        this.maxLevel = maxLevel;
    }
    public String getName() { return name; }
    public int getMaxLevel() { return maxLevel; }
    public abstract boolean canEnchantItem(org.bukkit.inventory.ItemStack item);
    public abstract void applyEffect(org.bukkit.entity.Player player, org.bukkit.inventory.ItemStack item, int level);
}
