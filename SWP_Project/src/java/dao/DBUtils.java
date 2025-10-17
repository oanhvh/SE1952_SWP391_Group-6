package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    private static final String DB = "Team6_SWP391_VMS";
    private static final String USER_NAME = "sa";
    private static final String PASSWORD = "123";

    public static Connection getConnection1() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        DriverManager.setLoginTimeout(5);
        String url = "jdbc:sqlserver://localhost:1433;databaseName=" + DB+ ";encrypt=true;trustServerCertificate=true";
        conn = DriverManager.getConnection(url, USER_NAME, PASSWORD);
        System.out.println("Connected.");
        return conn;
    }
    
//    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        System.out.println(getConnection1().toString());
//    }

}
