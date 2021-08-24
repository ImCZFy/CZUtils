package com.github.imczfy.czutils.utils;

import com.github.imczfy.czutils.CZUtils;
import com.github.imczfy.czutils.exceptions.LanguageNotFoundException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class LanguageUtils {
    private CZUtils i = CZUtils.getPlugin(CZUtils.class);
    private Player player;
    public LanguageUtils(Player player) {
        this.player = player;
    }
    public static String getLanguageList() {
        int i;
        List<String> languageList = CZUtils.getPlugin(CZUtils.class).getConfig().getStringList("Languages");
        StringBuilder stringBuilder = new StringBuilder();
        for (String lang : languageList) {
            stringBuilder.append(lang).append(" ");
        }
        return stringBuilder.toString();
    }
    public String getLanguage() {
        return i.getDataConfig().getString(player.getName() + ".language");
    }
    public void setLanguage(String lang) throws LanguageNotFoundException {
        List<String> languages = i.getConfig().getStringList("Languages");
        if (languages.contains(lang)) {
            i.getDataConfig().set(player.getName() + ".language", lang);
            i.reloadConfigurations();
            player.sendMessage(ChatUtils.t(getMessage("Language-Changed").replace("%lang%", lang)));
        } else {
            throw new LanguageNotFoundException();
        }
    }
    public String getMessage(String msg) {
        FileConfiguration languageFile = i.getLanguageFile(getLanguage());
        return languageFile.getString("Message." + msg);
    }
}