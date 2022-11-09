import com.alibaba.fastjson.JSON;
import dao.SQLInfo;
import io.vertx.core.buffer.Buffer;
import protocol.QueryPacket;
import protocol.ResultsetRowPacket;
import protocol.util.HexUtil;

import java.util.HashMap;
import java.util.Map;


public class ReadBuffer {

    public static Buffer readFromBuffer(SQLInfo sqlInfo, Buffer buffer) {

        byte[] bytes = buffer.getBytes();



        if (bytes.length > 6){

            if (bytes[4]==3){

                QueryPacket queryPacket = new QueryPacket();

//                System.out.println("DATA:  " + bytes.length);
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
                    byte [] enc_bytes = sqlConvert(sqlInfo, sql);
                    buffer.setBytes(3 + 1 + 1 + 1 + 1, enc_bytes);
                    buffer.setByte(0, (byte) ((enc_bytes.length + 3) & 0xff) );
                    buffer.setByte(1, (byte) ((enc_bytes.length + 3) >>> 8) );
                    buffer.setByte(2, (byte) ((enc_bytes.length + 3) >>> 16) );

                }
                else { //jdbc
                    queryPacket.read1(bytes, 1);
                    String sql = new String(queryPacket.message);
                    byte [] enc_bytes = sqlConvert(sqlInfo, sql);
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



        return buffer;

    }

    public static byte[] sqlConvert(SQLInfo sqlInfo, String sql){
        String db = "dahua_yuanqu_test";
        HashMap<String, String> headers = new HashMap<>(3);
        String requestUrl = "http://localhost:8888/encrypt_sql1";
        String jsonStr = "{\"db\": " +"\"" + db +"\"" + ", \"sql\": "  +"\""+ sql  + "\""+ "}";
        headers.put("content-type", "application/json");
        // 发送post请求
        String new_sql = SQLConverter.sendPostWithJson(requestUrl, jsonStr,headers);

        Map mapTypes = JSON.parseObject(new_sql);
//        for (Object obj : mapTypes.keySet()){
//            System.out.println("key为："+obj+"值为："+mapTypes.get(obj));
//        }
        new_sql = (String) mapTypes.get("encrypt_sql");
        String table = (String) mapTypes.get("table");
        sqlInfo.setEnc_sql(new_sql);
        sqlInfo.setTable(table);
        sqlInfo.setSql(sql);
        sqlInfo.setDatabase(db);
        // 并接收返回结果
        System.out.println("加密sql: " + new_sql + " table: "+ table);

        return new_sql.getBytes();
    }

    public static Buffer readFromMysqlBuffer(Buffer buffer) {
        byte[] bytes = buffer.getBytes();
        System.out.println("MYSQL DATA:  " + bytes.length);
//        for (int i = 0 ;i <bytes.length; i++){
//            System.out.print((bytes[i] & 0xFF) + " " );
//        }
//        System.out.println();
        ResultsetRowPacket resultSetRow = new ResultsetRowPacket();
        resultSetRow.read(bytes);
        System.out.println(resultSetRow);
        System.out.println(resultSetRow.columnCount);
        return buffer;
    }


}
