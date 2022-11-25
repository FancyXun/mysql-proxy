import protocol.ColumnCountPacket;
import protocol.ColumnDefinitionPacket;
import protocol.MysqlMessage;
import protocol.ResultsetRowPacket;

public class PacketTest {

    public static void main(String[] args){
//       testDefineColumnPackets();
//        testResultPackets();
        testPacket();
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

//    public static void testResultPackets() {
//        byte[][]  packets ={
//                {10,0,0,7,1,49,1,50,1,51,1,52,1,53,7,0,0,8,-2,0,0,2,0,0,0},
//        };
//        for (byte[] packet:packets){
//            MysqlMessage mysqlMessage = new MysqlMessage(packet);
//            ResultsetRowPacket resultsetRowPacket = new ResultsetRowPacket(mysqlMessage,5);
//            resultsetRowPacket.read(packet);
//            for (byte[] b:resultsetRowPacket.columnValues){
//                System.out.println(new String(b));
//            }
//
////            System.out.println(resultsetRowPacket.columnBytes);
//        }
//
//    }

    public  static void testPacket() {
        byte[][] packets = {
//                {1,0,0,1,2,44,0,0,2,3,100,101,102,6,116,101,115,116,100,98,6,116,101,115,116,95,48,6,116,101,115,116,95,48,2,105,100,2,105,100,12,63,0,11,0,0,0,3,3,80,0,0,0,48,0,0,3,3,100,101,102,6,116,101,115,116,100,98,6,116,101,115,116,95,48,6,116,101,115,116,95,48,4,110,97,109,101,4,110,97,109,101,12,8,0,0,1,0,0,-3,0,0,0,0,0,7,0,0,4,1,49,4,107,105,110,103,3,0,0,5,1,50,-5,3,0,0,6,1,51,-5,7,0,0,7,-2,0,0,34,0,0,0},
                {1,0,0,1,2,23,0,0,2,3,100,101,102,0,0,0,1,49,0,12,63,0,1,0,0,0,8,-127,0,0,0,0,23,0,0,3,3,100,101,102,0,0,0,1,120,0,12,45,0,4,0,0,0,-3,1,0,31,0,0,4,0,0,4,1,49,1,120,7,0,0,5,-2,0,0,2,0,0,0,}
        };
        MysqlMessage mm = new MysqlMessage(packets[0]);
        ColumnCountPacket cc = new ColumnCountPacket(mm);
        cc.read(packets[0]);
        System.out.println("columns:"+cc.columnCount);
        for (int i =0 ;i<cc.columnCount;i++){
            ColumnDefinitionPacket columnDefinitionPacket = new ColumnDefinitionPacket(mm);
            columnDefinitionPacket.read(packets[0]);
            System.out.println("cd[name]"+new String(columnDefinitionPacket.name));
            System.out.println("cd[table]"+new String(columnDefinitionPacket.table));
        }

            ResultsetRowPacket rr = new ResultsetRowPacket(mm,cc.columnCount);
            rr.read(packets[0]);

    }

}

