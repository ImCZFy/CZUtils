package com.github.imczfy.czutils.commands;

import com.github.imczfy.czutils.exceptions.LanguageNotFoundException;
import com.github.imczfy.czutils.utils.ChatUtils;
import com.github.imczfy.czutils.utils.LanguageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

public class LanguageCommand extends SubCommand{//

    @Override
    public void accessCommand(CommandSender sender, String[] args) {
        Player s = (Player) sender;
        LanguageUtils languageUtils = new LanguageUtils(s);
        if (args.length == 0) {
            s.sendMessage(ChatUtils.t(languageUtils.getMessage("Language-Usage")).replace("%lang%", LanguageUtils.getLanguageList()));
        }
        if (args.length == 1) {
            try {
                languageUtils.setLanguage(args[0].toUpperCase());
            } catch (LanguageNotFoundException e) {
                s.sendMessage(ChatUtils.t(languageUtils.getMessage("Language-Not-Found")).replace("%lang%", args[0]));
                e.printStackTrace();
            }
        }
    }
}
