package dao;

public class SQLConvertedData {
    public String queryId;
    public String sql;

    public SQLConvertedData(){
        this.queryId="default query";
    }
    public SQLConvertedData(String sql, String queryId){
        this.queryId=queryId;
        this.sql=sql;
    }
}
