package dev.denismasterherobrine.subtaxes;

import dev.denismasterherobrine.subtaxes.commands.farms.FarmsAddCommand;
import dev.denismasterherobrine.subtaxes.commands.CustomCommandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class SubTaxes extends JavaPlugin implements Listener {
    FileConfiguration config = getConfig();
    public String host_server, database_server, username_server, password_server;
    public int port_server;
    public int PlayerHPInt = 20, PlayerHPAdd, PlayerHPRemove;

    Date StartDate = new Date();

    @Override
    public void onEnable(){
        this.getCommand("farms").setExecutor(new FarmsAddCommand());

        // Fired when the server enables the plugin
        config.addDefault("SQLUsername"," ");
        config.addDefault("SQLPassword"," ");
        config.addDefault("SQLDatabaseName"," ");
        config.addDefault("SQLHost"," ");
        config.addDefault("SQLPort", 3306);

        //Start of the 1st cycle
        config.addDefault("StartTime", StartDate);

        //Integer number for HP
        config.addDefault("PlayerHP", PlayerHPInt);

        //Add/Remove heart coefficients to punish players for not paying.
        config.addDefault("HowMuchPlayerHPToRemove", PlayerHPRemove);
        config.addDefault("HowMuchPlayerHPToAdd", PlayerHPAdd);

        config.options().copyDefaults(true);
        saveConfig();

        host_server = config.getString("SQLHost");
        port_server = config.getInt("SQLPort");
        database_server = config.getString("SQLDatabaseName");
        username_server = config.getString("SQLUsername");
        password_server = config.getString("SQLPassword");

        getServer().getPluginManager().registerEvents((Listener)this, this);
    }

    @Override
    public void onDisable(){
        // Fired when the server stops and disables all plugins


    }

    public int getPlayerHPInt() {
        return PlayerHPInt;
    }

    public int getPlayerHPAdd() {
        return PlayerHPAdd;
    }

    public int getPlayerHPRemove() {
        return PlayerHPRemove;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return CustomCommandManager.onCommand(sender, command, label, args);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return CustomCommandManager.onTabComplete(sender, command, alias, args);
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        CustomCommandManager.onPlayerCommandPreprocess(event);
    }

    @EventHandler
    public void onPlayerCommandSend(PlayerCommandSendEvent event) {
        CustomCommandManager.onPlayerCommandSend(event);
    }

    @EventHandler
    public void onTabComplete(TabCompleteEvent event) {
        CustomCommandManager.onTabComplete(event);
    }
}
