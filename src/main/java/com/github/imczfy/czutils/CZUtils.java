package com.github.imczfy.czutils;

import com.github.imczfy.czutils.commands.CZUtilsCommand;
import com.github.imczfy.czutils.utils.Listener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
//
public final class CZUtils extends JavaPlugin {
    public static String prefix = "&eCZUtils » ";
    public static String cross = "&8--------------------";
    private PluginDescriptionFile pluginDescriptionFile = this.getDescription();
    private FileConfiguration dataConfig;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("--------------------------------");
        Bukkit.getConsoleSender().sendMessage(pluginDescriptionFile.getName() + " v" + pluginDescriptionFile.getVersion());
        Bukkit.getConsoleSender().sendMessage("--------------------------------");

        saveDefaultConfig();
        refreshLanguages();
        loadConfigurations();
        regCommands();
        regEvents();
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("--------------------------------");
        Bukkit.getConsoleSender().sendMessage(pluginDescriptionFile.getName() + " v" + pluginDescriptionFile.getVersion());
        Bukkit.getConsoleSender().sendMessage("--------------------------------");
    }

    public void refreshLanguages() {
            getConfig().getStringList("Languages").forEach(lang -> {
                File language = new File(getDataFolder(), lang.toLowerCase() + ".yml");
                FileConfiguration languageFile = YamlConfiguration.loadConfiguration(language);
                if (!language.exists()) {
                    saveResource(lang.toLowerCase() + ".yml", true);
                }
                languageFile.addDefaults(getConfig().getConfigurationSection("Message").getRoot());
                getLogger().info("Successfully loaded " + lang + ".yml");
            });
    }
    public FileConfiguration getLanguageFile(String language) {
        File languageFile = new File(getDataFolder(), language.toLowerCase() + ".yml");
        if (!languageFile.exists()) {
            getLogger().log(Level.WARNING, "Cannot find the language configuration file you want, please check the languages in config.yml");
        }
        return YamlConfiguration.loadConfiguration(languageFile);
    }
    public void loadConfigurations() {
        File dataFile = new File(getDataFolder(), "data.yml");
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        if (!dataFile.exists()) {
            saveResource("data.yml",true);
        }
    }

    public FileConfiguration getDataConfig() {
        return dataConfig;
    }

    public void regCommands() {
        getCommand("czutils").setExecutor(new CZUtilsCommand());
        getCommand("czutils").setTabCompleter(new CZUtilsCommand());
    }
    public void regEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new Listener(),this);
    }
    public void reloadConfigurations() {
        File dataFile = new File(getDataFolder(), "data.yml");
        try {
            getDataConfig().save(dataFile);
            getDataConfig().load(dataFile);
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }
        getConfig().getStringList("Languages").forEach(lang -> {
            File language = new File(getDataFolder(), lang.toLowerCase() + ".yml");
            FileConfiguration languageFile = YamlConfiguration.loadConfiguration(language);
            try {
                languageFile.save(language);
                languageFile.load(language);
            } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
                e.printStackTrace();
            }
        });
    }
}
