package task1;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.sql.*;
import java.util.Properties;


public class MyFirstConnection {
    public static Logger logger = LogManager.getRootLogger();


    public static void getTableContents(Connection connection, String sqlQuery) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        while (resultSet.next()) {
            logger.info("id - " + resultSet.getString(1) + "; name - " + resultSet.getString(2) + "; age - " +
                    resultSet.getString(3));
        }
    }

    public static void getTableContentsWithPrepare(Connection connection, String tableName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("Select * From " + tableName + " where id = ?");
        for (int i = 1; i < getCountLines(connection, tableName) + 1; i++) {
            statement.setInt(1, i);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                logger.info("id - " + resultSet.getString(1) + "; name - " + resultSet.getString(2) + "; age - " +
                        resultSet.getString(3));
            }
        }
    }

    public static int getCountLines(Connection connection, String tableName) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select count(*) from " + tableName);

        while (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public static void printAllTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SHOW TABLES");
        while (resultSet.next()) {
            logger.info(resultSet.getString(1));
        }
    }

    public static Connection getConnection() throws IOException, SQLException {
        Properties properties = new Properties();
        InputStream input = new FileInputStream("src/main/resources/jdbc.properties");
        properties.load(input);
        return DriverManager.getConnection(properties.getProperty("jdbc.url"),
                properties.getProperty("jdbc.username"), properties.getProperty("jdbc.password"));
    }
}
