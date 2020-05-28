package task1;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;

import static task1.Runner.PASSWORD;
import static task1.Runner.URL;
import static task1.Runner.USER;

public class MyFirstConnection {
    public static Logger logger = LogManager.getRootLogger();


    public static void printAllTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SHOW TABLES");
        while (resultSet.next()) {
            logger.info(resultSet.getString(1));
        }

    }

    public static void getTableContents(Connection connection, String sqlQuery) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        while (resultSet.next()) {
            logger.info(resultSet.getString(1)+ " " + resultSet.getString(2) + " " +
                    resultSet.getString(3));
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
