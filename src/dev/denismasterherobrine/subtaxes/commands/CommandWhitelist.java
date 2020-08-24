package dev.denismasterherobrine.subtaxes.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandWhitelist {
    private static String[] players = new String[]{"help", "farms", "tax", "m"};
    private static String[] admins = new String[]{"help", "farms", "tax", "m", "adm", "tp", "gamemode", "ban", "kick", "reload", "co"};
    public static void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
        e.setCancelled(true);
        String msg = e.getMessage();
        if(e.getPlayer().isOp()){
            for(String s : admins)
                if(msg.startsWith("/" + s))
                    e.setCancelled(false);
        }
        else{
            for(String s : players)
                if(msg.startsWith("/" + s))
                    e.setCancelled(false);
        }
        if(e.isCancelled()) e.getPlayer().sendMessage("§6Неизвестная команда!");
    }
    public static void onPlayerCommandSend(PlayerCommandSendEvent e) {
        e.getCommands().clear();
        if(e.getPlayer().isOp()){
            for(String s : admins)
                e.getCommands().add(s);
        }
        else {
            for(String s : players)
                e.getCommands().add(s);
        }
    }
    public static void onTabComplete(TabCompleteEvent e) {
        if(e.getSender() instanceof ConsoleCommandSender)
            return;
        e.getCompletions().clear();
        if(e.getSender().isOp()){
            for(String s : admins)
                e.getCompletions().add(s);
        }
        else {
            for(String s : players)
                e.getCompletions().add(s);
        }
    }
}
