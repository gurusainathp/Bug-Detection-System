package com.hsbc.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MySQLHelper {

    //resoruce bundle
    private static ResourceBundle resourceBundle;

    private MySQLHelper() {

    }

    //singleton and gets connection from sql
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        resourceBundle = ResourceBundle.getBundle("db");
        String userName = resourceBundle.getString("userName");
        String password = resourceBundle.getString("password");
        String url = resourceBundle.getString("url");
        String driver = resourceBundle.getString("driverClassName");
        //step1 Load the driver
        Class.forName(driver);
        //step2 Obtain the Connection
        return DriverManager.getConnection(url, userName, password);

    }


}
