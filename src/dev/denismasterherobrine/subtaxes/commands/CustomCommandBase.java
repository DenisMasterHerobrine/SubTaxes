package dev.denismasterherobrine.subtaxes.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CustomCommandBase {
    public final String[] ALIASES;
    public CustomCommandBase(String[] aliases){
        ALIASES = aliases;
    }
    public void execute(CommandSender sender, Command command, String label, String[] args){

    }
    public void tab(CommandSender sender, Command command, String alias, String[] args){

    }
}
