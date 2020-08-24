package dev.denismasterherobrine.subtaxes.utils;

import dev.denismasterherobrine.subtaxes.SubTaxes;

import java.sql.*;
import java.util.UUID;

public class Utils {
    public static String getId(int length){
        String list = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String result = "";
        for(int i = 0; i < length; i++)
            result += String.valueOf(list.charAt((int) Math.round(Math.random() * (list.length() - 1))));
        return result;
    }
    public static String getUinqueId(int length){
        try {
            openConnection();
            Statement statement = connection.createStatement();
            String result = "";
            while(true) {
                result = getId(length);
                ResultSet r = statement.executeQuery("SELECT * FROM farms;");
                while (r.next()) {
                    if(r.getString("ID").equals(result)) continue;
                }
                break;
            }
            connection.close();
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static Connection connection;
    // JDBC connector to MySQL database
    private static void openConnection() throws SQLException, ClassNotFoundException {
        String host = SubTaxes.getPlugin(SubTaxes.class).host_server;
        int port = SubTaxes.getPlugin(SubTaxes.class).port_server;
        String database = SubTaxes.getPlugin(SubTaxes.class).database_server;
        String username = SubTaxes.getPlugin(SubTaxes.class).username_server;
        String password = SubTaxes.getPlugin(SubTaxes.class).password_server;

        if (connection != null && !connection.isClosed()) {
            return;
        }

        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
    }
}
