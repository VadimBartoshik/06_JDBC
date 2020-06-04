package task3;

import com.github.javafaker.Faker;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Locale;
import java.util.Properties;

public class SocialNetwork {

    public static Logger logger = LogManager.getRootLogger();
    public static Faker faker = new Faker(new Locale("ru"));

    public static void CreateTableUser(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS users (\n" +
                "  id INT NOT NULL AUTO_INCREMENT,\n" +
                "  name VARCHAR(45) NOT NULL,\n" +
                "  surname VARCHAR(45) NOT NULL,\n" +
                "  birthday DATE NOT NULL,\n" +
                "  PRIMARY KEY (id));");
    }


    public static void CreateTableFriendShip(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS `social network`.`friendship` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `userId1` INT NOT NULL,\n" +
                "  `userId2` INT NOT NULL,\n" +
                "  `timestamp` DATETIME NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  INDEX `user1fk_idx` (`userId1` ASC) VISIBLE,\n" +
                "  INDEX `user2fk_idx` (`userId2` ASC) VISIBLE,\n" +
                "  CONSTRAINT `user1fk`\n" +
                "    FOREIGN KEY (`userId1`)\n" +
                "    REFERENCES `social network`.`users` (`id`)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE,\n" +
                "  CONSTRAINT `user2fk`\n" +
                "    FOREIGN KEY (`userId2`)\n" +
                "    REFERENCES `social network`.`users` (`id`)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE);");
    }

    public static void CreateTablePosts(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS `social network`.`posts` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `userId` INT NOT NULL,\n" +
                "  `text` VARCHAR(100) NOT NULL,\n" +
                "  `timestamp` DATE NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  INDEX `userIdfk_idx` (`userId` ASC) VISIBLE,\n" +
                "  CONSTRAINT `userIdfk`\n" +
                "    FOREIGN KEY (`userId`)\n" +
                "    REFERENCES `social network`.`users` (`id`)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE);\n");
    }

    public static void CreateTableLikes(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS `social network`.`likes` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "  `postId` INT NOT NULL,\n" +
                "  `userid` INT NOT NULL,\n" +
                "  `timestamp` YEAR NOT NULL DEFAULT 2025,\n" +
                "  INDEX `postIdfk_idx` (`postId` ASC) VISIBLE,\n" +
                "  INDEX `userIdfk_idx` (`userid` ASC) VISIBLE,\n" +
                "  CONSTRAINT `postIdfk`\n" +
                "    FOREIGN KEY (`postId`)\n" +
                "    REFERENCES `social network`.`posts` (`id`)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE,\n" +
                "  CONSTRAINT `userIdfklikes`\n" +
                "    FOREIGN KEY (`userid`)\n" +
                "    REFERENCES `social network`.`users` (`id`)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE);");
    }

    public static void addDataUsers(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO `social network`.`users` (`name`, `surname`, `birthday`)\n" +
                " VALUES ('" + faker.name().firstName() + "', '" + faker.name().lastName() + "', '" + getRandomDate() + "');");
    }

    public  static  void addDataFriendship(Connection connection)throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO `social network`.`friendship` (`userId1`, `userId2`, `timestamp`)" +
                " VALUES ('"+faker.number().numberBetween(1,getCountLines(connection,"`social network`.`users`")) +
                "', '"+ faker.number().numberBetween(1,getCountLines(connection,"`social network`.`users`"))  +
                "', '"+ getRandomDateTime()+"');");
    }

    public  static  void addDataPost(Connection connection)throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO `social network`.`posts` (`userId`, `text`, `timestamp`) VALUES" +
                " ('"+ faker.number().numberBetween(1,getCountLines(connection,"`social network`.`users`"))  +
                "               ', '"+faker.aquaTeenHungerForce().character()+"', '"+getRandomDateTime()+"');\n");
    }

    public  static  void addDataLike(Connection connection)throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO `social network`.`likes` (`postId`, `userid`) VALUES ('"+
                faker.number().numberBetween(1,getCountLines(connection,"`social network`.`posts`"))+
                "', '"+faker.number().numberBetween(1,getCountLines(connection,"`social network`.`users`"))+"');");
    }

    public static String getRandomDate(){
        java.util.Date dt = faker.date().birthday(18,50);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd ");
        return sdf.format(dt);
    }

    public static String getRandomDateTime(){
        java.util.Date dt = faker.date().birthday(1,3);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(dt);
    }

    public static int getCountLines(Connection connection, String tableName) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select count(*) from " + tableName);

        while (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public static Connection getConnection() throws IOException, SQLException {
        Properties properties = new Properties();
        InputStream input = new FileInputStream("src/main/resources/jdbc_task3.properties");
        properties.load(input);
        return DriverManager.getConnection(properties.getProperty("jdbc.url"),
                properties.getProperty("jdbc.username"), properties.getProperty("jdbc.password"));
    }
}
