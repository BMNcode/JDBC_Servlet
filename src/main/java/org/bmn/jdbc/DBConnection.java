package org.bmn.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bmn.config.Props;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    Props props = new Props();
    static final Logger userLogger = LogManager.getLogger(DBConnection.class);

    public Connection getNewConnection() throws SQLException, IOException {
        String url = "jdbc:postgresql://localhost:5477/exampleDB";
        System.out.println(props.getUrl());
        String user = "postgres";
        System.out.println(props.getUserName());
        String password = props.getPassword();
        System.out.println(props.getPassword());
        return DriverManager.getConnection(url, user, password);
    }
}
