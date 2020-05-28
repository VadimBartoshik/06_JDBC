package task1;
import java.sql.SQLException;


public class Runner {
    public static final String URL = "jdbc:mysql://localhost:3306/people";
    public static final String USER = "root";
    public static final String PASSWORD = "dflbv181818";

    public static void main(String[] args) {

        try {
            MyFirstConnection.printAllTable(MyFirstConnection.getConnection());
            MyFirstConnection.getTableContents(MyFirstConnection.getConnection(),"Select * From men");
            MyFirstConnection.getTableContents(MyFirstConnection.getConnection(),"Select * From women");
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

    }

}
