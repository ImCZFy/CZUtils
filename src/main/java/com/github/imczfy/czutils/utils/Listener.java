package com.github.imczfy.czutils.utils;

import com.github.imczfy.czutils.CZUtils;
import com.github.imczfy.czutils.exceptions.LanguageNotFoundException;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listener implements org.bukkit.event.Listener {//
    private CZUtils i = CZUtils.getPlugin(CZUtils.class);
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        LanguageUtils languageUtils = new LanguageUtils(event.getPlayer());
        if (!event.getPlayer().hasPlayedBefore() || languageUtils.getLanguage() == null) {
            try {
                languageUtils.setLanguage("ENGLISH");
            } catch (LanguageNotFoundException e) {
                e.printStackTrace();
            }
            for (Player player: Bukkit.getOnlinePlayers()) {
                LanguageUtils languageUtils1 = new LanguageUtils(player);
                String lang = languageUtils1.getLanguage();
                if (languageUtils1.getMessage("Join").equalsIgnoreCase("default")) {
                    player.sendMessage(event.getJoinMessage());
                    event.setJoinMessage(null);
                } else {
                    event.setJoinMessage(null);
                    player.sendMessage(PlaceholderAPI.setPlaceholders(event.getPlayer(), languageUtils1.getMessage("Join")));
                }
            }
        }
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
            for (Player player: Bukkit.getOnlinePlayers()) {
                LanguageUtils languageUtils1 = new LanguageUtils(player);
                String lang = languageUtils1.getLanguage();
                if (languageUtils1.getMessage("Quit").equalsIgnoreCase("default")) {
                    player.sendMessage(event.getQuitMessage());
                    event.setQuitMessage(null);
                } else {
                    event.setQuitMessage(null);
                    player.sendMessage(PlaceholderAPI.setPlaceholders(event.getPlayer(), languageUtils1.getMessage("Quit")));
            }
        }
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setFormat(i.getConfig().getString("Settings.Chat-Format").replace("$message", event.getMessage()));
    }
}