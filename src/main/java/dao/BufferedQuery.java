package dao;

import io.vertx.core.buffer.Buffer;

public class BufferedQuery {
    private Buffer buffer;
    private String sql;

    public BufferedQuery(Buffer buffer, String sql) {
        this.buffer = buffer;
        this.sql = sql;
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }



    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }


}
