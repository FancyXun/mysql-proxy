package dao;

import io.vertx.core.buffer.Buffer;

public class QueryBuffer {
    public Buffer mysqlbuffer;
    public String queryId;
    public QueryBuffer(Buffer buffer,String queryId){
        this.mysqlbuffer=buffer;
        this.queryId=queryId;
    }
}
