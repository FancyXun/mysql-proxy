package dao;

public class QueryResponse {
    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public QueryResultData getData() {
        return Data;
    }

    public void setData(QueryResultData data) {
        Data = data;
    }

    private String queryId;
    private QueryResultData Data;
}
