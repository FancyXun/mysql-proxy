package dao;

import protocol.ColumnDefinitionPacket;
import protocol.ResultsetRowPacket;

import java.util.List;

public class QueryResult {
    private List<String> columnDefinition;

    public List<List<String>> getRows() {
        return rows;
    }

    public void setRows(List<List<String>> rows) {
        this.rows = rows;
    }

    private List<List<String>> rows;



    public QueryResult(List<String> columnDefinition,List<List<String>> rows) {
        this.columnDefinition = columnDefinition;
        this.rows = rows;
    }

    public QueryResult(QueryResponse queryResponse){
        this.columnDefinition= queryResponse.getData().getColumns();
        this.rows=queryResponse.getData().getRows();
    }

    public List<String> getColumnDefinition() {
        return columnDefinition;
    }

    public void setColumnDefinition(List<String> columnDefinition) {
        this.columnDefinition = columnDefinition;
    }


}
