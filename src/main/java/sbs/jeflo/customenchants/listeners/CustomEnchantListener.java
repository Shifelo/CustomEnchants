package sbs.jeflo.customenchants.listeners;

import sbs.jeflo.customenchants.CustomEnchants;
import sbs.jeflo.customenchants.enchantments.CustomEnchantment;
import sbs.jeflo.customenchants.enchantments.tools.*;
import sbs.jeflo.customenchants.enchantments.sword.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomEnchantListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();
        if (tool == null || !tool.hasItemMeta()) return;
        ItemMeta meta = tool.getItemMeta();
        if (meta == null || meta.getLore() == null) return;
        for (String lore : meta.getLore()) {
            for (CustomEnchantment ench : CustomEnchants.CUSTOM_ENCHANTS) {
                if (lore.startsWith("customenchants:" + ench.getName() + ":")) {
                    int level = getLevelFromLore(lore);
                    if (ench instanceof ExcavatorEnchantment) {
                        ((ExcavatorEnchantment) ench).breakArea(event, level);
                    } else if (ench instanceof LumberjackEnchantment) {
                        ((LumberjackEnchantment) ench).breakTree(event, level);
                    } else if (ench instanceof VeinMinerEnchantment) {
                        ((VeinMinerEnchantment) ench).breakVein(event);
                    } else if (ench instanceof AutoSmeltEnchantment) {
                        ((AutoSmeltEnchantment) ench).autoSmelt(event);
                    } else if (ench instanceof ReplenishEnchantment) {
                        ((ReplenishEnchantment) ench).autoReplant(event);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        Player player = (Player) event.getDamager();
        ItemStack weapon = player.getInventory().getItemInMainHand();
        if (weapon == null || !weapon.hasItemMeta()) return;
        ItemMeta meta = weapon.getItemMeta();
        if (meta == null || meta.getLore() == null) return;
        for (String lore : meta.getLore()) {
            for (CustomEnchantment ench : CustomEnchants.CUSTOM_ENCHANTS) {
                if (lore.startsWith("customenchants:" + ench.getName() + ":")) {
                    int level = getLevelFromLore(lore);
                    if (ench instanceof BleedingEnchantment) {
                        ((BleedingEnchantment) ench).applyBleed(event, level);
                    } else if (ench instanceof ComboEnchantment) {
                        ((ComboEnchantment) ench).applyCombo(event, level, 1);
                    } else if (ench instanceof DisarmEnchantment) {
                        ((DisarmEnchantment) ench).tryDisarm(event);
                    } else if (ench instanceof VampirismEnchantment) {
                        ((VampirismEnchantment) ench).applyVampirism(event, level);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        Player killer = event.getEntity().getKiller();
        ItemStack weapon = killer.getInventory().getItemInMainHand();
        if (weapon == null || !weapon.hasItemMeta()) return;
        ItemMeta meta = weapon.getItemMeta();
        if (meta == null || meta.getLore() == null) return;
        for (String lore : meta.getLore()) {
            for (CustomEnchantment ench : CustomEnchants.CUSTOM_ENCHANTS) {
                if (lore.startsWith("customenchants:" + ench.getName() + ":")) {
                    if (ench instanceof DecapitateEnchantment) {
                        ((DecapitateEnchantment) ench).tryDropHead(event);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onAnvilCombine(PrepareAnvilEvent event) {
        ItemStack left = event.getInventory().getItem(0);
        ItemStack right = event.getInventory().getItem(1);
        if (left == null || right == null) return;
        if (right.getType() != Material.ENCHANTED_BOOK || !right.hasItemMeta()) return;
        ItemMeta bookMeta = right.getItemMeta();
        if (bookMeta == null || bookMeta.getLore() == null) return;
        for (String lore : bookMeta.getLore()) {
            for (CustomEnchantment ench : CustomEnchants.CUSTOM_ENCHANTS) {
                if (lore.startsWith("customenchants:" + ench.getName() + ":")) {
                    int level = getLevelFromLore(lore);
                    if (left.getType() == Material.ENCHANTED_BOOK && left.hasItemMeta()) {
                        ItemMeta leftMeta = left.getItemMeta();
                        if (leftMeta != null && leftMeta.getLore() != null) {
                            for (String leftLore : leftMeta.getLore()) {
                                if (leftLore.equals(lore) && ench.getMaxLevel() > level) {
                                    ItemStack result = new ItemStack(Material.ENCHANTED_BOOK);
                                    ItemMeta resultMeta = result.getItemMeta();
                                    String displayName = sbs.jeflo.customenchants.MessageProvider.get(ench.getName().toLowerCase(), "name", ench.getName());
                                    String format = sbs.jeflo.customenchants.MessageProvider.get("book", "format", "{name} {level}");
                                    String description = sbs.jeflo.customenchants.MessageProvider.get(ench.getName().toLowerCase(), "description", "");
                                    String formattedName = format.replace("{name}", displayName).replace("{level}", String.valueOf(level + 1));
                                    resultMeta.setDisplayName(formattedName);
                                    java.util.List<String> loreList = new java.util.ArrayList<>();
                                    loreList.add("customenchants:" + ench.getName() + ":" + (level + 1));
                                    for (String line : description.split("\n")) {
                                        if (!line.trim().isEmpty()) loreList.add(line);
                                    }
                                    resultMeta.setLore(loreList);
                                    result.setItemMeta(resultMeta);
                                    event.setResult(result);
                                    return;
                                }
                            }
                        }
                    }
                    if (ench.canEnchantItem(left)) {
                        ItemStack result = left.clone();
                        ItemMeta resultMeta = result.getItemMeta();
                        java.util.List<String> loreList = resultMeta.hasLore() ? new java.util.ArrayList<>(resultMeta.getLore()) : new java.util.ArrayList<>();
                        boolean already = false;
                        String enchantIdLore = "customenchants:" + ench.getName() + ":" + level;
                        String displayLore = sbs.jeflo.customenchants.MessageProvider.get(ench.getName().toLowerCase(), "name", ench.getName()) + " " + level;
                        loreList.removeIf(l -> l.startsWith("customenchants:" + ench.getName() + ":"));
                        for (String l : loreList) {
                            if (l.equals(displayLore) || l.startsWith("customenchants:" + ench.getName() + ":")) {
                                already = true;
                                break;
                            }
                        }
                        if (!already) {
                            loreList.add(displayLore);
                            loreList.add(enchantIdLore);
                            resultMeta.setLore(loreList);
                            result.setItemMeta(resultMeta);
                            event.setResult(result);
                        }
                        return;
                    }
                }
            }
        }
    }

    private int getLevelFromLore(String lore) {
        try {
            String[] parts = lore.split(":");
            return Integer.parseInt(parts[2]);
        } catch (Exception e) {
            return 1;
        }
    }
}
