package me.xiaozhangup.hayerquest.utils.manager;

import me.xiaozhangup.hayerquest.HayerQuest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    public static void createFile(String s) {
        File file = new File(HayerQuest.plugin.getDataFolder(), s + ".yml");
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static FileConfiguration getConfig(String s) {
        File file = new File(HayerQuest.plugin.getDataFolder(), s + ".yml");
        if (!file.exists()) {
            HayerQuest.plugin.saveResource(s, false);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void writeConfig(String s, String node, Object value) {
        FileConfiguration fileConfiguration = getConfig(s);
        fileConfiguration.set(node, value);
        try {
            fileConfiguration.save(new File(HayerQuest.plugin.getDataFolder(), s + ".yml"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
