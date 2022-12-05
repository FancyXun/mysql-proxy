package dao;

import protocol.ColumnDefinitionPacket;
import protocol.ResultsetRowPacket;

import java.util.List;

public class QueryResult {
    private List<String> columnDefinition;

    private List<List<byte[]>> rows;



    public QueryResult(List<String> columnDefinition,List<List<byte[]>> rows) {
        this.columnDefinition = columnDefinition;
        this.rows = rows;
    }

    public List<String> getColumnDefinition() {
        return columnDefinition;
    }

    public void setColumnDefinition(List<String> columnDefinition) {
        this.columnDefinition = columnDefinition;
    }




    public List<List<byte[]>> getRows() {
        return rows;
    }

    public void setRows(List<List<byte[]>> rows) {
        this.rows = rows;
    }


}
