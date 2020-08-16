package dev.denismasterherobrine.subtaxes.commands.farms;

import dev.denismasterherobrine.subtaxes.SubTaxes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class FarmsCommand implements CommandExecutor {
    private Connection connection;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
            if (command.getName().equalsIgnoreCase("farms")){
                if (commandSender instanceof Player) {
                        if (strings.length == 0) {
                            commandSender.sendMessage("Command usage: /farms add/remove <name of the farm>");
                            commandSender.sendMessage("Command usage: /farms transfer <name of the farm> <player nickname to transfer the farm>");
                            commandSender.sendMessage("Command usage: /farms decline to decline ALL FARMS. /farms decline <name of the farm> to decline a specific farm from transferring.");
                            return false;
                        }
                    }

                // /farms remove
                if (strings.length == 1){
                    if (strings[0].equals("remove")){
                        assert commandSender instanceof Player;
                        Player player = (Player) commandSender;
                        player.sendMessage("Укажите ферму, которую хотите удалить! (Пример: /farms remove IronFarm)");
                        return true;
                    }
                }
                // /farms add
                if (strings.length == 1){
                    if (strings[0].equals("add")){
                        if (commandSender instanceof Player){
                        Player player = (Player) commandSender;
                        String world = player.getWorld().getName();
                        Location loc = player.getLocation();
                        double x = loc.getX();
                        double y = loc.getY();
                        double z = loc.getZ();
                        UUID PlayerUUID = player.getUniqueId();
                        player.sendMessage("Ваша ферма успешно добавлена в список на рассмотрение!");
                        System.out.println("Player Add Info: " + world + " " + x + " " + y + " " + z + ".");
                        BukkitRunnable r = new BukkitRunnable() {
                            @Override
                            public void run() {
                                try {
                                    openConnection();
                                    Statement statement = connection.createStatement();
                                    String SQLString = "INSERT INTO FarmsTableTest (type, PlayerUUID, world, x, y, z, name) VALUES (0, '" + PlayerUUID + "', '" + world + "', " + x + ", " + y + ", " + z + ", '<no name is provided>');";
                                    statement.executeUpdate(SQLString);
                                    connection.close();
                                } catch (ClassNotFoundException | SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        r.runTaskAsynchronously(JavaPlugin.getPlugin(SubTaxes.class));
                        return true;
                    }
                }
            }
            }
            // /farms add <name>
            if (command.getName().equalsIgnoreCase("farms")){
                if (strings.length == 2){
                    if (strings[0].equals("add")){
                        if (strings[1] != null){
                            assert commandSender instanceof Player;
                            Player player = ((Player) commandSender);
                            String world = player.getWorld().getName();
                            Location loc = player.getLocation();
                            double x = loc.getX();
                            double y = loc.getY();
                            double z = loc.getZ();
                            UUID PlayerUUID = player.getUniqueId();
                            player.sendMessage("Ваша ферма " + strings[1] + " успешно добавлена в список на рассмотрение!");
                            System.out.println("Player Add Info: " + world + " " + x + " " + y + " " + z + ".");
                            BukkitRunnable r = new BukkitRunnable() {
                                @Override
                                public void run() {
                                    try {
                                        openConnection();
                                        Statement statement = connection.createStatement();
                                        String SQLString = "INSERT INTO FarmsTableTest (type, PlayerUUID, world, x, y, z, name) VALUES (0, '" + PlayerUUID + "', '" + world + "', " + x + ", " + y + ", " + z + ", '" + strings[1] + "');";
                                        statement.executeUpdate(SQLString);
                                        connection.close();
                                    } catch (ClassNotFoundException | SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            r.runTaskAsynchronously(JavaPlugin.getPlugin(SubTaxes.class));
                            return true;
                        }
                    }
                }
            }
            // /farms remove <name>
            if (command.getName().equalsIgnoreCase("farms")){
                if (strings.length == 2){
                    if (strings[0].equals("remove")){
                        if (strings[1] != null){
                        assert commandSender instanceof Player;
                        Player player = ((Player) commandSender);
                        String world = player.getWorld().getName();
                        Location loc = player.getLocation();
                        double x = loc.getX();
                        double y = loc.getY();
                        double z = loc.getZ();
                        UUID PlayerUUID = player.getUniqueId();
                        player.sendMessage("Ваша ферма " + strings[1] + " успешно добавлена в список на удаление!");
                        System.out.println("Player Remove Info: " + world + " " + x + " " + y + " " + z + ".");
                        BukkitRunnable r = new BukkitRunnable() {
                            @Override
                            public void run() {
                                try {
                                    openConnection();
                                    Statement statement = connection.createStatement();
                                    String SQLString = "INSERT INTO FarmsTableTest (type, PlayerUUID, world, x, y, z, name) VALUES (1, '" + PlayerUUID + "', '" + world + "', " + x + ", " + y + ", " + z + ", '" + strings[1] + "');";
                                    statement.executeUpdate(SQLString);
                                    connection.close();
                                } catch (ClassNotFoundException | SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        r.runTaskAsynchronously(JavaPlugin.getPlugin(SubTaxes.class));
                        return true;
                    }
                }
            }
        }
            // /farms transfer
        if (strings.length == 1){
            if (strings[0].equals("transfer")){
                assert commandSender instanceof Player;
                Player player = (Player) commandSender;
                player.sendMessage("Укажите ферму, которую хотите передать и ник игрока, которому передаётся она. (Пример: /farms transfer IronFarm Zakviel)");
                return true;
            }
        }

        // /farms transfer <name>
        if (strings.length == 2) {
            if (strings[0].equals("transfer")) {
                if (strings[1] != null) {
                    assert commandSender instanceof Player;
                    Player player = (Player) commandSender;
                    player.sendMessage("Укажите ник игрока, которому передаётся ферма. (Пример: /farms transfer IronFarm Zakviel)");
                    return true;
                }
            }
        }

        // /farms transfer <name> <player>
        if (strings.length == 3) {
            if (strings[0].equals("transfer")) {
                if (strings[1] != null) {
                    if (strings[2] != null)
                        if (commandSender instanceof Player) {
                        Player sender = (Player) commandSender;
                        Player target = Bukkit.getPlayer(strings[2]);
                        if (target.isOnline()) {
                            target.sendMessage("Вам пришёл запрос на перенос фермы" + strings[1] + " от " + sender + ". Пропишите /farms decline, чтобы отклонить.");
                            sender.sendMessage("Отправлена заявка на передачу фермы!");
                            UUID senderUUID = sender.getUniqueId();
                            UUID targetUUID = target.getUniqueId();
                            BukkitRunnable r = new BukkitRunnable() {
                                @Override
                                public void run() {
                                    try {
                                        openConnection();
                                        Statement statement = connection.createStatement();
                                        String SQLString = "INSERT INTO TransferFarmsTableTest (senderName, receiverName, name) VALUES ('" + senderUUID + "', '" + targetUUID + "', " + strings[1] + "');";
                                        statement.executeUpdate(SQLString);
                                        connection.close();
                                    } catch (ClassNotFoundException | SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            r.runTaskAsynchronously(JavaPlugin.getPlugin(SubTaxes.class));
                            return true;
                        }
                        sender.sendMessage("Данный игрок оффлайн, пожалуйста, отправьте запрос, когда игрок будет онлайн.");
                        return false;
                }
            }
        }

        }


        // /farms decline
        if (strings.length == 1) {
            if (strings[0].equals("decline")) {
                    assert commandSender instanceof Player;
                    Player player = (Player) commandSender;
                    UUID targeterUUID = player.getUniqueId();
                    BukkitRunnable r = new BukkitRunnable() {
                        @Override
                        public void run() {
                            try {
                                openConnection();
                                Statement statement = connection.createStatement();
                                ResultSet result = statement.executeQuery("SELECT * FROM TransferFarmsTableTest;");
                                while (result.next()) {
                                    UUID senderUUID = UUID.fromString(result.getString("senderName"));
                                    UUID receiverUUID = UUID.fromString(result.getString("receiverName"));
                                    String name = result.getString("name");
                                    if (receiverUUID.equals(targeterUUID)){
                                        Player sender = Bukkit.getPlayer(senderUUID);
                                        sender.sendMessage("Ваши запросы были отклонены.");
                                        commandSender.sendMessage("Вы отклонили все запросы на передачу фермы.");
                                        Statement statement1 = connection.createStatement();
                                        String SQLString = "DELETE FROM TransferFarmsTableTest WHERE receiverName = '" + receiverUUID + "');";
                                        statement1.executeUpdate(SQLString);
                                    };
                                }
                                connection.close();
                            } catch (ClassNotFoundException | SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    player.sendMessage("Вам не приходило никаких запросов на передачу ферм!");
                    return true;
                }
            }

        // /farms decline <name>
        if (strings.length == 2) {
            if (strings[0].equals("decline")) {
                if (strings[1] != null) {
                    assert commandSender instanceof Player;
                    Player player = (Player) commandSender;
                    UUID targeterUUID = player.getUniqueId();
                    BukkitRunnable r = new BukkitRunnable() {
                        @Override
                        public void run() {
                            try {
                                openConnection();
                                Statement statement = connection.createStatement();
                                ResultSet result = statement.executeQuery("SELECT * FROM TransferFarmsTableTest;");
                                while (result.next()) {
                                    UUID senderUUID = UUID.fromString(result.getString("senderName"));
                                    UUID receiverUUID = UUID.fromString(result.getString("receiverName"));
                                    String name = result.getString("name");
                                    if (receiverUUID.equals(targeterUUID) && name.equals(strings[1])){
                                        Player sender = Bukkit.getPlayer(senderUUID);
                                        assert sender != null;
                                        sender.sendMessage("Ваш запрос был отклонён.");
                                        commandSender.sendMessage("Вы отклонили запросы на передачу фермы " + name + ".");
                                        Statement statement1 = connection.createStatement();
                                        String SQLString = "DELETE FROM TransferFarmsTableTest WHERE receiverName = '" + receiverUUID + "', name = '" + name + "');";
                                        statement1.executeUpdate(SQLString);
                                    };
                                }
                                connection.close();
                            } catch (ClassNotFoundException | SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    player.sendMessage("Вам не приходило никаких запросов на передачу ферм!");
                    return true;
                }
            }
        }
        return false;
    }



    // JDBC connector to MySQL database
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
}
