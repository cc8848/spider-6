package util;

import java.sql.*;

/**
 * Created by 杨旭晖 on 2017/12/7.
 */
public class DBUtil {

    public static Connection getCon(){
        Connection con = null;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://139.199.207.90:3306/onlineStore?useUnicode=true&characterEncoding=utf8";
        String user = "root";
        String password = "rootadmin";

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            return con;
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        return con;
    }

    public static void allClose(Connection con, PreparedStatement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException var6) {
                var6.printStackTrace();
            }
        }

        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException var5) {
                var5.printStackTrace();
            }
        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException var4) {
                var4.printStackTrace();
            }
        }
    }

}
