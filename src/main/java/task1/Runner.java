package task1;
import com.github.javafaker.Faker;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

public class Runner {
    public static Logger logger = LogManager.getRootLogger();

    public static void main(String[] args) {

        try {
            logger.info("Table in database:");
            MyFirstConnection.printAllTable(MyFirstConnection.getConnection());
            logger.info("Women table data:");
            MyFirstConnection.getTableContents(MyFirstConnection.getConnection(),"Select * From women");
            logger.info("Men table data:");
            MyFirstConnection.getTableContentsWithPrepare(MyFirstConnection.getConnection(), "men");

        } catch (
                SQLException e) {
            logger.error(e.getMessage());
        }
        catch (IOException e){
            logger.error(e.getMessage());
        }

    }

}
