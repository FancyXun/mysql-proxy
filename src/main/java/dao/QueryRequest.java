package dao;

import com.alibaba.fastjson.annotation.JSONField;

public class QueryRequest {

    @JSONField(name="query_id")
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
