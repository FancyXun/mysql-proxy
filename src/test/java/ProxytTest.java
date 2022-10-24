import com.mysql.jdbc.PreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class ProxytTest {

    public static void main(String[] args) {
        String name="com.mysql.jdbc.Driver";
        String url="jdbc:mysql://127.0.0.1:43306/dahua_yuanqu_test?" +
                "useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true";
        String user="root";
        String password="root";
        try {
            Class.forName(name);//指定连接类型
            Connection conn = DriverManager.getConnection(url, user, password);//url为代理服务器的地址
            PreparedStatement pst = (PreparedStatement) conn.prepareStatement("select * from user_info limit 1 ;");//准备执行语句
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + ": " + resultSet.getString(2));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
