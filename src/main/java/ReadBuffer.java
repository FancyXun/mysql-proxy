import com.alibaba.fastjson.JSON;
//import com.sun.org.apache.xpath.internal.operations.String;
import dao.*;
import io.vertx.core.buffer.Buffer;

import protocol.QueryPacket;


import java.util.HashMap;
import java.util.Map;


public class ReadBuffer {
    private static String ENCRYPT_SQL_API ="http://localhost:8888/encrypt_sql2";

    public static SQLBuffer readFromBuffer(SQLInfo sqlInfo, Buffer buffer) {

        byte[] bytes = buffer.getBytes();
        SQLConvertedData sqlConvertedData=new SQLConvertedData();
        if (bytes.length > 6){
//            System.out.println("hhh");

            if (bytes[4]==3){

                QueryPacket queryPacket = new QueryPacket();

//                System.out.println("mysql client DATA:  " + bytes.length);
//                for (int i = 0 ;i <bytes.length; i++){
//                    System.out.print((bytes[i] & 0xFF) + " " );
//                }
//                System.out.println();

                // 复制报文数据的[2:
                // 报文分为消息头和消息体两部分，其中消息头占用固定的4个字节，消息体长度由消息头中的长度字段决定
                // 消息头的报文长度：用于标记当前请求消息的实际数据长度值，以字节为单位，占用3个字节，最大值为 0xFFFFFF，
                // 即接近 16 MB 大小（比16MB少1个字节）
                // a + b * 256 + c * 256 * 256

                // 判断是来自于mysql client还是jdbc， mysql client: 3 1 1 1 1  jdbc: 3 1 1
                if (bytes[5] == 0 ){ // mysql client
                    queryPacket.read1(bytes, 0);
                    String sql = new String(queryPacket.message);
                    sqlConvertedData=sqlConvert(sqlInfo,sql);
                    byte [] enc_bytes = sqlConvertedData.sql.getBytes();
                    buffer.setBytes(3 + 1 + 1 + 1 + 1, enc_bytes);
                    buffer.setByte(0, (byte) ((enc_bytes.length + 3) & 0xff) );
                    buffer.setByte(1, (byte) ((enc_bytes.length + 3) >>> 8) );
                    buffer.setByte(2, (byte) ((enc_bytes.length + 3) >>> 16) );

                }
                else { //jdbc
                    queryPacket.read1(bytes, 1);
                    String sql = new String(queryPacket.message);
                    sqlConvertedData=sqlConvert(sqlInfo,sql);
                    byte [] enc_bytes = sqlConvertedData.sql.getBytes();
                    buffer.setBytes(3 + 1 + 1 , enc_bytes);
                    buffer.setByte(0, (byte) ((enc_bytes.length+1) & 0xff) );
                    buffer.setByte(1, (byte) ((enc_bytes.length+1) >>> 8) );
                    buffer.setByte(2, (byte) ((enc_bytes.length+1) >>> 16) );
                }

//                byte [] tmp = buffer.getBytes();
//                System.out.println("ENC DATA:  " + tmp.length);
//                for (int i = 0 ;i <tmp.length; i++){
//                    System.out.print((tmp[i] & 0xFF) + " " );
//                }
//                System.out.println();

            }
        }

        return new SQLBuffer(buffer,sqlConvertedData.queryId);

    }

    public static SQLConvertedData sqlConvert(SQLInfo sqlInfo, String sql){
//        String db = "points";
        String db = "test";
        HashMap<String, String> headers = new HashMap<>(3);
        headers.put("content-type", "application/json");
//        String jsonStr = "{\"db\": " +"\"" + db +"\"" + ", \"sql\": "  +"\""+ sql  + "\""+ "}";
        SQLQueryRequest sqlQueryRequest = new SQLQueryRequest(sql,db);
        String jsonStr = JSON.toJSONString(sqlQueryRequest);

        // 发送post请求
        String new_sql = SQLConverter.sendPostWithJson(ENCRYPT_SQL_API, jsonStr,headers);
        System.out.println("sql encrypt response:"+new_sql);
//        Map mapTypes = JSON.parseObject(new_sql);
//        for (Object obj : mapTypes.keySet()){
//            System.out.println("key为："+obj+"值为："+mapTypes.get(obj));
//        }
        SQLResponse sqlResponse=JSON.parseObject(new_sql,SQLResponse.class);
//        new_sql = (String) mapTypes.get("encrypt_sql");
//        String table = (String) mapTypes.get("table");
//        String id = (String) mapTypes.get("id");
//        sqlInfo.setEnc_sql(new_sql);
//        sqlInfo.setTable(table);
//        sqlInfo.setSql(sql);
//        sqlInfo.setDatabase(db);
        // 并接收返回结果
        System.out.println("加密sql: " + sqlResponse.encryptSql + " table: "+ sqlResponse.table +  " id: " + sqlResponse.queryId) ;

        return new SQLConvertedData(sqlResponse.encryptSql,sqlResponse.queryId);
    }


}
