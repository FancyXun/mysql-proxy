import com.alibaba.fastjson.JSON;
import dao.QueryRequest;
import dao.QueryResponse;
import dao.QueryResult;
import io.vertx.core.buffer.Buffer;
import protocol.ColumnCountPacket;
import protocol.ColumnDefinitionPacket;
import protocol.MysqlMessage;
import protocol.ResultsetRowPacket;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WriteBuffer {

    private static String DECRYPT_API = "http://localhost:8888/decrypt_data";


    public static Buffer reWrite(Buffer buffer, String queryId) {
        byte[] bytes = buffer.getBytes();
//        printBytes("mysql response data:",bytes,false);
//        System.out.println("queryId:"+queryId);
        if(queryId==null){
            return buffer;
        }
        try {
//            System.out.println("rewriting result set");
            reWriteBuffer(buffer,queryId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return buffer;
        }
        return buffer;

    }

    public static void reWriteBuffer(Buffer buffer,String queryId) {
        byte[] bytes = buffer.getBytes();
        if (bytes[0]==1) {
//            printBytes("query data",bytes,false);
            QueryResult queryResult = readBytes(bytes);

            QueryResult newQueryResult= reWriteQueryResult(queryResult,queryId);

            ByteBuffer byteBuffer = writeBufferBytes(newQueryResult,buffer);
//            printBytes("rewrited bytes:",byteBuffer.array(),false);
            buffer.setBytes(0,byteBuffer.array());
        }

    }

    /**
     * the QueryResult will be sent to the sdk and get the decrypted results
     * @param queryResult
     */

    public static QueryResult reWriteQueryResult(QueryResult queryResult,String queryId){
        QueryRequest queryRequest = new QueryRequest(queryId,queryResult);
        String payload = JSON.toJSONString(queryRequest);
        HashMap<String, String> headers = new HashMap<>(3);
        headers.put("content-type", "application/json");
        String response = SQLConverter.sendPostWithJson(DECRYPT_API,payload,headers);
//        System.out.println(" decrypt data response:");
//        System.out.println(response);
        return parseData(response);
    }

    public static QueryResult parseData(String resp){
        QueryResponse queryResponse = JSON.parseObject(resp, QueryResponse.class);
//        System.out.println("queryId:"+queryResponse.getQueryId());
//        System.out.println("columns:"+queryResponse.getData().columns);
//        System.out.println("rows:"+queryResponse.getData().rows);
        return new QueryResult(queryResponse);
    }

    /**
     *  rewrite query result into mysql packet and put them into write buffer
     * @param queryResult data result
     * @param buffer input buffer
     * @return
     */

    public static ByteBuffer writeBufferBytes(QueryResult queryResult,Buffer buffer) {
        int columnCount = queryResult.getColumnDefinition().size();
        ColumnCountPacket countPacket = new ColumnCountPacket(columnCount,(byte)1);
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer.getBytes());
        countPacket.write(byteBuffer);
        int packetId =2;
        for (String s:queryResult.getColumnDefinition()){
            ColumnDefinitionPacket columnDefinitionPacket = new ColumnDefinitionPacket(s,(byte)packetId);
            packetId++;
            columnDefinitionPacket.write(byteBuffer);
        }
        for (List<String> row:queryResult.getRows()){
            ResultsetRowPacket resultsetRowPacket = new ResultsetRowPacket(columnCount,row,(byte)packetId);
            packetId++;
            resultsetRowPacket.write(byteBuffer);
        }
        byte[] endBytes = {7,0,0,7,-2,0,0,34,0,0,0};
        endBytes[3]=(byte)(columnCount+queryResult.getRows().size()+2);
        byteBuffer.put(endBytes);
        return byteBuffer;
    }

    public static void printBytes(String msg,byte[] data,boolean raw){
        System.out.println(msg+":"+data.length);
        for (int i = 0 ;i <data.length; i++){
            if(raw){
                System.out.print((data[i]) + "," );
            }else{
                System.out.print((data[i] & 0xFF) + "," );
            }
        }
        System.out.println();
    }

    /**
     * read data from mysql bytes and put into a java object
     * @param bytes mysql response raw bytes
     * @return formatted java object
     */
    public static QueryResult readBytes(byte[] bytes){
//        printBytes("query bytes",bytes,true);
        MysqlMessage mysqlMessage = new MysqlMessage(bytes);
        ColumnCountPacket columnCountPacket = new ColumnCountPacket(mysqlMessage);
        columnCountPacket.read(bytes);
        ColumnDefinitionPacket columnDefinitionPacket = new ColumnDefinitionPacket(mysqlMessage);
        List<String> columns = new ArrayList<>();
        List<List<String>> rows = new ArrayList<>();
        List<List<byte[]>> rowBytes = new ArrayList<>();
        for (int i = 0; i < columnCountPacket.columnCount; i++) {
            try {
                columnDefinitionPacket.read(bytes);
            } catch (Exception e) {
                System.out.println(" error data position:" + mysqlMessage.position());
                break;
            }
            columns.add(new String(columnDefinitionPacket.name));
        }

        /**
         * still by now we don't know how to separate the end bytes of a data packet,so it will continue
         * read bytes to the last one
         */
        try{
            for (; mysqlMessage.position() < mysqlMessage.length(); ) {
                ResultsetRowPacket resultsetRowPacket = new ResultsetRowPacket(mysqlMessage, columnCountPacket.columnCount);

                resultsetRowPacket.read(bytes);

                if (resultsetRowPacket.columnValues.size() == columnCountPacket.columnCount) {
                    rows.add(resultsetRowPacket.getColumnsString());
                    rowBytes.add(resultsetRowPacket.columnValues);
                }
            }
        }catch (Exception e){
            //todo: decide when to stop unpack mysql data of row packet
        }
        return new QueryResult(columns,rows);
    }

}
