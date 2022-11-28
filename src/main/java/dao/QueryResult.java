package dao;

import protocol.ColumnDefinitionPacket;
import protocol.ResultsetRowPacket;

import java.util.List;

public class QueryResult {

    public QueryResult(List<String> columnDefinition,List<byte[]> rows) {
        this.columnDefinition = columnDefinition;
        this.rows = rows;
    }

    public List<String> getColumnDefinition() {
        return columnDefinition;
    }

    public void setColumnDefinition(List<String> columnDefinition) {
        this.columnDefinition = columnDefinition;
    }

    private List<String> columnDefinition;

    public List<byte[]> getRows() {
        return rows;
    }

    public void setRows(List<byte[]> rows) {
        this.rows = rows;
    }

    private List<byte[]> rows;
}
