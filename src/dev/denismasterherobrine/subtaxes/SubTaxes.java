package dev.denismasterherobrine.subtaxes;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;

public class SubTaxes extends JavaPlugin {

    FileConfiguration config = getConfig();
    public String host_server, database_server, username_server, password_server;
    public int port_server;
    public int PlayerHPInt, PlayerHPAdd, PlayerHPRemove;

    Date StartDate = new Date();

    @Override
    public void onEnable(){
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
}
