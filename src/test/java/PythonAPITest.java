import dao.SQLInfo;



public class PythonAPITest {

    public static void main(String[] args) {
        SQLInfo sqlInfo = new SQLInfo();
        String sql = "select * from table_e80593620e1b268cdf897b0d776b07cc limit 1";
        ReadBuffer.sqlConvert(sqlInfo, sql);
    }

}
