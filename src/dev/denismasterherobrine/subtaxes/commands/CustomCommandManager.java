package dev.denismasterherobrine.subtaxes.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.ArrayList;
import java.util.List;

public class CustomCommandManager {
    public static List<CustomCommandBase> REGISTRY = new ArrayList<CustomCommandBase>();

    public static boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof ConsoleCommandSender) return false;

        return true;
    }
    public static List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }
    public static void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {

    }
    public static void onPlayerCommandSend(PlayerCommandSendEvent event) {

    }
    public static void onTabComplete(TabCompleteEvent event) {
        if(event.getSender() instanceof ConsoleCommandSender) return;

        event.getCompletions().clear();
        List<String> completions = new ArrayList<>();
        for(CustomCommandBase c : REGISTRY)
            for(String s : c.ALIASES)
            completions.add("/" + s);
        event.setCompletions(completions);
    }
}
