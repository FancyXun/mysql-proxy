package dao;

import protocol.ColumnDefinitionPacket;
import protocol.ResultsetRowPacket;

import java.util.ArrayList;
import java.util.List;

public class QueryResult {
    private List<String> columnDefinition;

    public List<List<byte[]>> getRowsRawBytes() {
        return rowsRawBytes;
    }

    public void setRowsRawBytes(List<List<byte[]>> rowsRawBytes) {
        this.rowsRawBytes = rowsRawBytes;
    }

    private List<List<byte[]>> rowsRawBytes;

    public List<List<String>> getRows() {
        return rows;
    }

    public void setRows(List<List<String>> rows) {
        this.rows = rows;
    }

    private List<List<String>> rows;



    public QueryResult(QueryResponse queryResponse) {
        this.columnDefinition = queryResponse.getData().getColumns();
        this.rows = queryResponse.getData().getRows();
    }

    public QueryResult(List<String> columnDefinition,List<List<byte[]>> rows){
        this.columnDefinition=columnDefinition;
        this.rowsRawBytes=rows;
    }

    public List<String> getColumnDefinition() {
        return columnDefinition;
    }

    public void setColumnDefinition(List<String> columnDefinition) {
        this.columnDefinition = columnDefinition;
    }




    public String getJson() {
        StringBuilder json = new StringBuilder();
        json.append("{\"columns\":");
        json.append(this.getColumnDefinition().toString()+",");
        json.append("\"rows\":");
        List<String> rowsList = new ArrayList<>();
        for (List<String> row:rows){
            rowsList.add(row.toString());
        }
        json.append(rowsList.toString());

        return json.toString();
    }


}
