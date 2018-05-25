package com.blackandwhite.grey;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

    private static Connection con;
    private static String user;
    private static String pass;

    public static void establishConnection(String user, String pass) {
        con = null;

        try {
            loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        String url = "jdbc:mysql://localhost:3306/grey?serverTimezone=EST&useSSL=false";

        try {
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
            con = null;
        }
    }

    private static void loadProperties() throws IOException {
        Properties prop = new Properties();
        InputStream stream = Database.class.getClassLoader().getResourceAsStream("assets/database.properties");

        if (stream != null) {
            prop.load(stream);

            user = prop.getProperty("user");
            pass = prop.getProperty("pass");

            stream.close();
        }
    }

    public static Connection getConnection() {
        return con;
    }
}
