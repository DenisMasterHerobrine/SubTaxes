package dev.denismasterherobrine.subtaxes;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;

public class SubTaxes extends JavaPlugin {

    FileConfiguration config = getConfig();
    public String host_server, database_server, username_server, password_server;
    public String host_weekly, database_weekly, username_weekly, password_weekly;
    public String host_personal, database_personal, username_personal, password_personal;
    public int port_server, port_weekly, port_personal;
    public int PlayerHPInt, PlayerHPAdd, PlayerHPRemove;

    Date StartDate = new Date();

    @Override
    public void onEnable(){
        // Fired when the server enables the plugin
        config.addDefault("SQLUsernameServerTax"," ");
        config.addDefault("SQLPasswordServerTax"," ");
        config.addDefault("SQLDatabaseNameServerTax"," ");
        config.addDefault("SQLHostServerTax"," ");
        config.addDefault("SQLPortServerTax", 3306);

        config.addDefault("SQLUsernameWeeklyTax"," ");
        config.addDefault("SQLPasswordWeeklyTax"," ");
        config.addDefault("SQLDatabaseNameWeeklyTax"," ");
        config.addDefault("SQLHostWeeklyTax"," ");
        config.addDefault("SQLPortWeeklyTax", 3306);

        config.addDefault("SQLUsernamePersonalTax"," ");
        config.addDefault("SQLPasswordPersonalTax"," ");
        config.addDefault("SQLDatabaseNamePersonalTax"," ");
        config.addDefault("SQLHostPersonalTax"," ");
        config.addDefault("SQLPortPersonalTax", 3306);

        //Start of the 1st cycle
        config.addDefault("StartTime", StartDate);

        //Integer number for HP
        config.addDefault("PlayerHP", PlayerHPInt);

        //Add/Remove heart coefficients to punish players for not paying.
        config.addDefault("HowMuchPlayerHPToRemove", PlayerHPRemove);
        config.addDefault("HowMuchPlayerHPToAdd", PlayerHPAdd);

        config.options().copyDefaults(true);
        saveConfig();

        host_server = config.getString("SQLHostServerTax");
        port_server = config.getInt("SQLPortServerTax");
        database_server = config.getString("SQLDatabaseNameServerTax");
        username_server = config.getString("SQLUsernameServerTax");
        password_server = config.getString("SQLPasswordServerTax");

        host_weekly = config.getString("SQLHostWeeklyTax");
        port_weekly = config.getInt("SQLPortWeeklyTax");
        database_weekly = config.getString("SQLDatabaseNameWeeklyTax");
        username_weekly = config.getString("SQLUsernameWeeklyTax");
        password_weekly = config.getString("SQLPasswordWeeklyTax");

        host_personal = config.getString("SQLHostPersonalTax");
        port_personal = config.getInt("SQLPortPersonalTax");
        database_personal = config.getString("SQLDatabaseNamePersonalTax");
        username_personal = config.getString("SQLUsernamePersonalTax");
        password_personal = config.getString("SQLPasswordPersonalTax");
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
