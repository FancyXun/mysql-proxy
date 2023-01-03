package dao;

public class QueryRequest {
    public String queryId;
    public QueryResultData data;
    public QueryRequest(String queryId,QueryResult queryResult){
        this.queryId=queryId;
        QueryResultData queryResultData = new QueryResultData();
        queryResultData.setColumns(queryResult.getColumnDefinition());
        queryResultData.setRows(queryResult.getRows());
        this.data= queryResultData;
    }
}
