import com.mysql.jdbc.PreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class ProxytTest {

    public static void main(String[] args) {
        String name="com.mysql.jdbc.Driver";
        String url="jdbc:mysql://127.0.0.1:43306/points?" +
                "useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&useSSL=false";
//        "useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true";

        String user="root";
        String password="root";
//        String password="root";
        try {
            Class.forName(name);//指定连接类型
            Connection conn = DriverManager.getConnection(url, user, password);//url为代理服务器的地址
//            PreparedStatement pst = (PreparedStatement) conn.prepareStatement("select * from table_329c4e327d415c112139e3a435a71854 limit 1 ;");//准备执行语句
//            PreparedStatement pst = (PreparedStatement) conn.prepareStatement("select 1,2,3,4,5;");//准//////////////////备执行语句
            String sql="select * from test_0;";
//            String sql  = "select repeat('a',2000) as x;";
//            String sql  = "select 1,'x';";

            PreparedStatement pst = (PreparedStatement) conn.prepareStatement(sql);//准备执行语句

            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
//                System.out.println(resultSet.getString(1) + ": " + resultSet.getString(2));
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
