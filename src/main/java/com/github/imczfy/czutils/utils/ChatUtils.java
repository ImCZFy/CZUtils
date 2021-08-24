package com.github.imczfy.czutils.utils;

import org.bukkit.ChatColor;

public class ChatUtils {
    //
    public static String t(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

}
