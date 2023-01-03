package dao;

public class QueryResponse extends Response {
    public QueryResultData getData() {
        return data;
    }

    public void setData(QueryResultData data) {
        this.data = data;
    }

    public QueryResultData data;
}
