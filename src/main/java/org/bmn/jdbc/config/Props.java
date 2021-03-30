package org.bmn.jdbc.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Props {

    public static final String PATH_TO_PROPERTIES = "src/main/resources/jdbc/jdbc.properties";

    private String url;
    private String userName;
    private String password;


    public String getUrl() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(PATH_TO_PROPERTIES)) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            url = properties.getProperty("url");
            return url;
        }
    }

    public String getUserName() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(PATH_TO_PROPERTIES)) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            userName = properties.getProperty("user");
            return userName;
        }
    }

    public String getPassword() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(PATH_TO_PROPERTIES)) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            password = properties.getProperty("password");
            return password;
        }
    }
}
