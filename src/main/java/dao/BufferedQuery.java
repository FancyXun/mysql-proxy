package dao;

import io.vertx.core.buffer.Buffer;

public class BufferedQuery {
    private Buffer buffer;

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    private String queryId;

    public BufferedQuery(Buffer buffer, String queryId) {
        this.buffer = buffer;
        this.queryId = queryId;
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }



}
