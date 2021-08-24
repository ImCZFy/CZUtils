package com.github.imczfy.czutils.commands;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {

    public abstract void accessCommand(CommandSender sender, String[] args);

}
