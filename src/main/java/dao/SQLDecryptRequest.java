package dao;

public class SQLDecryptRequest {
    public String db;
    public String sql;
    public SQLDecryptRequest(String db,String sql){
        this.db=db;
        this.sql=sql;
    }
}
