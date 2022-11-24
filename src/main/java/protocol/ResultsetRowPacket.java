package protocol;

import protocol.util.BufferUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <pre><b>resultset row packet.</b></pre>
 * @author 
 * <pre>seaboat</pre>
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * @version 1.0
 *  http://dev.mysql.com/doc/internals/en/com-query-response.html#text-resultset
 */
public class ResultsetRowPacket extends MysqlPacket {
	private static final byte NULL_MARK = (byte) 251;
	public int columnCount;
	public List<byte[]> columnValues;

	private MysqlMessage mm;

	public byte[] columnBytes;

	public ResultsetRowPacket() {

	}

	public ResultsetRowPacket(int columnCount) {
		this.columnCount = columnCount;
	}

	public ResultsetRowPacket(MysqlMessage mysqlMessage,int columnCount){

		this.mm=mysqlMessage;
		this.columnCount=columnCount;
		this.columnValues = new ArrayList<>();
	}

	@Override
	public void read(byte[] data) {
//		MysqlMessage mm = new MysqlMessage(data);
		packetLength = mm.readUB3();
		packetId = mm.read();
		byte[] v = {};
		for (int i = 0; i < columnCount; i++) {
			try {
				v= mm.readBytesWithLength();
				columnValues.add(v);
			}catch (Exception e){
				System.out.println("column: "+i+" got an error");
				System.out.println("mm position:" +mm.position());
				System.out.println(e.toString());
			}

		}
		columnBytes = mm.readBytes();
	}

	@Override
	public void write(ByteBuffer buffer) {
		BufferUtil.writeUB3(buffer, calcPacketSize());
		buffer.put(packetId);
		for (int i = 0; i < columnCount; i++) {
			byte[] fv = columnValues.get(i);
			if (fv == null) {
				buffer.put(NULL_MARK);
			} else {
				BufferUtil.writeLength(buffer, fv.length);
				buffer.put(fv);
			}
		}
	}

	@Override
	public int calcPacketSize() {
		int size = 0;
		for (int i = 0; i < columnCount; i++) {
			byte[] v = columnValues.get(i);
			size += (v == null || v.length == 0) ? 1 : BufferUtil.getLength(v);
		}
		return size;
	}

	@Override
	protected String getPacketInfo() {
		return "MySQL Resultset Row Packet";
	}

}
