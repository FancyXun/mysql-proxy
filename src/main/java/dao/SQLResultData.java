package dao;

public class SQLResultData {
    public String getQueryId() {
        return QueryId;
    }

    public void setQueryId(String queryId) {
        QueryId = queryId;
    }

    public String getQuerySQL() {
        return QuerySQL;
    }

    public void setQuerySQL(String querySQL) {
        QuerySQL = querySQL;
    }

    public String QueryId;
    public String QuerySQL;
}
