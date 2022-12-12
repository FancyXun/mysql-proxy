import io.vertx.core.buffer.Buffer;

import java.nio.ByteBuffer;

public class BufferTest {

    public static void main(String[] args){
        Buffer buffer = Buffer.buffer();
        buffer.setByte(0,(byte) 1);
//        byte[] bytes ={1,0,0,1,21,46,0,0,2,3,100,101,102,0,0,0,24,97,117,116,111,95,105,110,99,114,101,109,101,110,116,95,105,110,99,114,101,109,101,110,116,0,12,0,0,0,0,0,0,0,0,0,0,0,0,42,0,0,3,3,100,101,102,0,0,0,20,99,104,97,114,97,99,116,101,114,95,115,101,116,95,99,108,105,101,110,116,0,12,0,0,0,0,0,0,0,0,0,0,0,0,46,0,0,4,3,100,101,102,0,0,0,24,99,104,97,114,97,99,116,101,114,95,115,101,116,95,99,111,110,110,101,99,116,105,111,110,0,12,0,0,0,0,0,0,0,0,0,0,0,0,43,0,0,5,3,100,101,102,0,0,0,21,99,104,97,114,97,99,116,101,114,95,115,101,116,95,114,101,115,117,108,116,115,0,12,0,0,0,0,0,0,0,0,0,0,0,0,42,0,0,6,3,100,101,102,0,0,0,20,99,104,97,114,97,99,116,101,114,95,115,101,116,95,115,101,114,118,101,114,0,12,0,0,0,0,0,0,0,0,0,0,0,0,38,0,0,7,3,100,101,102,0,0,0,16,99,111,108,108,97,116,105,111,110,95,115,101,114,118,101,114,0,12,0,0,0,0,0,0,0,0,0,0,0,0,42,0,0,8,3,100,101,102,0,0,0,20,99,111,108,108,97,116,105,111,110,95,99,111,110,110,101,99,116,105,111,110,0,12,0,0,0,0,0,0,0,0,0,0,0,0,34,0,0,9,3,100,101,102,0,0,0,12,105,110,105,116,95,99,111,110,110,101,99,116,0,12,0,0,0,0,0,0,0,0,0,0,0,0,41,0,0,10,3,100,101,102,0,0,0,19,105,110,116,101,114,97,99,116,105,118,101,95,116,105,109,101,111,117,116,0,12,0,0,0,0,0,0,0,0,0,0,0,0,29,0,0,11,3,100,101,102,0,0,0,7,108,105,99,101,110,115,101,0,12,0,0,0,0,0,0,0,0,0,0,0,0,44,0,0,12,3,100,101,102,0,0,0,22,108,111,119,101,114,95,99,97,115,101,95,116,97,98,108,101,95,110,97,109,101,115,0,12,0,0,0,0,0,0,0,0,0,0,0,0,40,0,0,13,3,100,101,102,0,0,0,18,109,97,120,95,97,108,108,111,119,101,100,95,112,97,99,107,101,116,0,12,0,0,0,0,0,0,0,0,0,0,0,0,39,0,0,14,3,100,101,102,0,0,0,17,110,101,116,95,98,117,102,102,101,114,95,108,101,110,103,116,104,0,12,0,0,0,0,0,0,0,0,0,0,0,0,39,0,0,15,3,100,101,102,0,0,0,17,110,101,116,95,119,114,105,116,101,95,116,105,109,101,111,117,116,0,12,0,0,0,0,0,0,0,0,0,0,0,0,38,0,0,16,3,100,101,102,0,0,0,16,113,117,101,114,121,95,99,97,99,104,101,95,115,105,122,101,0,12,0,0,0,0,0,0,0,0,0,0,0,0,38,0,0,17,3,100,101,102,0,0,0,16,113,117,101,114,121,95,99,97,99,104,101,95,116,121,112,101,0,12,0,0,0,0,0,0,0,0,0,0,0,0,30,0,0,18,3,100,101,102,0,0,0,8,115,113,108,95,109,111,100,101,0,12,0,0,0,0,0,0,0,0,0,0,0,0,38,0,0,19,3,100,101,102,0,0,0,16,115,121,115,116,101,109,95,116,105,109,101,95,122,111,110,101,0,12,0,0,0,0,0,0,0,0,0,0,0,0,31,0,0,20,3,100,101,102,0,0,0,9,116,105,109,101,95,122,111,110,101,0,12,0,0,0,0,0,0,0,0,0,0,0,0,43,0,0,21,3,100,101,102,0,0,0,21,116,114,97,110,115,97,99,116,105,111,110,95,105,115,111,108,97,116,105,111,110,0,12,0,0,0,0,0,0,0,0,0,0,0,0,34,0,0,22,3,100,101,102,0,0,0,12,119,97,105,116,95,116,105,109,101,111,117,116,0,12,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,23,-2,0,0,34,0,0,0,4,117,116,102,56,4,117,116,102,56,6,108,97,116,105,110,49,17,108,97,116,105,110,49,95,115,119,101,100,105,115,104,95,99,105,15,117,116,102,56,95,103,101,110,101,114,97,108,95,99,105,0,5,50,56,56,48,48,3,71,80,76,1,48,7,52,49,57,52,51,48,52,5,49,54,51,56,52,2,54,48,7,49,48,52,56,53,55,54,3,79,70,70,-119,79,78,76,89,95,70,85,76,76,95,71,82,79,};
        byte[] bytes = {46,97,97,97};
//        buffer.setBytes(0,bytes);
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        buffer.setBytes(0,bytes);
//        buffer.appendBytes(bytes,0,bytes.length);

        System.out.println(buffer.getBytes().length);
        for (byte b:buffer.getBytes()){
            System.out.print((b)+",");
        }
    }
}
