package org.bmn.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Props {

    //путь к нашему файлу конфигураций
    public static final String PATH_TO_PROPERTIES = "src/main/resources/jdbc/jdbc.properties";

    private String url;
    private String userName;
    private String password;


    public String getUrl() throws IOException{
        FileInputStream fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
        Properties properties = new Properties();
        properties.load(fileInputStream);
        url = properties.getProperty("url");
        fileInputStream.close();
        return url;
    }

    public String getUserName() throws IOException{
        FileInputStream fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
        Properties properties = new Properties();
        properties.load(fileInputStream);
        userName = properties.getProperty("user");
        fileInputStream.close();
        return userName;
    }

    public String getPassword() throws IOException{
        FileInputStream fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
        Properties properties = new Properties();
        properties.load(fileInputStream);
        password = properties.getProperty("password");
        fileInputStream.close();
        return password;
    }
}
