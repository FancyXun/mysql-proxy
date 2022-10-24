package dao;

public class SQLInfo {

    private String sql;
    private String enc_sql;
    private String table;
    private String database;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getEnc_sql() {
        return enc_sql;
    }

    public void setEnc_sql(String enc_sql) {
        this.enc_sql = enc_sql;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
