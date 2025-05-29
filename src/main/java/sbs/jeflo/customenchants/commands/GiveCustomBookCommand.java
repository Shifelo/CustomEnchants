package sbs.jeflo.customenchants.commands;

import sbs.jeflo.customenchants.CustomEnchants;
import sbs.jeflo.customenchants.enchantments.CustomEnchantment;
import sbs.jeflo.customenchants.MessageProvider;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GiveCustomBookCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(MessageProvider.get("usage"));
            return true;
        }
        String enchantName = args[0].toLowerCase();
        int level = 1;
        String targetName = null;
        Player targetPlayer = null;
        if (args.length > 2) {
            try { level = Integer.parseInt(args[1]); } catch (Exception ignored) {}
            targetName = args[2];
        } else if (args.length == 2) {
            try {
                level = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                targetName = args[1];
            }
        }
        if (targetName != null) {
            targetPlayer = org.bukkit.Bukkit.getPlayerExact(targetName);
            if (targetPlayer == null) {
                sender.sendMessage("§cJugador no encontrado: " + targetName);
                return true;
            }
        } else if (sender instanceof Player) {
            targetPlayer = (Player) sender;
        } else {
            sender.sendMessage(MessageProvider.get("only_players"));
            return true;
        }
        CustomEnchantment enchant = null;
        for (CustomEnchantment ench : CustomEnchants.CUSTOM_ENCHANTS) {
            if (ench.getName().equalsIgnoreCase(enchantName)) {
                enchant = ench;
                break;
            }
        }
        if (enchant == null) {
            sender.sendMessage(MessageProvider.get("not_found"));
            return true;
        }
        String displayName = MessageProvider.get(enchant.getName().toLowerCase(), "name", "§b" + enchant.getName());
        String description = MessageProvider.get(enchant.getName().toLowerCase(), "description", "§7No description available.");
        String format = MessageProvider.get("book", "format", "{name} {level}");
        List<String> bookLore = new ArrayList<>();
        bookLore.add("customenchants:" + enchant.getName() + ":" + level);
        for (String line : description.split("\n")) {
            if (!line.trim().isEmpty()) bookLore.add(line);
        }
        displayName = format.replace("{name}", displayName).replace("{level}", String.valueOf(level));
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = book.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(bookLore);
        book.setItemMeta(meta);
        targetPlayer.getInventory().addItem(book);
        sender.sendMessage(MessageProvider.get("given").replace("{name}", displayName).replace("{level}", String.valueOf(level)) + " a " + targetPlayer.getName());
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> names = new ArrayList<>();
            for (CustomEnchantment ench : CustomEnchants.CUSTOM_ENCHANTS) {
                names.add(ench.getName().toLowerCase());
            }
            return names;
        }
        return Collections.emptyList();
    }
}
