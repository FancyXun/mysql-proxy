import protocol.ColumnDefinitionPacket;
import protocol.MysqlMessage;
import protocol.ResultsetRowPacket;

public class PacketTest {

    public static void main(String[] args){
//       testDefineColumnPackets();
        testResultPackets();

    }

    public static void testDefineColumnPackets() {
        byte[][] packets = {
                {46,0,0,2,3,100,101,102,0,0,0,24,97,117,116,111,95,105,110,99,114,101,109,101,110,116,95,105,110,99,114,101,109,101,110,116,0,12,63,0,21,0,0,0,8,-96,0,0,0,0},
                {42,0,0,3,3,100,101,102,0,0,0,20,99,104,97,114,97,99,116,101,114,95,115,101,116,95,99,108,105,101,110,116,0,12,33,0,12,0,0,0,-3,0,0,31,0,0},
                {46,0,0,4,3,100,101,102,0,0,0,24,99,104,97,114,97,99,116,101,114,95,115,101,116,95,99,111,110,110,101,99,116,105,111,110,0,12,33,0,12,0,0,0,-3,0,0,31,0,0},
                {43,0,0,5,3,100,101,102,0,0,0,21,99,104,97,114,97,99,116,101,114,95,115,101,116,95,114,101,115,117,108,116,115,0,12,33,0,12,0,0,0,-3,0,0,31,0,0},
                {44,0,0,12,3,100,101,102,0,0,0,22,108,111,119,101,114,95,99,97,115,101,95,116,97,98,108,101,95,110,97,109,101,115,0,12,63,0,21,0,0,0,8,-96,0,0,0,0},
                {}
        };
        for (byte[] packet:packets) {
            MysqlMessage mysqlMessage = new MysqlMessage(packet);
            ColumnDefinitionPacket columnDefinitionPacket = new ColumnDefinitionPacket(mysqlMessage);
            columnDefinitionPacket.read(packet);
            System.out.println(new String(columnDefinitionPacket.name));
        }
    }

    public static void testResultPackets() {
        byte[][]  packets ={
                {10,0,0,7,1,49,1,50,1,51,1,52,1,53,7,0,0,8,-2,0,0,2,0,0,0},
        };
        for (byte[] packet:packets){
            MysqlMessage mysqlMessage = new MysqlMessage(packet);
            ResultsetRowPacket resultsetRowPacket = new ResultsetRowPacket(mysqlMessage,5);
            resultsetRowPacket.read(packet);
            for (byte[] b:resultsetRowPacket.columnValues){
                System.out.println(new String(b));
            }

//            System.out.println(resultsetRowPacket.columnBytes);
        }

    }

}

