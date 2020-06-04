package task3;

import com.github.javafaker.Faker;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Locale;

public class Runner {

    public static Logger logger = LogManager.getRootLogger();

    public static void main(String[] args) {

            try {
          SocialNetwork.CreateTableUser(SocialNetwork.getConnection());
          SocialNetwork.CreateTableFriendShip(SocialNetwork.getConnection());
          SocialNetwork.CreateTablePosts(SocialNetwork.getConnection());
          SocialNetwork.CreateTableLikes(SocialNetwork.getConnection());
                for (int i = 0; i <1000 ; i++) {
                    SocialNetwork.addDataLike(SocialNetwork.getConnection());
                }

        } catch (SQLException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
