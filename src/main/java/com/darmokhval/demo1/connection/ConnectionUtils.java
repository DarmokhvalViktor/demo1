package com.darmokhval.demo1.connection;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        return MySQLConnector.getMySQLConnection();
    }
    public static void close(Connection connection) {
        if(connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void rollback(Connection connection) {
        if(connection == null) {
            return;
        }
        try {
            connection.rollback();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
