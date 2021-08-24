package com.github.imczfy.czutils.commands;

import com.github.imczfy.czutils.CZUtils;
import com.github.imczfy.czutils.utils.ChatUtils;
import com.github.imczfy.czutils.utils.LanguageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CZUtilsCommand implements CommandExecutor, TabCompleter {
    private CZUtils i = CZUtils.getPlugin(CZUtils.class);
    private LanguageCommand languageCommand;
    public CZUtilsCommand() {
        this.languageCommand = new LanguageCommand();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatUtils.t(CZUtils.cross));
            sender.sendMessage(ChatUtils.t("&bCZUtils &7Console Help"));
            sender.sendMessage(ChatUtils.t("§cCOMING SOON"));
            sender.sendMessage(ChatUtils.t(CZUtils.cross));
            return true;
        } else {
            Player s = (Player) sender;
            LanguageUtils languageUtils = new LanguageUtils(s);
            if (args.length == 0) {
                s.sendMessage(ChatUtils.t(languageUtils.getMessage("Help-Line-1")));
                s.sendMessage(ChatUtils.t(languageUtils.getMessage("Help-Line-2")));
                s.sendMessage(ChatUtils.t(languageUtils.getMessage("Help-Line-3")));
                return true;
            }
            List<String> arguments = new ArrayList<>();
            for (int i = 0; i < args.length; i++) {
                if (i == 0) {
                    continue;
                }
                arguments.add(args[i]);
            }
            if (args[0].equalsIgnoreCase("help")) {
                s.sendMessage(ChatUtils.t(languageUtils.getMessage("Help-Line-1")));
                s.sendMessage(ChatUtils.t(languageUtils.getMessage("Help-Line-2")));
                s.sendMessage(ChatUtils.t(languageUtils.getMessage("Help-Line-3")));
                return true;
            }
            if (args[0].equalsIgnoreCase("language")) {
                this.languageCommand.accessCommand(sender, arguments.toArray(new String[0]));
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                if (s.hasPermission("czutils.*") || s.hasPermission("czutils.reload") || s.isOp()) {
                    i.reloadConfigurations();
                    s.sendMessage(ChatUtils.t(languageUtils.getMessage("Reloaded")));
                    return true;
                }
            }
        }
        return false;
    }
    //
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        } else {
            Player s = (Player) sender;
            List<String> arguments = new ArrayList<>();
            for (int i = 0; i < args.length; i++) {
                if (i == 0) {
                    continue;
                }
                arguments.add(args[i]);
            }
            if (args.length == 1) {
                List<String> commands = new ArrayList<>();
                commands.add("help");
                commands.add("reload");
                commands.add("language");
                return commands;
            }
            if (args[0].equalsIgnoreCase("language")) {
                if (args.length == 2) {
                    return CZUtils.getPlugin(CZUtils.class).getConfig().getStringList("Languages");
                }
            }
        }
        return null;
    }
}
