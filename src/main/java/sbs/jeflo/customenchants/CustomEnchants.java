package sbs.jeflo.customenchants;

import sbs.jeflo.customenchants.commands.GiveCustomBookCommand;
import sbs.jeflo.customenchants.enchantments.tools.*;
import sbs.jeflo.customenchants.enchantments.sword.*;
import sbs.jeflo.customenchants.enchantments.CustomEnchantment;
import sbs.jeflo.customenchants.listeners.CustomEnchantListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.List;

public class CustomEnchants extends JavaPlugin {
    public static final List<CustomEnchantment> CUSTOM_ENCHANTS = new ArrayList<>();
    @Override
    public void onEnable() {
        CUSTOM_ENCHANTS.add(new ExcavatorEnchantment());
        CUSTOM_ENCHANTS.add(new LumberjackEnchantment());
        CUSTOM_ENCHANTS.add(new VeinMinerEnchantment());
        CUSTOM_ENCHANTS.add(new AutoSmeltEnchantment());
        CUSTOM_ENCHANTS.add(new ReplenishEnchantment());
        CUSTOM_ENCHANTS.add(new BleedingEnchantment());
        CUSTOM_ENCHANTS.add(new DecapitateEnchantment());
        CUSTOM_ENCHANTS.add(new VampirismEnchantment());
        CUSTOM_ENCHANTS.add(new ComboEnchantment());
        CUSTOM_ENCHANTS.add(new DisarmEnchantment());
        PluginCommand cmd = getCommand("givecustombook");
        if (cmd != null) cmd.setExecutor(new GiveCustomBookCommand());
        getServer().getPluginManager().registerEvents(new CustomEnchantListener(), this);
        getLogger().info("CustomEnchants plugin enabled!");
    }
    @Override
    public void onDisable() {
        getLogger().info("CustomEnchants plugin disabled!");
    }
}
