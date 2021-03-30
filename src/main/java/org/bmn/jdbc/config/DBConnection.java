package org.bmn.jdbc.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getNewConnection() throws SQLException, IOException {
        Props props = new Props();
        return DriverManager.getConnection(props.getUrl(), props.getUserName(), props.getPassword());
    }
}
