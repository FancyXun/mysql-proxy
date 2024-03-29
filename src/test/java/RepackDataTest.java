import dao.QueryResult;
import io.vertx.core.buffer.Buffer;
import protocol.ColumnCountPacket;
import protocol.ColumnDefinitionPacket;
import protocol.ResultsetRowPacket;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class RepackDataTest {
    public static void main(String[] args) {

//        byte b =-128;
//        byte[] arr ={1,2,3,4,5};
//        ByteBuffer buffer = ByteBuffer.wrap(arr);
//        System.out.println(buffer);
//        System.out.println(b&0xff);
//        for (int i =0;i<5;i++){
//            buffer.put(i,(byte)i);
//        }
//        for (byte t:buffer.array()){
//            System.out.print((t&0xff)+" ");
//        }
//        System.out.println();
        List<List<String>> rows = generateRowData(3,4);
        List<String> columnDefs = new ArrayList<>();
        columnDefs.add("col1");
        columnDefs.add("col2");
        columnDefs.add("col3");
        QueryResult queryResult = new QueryResult(columnDefs,rows);

        Buffer buffer1 = Buffer.buffer();
        buffer1.appendBytes(new byte[256]);

        ByteBuffer byteBuffer = writeBufferBytes(queryResult,buffer1);
        byte[] bytes = byteBuffer.array();
        for (int i=0;i<bytes.length;i++){
            System.out.print((bytes[i]&0xff)+",");
        }
        System.out.println();
    }


    public static ByteBuffer writeBufferBytes(QueryResult queryResult, Buffer buffer) {
        int columnCount = queryResult.getColumnDefinition().size();
        ColumnCountPacket countPacket = new ColumnCountPacket(columnCount,(byte)1);
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer.getBytes());
        countPacket.write(byteBuffer);
        int packetId =2;
        for (java.lang.String s:queryResult.getColumnDefinition()){
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

    public static List<List<byte[]>> generateRowBytesData(int col,int row){
        List<List<byte[]>> res = new ArrayList<>();
        for(int i=0;i<row;i++){
            List<byte[]> tempRow = new ArrayList<>();
            for(int j=0;j<col;j++){
                byte[] colx =new byte[]{(byte) (i*col+j+48)};
                tempRow.add(colx);
            }
            res.add(tempRow);
        }
        return res;
    }

    public static List<List<String>> generateRowData(int col,int row){
        List<List<String>> res = new ArrayList<>();
        for(int i=0;i<row;i++){
            List<String> tempRow = new ArrayList<>();
            for(int j=0;j<col;j++){
                String colx =i*col+j+48+"";
                tempRow.add(colx);
            }
            res.add(tempRow);
        }
        return res;
    }

}
