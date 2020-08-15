package dev.denismasterherobrine.subtaxes.commands.farms;

import dev.denismasterherobrine.subtaxes.SubTaxes;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class FarmsAddCommand implements CommandExecutor {
    private Connection connection;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player){
            if (s.equalsIgnoreCase("farms")){
                if (strings.length == 0){
                    commandSender.sendMessage("Command usage: /farms <add/transfer/remove> <name of the farm> <transfer: player nickname to transfer the farm>");
                    return false;
                }
            }
            if (s.equalsIgnoreCase("farms")){
                if (strings.length == 1){
                    if (strings[0].equals("add")){
                        Player player = ((Player) commandSender).getPlayer();
                        if (player != null) {
                        World world = player.getWorld();
                        Location loc = player.getLocation();
                        double x = loc.getX();
                        double y = loc.getY();
                        double z = loc.getZ();
                        UUID PlayerUUID = player.getUniqueId();
                        BukkitRunnable r = new BukkitRunnable() {
                            @Override
                            public void run() {
                                try {
                                    openConnection();
                                    Statement statement = connection.createStatement();
                                    String SQLString = "INSERT INTO FarmsTableTest (PlayerUUID, world, x, y, z, name) VALUES (" + PlayerUUID + ", " + world + ", " + x + ", " + y + ", " + z + ", " + null + ");";
                                    statement.executeUpdate(SQLString);
                                    connection.close();
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        r.runTaskAsynchronously(JavaPlugin.getPlugin(SubTaxes.class));
                    }
                }
                }
            }

            if (s.equalsIgnoreCase("farms")){
                if (strings.length == 2){
                    if (strings[0].equals("add")){
                        if (!strings[1].equals(null)){
                            Player player = ((Player) commandSender).getPlayer();
                            if (player != null) {
                                World world = player.getWorld();
                                Location loc = player.getLocation();
                                double x = loc.getX();
                                double y = loc.getY();
                                double z = loc.getZ();
                                UUID PlayerUUID = player.getUniqueId();
                                BukkitRunnable r = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            openConnection();
                                            Statement statement = connection.createStatement();
                                            String SQLString = "INSERT INTO FarmsTableTest (PlayerUUID, world, x, y, z, name) VALUES (" + PlayerUUID + ", " + world + ", " + x + ", " + y + ", " + z + ", " + strings[1] + ");";
                                            statement.executeUpdate(SQLString);
                                            connection.close();
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                r.runTaskAsynchronously(JavaPlugin.getPlugin(SubTaxes.class));
                            }
                        }
                    }
                }
            };
        }
        return true;
    };


    public void openConnection() throws SQLException, ClassNotFoundException {
        String host = SubTaxes.getPlugin(SubTaxes.class).host_server;
        int port = SubTaxes.getPlugin(SubTaxes.class).port_server;
        String database = SubTaxes.getPlugin(SubTaxes.class).database_server;
        String username = SubTaxes.getPlugin(SubTaxes.class).username_server;
        String password = SubTaxes.getPlugin(SubTaxes.class).password_server;

        if (connection != null && !connection.isClosed()) {
            return;
        }

        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
        }
    }

    public void closeConnectiom() throws SQLException, ClassNotFoundException {
        try {
            if (connection != null && !connection.isClosed()){
                connection.close();
                }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

//statement.executeUpdate("Update FarmsTableTest Set PlayerUUID = @PlayerUUID, world = @world, x = @x, y = @y, z = @z");
//
