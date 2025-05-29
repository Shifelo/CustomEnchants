package sbs.jeflo.customenchants;

import org.bukkit.configuration.file.YamlConfiguration;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MessageProvider {
    private static YamlConfiguration messages;

    static {
        try (InputStream in = MessageProvider.class.getClassLoader().getResourceAsStream("messages.yml");
             InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            if (in != null) {
                messages = YamlConfiguration.loadConfiguration(reader);
            } else {
                messages = new YamlConfiguration();
            }
        } catch (Exception e) {
            e.printStackTrace();
            messages = new YamlConfiguration();
        }
    }

    public static String get(String key) {
        return messages.getString("en." + key, "§c[Missing message: " + key + "]");
    }

    public static String get(String section, String key) {
        return messages.getString("en." + section + "." + key,
                "§c[Missing message: " + section + "." + key + "]");
    }

    public static String get(String section, String key, String fallback) {
        return messages.getString("en." + section + "." + key, fallback);
    }
}
