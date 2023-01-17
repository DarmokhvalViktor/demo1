package com.darmokhval.demo1.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {
    public static Connection getMySQLConnection() throws ClassNotFoundException, SQLException {
        String hostName = "localhost";
        String databaseName = "demodb";
        String userName = "root";
        String password = "root1212";
        return getMySQLConnection(hostName, databaseName, userName, password);
    }
    public static Connection getMySQLConnection(String hostName,
            String databaseName, String userName, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + databaseName;
        Connection connection = DriverManager.getConnection(connectionURL, userName, password);
        return connection;
    }
}
