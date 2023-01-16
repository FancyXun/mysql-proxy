package dao;

public class SQLQueryRequest {
    public String sql;
    public String db;
    public SQLQueryRequest(String sql,String db){
        this.sql=sql;
        this.db=db;
    }
}
