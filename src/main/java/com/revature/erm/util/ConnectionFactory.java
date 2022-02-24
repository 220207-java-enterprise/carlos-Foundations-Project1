package com.revature.erm.util;

import java.io.FileReader;//used to read from application.properties file
import java.io.IOException;//used for exceptions
import java.sql.Connection;//used to establish connection with SQL
import java.sql.DriverManager;//manages JBDC drivers
import java.sql.SQLException;//used for SQL exception
import java.util.Properties;//Arthur didn't need this, so perhaps can do this without

public class ConnectionFactory {

    private static ConnectionFactory connectionFactory;//creates static instance of ConnectionFactory

    static {
        try {
            Class.forName("org.postgresql.Driver");//try to import Driver class
        } catch (ClassNotFoundException e) {//throw exception if class is not found
            e.printStackTrace();
        }
    }

    private Properties props = new Properties();//new object 'props' instantiation of Properties class

    private ConnectionFactory() {//create method called ConnectionFactory
        try {
            props.load(new FileReader("src/main/resources/application.properties"));//load and read from application properties
        } catch (IOException e) {//IO exception if it does not work
            e.printStackTrace();
        }
    }

    public static ConnectionFactory getInstance() {
        if (connectionFactory == null) {//if connectionFactory is null
            connectionFactory = new ConnectionFactory();//assign connectionFactory to new ConnectionFactory
        }
        return connectionFactory;
    }

    public Connection getConnection() throws SQLException {
        System.out.println(props.getProperty("db-url") + " " +props.getProperty("db-username") + " " +props.getProperty("db-password"));
        //Read and create connection using application.resources
        Connection conn = DriverManager.getConnection(props.getProperty("db-url"), props.getProperty("db-username"), props.getProperty("db-password"));

        if (conn == null) {//if conn is null
            throw new RuntimeException("Could not establish connection with the database.");
        }
        return conn;
    }

}
