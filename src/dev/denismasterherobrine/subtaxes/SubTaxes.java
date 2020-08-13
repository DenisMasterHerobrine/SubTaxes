package dev.denismasterherobrine.subtaxes;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class SubTaxes extends JavaPlugin {

    FileConfiguration config = getConfig();
    public String host, database, username, password;
    public int port;

    @Override
    public void onEnable(){
        // Fired when the server enables the plugin.
        config.addDefault("SQLUsername"," ");
        config.addDefault("SQLPassword"," ");
        config.addDefault("SQLDatabaseName"," ");
        config.addDefault("SQLHost"," ");
        config.addDefault("SQLPort", 3306);

        config.options().copyDefaults(true);
        saveConfig();

        host = config.getString("SQLHost");
        port = config.getInt("SQLPort");
        database = config.getString("SQLDatabaseName");
        username = config.getString("SQLUsername");
        password = config.getString("SQLPassword");

    }

    @Override
    public void onDisable(){
        // Fired when the server stops and disables all plugins.
        // Now this should work now.
    }
}
