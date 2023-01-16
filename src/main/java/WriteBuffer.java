import dao.QueryBuffer;
import dao.QueryResult;
import io.vertx.core.buffer.Buffer;
import protocol.ColumnCountPacket;
import protocol.ColumnDefinitionPacket;
import protocol.MysqlMessage;
import protocol.ResultsetRowPacket;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class WriteBuffer {
    public static Buffer readFromMysqlBuffer(Buffer buffer,String queryId) {
        try {
            rewriteBuffer(buffer,queryId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return buffer;
        }
        return buffer;

    }

    public static void rewriteBuffer(Buffer buffer,String queryId) {
        byte[] bytes = buffer.getBytes();
        System.out.println("MYSQL response DATA:  " + bytes.length);
        for (int i = 0 ;i <bytes.length; i++){
            System.out.print((bytes[i] & 0xFF) + " " );
        }
        System.out.println(bytes[0]);
        if (bytes[0]==1) {
            System.out.println("data packet:"+bytes.length);
//            System.out.println(bytes);
            for (int i = 0; i < bytes.length; i++) {
//                System.out.print((bytes[i] & 0xFF) + " " );
                System.out.print((bytes[i]) + ",");
            }
            System.out.println();

            MysqlMessage mysqlMessage = new MysqlMessage(bytes);
            ColumnCountPacket columnCountPacket = new ColumnCountPacket(mysqlMessage);
            columnCountPacket.read(bytes);
            ColumnDefinitionPacket columnDefinitionPacket = new ColumnDefinitionPacket(mysqlMessage);
            List<String> columns = new ArrayList<>();
            List<String> rows = new ArrayList<>();
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

            for (; mysqlMessage.position() < mysqlMessage.length(); ) {
                ResultsetRowPacket resultsetRowPacket = new ResultsetRowPacket(mysqlMessage, columnCountPacket.columnCount);

                resultsetRowPacket.read(bytes);

                if (resultsetRowPacket.columnValues.size() == columnCountPacket.columnCount) {
                    rows.add(resultsetRowPacket.toString());
                    rowBytes.add(resultsetRowPacket.columnValues);
                }
            }
            QueryResult queryResult = new QueryResult(columns,rowBytes);

            ByteBuffer byteBuffer = writeBufferBytes(queryResult,buffer);
            System.out.println("byte buffer len:"+byteBuffer.array().length);
            for (byte b:byteBuffer.array()){
                System.out.print((b)+",");
            }
            System.out.println();
            buffer.setBytes(0,byteBuffer.array());

//            buffer.appendBytes(byteBuffer.array(),0,byteBuffer.array().length);
            System.out.println("buffer len:"+buffer.getBytes().length);
            for (byte b:buffer.getBytes()){
                System.out.print((b)+",");
            }
            System.out.println();
        }

    }

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
        for (List<byte[]> row:queryResult.getRows()){
            ResultsetRowPacket resultsetRowPacket = new ResultsetRowPacket(columnCount,row,(byte)packetId);
            packetId++;
            resultsetRowPacket.write(byteBuffer);
        }
        byte[] endBytes = {7,0,0,7,-2,0,0,34,0,0,0};
        endBytes[3]=(byte)(columnCount+queryResult.getRows().size()+2);
        byteBuffer.put(endBytes);
        return byteBuffer;
    }


}
