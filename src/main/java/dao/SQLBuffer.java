package dao;

import io.vertx.core.buffer.Buffer;

public class SQLBuffer {
    public Buffer buffer;
    public String queryId;
    public SQLBuffer(Buffer buffer, String queryId){
        this.buffer=buffer;
        this.queryId=queryId;
    }
}
